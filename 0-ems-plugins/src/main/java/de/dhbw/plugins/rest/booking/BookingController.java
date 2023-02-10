package de.dhbw.plugins.rest.booking;

import de.dhbw.cleanproject.adapter.model.booking.data.BookingUnsafeDataToBookingAttributeDataAdapterMapper;
import de.dhbw.cleanproject.adapter.model.booking.data.BookingUpdateData;
import de.dhbw.cleanproject.adapter.model.booking.model.BookingModel;
import de.dhbw.cleanproject.application.booking.BookingAttributeData;
import de.dhbw.cleanproject.application.booking.BookingDomainService;
import de.dhbw.cleanproject.application.mediator.service.impl.BookingService;
import de.dhbw.cleanproject.domain.booking.Booking;
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

    private final BookingService bookingService;
    private final BookingDomainService bookingDomainService;
    private final BookingUnsafeDataToBookingAttributeDataAdapterMapper dataAdapterMapper;
    private final BookingModelFactory bookingModelFactory;

    @GetMapping
    public ResponseEntity<BookingModel> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId) {
        Optional<Booking> optionalBooking = bookingService.find(userId, financialLedgerId, bookingId);
        if (!optionalBooking.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(bookingModelFactory.create(userId, financialLedgerId, optionalBooking.get()));
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId, @Valid @RequestBody BookingUpdateData data) {
        Optional<Booking> optionalBooking = bookingService.find(userId, financialLedgerId, bookingId);
        if (!optionalBooking.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Booking booking = optionalBooking.get();
        BookingAttributeData attributeData = dataAdapterMapper.apply(data);
        optionalBooking = bookingDomainService.updateByAttributeData(booking, attributeData);
        if (!optionalBooking.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = linkTo(methodOn(this.getClass()).findOne(userId, financialLedgerId, bookingId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId) {
        if (!bookingService.delete(userId, financialLedgerId, bookingId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }



}
