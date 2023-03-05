package de.dhbw.plugins.rest.controller.bookingcategory;

import de.dhbw.ems.adapter.application.bookingcategory.BookingCategoryApplicationAdapter;
import de.dhbw.plugins.rest.controller.bookingcategory.data.BookingCategoryData;
import de.dhbw.ems.adapter.mapper.data.bookingcategory.BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper;
import de.dhbw.plugins.rest.mapper.model.bookingcategory.model.BookingCategoryModel;
import de.dhbw.ems.application.domain.service.bookingcategory.entity.BookingCategoryAttributeData;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.plugins.rest.mapper.controller.factory.bookingcategory.BookingCategoryModelFactory;
import de.dhbw.plugins.rest.controller.utils.WebMvcLinkBuilderUtils;
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
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerAggregateId}/category/{bookingCategoryAggregateId}", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingCategoryController {

    private final BookingCategoryApplicationAdapter bookingCategoryApplicationAdapter;
    private final BookingCategoryModelFactory bookingCategoryModelFactory;

    private final BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper bookingCategoryDataToBookingCategoryAttributeDataAdapterMapper;

    @GetMapping
    public ResponseEntity<BookingCategoryModel> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId, @PathVariable("bookingCategoryAggregateId") UUID bookingCategoryAggregateId) {
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = bookingCategoryApplicationAdapter.find(userId, financialLedgerAggregateId, bookingCategoryAggregateId);
        if (!optionalBookingCategoryAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(bookingCategoryModelFactory.create(userId, financialLedgerAggregateId, optionalBookingCategoryAggregate.get()));
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId, @PathVariable("bookingCategoryAggregateId") UUID bookingCategoryAggregateId, @Valid @RequestBody BookingCategoryData data) {
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = bookingCategoryApplicationAdapter.find(userId, financialLedgerAggregateId, bookingCategoryAggregateId);
        if (!optionalBookingCategoryAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        BookingCategoryAggregate bookingCategoryAggregate = optionalBookingCategoryAggregate.get();

        BookingCategoryAttributeData attributeData = bookingCategoryDataToBookingCategoryAttributeDataAdapterMapper.apply(data);
        optionalBookingCategoryAggregate = bookingCategoryApplicationAdapter.updateByAttributeData(bookingCategoryAggregate, attributeData);
        if (!optionalBookingCategoryAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        WebMvcLinkBuilder uriComponents = linkTo(methodOn(this.getClass()).findOne(userId, financialLedgerAggregateId, bookingCategoryAggregateId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId, @PathVariable("bookingCategoryAggregateId") UUID bookingCategoryAggregateId) {
        if (!bookingCategoryApplicationAdapter.exists(userId, financialLedgerAggregateId, bookingCategoryAggregateId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        bookingCategoryApplicationAdapter.delete(userId, financialLedgerAggregateId, bookingCategoryAggregateId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }



}
