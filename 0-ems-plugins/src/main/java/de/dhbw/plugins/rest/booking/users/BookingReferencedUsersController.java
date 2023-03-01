package de.dhbw.plugins.rest.booking.users;

import de.dhbw.ems.adapter.application.booking.BookingApplicationAdapter;
import de.dhbw.ems.adapter.model.user.preview.UserPreviewCollectionModel;
import de.dhbw.ems.adapter.model.user.userdata.AppendUserData;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerAggregateId}/booking/{bookingAggregateId}/users", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingReferencedUsersController {

    private final BookingApplicationAdapter bookingApplicationAdapter;
    private final ReferencedUserPreviewCollectionModelFactory referencedUserPreviewCollectionModelFactory;

    @GetMapping
    public ResponseEntity<UserPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId, @PathVariable("bookingAggregateId") UUID bookingAggregateId) {
        Optional<BookingAggregate> optionalBooking = bookingApplicationAdapter.find(userId, financialLedgerAggregateId, bookingAggregateId);
        if (!optionalBooking.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(referencedUserPreviewCollectionModelFactory.create(userId, financialLedgerAggregateId, bookingAggregateId, optionalBooking.get().getReferencedUsers()));
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId, @PathVariable("bookingAggregateId") UUID bookingAggregateId, @Valid @RequestBody AppendUserData data) {
        UUID referencedUserId = UUID.fromString(data.getUserId());
        if (!bookingApplicationAdapter.referenceUser(userId, financialLedgerAggregateId, bookingAggregateId, referencedUserId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(BookingReferencedUserController.class).findOne(userId, financialLedgerAggregateId, bookingAggregateId, referencedUserId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
