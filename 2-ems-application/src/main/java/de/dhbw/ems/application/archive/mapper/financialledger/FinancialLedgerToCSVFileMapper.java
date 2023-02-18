package de.dhbw.ems.application.archive.mapper.financialledger;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.mapper.CSVFileMapper;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import org.springframework.stereotype.Component;

@Component
public class FinancialLedgerToCSVFileMapper extends CSVFileMapper implements FinancialLedgerToCSVFileMapperFunction {

    @Override
    public TmpFile apply(final FinancialLedger financialLedger) {
        return map(financialLedger);
    }

    private TmpFile map(final FinancialLedger financialLedger) {
        String[] headers = {"Name"};
        return createCSVFile(headers, printer -> {
            printer.printRecord(financialLedger.getTitle());
        });
    }
}
