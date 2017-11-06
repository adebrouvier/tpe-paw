import ar.edu.itba.paw.interfaces.persistence.DuplicateUsernameException;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.TournamentServiceImpl;
import ar.edu.itba.paw.service.UserServiceImpl;
import jdk.nashorn.internal.scripts.JO;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.configuration.injection.MockInjection;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UserServiceTest {
/*    @Mock
    UserDao userDao;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @InjectMocks
    UserServiceImpl userServiceImpl;


    @Before
    public void setUp() throws DuplicateUsernameException {
        Mockito.when(userDao.findById(1)).thenReturn(new User(1,"Jorge","Macros"));
        Mockito.when(userDao.findById(123)).thenReturn(null);
        Mockito.when(userDao.create("Mama", "NombreDeMiHijo")).thenReturn(new User(1, "Mama", "NombreDeMiHijo"));
        Mockito.when(userDao.create("NombreYaUsado", "Lalala")).thenThrow(new DuplicateUsernameException());
        Mockito.when(userDao.findByName("Mama")).thenReturn(new User(1, "Mama", "NombreDeMiHijo"));
        Mockito.when(userDao.findByName("NoExisto")).thenReturn(null);
    }

    @Test
    public void testCreateUserSuccess() throws DuplicateUsernameException {
        assertNotNull(userServiceImpl.create("Mama", "NombreDeMiHijo"));
    }

    @Test(expected = DuplicateUsernameException.class)
    public void testUserAlreadyCreated() throws DuplicateUsernameException {
        userServiceImpl.create("NombreYaUsado", "Lalala");
    }

    @Test
    public void testFindUserByIdSuccess() {
        assertNotNull(userServiceImpl.findById(1));
    }

    @Test
    public void testFindUserByIdFailure() {
        assertNull(userServiceImpl.findById(123));
    } */
}
