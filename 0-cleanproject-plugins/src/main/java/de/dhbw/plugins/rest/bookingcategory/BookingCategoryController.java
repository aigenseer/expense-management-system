package de.dhbw.plugins.rest.bookingcategory;

import de.dhbw.cleanproject.adapter.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.bookingcategory.data.BookingCategoryData;
import de.dhbw.cleanproject.adapter.bookingcategory.data.BookingCategoryModel;
import de.dhbw.cleanproject.adapter.bookingcategory.data.BookingCategoryToBookingCategoryModelMapper;
import de.dhbw.cleanproject.adapter.bookingcategory.data.BookingCategoryUpdateDataToBookingCategoryMapper;
import de.dhbw.cleanproject.application.BookingCategoryApplicationService;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.plugins.mapper.booking.BookingsToBookingPreviewCollectionMapper;
import de.dhbw.plugins.rest.bookings.BookingsController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerId}/category/{bookingCategoryId}", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingCategoryController {

    private final UserOperationService userOperationService;
    private final BookingCategoryApplicationService bookingCategoryApplicationService;
    private final BookingCategoryToBookingCategoryModelMapper bookingCategoryToBookingCategoryModelMapper;
    private final BookingCategoryUpdateDataToBookingCategoryMapper bookingCategoryUpdateDataToBookingCategoryMapper;
    private final BookingsToBookingPreviewCollectionMapper bookingsToBookingPreviewCollectionMapper;

    @GetMapping
    public ResponseEntity<BookingCategoryModel> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingCategoryId") UUID bookingCategoryId) {
        Optional<BookingCategory> optionalBookingCategory = userOperationService.getBookingCategory(userId, financialLedgerId, bookingCategoryId);
        if (!optionalBookingCategory.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        BookingCategory bookingCategory = optionalBookingCategory.get();

        BookingPreviewCollectionModel previewCollectionModel = bookingsToBookingPreviewCollectionMapper.apply(BookingsToBookingPreviewCollectionMapper
                .Context.builder()
                .userId(userId)
                .bookings(bookingCategory.getBookings())
                .build());
        Link selfLink = linkTo(methodOn(BookingsController.class).listAll(userId, financialLedgerId)).withSelfRel();
        previewCollectionModel.add(selfLink);

        BookingCategoryModel model = bookingCategoryToBookingCategoryModelMapper.apply(Pair.with(bookingCategory, previewCollectionModel));

        selfLink = linkTo(methodOn(getClass()).findOne(userId, financialLedgerId, bookingCategoryId)).withSelfRel()
                .andAffordance(afford(methodOn(getClass()).update(userId, financialLedgerId, bookingCategoryId, null)))
                .andAffordance(afford(methodOn(getClass()).delete(userId, financialLedgerId, bookingCategoryId)));
        model.add(selfLink);

        return ResponseEntity.ok(model);
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingCategoryId") UUID bookingCategoryId, @Valid @RequestBody BookingCategoryData data) {
        Optional<BookingCategory> optionalBookingCategory = userOperationService.getBookingCategory(userId, financialLedgerId, bookingCategoryId);
        if (!optionalBookingCategory.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        BookingCategory bookingCategory = optionalBookingCategory.get();

        bookingCategory = bookingCategoryUpdateDataToBookingCategoryMapper.apply(Pair.with(bookingCategory, data));
        bookingCategoryApplicationService.save(bookingCategory);
        WebMvcLinkBuilder uriComponents = linkTo(methodOn(this.getClass()).findOne(userId, financialLedgerId, bookingCategoryId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingCategoryId") UUID bookingCategoryId) {
        if (!userOperationService.existsBookingCategoryById(userId, financialLedgerId, bookingCategoryId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        userOperationService.deleteBookingCategoryById(userId, financialLedgerId, bookingCategoryId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }



}
