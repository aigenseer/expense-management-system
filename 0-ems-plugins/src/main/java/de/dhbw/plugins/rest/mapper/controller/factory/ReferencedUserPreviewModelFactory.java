package de.dhbw.plugins.rest.mapper.controller.factory;

import de.dhbw.ems.adapter.model.user.preview.UserPreview;
import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.rest.mapper.controller.model.UserToUserPreviewMapper;
import de.dhbw.plugins.rest.controller.booking.user.BookingReferencedUserController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class ReferencedUserPreviewModelFactory {

    private final UserToUserPreviewMapper userToUserPreviewMapper;

    public UserPreview create(UUID userId, UUID financialLedgerAggregateId, UUID bookingAggregateId, User referencedUser){
        UserPreview model = userToUserPreviewMapper.apply(referencedUser);
        model.removeLinks();

        Link selfLink = linkTo(methodOn(BookingReferencedUserController.class).findOne(userId, financialLedgerAggregateId, bookingAggregateId, referencedUser.getId())).withSelfRel()
                .andAffordance(afford(methodOn(BookingReferencedUserController.class).delete(userId, financialLedgerAggregateId, bookingAggregateId, referencedUser.getId())));
        model.add(selfLink);
        return model;
    }

}
