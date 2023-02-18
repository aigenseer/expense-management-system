package de.dhbw.ems.application.archive.mapper.user;


import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.domain.user.User;

import java.util.function.Function;

public interface UsersToCSVFileMapperFunction extends Function<Iterable<User>, TmpFile> {

}
