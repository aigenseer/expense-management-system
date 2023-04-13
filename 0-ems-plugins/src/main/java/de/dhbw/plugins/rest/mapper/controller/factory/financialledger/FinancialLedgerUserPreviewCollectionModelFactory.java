package de.dhbw.plugins.rest.mapper.controller.factory.financialledger;

import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import de.dhbw.plugins.rest.controller.financialledger.user.FinancialLedgerUserController;
import de.dhbw.plugins.rest.controller.financialledger.users.FinancialLedgerUsersController;
import de.dhbw.plugins.rest.mapper.controller.model.user.UsersToUserPreviewCollectionMapper;
import de.dhbw.plugins.rest.mapper.model.user.preview.UserPreviewCollectionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class FinancialLedgerUserPreviewCollectionModelFactory {

    private final UsersToUserPreviewCollectionMapper usersToUserPreviewCollectionMapper;

    public UserPreviewCollectionModel create(UUID userId, FinancialLedger financialLedger){
        UserPreviewCollectionModel previewCollectionModel = usersToUserPreviewCollectionMapper.apply(financialLedger.getAuthorizedUser());
        previewCollectionModel.getContent().forEach(userPreview -> {
            Link selfLink = linkTo(methodOn(FinancialLedgerUserController.class).findOne(financialLedger.getId(), userPreview.getId())).withSelfRel();
            userPreview.removeLinks();
            userPreview.add(selfLink);
        });

        Link selfLink = linkTo(methodOn(FinancialLedgerUsersController.class).listAll(userId, financialLedger.getId())).withSelfRel()
                .andAffordance(afford(methodOn(FinancialLedgerUsersController.class).create(userId, financialLedger.getId(), null)));
        previewCollectionModel.add(selfLink);
        return previewCollectionModel;
    }

}
