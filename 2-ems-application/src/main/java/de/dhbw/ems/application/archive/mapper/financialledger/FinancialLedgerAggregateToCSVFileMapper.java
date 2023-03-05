package de.dhbw.ems.application.archive.mapper.financialledger;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.mapper.CSVFactory;
import de.dhbw.ems.application.archive.mapper.CSVFileMapper;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import org.springframework.stereotype.Component;

@Component
public class FinancialLedgerAggregateToCSVFileMapper extends CSVFileMapper implements FinancialLedgerAggregateToCSVFileMapperFunction {

    public FinancialLedgerAggregateToCSVFileMapper(
            final CSVFactory csvFactory
    ){
        super(csvFactory);
    }

    @Override
    public TmpFile apply(final FinancialLedgerAggregate financialLedgerAggregate) {
        return map(financialLedgerAggregate);
    }

    private TmpFile map(final FinancialLedgerAggregate financialLedgerAggregate) {
        String[] headers = {"Name"};
        return createCSVFile(headers, writer -> {
            writer.addRecord(financialLedgerAggregate.getFinancialLedger().getTitle());
        });
    }
}
