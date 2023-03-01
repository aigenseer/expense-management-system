package de.dhbw.ems.adapter.model.financialledger.model;

import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FinancialLedgerAggregateToModelMapper implements FinancialLedgerAggregateToFinancialLedgerModelAdapterMapper {

    @Override
    public FinancialLedgerModel apply(final FinancialLedgerAggregate financialLedgerAggregate) {
        return map(financialLedgerAggregate);
    }

    private FinancialLedgerModel map(final FinancialLedgerAggregate financialLedgerAggregate) {
        FinancialLedgerModel.FinancialLedgerModelBuilder builder = FinancialLedgerModel.builder()
                .name(financialLedgerAggregate.getFinancialLedger().getTitle());
        return builder.build();
    }
}
