package de.dhbw.plugins.rest.controller.bookings;

import de.dhbw.ems.adapter.application.booking.BookingApplicationAdapter;
import de.dhbw.ems.adapter.application.financialledger.FinancialLedgerApplicationAdapter;
import de.dhbw.ems.adapter.mapper.data.booking.BookingUnsafeDataToBookingAttributeDataAdapterMapper;
import de.dhbw.ems.application.domain.service.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import de.dhbw.plugins.rest.controller.booking.BookingController;
import de.dhbw.plugins.rest.controller.booking.data.BookingData;
import de.dhbw.plugins.rest.controller.utils.WebMvcLinkBuilderUtils;
import de.dhbw.plugins.rest.mapper.controller.factory.booking.BookingPreviewCollectionModelFactory;
import de.dhbw.plugins.rest.mapper.model.booking.preview.BookingPreviewCollectionModel;
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
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerId}/bookings", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingsController {

    private final FinancialLedgerApplicationAdapter financialLedgerApplicationAdapter;
    private final BookingApplicationAdapter bookingApplicationAdapter;
    private final BookingUnsafeDataToBookingAttributeDataAdapterMapper dataAdapterMapper;
    private final BookingPreviewCollectionModelFactory bookingPreviewCollectionModelFactory;

    @GetMapping
    public ResponseEntity<BookingPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationAdapter.find(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(bookingPreviewCollectionModelFactory.create(userId, financialLedgerId, optionalFinancialLedger.get().getBookingAggregates()));
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @Valid @RequestBody BookingData data) {
        BookingAggregateAttributeData attributeData = dataAdapterMapper.apply(data);
        Optional<BookingAggregate> optionalBookingAggregate = bookingApplicationAdapter.create(userId, financialLedgerId, attributeData);
        if (!optionalBookingAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(BookingController.class).findOne(userId, financialLedgerId,  optionalBookingAggregate.get().getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
