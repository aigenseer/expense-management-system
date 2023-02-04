package de.dhbw.cleanproject.adapter.financialledger.data;

import de.dhbw.cleanproject.application.user.UserApplicationService;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class FinancialLedgerDataToFinancialLedgerMapper implements Function<FinancialLedgerData, FinancialLedger> {

    private final UserApplicationService userApplicationService;

    @Override
    public FinancialLedger apply(final FinancialLedgerData data) {
        return map(data);
    }

    private FinancialLedger map(final FinancialLedgerData data) {
        FinancialLedger.FinancialLedgerBuilder builder = FinancialLedger.builder();
        builder.id(UUID.randomUUID());
        builder.name(data.getName());
        return builder.build();
    }

}
