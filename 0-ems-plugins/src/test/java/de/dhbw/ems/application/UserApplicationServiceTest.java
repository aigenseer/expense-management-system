package de.dhbw.ems.application;

import de.dhbw.ems.abstractioncode.valueobject.email.Email;
import de.dhbw.ems.abstractioncode.valueobject.phonennumber.InternationalPhoneCode;
import de.dhbw.ems.abstractioncode.valueobject.phonennumber.PhoneNumber;
import de.dhbw.ems.application.user.UserApplicationService;
import de.dhbw.ems.application.user.UserAttributeData;
import de.dhbw.ems.domain.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest()
@ComponentScan("de.dhbw")
public class UserApplicationServiceTest {

    @Autowired
    private UserApplicationService applicationService;

    private final User entity1 = User.builder()
            .id(UUID.fromString("12345678-1234-1234-a123-123456789001"))
            .email(new Email("example-user-1@example.de"))
            .name("Example-User-1")
            .phoneNumber(new PhoneNumber(123456789, InternationalPhoneCode.DE))
        .build();

    private final User entity2 = User.builder()
            .id(UUID.fromString("12345678-1234-1234-a123-123456789003"))
            .email(new Email("example-user-3@example.de"))
            .name("Example-User-3")
            .phoneNumber(new PhoneNumber(123456789, InternationalPhoneCode.DE))
            .build();
    private final UserAttributeData userAttributeData = UserAttributeData.builder()
            .email(new Email("test@test.de"))
            .name("Name")
            .phoneNumber(new PhoneNumber(1234567, InternationalPhoneCode.DE))
            .build();

    @Test
    public void testSave() {
        applicationService.save(entity2);
        Optional<User> result = applicationService.findById(entity2.getId());
        assertTrue(result.isPresent());
        checkEntity(entity2, result.get());
    }

    @Test
    public void testDeleteById() {
        applicationService.deleteById(entity1.getId());
        Optional<User> result = applicationService.findById(entity1.getId());
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindAll(){
        List<User> resultList = applicationService.findAll();
        assertEquals(2, resultList.size());
        Optional<User> result = resultList.stream().filter(user -> user.getId().equals( entity1.getId())).findFirst();
        assertTrue(result.isPresent());
        checkEntity(entity1, result.get());
    }

    @Test
    public void testFindById(){
        Optional<User> result = applicationService.findById(entity1.getId());
        assertTrue(result.isPresent());
        checkEntity(entity1, result.get());
    }

    @Test
    public void testFindAllById() {
        List<User> resultList = applicationService.findAllById(new ArrayList<UUID>(){{ add(entity1.getId()); }});
        assertEquals(1, resultList.size());
        Optional<User> result = resultList.stream().filter(user -> user.getId().equals( entity1.getId())).findFirst();
        assertTrue(result.isPresent());
        checkEntity(entity1, result.get());
    }

    @Test
    public void testCreate(){
        Optional<User> optionalUser = applicationService.createByAttributeData(userAttributeData);
        assertTrue(optionalUser.isPresent());
        checkAttributeData(userAttributeData, optionalUser.get());
    }

    @Test
    public void testUpdateById(){
        Optional<User> optionalUser = applicationService.updateByAttributeDataWithId(entity1.getId(), userAttributeData);
        assertTrue(optionalUser.isPresent());
        checkAttributeData(userAttributeData, optionalUser.get());
        optionalUser = applicationService.findById(entity1.getId());
        assertTrue(optionalUser.isPresent());
        checkAttributeData(userAttributeData, optionalUser.get());
    }

    @Test
    public void testUpdateByEntity(){
        Optional<User> optionalUser = applicationService.updateByAttributeData(entity1, userAttributeData);
        assertTrue(optionalUser.isPresent());
        checkAttributeData(userAttributeData, optionalUser.get());
        optionalUser = applicationService.findById(entity1.getId());
        assertTrue(optionalUser.isPresent());
        checkAttributeData(userAttributeData, optionalUser.get());
    }

    private void checkAttributeData(UserAttributeData attributeData, User actualEntity){
        assertEquals(attributeData.getEmail(), actualEntity.getEmail());
        assertEquals(attributeData.getName(), actualEntity.getName());
        assertEquals(attributeData.getPhoneNumber(), actualEntity.getPhoneNumber());
    }

    private void checkEntity(User expectedEntity, User actualEntity){
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getEmail(), actualEntity.getEmail());
        assertEquals(expectedEntity.getName(), actualEntity.getName());
        assertEquals(expectedEntity.getPhoneNumber(), actualEntity.getPhoneNumber());
    }

}
