package de.dhbw.plugins.rest.mapper.controller.factory.booking;

import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.rest.controller.booking.user.BookingReferencedUserController;
import de.dhbw.plugins.rest.mapper.controller.model.user.UserToUserPreviewMapper;
import de.dhbw.plugins.rest.mapper.model.user.preview.UserPreview;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class ReferencedUserPreviewModelFactory {

    private final UserToUserPreviewMapper userToUserPreviewMapper;

    public UserPreview create(UUID userId, UUID financialLedgerId, UUID bookingAggregateId, User referencedUser){
        UserPreview model = userToUserPreviewMapper.apply(referencedUser);
        model.removeLinks();

        Link selfLink = linkTo(methodOn(BookingReferencedUserController.class).findOne(userId, financialLedgerId, bookingAggregateId, referencedUser.getId())).withSelfRel()
                .andAffordance(afford(methodOn(BookingReferencedUserController.class).delete(userId, financialLedgerId, bookingAggregateId, referencedUser.getId())));
        model.add(selfLink);
        return model;
    }

}
