package de.dhbw.plugins.mapper.financialledger;

import de.dhbw.cleanproject.adapter.financialledger.preview.FinancialLedgerPreviewModel;
import de.dhbw.cleanproject.adapter.financialledger.preview.FinancialLedgerToFinancialLedgerPreviewModelMapper;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
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
        private final FinancialLedger financialLedger;
    }

    private final FinancialLedgerToFinancialLedgerPreviewModelMapper financialLedgerToFinancialLedgerPreviewModelMapper;

    @Override
    public FinancialLedgerPreviewModel apply(final FinancialLedgerToFinancialLedgerPreviewMapper.Context context) {
        return map(context);
    }

    private FinancialLedgerPreviewModel map(final FinancialLedgerToFinancialLedgerPreviewMapper.Context context) {
        FinancialLedgerPreviewModel preview = financialLedgerToFinancialLedgerPreviewModelMapper.apply(context.getFinancialLedger());
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(FinancialLedgerController.class).findOne(context.getUserId(), context.getFinancialLedger().getId())).withSelfRel();
        preview.add(selfLink);
        return preview;
    }

}
