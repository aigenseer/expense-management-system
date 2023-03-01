package de.dhbw.plugins.mapper.financialledger;

import de.dhbw.ems.adapter.model.financialledger.preview.FinancialLedgerAggregateToFinancialLedgerPreviewModelAdapterMapper;
import de.dhbw.ems.adapter.model.financialledger.preview.FinancialLedgerPreviewModel;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.plugins.rest.financialledger.FinancialLedgerController;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@Component
@RequiredArgsConstructor
public class FinancialLedgerToFinancialLedgerPreviewMapper implements Function<FinancialLedgerToFinancialLedgerPreviewMapper.Context, FinancialLedgerPreviewModel> {

    @RequiredArgsConstructor
    @Getter
    @Builder
    static class Context{
        private final UUID userId;
        private final FinancialLedgerAggregate financialLedgerAggregate;
    }

    private final FinancialLedgerAggregateToFinancialLedgerPreviewModelAdapterMapper financialLedgerToFinancialLedgerPreviewModelMapper;

    @Override
    public FinancialLedgerPreviewModel apply(final FinancialLedgerToFinancialLedgerPreviewMapper.Context context) {
        return map(context);
    }

    private FinancialLedgerPreviewModel map(final FinancialLedgerToFinancialLedgerPreviewMapper.Context context) {
        FinancialLedgerPreviewModel preview = financialLedgerToFinancialLedgerPreviewModelMapper.apply(context.getFinancialLedgerAggregate());
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(FinancialLedgerController.class).findOne(context.getUserId(), context.getFinancialLedgerAggregate().getId())).withSelfRel();
        preview.add(selfLink);
        return preview;
    }

}
