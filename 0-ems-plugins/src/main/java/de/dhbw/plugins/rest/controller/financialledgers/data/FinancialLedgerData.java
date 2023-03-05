package de.dhbw.plugins.rest.controller.financialledgers.data;

import de.dhbw.ems.adapter.mapper.data.financialledger.IFinancialLedgerData;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class FinancialLedgerData implements IFinancialLedgerData {

    @NotEmpty(message = "The name is required.")
    @Size(min = 2, max = 100, message = "The length of name must be between 2 and 100 characters.")
    private String title;

}