package de.dhbw.plugins.rest.mapper.controller.model.financialledger;

import de.dhbw.ems.adapter.application.financialledger.FinancialLedgerAppAdapter;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import de.dhbw.plugins.rest.mapper.model.financialledger.preview.FinancialLedgerPreviewCollectionModel;
import de.dhbw.plugins.rest.mapper.model.financialledger.preview.FinancialLedgerPreviewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class FinancialLedgersToFinancialLedgerPreviewCollectionMapper implements Function<UUID, FinancialLedgerPreviewCollectionModel> {

    private final FinancialLedgerToFinancialLedgerPreviewMapper financialLedgerToFinancialLedgerPreviewMapper;
    private final FinancialLedgerAppAdapter financialLedgerAppAdapter;

    @Override
    public FinancialLedgerPreviewCollectionModel apply(final UUID userId) {
        return map(userId);
    }

    private FinancialLedgerPreviewCollectionModel map(final  UUID userId) {
        List<FinancialLedger> financialLedgers = financialLedgerAppAdapter.findFinancialLedgersByUserId(userId);
        List<FinancialLedgerPreviewModel> previewModels = financialLedgers.stream()
                .map(financialLedger -> financialLedgerToFinancialLedgerPreviewMapper
                            .apply(FinancialLedgerToFinancialLedgerPreviewMapper.Context.builder()
                                    .userId(userId)
                                    .financialLedger(financialLedger)
                        .build())
                )
                .collect(Collectors.toList());
        return new FinancialLedgerPreviewCollectionModel(previewModels);
    }

}
