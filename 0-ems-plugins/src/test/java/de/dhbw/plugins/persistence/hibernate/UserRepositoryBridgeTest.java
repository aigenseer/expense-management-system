package de.dhbw.plugins.persistence.hibernate;

import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.persistence.hibernate.user.SpringDataUserRepository;
import de.dhbw.plugins.persistence.hibernate.user.UserRepositoryBridge;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@ComponentScan("de.dhbw.plugins.persistence.hibernate")
public class UserRepositoryBridgeTest {

    @Mock
    private SpringDataUserRepository springDataRepository;

    @InjectMocks
    private UserRepositoryBridge repositoryBridge;

    private final User entity = User.builder().id(UUID.randomUUID()).build();
    private final List<UUID> entityIds = new ArrayList<UUID>(){{ add(entity.getId()); }};
    private final List<User> entities = new ArrayList<User>(){{ add(entity); }};

    @Before()
    public void setup(){
        when(springDataRepository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(springDataRepository.findAllById(entityIds)).thenReturn(entities);
        when(springDataRepository.findAll()).thenReturn(entities);
    }

    @Test
    public void testSave() {
        repositoryBridge.save(entity);
        verify(springDataRepository).save(entity);
    }

    @Test
    public void testDeleteById() {
        repositoryBridge.deleteById(entity.getId());
        verify(springDataRepository).deleteById(entity.getId());
    }

    @Test
    public void testFindAll(){
        List<User> result = repositoryBridge.findAll();
        assertEquals(1, result.size());
        assertTrue(result.contains(entity));
        verify(springDataRepository).findAll();
    }

    @Test
    public void testFindById(){
        Optional<User> result = repositoryBridge.findById(entity.getId());
        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(springDataRepository).findById(entity.getId());
    }

}
