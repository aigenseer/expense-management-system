package de.dhbw.ems.application.archive.mapper.user;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.mapper.CSVFileMapper;
import de.dhbw.ems.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class UsersToCSVFileMapper extends CSVFileMapper implements UsersToCSVFileMapperFunction {

    @Override
    public TmpFile apply(final Iterable<User> users) {
        return map(users);
    }

    private TmpFile map(final Iterable<User> users) {
        String[] headers = {"Name", "Email", "Phone Number"};
        return createCSVFile(headers, printer -> {
            for (User user: users) {
                printer.printRecord(user.getName(), user.getEmail().toString(), user.getPhoneNumber().toString());
            }
        });
    }
}
