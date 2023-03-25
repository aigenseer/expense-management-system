package de.dhbw.ems.adapter.mapper.data.financialledger;

import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerAttributeData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FinancialLedgerDataToAttributeDataMapper implements FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper {

    @Override
    public FinancialLedgerAttributeData apply(final IFinancialLedgerData data) {
        return map(data);
    }

    private FinancialLedgerAttributeData map(final IFinancialLedgerData data) {
        return FinancialLedgerAttributeData.builder().title(data.getTitle()).build();
    }

}
