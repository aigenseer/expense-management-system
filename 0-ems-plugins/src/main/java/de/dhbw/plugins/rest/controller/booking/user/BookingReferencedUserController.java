package de.dhbw.plugins.rest.controller.booking.user;

import de.dhbw.ems.adapter.application.booking.BookingApplicationAdapter;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.rest.mapper.controller.factory.booking.ReferencedUserPreviewModelFactory;
import de.dhbw.plugins.rest.mapper.model.user.preview.UserPreview;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerId}/booking/{bookingAggregateId}/user/{referencedUserId}", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingReferencedUserController {

    private final BookingApplicationAdapter bookingApplicationAdapter;
    private final ReferencedUserPreviewModelFactory referencedUserPreviewModelFactory;

    @GetMapping
    public ResponseEntity<UserPreview> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingAggregateId") UUID bookingAggregateId, @PathVariable("referencedUserId") UUID referencedUserId) {
        Optional<BookingAggregate> optionalBookingAggregate = bookingApplicationAdapter.find(userId, financialLedgerId, bookingAggregateId);
        if (!optionalBookingAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        BookingAggregate bookingAggregate = optionalBookingAggregate.get();
        Optional<User> optionalReferencedUser = bookingAggregate.getReferencedUsers().stream().filter(user -> user.getId().equals(referencedUserId)).findFirst();
        if (!optionalReferencedUser.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(referencedUserPreviewModelFactory.create(userId, financialLedgerId, bookingAggregateId, optionalReferencedUser.get()));
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingAggregateId") UUID bookingAggregateId, @PathVariable("referencedUserId") UUID referencedUserId) {
        if (!bookingApplicationAdapter.exists(userId, financialLedgerId, bookingAggregateId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if (!bookingApplicationAdapter.deleteUserReference(userId, financialLedgerId, bookingAggregateId, referencedUserId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }



}
