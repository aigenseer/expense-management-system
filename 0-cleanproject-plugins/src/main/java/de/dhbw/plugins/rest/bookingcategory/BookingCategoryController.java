package de.dhbw.plugins.rest.bookingcategory;

import de.dhbw.cleanproject.adapter.model.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.model.bookingcategory.data.BookingCategoryData;
import de.dhbw.cleanproject.adapter.model.bookingcategory.data.BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper;
import de.dhbw.cleanproject.adapter.model.bookingcategory.data.BookingCategoryModel;
import de.dhbw.cleanproject.adapter.model.bookingcategory.data.BookingCategoryToBookingCategoryModelAdapterMapper;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.application.bookingcategory.BookingCategoryApplicationService;
import de.dhbw.cleanproject.application.bookingcategory.BookingCategoryAttributeData;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.plugins.mapper.booking.BookingsToBookingPreviewCollectionMapper;
import de.dhbw.plugins.rest.bookings.BookingsController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
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
    private final BookingCategoryToBookingCategoryModelAdapterMapper bookingCategoryToBookingCategoryModelAdapterMapper;
    private final BookingsToBookingPreviewCollectionMapper bookingsToBookingPreviewCollectionMapper;
    private final BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper bookingCategoryDataToBookingCategoryAttributeDataAdapterMapper;

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

        BookingCategoryModel model = bookingCategoryToBookingCategoryModelAdapterMapper.apply(bookingCategory);
        model.setBookingPreviewCollectionModel(previewCollectionModel);

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

        BookingCategoryAttributeData attributeData = bookingCategoryDataToBookingCategoryAttributeDataAdapterMapper.apply(data);
        optionalBookingCategory = bookingCategoryApplicationService.updateByAttributeData(bookingCategory, attributeData);
        if (!optionalBookingCategory.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

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
