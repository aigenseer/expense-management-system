package de.dhbw.plugins.rest.booking.users;

import de.dhbw.cleanproject.adapter.model.user.preview.UserPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.model.user.userdata.AppendUserData;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.plugins.mapper.booking.ReferencedUserPreviewCollectionModelFactory;
import de.dhbw.plugins.rest.booking.user.BookingReferencedUserController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerId}/booking/{bookingId}/users", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingReferencedUsersController {

    private final UserOperationService userOperationService;
    private final ReferencedUserPreviewCollectionModelFactory referencedUserPreviewCollectionModelFactory;

    @GetMapping
    public ResponseEntity<UserPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId) {
        Optional<Booking> optionalBooking = userOperationService.getBooking(userId, financialLedgerId, bookingId);
        if (!optionalBooking.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(referencedUserPreviewCollectionModelFactory.create(userId, financialLedgerId, bookingId, optionalBooking.get().getReferencedUsers()));
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId, @Valid @RequestBody AppendUserData data) {
        UUID referencedUserId = UUID.fromString(data.getUserId());
        if (!userOperationService.referenceUserToBooking(userId, financialLedgerId, bookingId, referencedUserId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(BookingReferencedUserController.class).findOne(userId, financialLedgerId, bookingId, referencedUserId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
