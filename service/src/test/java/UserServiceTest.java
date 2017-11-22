import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UserServiceTest {
    @Mock
    private UserDao userDao;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    private final String TOURNAMENT_NAME = "Torneo";
    private final int MATCH_ID = 1;
    private final String PASSWORD = "serenito";
    private final String USERNAME = "postre";

    @Before
    public void setUp() {
        Mockito.when(userDao.findById(1)).thenReturn(new User(USERNAME,PASSWORD));
        Mockito.when(userDao.findById(123)).thenReturn(null);
        Mockito.when(userDao.create(USERNAME, PASSWORD)).thenReturn(new User(USERNAME,PASSWORD));
        Mockito.when(userDao.findByName(USERNAME)).thenReturn(new User(USERNAME,PASSWORD));
        Mockito.when(userDao.findByName("NoExisto")).thenReturn(null);
    }

    @Test
    public void testCreateUserSuccess()  {
        assertNotNull(userServiceImpl.create(USERNAME, PASSWORD));
    }

    @Test
    public void testFindUserByIdSuccess() {
        assertNotNull(userServiceImpl.findById(1));
    }

    @Test
    public void testFindUserByIdFailure() {
        assertNull(userServiceImpl.findById(123));
    }
}
