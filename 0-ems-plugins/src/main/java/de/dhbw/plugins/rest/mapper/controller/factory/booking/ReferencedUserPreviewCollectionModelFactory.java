package de.dhbw.plugins.rest.mapper.controller.factory.booking;

import de.dhbw.plugins.rest.mapper.model.user.preview.UserPreviewCollectionModel;
import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.rest.mapper.controller.model.user.UsersToUserPreviewCollectionMapper;
import de.dhbw.plugins.rest.controller.booking.user.BookingReferencedUserController;
import de.dhbw.plugins.rest.controller.booking.users.BookingReferencedUsersController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class ReferencedUserPreviewCollectionModelFactory {

    private final UsersToUserPreviewCollectionMapper usersToUserPreviewCollectionMapper;

    public UserPreviewCollectionModel create(UUID userId, UUID financialLedgerAggregateId, UUID bookingAggregateId, Iterable<User> users){
        UserPreviewCollectionModel previewCollectionModel = usersToUserPreviewCollectionMapper.apply(users);
        previewCollectionModel.getContent().forEach(userPreview -> {
            Link selfLink = linkTo(methodOn(BookingReferencedUserController.class).findOne(userId, financialLedgerAggregateId, bookingAggregateId, userPreview.getId())).withSelfRel();
            userPreview.removeLinks();
            userPreview.add(selfLink);
        });
        Link selfLink = linkTo(methodOn(BookingReferencedUsersController.class).listAll(userId, financialLedgerAggregateId, bookingAggregateId)).withSelfRel()
                .andAffordance(afford(methodOn(BookingReferencedUsersController.class).create(userId, financialLedgerAggregateId, bookingAggregateId, null)));
        previewCollectionModel.add(selfLink);
        return previewCollectionModel;
    }
}
