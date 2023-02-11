package de.dhbw.plugins.rest.booking;

import de.dhbw.ems.adapter.application.booking.BookingApplicationAdapter;
import de.dhbw.ems.adapter.model.booking.data.BookingUnsafeDataToBookingAttributeDataAdapterMapper;
import de.dhbw.ems.adapter.model.booking.data.BookingUpdateData;
import de.dhbw.ems.adapter.model.booking.model.BookingModel;
import de.dhbw.ems.application.booking.BookingAttributeData;
import de.dhbw.ems.domain.booking.Booking;
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
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerId}/booking/{bookingId}", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingController {

    private final BookingApplicationAdapter bookingApplicationAdapter;
    private final BookingUnsafeDataToBookingAttributeDataAdapterMapper dataAdapterMapper;
    private final BookingModelFactory bookingModelFactory;

    @GetMapping
    public ResponseEntity<BookingModel> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId) {
        Optional<Booking> optionalBooking = bookingApplicationAdapter.find(userId, financialLedgerId, bookingId);
        if (!optionalBooking.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(bookingModelFactory.create(userId, financialLedgerId, optionalBooking.get()));
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId, @Valid @RequestBody BookingUpdateData data) {
        Optional<Booking> optionalBooking = bookingApplicationAdapter.find(userId, financialLedgerId, bookingId);
        if (!optionalBooking.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Booking booking = optionalBooking.get();
        BookingAttributeData attributeData = dataAdapterMapper.apply(data);
        optionalBooking = bookingApplicationAdapter.updateByAttributeData(booking, attributeData);
        if (!optionalBooking.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = linkTo(methodOn(this.getClass()).findOne(userId, financialLedgerId, bookingId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId) {
        if (!bookingApplicationAdapter.delete(userId, financialLedgerId, bookingId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }



}
