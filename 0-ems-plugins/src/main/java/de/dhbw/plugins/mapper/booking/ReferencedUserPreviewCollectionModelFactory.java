package de.dhbw.plugins.mapper.booking;

import de.dhbw.ems.adapter.model.user.preview.UserPreviewCollectionModel;
import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.mapper.user.UsersToUserPreviewCollectionMapper;
import de.dhbw.plugins.rest.booking.user.BookingReferencedUserController;
import de.dhbw.plugins.rest.booking.users.BookingReferencedUsersController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class ReferencedUserPreviewCollectionModelFactory {

    private final UsersToUserPreviewCollectionMapper usersToUserPreviewCollectionMapper;

    public UserPreviewCollectionModel create(UUID userId, UUID financialLedgerId, UUID bookingId, Iterable<User> users){
        UserPreviewCollectionModel previewCollectionModel = usersToUserPreviewCollectionMapper.apply(users);
        previewCollectionModel.getContent().forEach(userPreview -> {
            Link selfLink = linkTo(methodOn(BookingReferencedUserController.class).findOne(userId, financialLedgerId, bookingId, userPreview.getId())).withSelfRel();
            userPreview.removeLinks();
            userPreview.add(selfLink);
        });
        Link selfLink = linkTo(methodOn(BookingReferencedUsersController.class).listAll(userId, financialLedgerId, bookingId)).withSelfRel()
                .andAffordance(afford(methodOn(BookingReferencedUsersController.class).create(userId, financialLedgerId, bookingId, null)));
        previewCollectionModel.add(selfLink);
        return previewCollectionModel;
    }
}
