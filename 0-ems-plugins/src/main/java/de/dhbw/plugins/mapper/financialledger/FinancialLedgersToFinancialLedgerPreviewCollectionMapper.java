package de.dhbw.plugins.mapper.financialledger;

import de.dhbw.ems.adapter.application.financialledger.FinancialLedgerAppAdapter;
import de.dhbw.ems.adapter.model.financialledger.preview.FinancialLedgerPreviewCollectionModel;
import de.dhbw.ems.adapter.model.financialledger.preview.FinancialLedgerPreviewModel;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
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
        List<FinancialLedgerAggregate> financialLedgerAggregates = financialLedgerAppAdapter.findFinancialLedgerAggregatesByUserId(userId);
        List<FinancialLedgerPreviewModel> previewModels = financialLedgerAggregates.stream()
                .map(financialLedger -> financialLedgerToFinancialLedgerPreviewMapper
                            .apply(FinancialLedgerToFinancialLedgerPreviewMapper.Context.builder()
                                    .userId(userId)
                                    .financialLedgerAggregate(financialLedger)
                        .build())
                )
                .collect(Collectors.toList());
        return new FinancialLedgerPreviewCollectionModel(previewModels);
    }

}
