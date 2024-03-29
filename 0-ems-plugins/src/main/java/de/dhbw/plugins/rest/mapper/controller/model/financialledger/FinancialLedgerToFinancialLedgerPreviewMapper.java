package de.dhbw.plugins.rest.mapper.controller.model.financialledger;

import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import de.dhbw.plugins.rest.controller.financialledger.FinancialLedgerController;
import de.dhbw.plugins.rest.mapper.model.financialledger.preview.FinancialLedgerToFinancialLedgerPreviewModelMapper;
import de.dhbw.plugins.rest.mapper.model.financialledger.preview.FinancialLedgerPreviewModel;
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
