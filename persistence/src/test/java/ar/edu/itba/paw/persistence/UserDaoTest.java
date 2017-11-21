package ar.edu.itba.paw.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.DataSource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class UserDaoTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private javax.sql.DataSource ds;
    @Autowired
    private UserHibernateDao userDao;

    private static String CREATE_USERNAME = "Jorgito";

    @Test
    @Transactional
    public void testCreationSuccessful() {
        userDao.create(CREATE_USERNAME, "Meconio");
        assertNotNull(userDao.findByName(CREATE_USERNAME));
    }

}
