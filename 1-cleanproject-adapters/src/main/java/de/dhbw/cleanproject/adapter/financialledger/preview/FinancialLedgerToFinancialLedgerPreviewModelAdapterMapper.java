package de.dhbw.cleanproject.adapter.financialledger.preview;

import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class FinancialLedgerToFinancialLedgerPreviewModelAdapterMapper implements Function<FinancialLedger, FinancialLedgerPreviewModel> {

    @Override
    public FinancialLedgerPreviewModel apply(final FinancialLedger financialLedger) {
        return map(financialLedger);
    }

    private FinancialLedgerPreviewModel map(final FinancialLedger financialLedger) {
        return FinancialLedgerPreviewModel.builder()
                .name(financialLedger.getName())
                .build();
    }
}
