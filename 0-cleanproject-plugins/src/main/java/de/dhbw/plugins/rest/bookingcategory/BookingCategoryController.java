package de.dhbw.plugins.rest.bookingcategory;

import de.dhbw.cleanproject.adapter.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.booking.preview.BookingPreviewModel;
import de.dhbw.cleanproject.adapter.booking.preview.BookingToBookingPreviewModelMapper;
import de.dhbw.cleanproject.adapter.bookingcategory.data.BookingCategoryData;
import de.dhbw.cleanproject.adapter.bookingcategory.data.BookingCategoryModel;
import de.dhbw.cleanproject.adapter.bookingcategory.data.BookingCategoryToBookingCategoryModelMapper;
import de.dhbw.cleanproject.adapter.bookingcategory.data.BookingCategoryUpdateDataToBookingCategoryMapper;
import de.dhbw.cleanproject.application.BookingCategoryApplicationService;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
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
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerId}/category/{bookingCategoryId}", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingCategoryController {

    private final UserOperationService userOperationService;
    private final BookingCategoryApplicationService bookingCategoryApplicationService;
    private final BookingToBookingPreviewModelMapper previewModelMapper;
    private final BookingCategoryToBookingCategoryModelMapper bookingCategoryToBookingCategoryModelMapper;
    private final BookingCategoryUpdateDataToBookingCategoryMapper bookingCategoryUpdateDataToBookingCategoryMapper;


    @GetMapping
    public ResponseEntity<BookingCategoryModel> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingCategoryId") UUID bookingCategoryId) {
        Optional<BookingCategory> optionalBookingCategory = userOperationService.getBookingCategory(userId, financialLedgerId, bookingCategoryId);
        if (!optionalBookingCategory.isPresent()) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        BookingCategory bookingCategory = optionalBookingCategory.get();

        List<BookingPreviewModel> previewModels = bookingCategory.getBookings().stream()
                .map(booking -> {
                    BookingPreviewModel preview = previewModelMapper.apply(booking);
//                    Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(BookingController.class).findOne(userId, financialLedgerId, booking.getId())).withSelfRel();
//                    preview.add(selfLink);
                    return preview;
                })
                .collect(Collectors.toList());
        BookingPreviewCollectionModel previewCollectionModel = new BookingPreviewCollectionModel(previewModels);

        BookingCategoryModel model = bookingCategoryToBookingCategoryModelMapper.apply(Pair.with(bookingCategory, previewCollectionModel));

        Link selfLink = linkTo(methodOn(getClass()).findOne(userId, financialLedgerId, bookingCategoryId)).withSelfRel()
                .andAffordance(afford(methodOn(getClass()).update(userId, financialLedgerId, bookingCategoryId, null)))
                .andAffordance(afford(methodOn(getClass()).delete(userId, financialLedgerId, bookingCategoryId)));
        model.add(selfLink);

        return ResponseEntity.ok(model);
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingCategoryId") UUID bookingCategoryId, @Valid @RequestBody BookingCategoryData data) {
        Optional<BookingCategory> optionalBookingCategory = userOperationService.getBookingCategory(userId, financialLedgerId, bookingCategoryId);
        if (!optionalBookingCategory.isPresent()) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        BookingCategory bookingCategory = optionalBookingCategory.get();

        bookingCategory = bookingCategoryUpdateDataToBookingCategoryMapper.apply(Pair.with(bookingCategory, data));
        bookingCategoryApplicationService.save(bookingCategory);
        WebMvcLinkBuilder uriComponents = linkTo(methodOn(this.getClass()).findOne(userId, financialLedgerId, bookingCategoryId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingCategoryId") UUID bookingCategoryId) {
        return null;
    }



}
