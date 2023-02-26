package de.dhbw.plugins.rest.booking;

import de.dhbw.ems.adapter.application.booking.BookingApplicationAdapter;
import de.dhbw.ems.adapter.model.booking.data.BookingUnsafeDataToBookingAttributeDataAdapterMapper;
import de.dhbw.ems.adapter.model.booking.data.BookingUpdateData;
import de.dhbw.ems.adapter.model.booking.model.BookingModel;
import de.dhbw.ems.application.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.plugins.mapper.booking.BookingModelFactory;
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
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerAggregateId}/booking/{bookingAggregateId}", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingController {

    private final BookingApplicationAdapter bookingApplicationAdapter;
    private final BookingUnsafeDataToBookingAttributeDataAdapterMapper dataAdapterMapper;
    private final BookingModelFactory bookingModelFactory;

    @GetMapping
    public ResponseEntity<BookingModel> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId, @PathVariable("bookingAggregateId") UUID bookingAggregateId) {
        Optional<BookingAggregate> optionalBookingAggregate = bookingApplicationAdapter.find(userId, financialLedgerAggregateId, bookingAggregateId);
        if (!optionalBookingAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(bookingModelFactory.create(userId, financialLedgerAggregateId, optionalBookingAggregate.get()));
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId, @PathVariable("bookingAggregateId") UUID bookingAggregateId, @Valid @RequestBody BookingUpdateData data) {
        Optional<BookingAggregate> optionalBookingAggregate = bookingApplicationAdapter.find(userId, financialLedgerAggregateId, bookingAggregateId);
        if (!optionalBookingAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        BookingAggregate bookingAggregate = optionalBookingAggregate.get();
        BookingAggregateAttributeData attributeData = dataAdapterMapper.apply(data);
        optionalBookingAggregate = bookingApplicationAdapter.updateByAttributeData(bookingAggregate, attributeData);
        if (!optionalBookingAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = linkTo(methodOn(this.getClass()).findOne(userId, financialLedgerAggregateId, bookingAggregateId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId, @PathVariable("bookingAggregateId") UUID bookingAggregateId) {
        if (!bookingApplicationAdapter.delete(userId, financialLedgerAggregateId, bookingAggregateId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }



}
