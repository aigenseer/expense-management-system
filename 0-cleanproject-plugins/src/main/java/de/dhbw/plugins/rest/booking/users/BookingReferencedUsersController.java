package de.dhbw.plugins.rest.booking.users;

import de.dhbw.cleanproject.adapter.user.preview.UserPreview;
import de.dhbw.cleanproject.adapter.user.preview.UserPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.user.preview.UserToUserPreviewModelMapper;
import de.dhbw.cleanproject.adapter.user.userdata.AppendUserData;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.plugins.rest.booking.user.BookingReferencedUserController;
import de.dhbw.plugins.rest.financialledger.user.FinancialLedgerUserController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerId}/booking/{bookingId}/users", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingReferencedUsersController {

    private final UserOperationService userOperationService;
    private final UserToUserPreviewModelMapper previewModelMapper;

    @GetMapping
    public ResponseEntity<UserPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId) {
        Optional<Booking> optionalBooking = userOperationService.getBooking(userId, financialLedgerId, bookingId);
        if (!optionalBooking.isPresent()) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Booking booking = optionalBooking.get();

        List<UserPreview> userPreviewModels = booking.getReferencedUsers().stream().map(user -> {
            UserPreview preview = previewModelMapper.apply(user);
            Link selfLink = linkTo(methodOn(FinancialLedgerUserController.class).findOne(user.getId(), financialLedgerId)).withSelfRel();
            preview.add(selfLink);
            return preview;
        }).collect(Collectors.toList());
        UserPreviewCollectionModel previewCollectionModel = new UserPreviewCollectionModel(userPreviewModels);

        Link selfLink = linkTo(methodOn(getClass()).listAll(userId, financialLedgerId, bookingId)).withSelfRel()
                .andAffordance(afford(methodOn(getClass()).create(userId, financialLedgerId, bookingId, null)));
        previewCollectionModel.add(selfLink);

        return ResponseEntity.ok(previewCollectionModel);
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId, @Valid @RequestBody AppendUserData data) {
        UUID referencedUserId = UUID.fromString(data.getUserId());
        if (!userOperationService.referenceUserToBooking(userId, financialLedgerId, bookingId, referencedUserId)) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(BookingReferencedUserController.class).findOne(userId, financialLedgerId, bookingId, referencedUserId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
