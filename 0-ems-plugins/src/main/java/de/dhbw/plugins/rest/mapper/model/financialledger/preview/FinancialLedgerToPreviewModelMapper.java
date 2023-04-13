package de.dhbw.plugins.rest.mapper.model.financialledger.preview;

import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import org.springframework.stereotype.Component;

@Component
public class FinancialLedgerToPreviewModelMapper implements FinancialLedgerToFinancialLedgerPreviewModelMapper {

    @Override
    public FinancialLedgerPreviewModel apply(final FinancialLedger financialLedger) {
        return map(financialLedger);
    }

    private FinancialLedgerPreviewModel map(final FinancialLedger financialLedger) {
        return FinancialLedgerPreviewModel.builder()
                .name(financialLedger.getTitle())
                .build();
    }
}
