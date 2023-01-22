package de.dhbw.cleanproject.adapter.financialledger.data;

import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class FinancialLedgerUpdateDataToFinancialLedgerMapper implements Function<Pair<FinancialLedger, FinancialLedgerData>, FinancialLedger> {

    @Override
    public FinancialLedger apply(final Pair<FinancialLedger, FinancialLedgerData> data) {
        return map(data);
    }

    private FinancialLedger map(final Pair<FinancialLedger, FinancialLedgerData> pair) {
        FinancialLedger.FinancialLedgerBuilder builder = FinancialLedger.builder();
        FinancialLedger financialLedger = pair.getValue0();
        builder.id(financialLedger.getId());
        builder.authorizedUser(financialLedger.getAuthorizedUser());
        builder.name(pair.getValue1().getName());
        return builder.build();
    }

}
