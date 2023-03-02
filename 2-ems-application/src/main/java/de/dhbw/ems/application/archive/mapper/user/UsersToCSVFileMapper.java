package de.dhbw.ems.application.archive.mapper.user;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.mapper.CSVFactory;
import de.dhbw.ems.application.archive.mapper.CSVFileMapper;
import de.dhbw.ems.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class UsersToCSVFileMapper extends CSVFileMapper implements UsersToCSVFileMapperFunction {

    public UsersToCSVFileMapper(
            final CSVFactory csvFactory
    ){
        super(csvFactory);
    }

    @Override
    public TmpFile apply(final Iterable<User> users) {
        return map(users);
    }

    private TmpFile map(final Iterable<User> users) {
        String[] headers = {"Name", "Email", "Phone Number"};
        return createCSVFile(headers, writer -> {
            for (User user: users) {
                writer.addRecord(user.getName(), user.getEmail().toString(), user.getPhoneNumber().toString());
            }
        });
    }
}
