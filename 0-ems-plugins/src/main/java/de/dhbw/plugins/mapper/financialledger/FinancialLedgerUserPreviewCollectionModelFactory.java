package de.dhbw.plugins.mapper.financialledger;

import de.dhbw.ems.adapter.model.user.preview.UserPreviewCollectionModel;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.plugins.mapper.user.UsersToUserPreviewCollectionMapper;
import de.dhbw.plugins.rest.financialledger.user.FinancialLedgerUserController;
import de.dhbw.plugins.rest.financialledger.users.FinancialLedgerUsersController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
