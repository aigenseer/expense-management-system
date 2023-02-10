package de.dhbw.plugins.rest.bookings;

import de.dhbw.cleanproject.adapter.model.booking.data.BookingData;
import de.dhbw.cleanproject.adapter.model.booking.data.BookingUnsafeDataToBookingAttributeDataAdapterMapper;
import de.dhbw.cleanproject.adapter.model.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.cleanproject.application.booking.BookingAttributeData;
import de.dhbw.cleanproject.application.mediator.service.impl.BookingService;
import de.dhbw.cleanproject.application.mediator.service.impl.FinancialLedgerService;
import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.plugins.mapper.booking.BookingPreviewCollectionModelFactory;
import de.dhbw.plugins.rest.booking.BookingController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
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

    private final FinancialLedgerService financialLedgerService;
    private final BookingService bookingService;
    private final BookingUnsafeDataToBookingAttributeDataAdapterMapper dataAdapterMapper;
    private final BookingPreviewCollectionModelFactory bookingPreviewCollectionModelFactory;

    @GetMapping
    public ResponseEntity<BookingPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerService.find(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(bookingPreviewCollectionModelFactory.create(userId, financialLedgerId, optionalFinancialLedger.get().getBookings()));
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @Valid @RequestBody BookingData data) {
        BookingAttributeData attributeData = dataAdapterMapper.apply(data);
        Optional<Booking> optionalBooking = bookingService.create(userId, financialLedgerId, attributeData);
        if (!optionalBooking.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(BookingController.class).findOne(userId, financialLedgerId,  optionalBooking.get().getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
