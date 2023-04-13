package ua.foxminded.springbootjdbcapi.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.foxminded.springbootjdbcapi.dao.StudentDao;
import ua.foxminded.springbootjdbcapi.entity.Student;
import ua.foxminded.springbootjdbcapp.exception.DaoException;

@Repository
public class StudentDaoImpl implements StudentDao {
    private static final String PROPERTY_STUDENT_ADD = "INSERT INTO schedule.students(group_id, first_name, last_name) VALUES (?, ?, ?)";
    private static final String PROPERTY_STUDENT_UPDATE = "UPDATE schedule.students SET group_id = ?, first_name = ?, last_name = ? WHERE student_id = ?";
    private static final String PROPERTY_STUDENT_DELETE = "DELETE FROM schedule.students WHERE student_id = ?";
    private static final String PROPERTY_STUDENT_COURSE_ADD = "INSERT INTO schedule.students_courses(student_id, course_id) VALUES (?, ?)";
    private static final String PROPERTY_STUDENT_COURSE_DELETE = "DELETE FROM schedule.students_courses WHERE student_id = ? and course_id = ?";
    private static final String MASK_MESSAGE_ADD_EXCEPTION = "Don't save student %s in base";
    private static final String STUDENT_WITH_ID = "Student with id ";
    private static final String NOT_FOUND = " not found!";
    private static final int DEFAULT_GROUP_ID = 10;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addStudentCourse(int studentId, int courseId) throws DaoException {
	String sql = PROPERTY_STUDENT_COURSE_ADD;
	Object[] params = {studentId, courseId};
	int result = jdbcTemplate.update(sql, params);
	
	if (result > 0) {
	    System.out.println(STUDENT_WITH_ID + studentId + " added to course " + courseId);
	} else {
	    throw new DaoException(STUDENT_WITH_ID + studentId + NOT_FOUND);
	}
    }

    @Override
    public void removeStudentFromCourse(int studentId, int courseId) throws DaoException {
	String sql = PROPERTY_STUDENT_COURSE_DELETE;
	Object[] params = {studentId, courseId};
	int result = jdbcTemplate.update(sql, params);
	
	if (result > 0) {
	    System.out.println(STUDENT_WITH_ID + studentId + " removed from course " + courseId);
	} else {
	    throw new DaoException(STUDENT_WITH_ID + studentId + " not found" + " at course " + courseId + "!");
	}

    }

    @Override
    public List<Student> getStudentsWithCourseName(String courseName) {
	List<Student> listOfStudents = jdbcTemplate.query("SELECT * FROM schedule.students WHERE student_id IN"
		+ "(SELECT student_id FROM schedule.students_courses WHERE course_id IN "
		+ "(SELECT course_id FROM schedule.courses WHERE course_name = " + "'" + courseName + "'" + "))"
		+ " ORDER BY student_id", new BeanPropertyRowMapper<Student>(Student.class));

	for (Student student : listOfStudents) {
	    System.out.println(student);
	}

	return listOfStudents;
    }

    @Override
    public void add(Student entity) throws DaoException {
	String sql = PROPERTY_STUDENT_ADD;
	Object[] params = {entity.getGroupId(), entity.getFirstName(), entity.getLastName()};
	int result = jdbcTemplate.update(sql, params);
	
	if (result > 0) {
	    System.out.println("Created new student in base!");
	} else {
	    throw new DaoException("Student " + entity.getFirstName() + " " + entity.getLastName() + NOT_FOUND);
	}
    }

    @Override
    public Student findById(int studentId) {
	Student student = jdbcTemplate.queryForObject(
		"SELECT student_id, group_id, first_name, last_name FROM schedule.students WHERE student_id = " + studentId,
		new BeanPropertyRowMapper<Student>(Student.class));
	System.out.println(student);
	
	return student;
    }

    @Override
    public List<Student> findAll() {
	List<Student> listOfStudents = jdbcTemplate.query("SELECT * FROM schedule.students",
		new BeanPropertyRowMapper<Student>(Student.class));

	for (Student student : listOfStudents) {
	    System.out.println(student);
	}

	return listOfStudents;
    }

    @Override
    public void update(Student entity, int studentId) throws DaoException {
	String sql = PROPERTY_STUDENT_UPDATE;
	Object[] params = {entity.getGroupId(), entity.getFirstName(), entity.getLastName(), studentId};
	int result = jdbcTemplate.update(sql, params);
	
	if (result > 0) {
	    System.out.println(STUDENT_WITH_ID + studentId + " updated");
	} else {
	    throw new DaoException(STUDENT_WITH_ID + studentId + NOT_FOUND);
	}
    }

    @Override
    public void deleteById(int studentId) throws DaoException {
	String sql = PROPERTY_STUDENT_DELETE;
	Object[] params = {studentId};
	int result = jdbcTemplate.update(sql, params);
	
	if (result > 0) {
	    System.out.println(STUDENT_WITH_ID + studentId + " removed");
	} else {
	    throw new DaoException(STUDENT_WITH_ID + studentId + NOT_FOUND);
	}
    }
    
    private void addStudentToBase(Student student) throws DaoException {
	try {
	    add(student);
	} catch (DaoException e) {
	    throw new DaoException(String.format(MASK_MESSAGE_ADD_EXCEPTION, student), e);
	}
    }
    
    @Override
    public void createStudent(String firstName, String lastName) throws DaoException {
	Student student = Student.builder()
		.withFirstName(firstName)
		.withLastName(lastName)
		.withGroupId(DEFAULT_GROUP_ID)
		.build();
		
	addStudentToBase(student);
    }
}
