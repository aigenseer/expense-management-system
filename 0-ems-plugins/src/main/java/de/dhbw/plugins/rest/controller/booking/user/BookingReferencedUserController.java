package de.dhbw.plugins.rest.controller.booking.user;

import de.dhbw.ems.adapter.application.booking.BookingApplicationAdapter;
import de.dhbw.ems.adapter.model.user.preview.UserPreview;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.mapper.booking.ReferencedUserPreviewModelFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerAggregateId}/booking/{bookingAggregateId}/user/{referencedUserId}", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingReferencedUserController {

    private final BookingApplicationAdapter bookingApplicationAdapter;
    private final ReferencedUserPreviewModelFactory referencedUserPreviewModelFactory;

    @GetMapping
    public ResponseEntity<UserPreview> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId, @PathVariable("bookingAggregateId") UUID bookingAggregateId, @PathVariable("referencedUserId") UUID referencedUserId) {
        Optional<BookingAggregate> optionalBookingAggregate = bookingApplicationAdapter.find(userId, financialLedgerAggregateId, bookingAggregateId);
        if (!optionalBookingAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        BookingAggregate bookingAggregate = optionalBookingAggregate.get();
        Optional<User> optionalReferencedUser = bookingAggregate.getReferencedUsers().stream().filter(user -> user.getId().equals(referencedUserId)).findFirst();
        if (!optionalReferencedUser.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(referencedUserPreviewModelFactory.create(userId, financialLedgerAggregateId, bookingAggregateId, optionalReferencedUser.get()));
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId, @PathVariable("bookingAggregateId") UUID bookingAggregateId, @PathVariable("referencedUserId") UUID referencedUserId) {
        if (!bookingApplicationAdapter.exists(userId, financialLedgerAggregateId, bookingAggregateId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if (!bookingApplicationAdapter.deleteUserReference(referencedUserId, financialLedgerAggregateId, bookingAggregateId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }



}
