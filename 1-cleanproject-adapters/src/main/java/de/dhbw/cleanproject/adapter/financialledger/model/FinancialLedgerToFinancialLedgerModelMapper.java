package de.dhbw.cleanproject.adapter.financialledger.model;

import de.dhbw.cleanproject.adapter.user.preview.UserPreviewCollectionModel;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class FinancialLedgerToFinancialLedgerModelMapper implements Function<Pair<FinancialLedger, UserPreviewCollectionModel>, FinancialLedgerModel> {

    @Override
    public FinancialLedgerModel apply(final Pair<FinancialLedger, UserPreviewCollectionModel> pair) {
        return map(pair);
    }

    private FinancialLedgerModel map(final Pair<FinancialLedger, UserPreviewCollectionModel> pair) {
        FinancialLedger financialLedger = pair.getValue0();
        FinancialLedgerModel.FinancialLedgerModelBuilder builder = FinancialLedgerModel.builder()
                .name(financialLedger.getName())
                .userPreviewCollectionModel(pair.getValue1());
        return builder.build();
    }
}
