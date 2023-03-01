package de.dhbw.plugins.rest.controller.bookings;

import de.dhbw.ems.adapter.application.booking.BookingApplicationAdapter;
import de.dhbw.ems.adapter.application.financialledger.FinancialLedgerApplicationAdapter;
import de.dhbw.ems.adapter.model.booking.data.BookingData;
import de.dhbw.ems.adapter.model.booking.data.BookingUnsafeDataToBookingAttributeDataAdapterMapper;
import de.dhbw.ems.adapter.model.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.ems.application.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.plugins.mapper.booking.factory.BookingPreviewCollectionModelFactory;
import de.dhbw.plugins.rest.controller.booking.BookingController;
import de.dhbw.plugins.rest.controller.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerAggregateId}/bookings", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingsController {

    private final FinancialLedgerApplicationAdapter financialLedgerApplicationAdapter;
    private final BookingApplicationAdapter bookingApplicationAdapter;
    private final BookingUnsafeDataToBookingAttributeDataAdapterMapper dataAdapterMapper;
    private final BookingPreviewCollectionModelFactory bookingPreviewCollectionModelFactory;

    @GetMapping
    public ResponseEntity<BookingPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId) {
        Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = financialLedgerApplicationAdapter.find(userId, financialLedgerAggregateId);
        if (!optionalFinancialLedgerAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(bookingPreviewCollectionModelFactory.create(userId, financialLedgerAggregateId, optionalFinancialLedgerAggregate.get().getBookingAggregates()));
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId, @Valid @RequestBody BookingData data) {
        BookingAggregateAttributeData attributeData = dataAdapterMapper.apply(data);
        Optional<BookingAggregate> optionalBookingAggregate = bookingApplicationAdapter.create(userId, financialLedgerAggregateId, attributeData);
        if (!optionalBookingAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(BookingController.class).findOne(userId, financialLedgerAggregateId,  optionalBookingAggregate.get().getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
