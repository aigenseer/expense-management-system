package de.dhbw.plugins.rest.booking.user;

import de.dhbw.cleanproject.adapter.user.preview.UserPreview;
import de.dhbw.cleanproject.adapter.user.preview.UserToUserPreviewModelMapper;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerId}/booking/{bookingId}/user/{referencedUserId}", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingReferencedUserController {

    private final UserOperationService userOperationService;
    private final UserToUserPreviewModelMapper userToUserPreviewModelMapper;


    @GetMapping
    public ResponseEntity<UserPreview> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId, @PathVariable("referencedUserId") UUID referencedUserId) {
        Optional<Booking> optionalBooking = userOperationService.getBooking(userId, financialLedgerId, bookingId);
        if (!optionalBooking.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Booking booking = optionalBooking.get();
        Optional<User> optionalReferencedUser = booking.getReferencedUsers().stream().filter(user -> user.getId().equals(referencedUserId)).findFirst();
        if (!optionalReferencedUser.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User referencedUser = optionalReferencedUser.get();
        UserPreview model = userToUserPreviewModelMapper.apply(referencedUser);

        Link selfLink = linkTo(methodOn(getClass()).findOne(userId, financialLedgerId, bookingId, referencedUserId)).withSelfRel()
                .andAffordance(afford(methodOn(getClass()).delete(userId, financialLedgerId, bookingId, referencedUserId)));
        model.add(selfLink);

        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId, @PathVariable("referencedUserId") UUID referencedUserId) {
        if (!userOperationService.existsBookingById(userId, financialLedgerId, bookingId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if (!userOperationService.unlinkUserToBooking(referencedUserId, financialLedgerId, bookingId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }



}
