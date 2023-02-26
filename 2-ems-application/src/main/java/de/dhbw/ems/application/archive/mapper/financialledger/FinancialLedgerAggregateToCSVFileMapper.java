package de.dhbw.ems.application.archive.mapper.financialledger;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.mapper.CSVFileMapper;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import org.springframework.stereotype.Component;

@Component
public class FinancialLedgerAggregateToCSVFileMapper extends CSVFileMapper implements FinancialLedgerAggregateToCSVFileMapperFunction {

    @Override
    public TmpFile apply(final FinancialLedgerAggregate financialLedgerAggregate) {
        return map(financialLedgerAggregate);
    }

    private TmpFile map(final FinancialLedgerAggregate financialLedgerAggregate) {
        String[] headers = {"Name"};
        return createCSVFile(headers, printer -> {
            printer.printRecord(financialLedgerAggregate.getFinancialLedger().getTitle());
        });
    }
}
