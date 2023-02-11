package de.dhbw.ems.adapter.model.financialledger.data;

import de.dhbw.ems.application.financialledger.FinancialLedgerAttributeData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class FinancialLedgerDataToAttributeDataMapper implements FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper {

    @Override
    public FinancialLedgerAttributeData apply(final FinancialLedgerData data) {
        return map(data);
    }

    private FinancialLedgerAttributeData map(final FinancialLedgerData data) {
        return FinancialLedgerAttributeData.builder().name(data.getName()).build();
    }

}
