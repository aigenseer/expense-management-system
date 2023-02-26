package de.dhbw.plugins.mapper.financialledger;

import de.dhbw.ems.adapter.model.user.preview.UserPreview;
import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.mapper.user.UserToUserPreviewMapper;
import de.dhbw.plugins.rest.financialledger.user.FinancialLedgerUserController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class FinancialLedgerUserPreviewModelFactory {

    private final UserToUserPreviewMapper userToUserPreviewMapper;

    public UserPreview create(UUID financialLedgerAggregateId, User user){
        UserPreview userPreview = userToUserPreviewMapper.apply(user);
        userPreview.removeLinks();

        Link selfLink = linkTo(methodOn(FinancialLedgerUserController.class).findOne(financialLedgerAggregateId, user.getId())).withSelfRel()
                .andAffordance(afford(methodOn(FinancialLedgerUserController.class).delete(financialLedgerAggregateId, user.getId())));
        userPreview.add(selfLink);
        return userPreview;
    }
}
