package de.dhbw.plugins.rest.mapper.model.financialledger.preview;

import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import org.springframework.stereotype.Component;

@Component
public class FinancialLedgerAggregateToPreviewModelMapper implements FinancialLedgerAggregateToFinancialLedgerPreviewModelAdapterMapper {

    @Override
    public FinancialLedgerPreviewModel apply(final FinancialLedgerAggregate financialLedgerAggregate) {
        return map(financialLedgerAggregate);
    }

    private FinancialLedgerPreviewModel map(final FinancialLedgerAggregate financialLedgerAggregate) {
        return FinancialLedgerPreviewModel.builder()
                .name(financialLedgerAggregate.getFinancialLedger().getTitle())
                .build();
    }
}
