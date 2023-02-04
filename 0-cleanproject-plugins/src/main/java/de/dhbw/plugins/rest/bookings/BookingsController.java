package de.dhbw.plugins.rest.bookings;

import de.dhbw.cleanproject.adapter.booking.data.BookingData;
import de.dhbw.cleanproject.adapter.booking.data.BookingDataToBookingMapper;
import de.dhbw.cleanproject.adapter.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.cleanproject.application.UserApplicationService;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.plugins.mapper.booking.BookingsToBookingPreviewCollectionMapper;
import de.dhbw.plugins.rest.booking.BookingController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.javatuples.Triplet;
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
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerId}/bookings", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingsController {

    private final UserApplicationService userApplicationService;
    private final UserOperationService userOperationService;
    private final BookingDataToBookingMapper bookingDataToBookingMapper;
    private final BookingsToBookingPreviewCollectionMapper bookingsToBookingPreviewCollectionMapper;

    @GetMapping
    public ResponseEntity<BookingPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        Optional<FinancialLedger> optionalFinancialLedger = userOperationService.findFinancialLedgerByUserId(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = optionalFinancialLedger.get();

        BookingPreviewCollectionModel previewCollectionModel = bookingsToBookingPreviewCollectionMapper.apply(BookingsToBookingPreviewCollectionMapper
                .Context.builder()
                        .userId(userId)
                        .bookings(financialLedger.getBookings())
                .build());

        Link selfLink = linkTo(methodOn(this.getClass()).listAll(userId, financialLedgerId)).withSelfRel()
                .andAffordance(afford(methodOn(this.getClass()).create(userId, financialLedgerId, null)));
        previewCollectionModel.add(selfLink);

        return ResponseEntity.ok(previewCollectionModel);
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @Valid @RequestBody BookingData data) {
        Optional<User> optionalUser = userApplicationService.findById(userId);
        if (!optionalUser.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        User user = optionalUser.get();

        Booking booking = bookingDataToBookingMapper.apply(Triplet.with(data, financialLedgerId, user));
        Optional<Booking> optionalBooking = userOperationService.addBooking(userId, financialLedgerId, booking);
        if (!optionalBooking.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        booking = optionalBooking.get();
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(BookingController.class).findOne(userId, financialLedgerId, booking.getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
