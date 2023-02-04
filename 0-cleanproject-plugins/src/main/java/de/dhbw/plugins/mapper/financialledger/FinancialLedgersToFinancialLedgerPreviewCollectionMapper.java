package de.dhbw.plugins.mapper.financialledger;

import de.dhbw.cleanproject.adapter.financialledger.preview.FinancialLedgerPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.financialledger.preview.FinancialLedgerPreviewModel;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Component
@RequiredArgsConstructor
public class FinancialLedgersToFinancialLedgerPreviewCollectionMapper implements Function<FinancialLedgersToFinancialLedgerPreviewCollectionMapper.Context, FinancialLedgerPreviewCollectionModel> {

    @RequiredArgsConstructor
    @Getter
    @Builder
    public static class Context{
        private final UUID userId;
        private final Iterable<FinancialLedger> financialLedgers;
    }

    private final FinancialLedgerToFinancialLedgerPreviewMapper financialLedgerToFinancialLedgerPreviewMapper;

    @Override
    public FinancialLedgerPreviewCollectionModel apply(final FinancialLedgersToFinancialLedgerPreviewCollectionMapper.Context context) {
        return map(context);
    }

    private FinancialLedgerPreviewCollectionModel map(final FinancialLedgersToFinancialLedgerPreviewCollectionMapper.Context context) {
        List<FinancialLedgerPreviewModel> previewModels = StreamSupport.stream(context.getFinancialLedgers().spliterator(), false)
                .map(financialLedger -> financialLedgerToFinancialLedgerPreviewMapper
                            .apply(FinancialLedgerToFinancialLedgerPreviewMapper.Context.builder()
                                    .userId(context.getUserId())
                                    .financialLedger(financialLedger)
                        .build())
                )
                .collect(Collectors.toList());
        return new FinancialLedgerPreviewCollectionModel(previewModels);
    }

}
