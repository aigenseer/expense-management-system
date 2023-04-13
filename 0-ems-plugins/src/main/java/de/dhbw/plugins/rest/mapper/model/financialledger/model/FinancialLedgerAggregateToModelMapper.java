package de.dhbw.plugins.rest.mapper.model.financialledger.model;

import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FinancialLedgerAggregateToModelMapper implements FinancialLedgerAggregateToFinancialLedgerModelMapper {

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
