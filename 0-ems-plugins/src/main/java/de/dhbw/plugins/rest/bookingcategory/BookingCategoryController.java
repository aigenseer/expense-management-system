package de.dhbw.plugins.rest.bookingcategory;

import de.dhbw.ems.adapter.application.bookingcategory.BookingCategoryApplicationAdapter;
import de.dhbw.ems.adapter.model.bookingcategory.data.BookingCategoryData;
import de.dhbw.ems.adapter.model.bookingcategory.data.BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper;
import de.dhbw.ems.adapter.model.bookingcategory.data.BookingCategoryModel;
import de.dhbw.ems.application.bookingcategory.BookingCategoryAttributeData;
import de.dhbw.ems.application.bookingcategory.BookingCategoryDomainService;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import de.dhbw.plugins.mapper.bookingcategory.BookingCategoryModelFactory;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerId}/category/{bookingCategoryId}", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingCategoryController {

    private final BookingCategoryApplicationAdapter bookingCategoryApplicationAdapter;
    private final BookingCategoryDomainService bookingCategoryDomainService;
    private final BookingCategoryModelFactory bookingCategoryModelFactory;

    private final BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper bookingCategoryDataToBookingCategoryAttributeDataAdapterMapper;

    @GetMapping
    public ResponseEntity<BookingCategoryModel> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingCategoryId") UUID bookingCategoryId) {
        Optional<BookingCategory> optionalBookingCategory = bookingCategoryApplicationAdapter.find(userId, financialLedgerId, bookingCategoryId);
        if (!optionalBookingCategory.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(bookingCategoryModelFactory.create(userId, financialLedgerId, optionalBookingCategory.get()));
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingCategoryId") UUID bookingCategoryId, @Valid @RequestBody BookingCategoryData data) {
        Optional<BookingCategory> optionalBookingCategory = bookingCategoryApplicationAdapter.find(userId, financialLedgerId, bookingCategoryId);
        if (!optionalBookingCategory.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        BookingCategory bookingCategory = optionalBookingCategory.get();

        BookingCategoryAttributeData attributeData = bookingCategoryDataToBookingCategoryAttributeDataAdapterMapper.apply(data);
        optionalBookingCategory = bookingCategoryDomainService.updateByAttributeData(bookingCategory, attributeData);
        if (!optionalBookingCategory.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        WebMvcLinkBuilder uriComponents = linkTo(methodOn(this.getClass()).findOne(userId, financialLedgerId, bookingCategoryId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingCategoryId") UUID bookingCategoryId) {
        if (!bookingCategoryApplicationAdapter.exists(userId, financialLedgerId, bookingCategoryId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        bookingCategoryApplicationAdapter.delete(userId, financialLedgerId, bookingCategoryId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }



}
