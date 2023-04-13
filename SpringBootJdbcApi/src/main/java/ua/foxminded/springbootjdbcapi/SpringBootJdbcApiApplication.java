package ua.foxminded.springbootjdbcapi;

import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.foxminded.springbootjdbcapi.dao.CourseDao;
import ua.foxminded.springbootjdbcapi.dao.GroupDao;
import ua.foxminded.springbootjdbcapi.dao.SpringJdbcConfig;
import ua.foxminded.springbootjdbcapi.dao.StudentDao;
import ua.foxminded.springbootjdbcapi.dao.impl.CourseDaoImpl;
import ua.foxminded.springbootjdbcapi.dao.impl.GroupDaoImpl;
import ua.foxminded.springbootjdbcapi.dao.impl.StudentDaoImpl;
import ua.foxminded.springbootjdbcapi.menu.MenuStarter;
import ua.foxminded.springbootjdbcapi.menu.MenuStarterImpl;
import ua.foxminded.springbootjdbcapi.viewprovider.ViewProvider;
import ua.foxminded.springbootjdbcapp.exception.DaoException;


@SpringBootApplication
public class SpringBootJdbcApiApplication {

    public static void main(String[] args) throws DaoException {
	SpringApplication.run(SpringBootJdbcApiApplication.class, args);
	
	SpringJdbcConfig springJdbcConfig = new SpringJdbcConfig();
	StudentDao studentDao = new StudentDaoImpl(springJdbcConfig.jdbcTemplate());
	CourseDao courseDao = new CourseDaoImpl(springJdbcConfig.jdbcTemplate());
	GroupDao groupDao = new GroupDaoImpl(springJdbcConfig.jdbcTemplate());
	ViewProvider viewProvider = new ViewProvider(new Scanner(System.in));
	MenuStarter menuStarter = new MenuStarterImpl(studentDao, courseDao, groupDao, viewProvider);

	menuStarter.startMenu();
    }
}
