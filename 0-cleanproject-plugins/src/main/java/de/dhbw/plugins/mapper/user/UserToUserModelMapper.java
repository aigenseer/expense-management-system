package de.dhbw.plugins.mapper.user;

import de.dhbw.cleanproject.adapter.financialledger.preview.FinancialLedgerPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.user.usermodel.UserModel;
import de.dhbw.cleanproject.adapter.user.usermodel.UserToUserModelAdapterMapper;
import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.plugins.mapper.financialledger.FinancialLedgersToFinancialLedgerPreviewCollectionMapper;
import de.dhbw.plugins.rest.financialledgers.FinancialLedgersController;
import de.dhbw.plugins.rest.user.UserController;
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

    private final UserToUserModelAdapterMapper userToUserModelMapper;
    private final FinancialLedgersToFinancialLedgerPreviewCollectionMapper financialLedgersToFinancialLedgerPreviewCollectionMapper;

    @Override
    public UserModel apply(final User user) {
        return map(user);
    }

    private UserModel map(final User user) {
        UserModel userModel = userToUserModelMapper.apply(user);

        FinancialLedgerPreviewCollectionModel financialLedgerPreviewCollectionModel = financialLedgersToFinancialLedgerPreviewCollectionMapper.apply(FinancialLedgersToFinancialLedgerPreviewCollectionMapper.Context
                .builder()
                .userId(user.getId())
                .financialLedgers(user.getFinancialLedgers())
                .build());
        Link selfLink = linkTo(methodOn(FinancialLedgersController.class).listAll(user.getId())).withSelfRel();
        financialLedgerPreviewCollectionModel.add(selfLink);

        userModel.setFinancialLedgerPreviewCollectionModel(financialLedgerPreviewCollectionModel);
        selfLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).findOne(user.getId())).withSelfRel();
        userModel.add(selfLink);
        return userModel;
    }

}
