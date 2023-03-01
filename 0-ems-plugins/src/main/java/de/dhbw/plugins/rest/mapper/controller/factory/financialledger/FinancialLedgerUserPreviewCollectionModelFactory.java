package de.dhbw.plugins.rest.mapper.controller.factory.financialledger;

import de.dhbw.plugins.rest.mapper.model.user.preview.UserPreviewCollectionModel;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.plugins.rest.mapper.controller.model.user.UsersToUserPreviewCollectionMapper;
import de.dhbw.plugins.rest.controller.financialledger.user.FinancialLedgerUserController;
import de.dhbw.plugins.rest.controller.financialledger.users.FinancialLedgerUsersController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class FinancialLedgerUserPreviewCollectionModelFactory {

    private final UsersToUserPreviewCollectionMapper usersToUserPreviewCollectionMapper;

    public UserPreviewCollectionModel create(UUID userId, FinancialLedgerAggregate financialLedgerAggregate){
        UserPreviewCollectionModel previewCollectionModel = usersToUserPreviewCollectionMapper.apply(financialLedgerAggregate.getAuthorizedUser());
        previewCollectionModel.getContent().forEach(userPreview -> {
            Link selfLink = linkTo(methodOn(FinancialLedgerUserController.class).findOne(financialLedgerAggregate.getId(), userPreview.getId())).withSelfRel();
            userPreview.removeLinks();
            userPreview.add(selfLink);
        });

        Link selfLink = linkTo(methodOn(FinancialLedgerUsersController.class).listAll(userId, financialLedgerAggregate.getId())).withSelfRel()
                .andAffordance(afford(methodOn(FinancialLedgerUsersController.class).create(userId, financialLedgerAggregate.getId(), null)));
        previewCollectionModel.add(selfLink);
        return previewCollectionModel;
    }

}
