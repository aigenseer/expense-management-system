package de.dhbw.ems.application.archive.mapper.financialledger;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.mapper.CSVFactory;
import de.dhbw.ems.application.archive.mapper.CSVFileMapper;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import org.springframework.stereotype.Component;

@Component
public class FinancialLedgerToCSVFileMapper extends CSVFileMapper implements FinancialLedgerToCSVFileMapperFunction {

    public FinancialLedgerToCSVFileMapper(
            final CSVFactory csvFactory
    ){
        super(csvFactory);
    }

    @Override
    public TmpFile apply(final FinancialLedger financialLedger) {
        return map(financialLedger);
    }

    private TmpFile map(final FinancialLedger financialLedger) {
        String[] headers = {"Name"};
        return createCSVFile(headers, writer -> {
            writer.addRecord(financialLedger.getTitle());
        });
    }
}
