package de.dhbw.ems.adapter.model.financialledger.model;

import de.dhbw.ems.domain.financialledger.FinancialLedger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class FinancialLedgerToFinancialLedgerModelAdapterMapper implements Function<FinancialLedger, FinancialLedgerModel> {

    @Override
    public FinancialLedgerModel apply(final FinancialLedger financialLedger) {
        return map(financialLedger);
    }

    private FinancialLedgerModel map(final FinancialLedger financialLedger) {
        FinancialLedgerModel.FinancialLedgerModelBuilder builder = FinancialLedgerModel.builder()
                .name(financialLedger.getTitle());
        return builder.build();
    }
}
