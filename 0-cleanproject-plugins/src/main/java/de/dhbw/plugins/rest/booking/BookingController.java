package de.dhbw.plugins.rest.booking;

import de.dhbw.cleanproject.adapter.booking.data.BookingUpdateData;
import de.dhbw.cleanproject.adapter.booking.data.BookingUpdateDataToBookingMapper;
import de.dhbw.cleanproject.adapter.booking.model.BookingModel;
import de.dhbw.cleanproject.adapter.booking.model.BookingToBookingModelMapper;
import de.dhbw.cleanproject.adapter.bookingcategory.preview.BookingCategoryPreviewModel;
import de.dhbw.cleanproject.adapter.bookingcategory.preview.BookingCategoryToBookingCategoryPreviewModelMapper;
import de.dhbw.cleanproject.adapter.user.preview.UserPreview;
import de.dhbw.cleanproject.adapter.user.preview.UserPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.user.preview.UserToUserPreviewModelMapper;
import de.dhbw.cleanproject.application.BookingApplicationService;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.plugins.rest.booking.user.BookingReferencedUserController;
import de.dhbw.plugins.rest.booking.users.BookingReferencedUsersController;
import de.dhbw.plugins.rest.bookingcategory.BookingCategoryController;
import de.dhbw.plugins.rest.user.UserController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerId}/booking/{bookingId}", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingController {

    private final UserOperationService userOperationService;
    private final UserToUserPreviewModelMapper userToUserPreviewModelMapper;
    private final BookingToBookingModelMapper bookingToBookingModelMapper;
    private final BookingCategoryToBookingCategoryPreviewModelMapper bookingCategoryToBookingCategoryPreviewModelMapper;
    private final BookingUpdateDataToBookingMapper bookingUpdateDataToBookingMapper;
    private final BookingApplicationService bookingApplicationService;


    @GetMapping
    public ResponseEntity<BookingModel> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId) {
        Optional<Booking> optionalBooking = userOperationService.getBooking(userId, financialLedgerId, bookingId);
        if (!optionalBooking.isPresent()) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Booking booking = optionalBooking.get();

        List<UserPreview> referencedUsers = booking.getReferencedUsers().stream()
                .map(userToUserPreviewModelMapper)
                .collect(Collectors.toList());
        UserPreviewCollectionModel referencedUserPreviewCollectionModel = new UserPreviewCollectionModel(referencedUsers);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(BookingReferencedUsersController.class).listAll(userId, financialLedgerId, bookingId)).withSelfRel();
        referencedUserPreviewCollectionModel.add(selfLink);

        BookingCategory category = booking.getCategory();
        BookingCategoryPreviewModel bookingCategoryPreviewModel = null;
        if(category != null){
            bookingCategoryPreviewModel = bookingCategoryToBookingCategoryPreviewModelMapper.apply(category);
            selfLink = linkTo(methodOn(BookingCategoryController.class).findOne(userId, financialLedgerId, category.getId())).withSelfRel();
            bookingCategoryPreviewModel.add(selfLink);
        }
        BookingModel model = bookingToBookingModelMapper.apply(Triplet.with(booking, referencedUserPreviewCollectionModel, bookingCategoryPreviewModel));

        selfLink = linkTo(methodOn(getClass()).findOne(userId, financialLedgerId, bookingId)).withSelfRel()
                .andAffordance(afford(methodOn(getClass()).update(userId, financialLedgerId, bookingId, null)))
                .andAffordance(afford(methodOn(getClass()).delete(userId, financialLedgerId, bookingId)));
        model.add(selfLink);

        return ResponseEntity.ok(model);
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId, @Valid @RequestBody BookingUpdateData data) {
        Optional<Booking> optionalBooking = userOperationService.getBooking(userId, financialLedgerId, bookingId);
        if (!optionalBooking.isPresent()) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Booking booking = optionalBooking.get();
        booking = bookingUpdateDataToBookingMapper.apply(Pair.with(booking, data));
        bookingApplicationService.save(booking);
        WebMvcLinkBuilder uriComponents = linkTo(methodOn(this.getClass()).findOne(userId, financialLedgerId, bookingId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId) {
        if (!userOperationService.deleteBookingById(userId, financialLedgerId, bookingId)) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }



}
