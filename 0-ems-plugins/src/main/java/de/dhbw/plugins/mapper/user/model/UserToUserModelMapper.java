package de.dhbw.plugins.mapper.user.model;

import de.dhbw.ems.adapter.model.financialledger.preview.FinancialLedgerPreviewCollectionModel;
import de.dhbw.ems.adapter.model.user.usermodel.UserModel;
import de.dhbw.ems.adapter.model.user.usermodel.UserToUserModelAdapterMapper;
import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.mapper.financialledger.model.FinancialLedgersToFinancialLedgerPreviewCollectionMapper;
import de.dhbw.plugins.rest.controller.financialledgers.FinancialLedgersController;
import de.dhbw.plugins.rest.controller.user.UserController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class UserToUserModelMapper implements Function<User, UserModel> {

    private final UserToUserModelAdapterMapper userToUserModelAdapterMapper;
    private final FinancialLedgersToFinancialLedgerPreviewCollectionMapper financialLedgersToFinancialLedgerPreviewCollectionMapper;

    @Override
    public UserModel apply(final User user) {
        return map(user);
    }

    private UserModel map(final User user) {
        UserModel userModel = userToUserModelAdapterMapper.apply(user);

        FinancialLedgerPreviewCollectionModel financialLedgerPreviewCollectionModel = financialLedgersToFinancialLedgerPreviewCollectionMapper.apply(user.getId());
        Link selfLink = linkTo(methodOn(FinancialLedgersController.class).listAll(user.getId())).withSelfRel();
        financialLedgerPreviewCollectionModel.add(selfLink);

        userModel.setFinancialLedgerPreviewCollectionModel(financialLedgerPreviewCollectionModel);
        selfLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).findOne(user.getId())).withSelfRel();
        userModel.add(selfLink);
        return userModel;
    }

}
