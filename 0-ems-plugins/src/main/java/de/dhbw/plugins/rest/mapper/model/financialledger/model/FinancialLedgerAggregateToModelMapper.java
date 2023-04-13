package de.dhbw.plugins.rest.mapper.model.financialledger.model;

import de.dhbw.ems.domain.financialledger.entity.FinancialLedgerAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FinancialLedgerAggregateToModelMapper implements FinancialLedgerAggregateToFinancialLedgerModelMapper {

    @Override
    public FinancialLedgerModel apply(final FinancialLedgerAggregate financialLedgerAggregate) {
        return map(financialLedgerAggregate);
    }

    private FinancialLedgerModel map(final FinancialLedgerAggregate financialLedgerAggregate) {
        FinancialLedgerModel.FinancialLedgerModelBuilder builder = FinancialLedgerModel.builder()
                .name(financialLedgerAggregate.getTitle());
        return builder.build();
    }
}
