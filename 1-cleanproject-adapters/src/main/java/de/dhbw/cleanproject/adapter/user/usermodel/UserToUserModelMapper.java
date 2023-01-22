package de.dhbw.cleanproject.adapter.user.usermodel;

import de.dhbw.cleanproject.adapter.financialledger.preview.FinancialLedgerPreviewModel;
import de.dhbw.cleanproject.adapter.financialledger.preview.FinancialLedgerPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.financialledger.preview.FinancialLedgerToFinancialLedgerPreviewModelMapper;
import de.dhbw.cleanproject.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserToUserModelMapper implements Function<User, UserModel> {

    private final FinancialLedgerToFinancialLedgerPreviewModelMapper financialLedgerToFinancialLedgerPreviewModelMapper;

    @Override
    public UserModel apply(final User user) {
        return map(user);
    }

    private UserModel map(final User user) {
        UserModel.UserModelBuilder userModelBuilder = UserModel.builder()
                .name(user.getName())
                .email(user.getEmail().toString())
                .phoneNumber(user.getPhoneNumber() != null? user.getPhoneNumber().toString(): null);
        List<FinancialLedgerPreviewModel> financialLedgerPreviews = user.getFinancialLedgers().stream().map(financialLedgerToFinancialLedgerPreviewModelMapper).collect(Collectors.toList());
        userModelBuilder.financialLedgers(new FinancialLedgerPreviewCollectionModel(financialLedgerPreviews));
        return userModelBuilder.build();
    }
}
