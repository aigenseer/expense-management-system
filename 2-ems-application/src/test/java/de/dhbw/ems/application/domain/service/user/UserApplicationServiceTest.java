package de.dhbw.ems.application.domain.service.user;

import de.dhbw.ems.abstractioncode.valueobject.email.Email;
import de.dhbw.ems.abstractioncode.valueobject.phonennumber.InternationalPhoneCode;
import de.dhbw.ems.abstractioncode.valueobject.phonennumber.PhoneNumber;
import de.dhbw.ems.domain.user.User;
import de.dhbw.ems.domain.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserApplicationServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserApplicationService applicationService;

    private final User entity = User.builder()
            .id(UUID.fromString("12345678-1234-1234-a123-123456789003"))
            .email(new Email("example-user-3@example.de"))
            .name("Example-User-3")
            .phoneNumber(new PhoneNumber(123456789, InternationalPhoneCode.DE))
            .build();

    private final UserAttributeData userAttributeData = UserAttributeData.builder()
            .email(entity.getEmail())
            .name(entity.getName())
            .phoneNumber(entity.getPhoneNumber())
            .build();

    private final List<User> entities = new ArrayList<User>(){{ add(entity); }};

    @Before()
    public void setup(){
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(repository.findAll()).thenReturn(entities);
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
    }

    @Test
    public void testSave() {
        User user =  applicationService.save(entity);
        checkEntity(entity, user);
        verify(repository).save(entity);
    }

    @Test
    public void testDeleteById() {
        applicationService.deleteById(entity.getId());
        verify(repository).deleteById(entity.getId());
    }

    @Test
    public void testFindAll(){
        List<User> resultList = applicationService.findAll();
        assertEquals(1, resultList.size());
        Optional<User> result = resultList.stream().filter(user -> user.getId().equals(entity.getId())).findFirst();
        assertTrue(result.isPresent());
        checkEntity(entity, result.get());
        verify(repository).findAll();
    }

    @Test
    public void testFindById(){
        Optional<User> result = applicationService.findById(entity.getId());
        assertTrue(result.isPresent());
        checkEntity(entity, result.get());
        verify(repository).findById(entity.getId());
    }

    @Test
    public void testCreate(){
        when(repository.save(any())).thenReturn(entity);
        Optional<User> result =  applicationService.createByAttributeData(userAttributeData);
        assertTrue(result.isPresent());
        checkEntity(entity, result.get());
        verify(repository).save(any());
        checkAttributeData(userAttributeData, result.get());
    }

    @Test
    public void testUpdateById(){
        Optional<User> optionalUser = applicationService.updateByAttributeDataWithId(entity.getId(), userAttributeData);
        assertTrue(optionalUser.isPresent());
        checkAttributeData(userAttributeData, optionalUser.get());
    }

    @Test
    public void testUpdateByEntity(){
        UserAttributeData attributeData = userAttributeData;
        Optional<User> optionalUser = applicationService.updateByAttributeData(entity, attributeData);
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
