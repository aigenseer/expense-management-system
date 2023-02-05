package de.dhbw.plugins.mapper.booking;

import de.dhbw.cleanproject.adapter.model.user.preview.UserPreview;
import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.plugins.mapper.user.UserToUserPreviewMapper;
import de.dhbw.plugins.rest.booking.user.BookingReferencedUserController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class ReferencedUserPreviewModelFactory {

    private final UserToUserPreviewMapper userToUserPreviewMapper;

    public UserPreview create(UUID userId, UUID financialLedgerId, UUID bookingId, User referencedUser){
        UserPreview model = userToUserPreviewMapper.apply(referencedUser);
        model.removeLinks();

        Link selfLink = linkTo(methodOn(BookingReferencedUserController.class).findOne(userId, financialLedgerId, bookingId, referencedUser.getId())).withSelfRel()
                .andAffordance(afford(methodOn(BookingReferencedUserController.class).delete(userId, financialLedgerId, bookingId, referencedUser.getId())));
        model.add(selfLink);
        return model;
    }

}
