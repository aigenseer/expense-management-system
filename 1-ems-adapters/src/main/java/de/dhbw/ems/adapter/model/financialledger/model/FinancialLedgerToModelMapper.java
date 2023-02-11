package de.dhbw.ems.adapter.model.financialledger.model;

import de.dhbw.ems.domain.financialledger.FinancialLedger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FinancialLedgerToModelMapper implements FinancialLedgerToFinancialLedgerModelAdapterMapper {

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
