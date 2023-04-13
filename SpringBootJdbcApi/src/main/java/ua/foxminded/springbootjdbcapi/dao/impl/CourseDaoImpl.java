package ua.foxminded.springbootjdbcapi.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.foxminded.springbootjdbcapi.dao.CourseDao;
import ua.foxminded.springbootjdbcapi.entity.Course;
import ua.foxminded.springbootjdbcapp.exception.DaoException;

@Repository
public class CourseDaoImpl implements CourseDao {
    private static final String PROPERTY_COURSE_ADD = "INSERT INTO schedule.courses(course_name, course_description) VALUES (?, ?)";
    private static final String PROPERTY_COURSE_GET_ALL = "SELECT * FROM schedule.courses";
    private static final String PROPERTY_COURSE_UPDATE = "UPDATE schedule.courses SET course_name = ?, course_description = ? WHERE course_id = ?";
    private static final String PROPERTY_COURSE_DELETE = "DELETE FROM schedule.courses WHERE course_id = ?";
    private static final String NOT_FOUND = " not found!";
    private static final String COURSE_WITH_ID = "Course with id ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CourseDaoImpl(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Course entity) throws DaoException {
	String sql = PROPERTY_COURSE_ADD;
	Object[] params = {entity.getCourseName(), entity.getCourseDescription()};
	int result = jdbcTemplate.update(sql, params);
	
	if (result > 0) {
	    System.out.println("Created new course in base!");
	} else {
	    throw new DaoException("Course " + entity.getCourseName() + NOT_FOUND);
	}
    }

    @Override
    public Course findById(int id) {
	Course course = jdbcTemplate.queryForObject(
		"SELECT course_id, course_name, course_description FROM schedule.courses WHERE course_id = " + id,
		new BeanPropertyRowMapper<Course>(Course.class));
	System.out.println(course);
	
	return course;
    }

    @Override
    public List<Course> findAll() {
	List<Course> listOfStudents = jdbcTemplate.query(PROPERTY_COURSE_GET_ALL,
		new BeanPropertyRowMapper<Course>(Course.class));

	for (Course course : listOfStudents) {
	    System.out.println(course);
	}

	return listOfStudents;
    }

    @Override
    public void update(Course entity, int studentId) throws DaoException {
	String sql = PROPERTY_COURSE_UPDATE;
	Object[] params = {entity.getCourseName(), entity.getCourseDescription(), studentId};
	int result = jdbcTemplate.update(sql, params);
	
	if (result > 0) {
	    System.out.println(COURSE_WITH_ID + studentId + " updated");
	} else {
	    throw new DaoException(COURSE_WITH_ID + studentId + NOT_FOUND);
	}
    }

    @Override
    public void deleteById(int id) throws DaoException {
	String sql = PROPERTY_COURSE_DELETE;
	Object[] params = {id};
	int result = jdbcTemplate.update(sql, params);
	
	if (result > 0) {
	    System.out.println(COURSE_WITH_ID + id + " removed");
	} else {
	    throw new DaoException(COURSE_WITH_ID + id + NOT_FOUND);
	}
    }

    @Override
    public List<Course> getCoursesForStudentId(int studentId) {
	List<Course> listOfCourses = jdbcTemplate.query("SELECT courses.course_id, courses.course_name, courses.course_description "
		    + "FROM schedule.courses INNER JOIN schedule.students_courses ON courses.course_id = students_courses.course_id "
		    + "WHERE students_courses.student_id = " + studentId, new BeanPropertyRowMapper<Course>(Course.class));

	for (Course course : listOfCourses) {
	    System.out.println(course);
	}

	return listOfCourses;
    }

    @Override
    public List<Course> getCoursesMissingForStudentId(int studentId) {
	List<Course> listOfCourses = jdbcTemplate.query("SELECT course_id, course_name, course_description "
		    + "FROM schedule.courses c WHERE NOT EXISTS (SELECT * FROM schedule.students_courses s_c WHERE student_id = "
		    + studentId + " AND c.course_id = s_c.course_id)", new BeanPropertyRowMapper<Course>(Course.class));

	for (Course course : listOfCourses) {
	    System.out.println(course);
	}

	return listOfCourses;
    }
}
