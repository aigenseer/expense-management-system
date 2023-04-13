package de.dhbw.ems.adapter.mapper.data.financialledger;

import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FinancialLedgerDataToAttributeDataMapper implements FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper {

    @Override
    public FinancialLedgerData apply(final IFinancialLedgerData data) {
        return map(data);
    }

    private FinancialLedgerData map(final IFinancialLedgerData data) {
        return FinancialLedgerData.builder().title(data.getTitle()).build();
    }

}
