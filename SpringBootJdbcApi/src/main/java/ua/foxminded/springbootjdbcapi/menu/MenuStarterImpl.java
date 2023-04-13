package ua.foxminded.springbootjdbcapi.menu;

import java.util.InputMismatchException;
import java.util.List;
import ua.foxminded.springbootjdbcapi.dao.CourseDao;
import ua.foxminded.springbootjdbcapi.dao.GroupDao;
import ua.foxminded.springbootjdbcapi.dao.StudentDao;
import ua.foxminded.springbootjdbcapi.entity.Student;
import ua.foxminded.springbootjdbcapi.viewprovider.ViewProvider;
import ua.foxminded.springbootjdbcapp.exception.DaoException;

public class MenuStarterImpl implements MenuStarter {
    private static final String MESSAGE_FIRST_NAME = "Input first name: ";
    private static final String MESSAGE_LAST_NAME = "Input last name: ";
    private static final String MESSAGE_INPUT_COURSE_NAME = "Input course name: ";
    private static final String MESSAGE_INPUT_STUDENT_ID = "Input student_id for add to course: ";
    private static final String MESSAGE_INPUT_COURSE_ID = "Input course_id from list courses for removing student: ";
    private static final String MESSAGE_INPUT_STUDENT_ID_REMOVE = "Input student_id for remove from course: ";
    private static final String MESSAGE_EXCEPTION_NOT_NUMBER = "You inputted not a number. Please input number ";
    private static final String MESSAGE_QUIT_APPLICATION = "Quitting application...";

    private StudentDao studentDao;
    private CourseDao courseDao;
    private GroupDao groupDao;
    private ViewProvider viewProvider;

    public MenuStarterImpl(StudentDao studentDaoImpl, CourseDao courseDaoImpl, GroupDao groupDao, ViewProvider viewProvider) {
	this.studentDao = studentDaoImpl;
	this.courseDao = courseDaoImpl;
	this.groupDao = groupDao;
	this.viewProvider = viewProvider;
    }

    @Override
    public void startMenu() throws DaoException {
	boolean isWork = true;
	while (isWork) {
	    viewProvider.printMessage("\nMain menu\n" + "1. Find all groups with less or equals student count\n"
		    + "2. Find all students related to course with given name\n" + "3. Add new student\n"
		    + "4. Delete student by STUDENT_ID\n" + "5. Add a student to the course (from a list)\n"
		    + "6. Remove the student from one of his or her courses\n" + "0. Exit\n"
		    + "Enter a number from the list:\n");
	    int choose = viewProvider.readInt();

	    switch (choose) {
	    case 1:
		findAllGroupsWithStudentCount();
		break;
	    case 2:
		findAllStudentsToCourseName();
		break;
	    case 3:
		addStudent();
		break;
	    case 4:
		deleteStudentById();
		break;
	    case 5:
		addStudentToCourse();
		break;
	    case 6:
		removeStudentFromCourse();
		break;
	    case 0:
		isWork = false;
		viewProvider.printMessage(MESSAGE_QUIT_APPLICATION);
		break;
	    default:
		viewProvider.printMessage("Incorrect command\n");
	    }
	}
    }

    @Override
    public void findAllGroupsWithStudentCount() throws DaoException {
	int studentCount = inputStudentCount();

	groupDao.getGroupsWithLessEqualsStudentCount(studentCount);
    }

    @Override
    public void findAllStudentsToCourseName() throws DaoException {
	System.out.print(MESSAGE_INPUT_COURSE_NAME);
	String courseName = viewProvider.read();

	List<Student> students = studentDao.getStudentsWithCourseName(courseName);
	students.stream().forEach(System.out::println);
    }

    @Override
    public void addStudent() throws DaoException {
	System.out.print(MESSAGE_FIRST_NAME);
	String firstName = viewProvider.read();

	System.out.print(MESSAGE_LAST_NAME);
	String lastName = viewProvider.read();

	studentDao.createStudent(firstName, lastName);
    }

    @Override
    public void deleteStudentById() throws DaoException {
	int studentId = inputStudentId();

	studentDao.deleteById(studentId);
    }

    @Override
    public void addStudentToCourse() throws DaoException {
	System.out.print(MESSAGE_INPUT_STUDENT_ID);
	int studentId = viewProvider.readInt();

	courseDao.getCoursesMissingForStudentId(studentId);

	System.out.print("Input course_id from list courses for adding student: ");
	int courseId = viewProvider.readInt();

	studentDao.addStudentCourse(studentId, courseId);
    }

    @Override
    public void removeStudentFromCourse() throws DaoException {
	System.out.print(MESSAGE_INPUT_STUDENT_ID_REMOVE);
	int studentId = viewProvider.readInt();

	courseDao.getCoursesForStudentId(studentId);

	System.out.print(MESSAGE_INPUT_COURSE_ID);
	int courseId = viewProvider.readInt();

	studentDao.removeStudentFromCourse(studentId, courseId);
    }

    private int inputStudentId() throws DaoException {
	int result = -1;

	do {
	    System.out.print("Input student_id for deleting: ");
	    try {
		result = viewProvider.readInt();
	    } catch (InputMismatchException e) {
		throw new DaoException(MESSAGE_EXCEPTION_NOT_NUMBER);
	    }
	} while (result == -1);

	return result;
    }

    private int inputStudentCount() {
	int studentCount = 0;
	System.out.print("Input student count: ");

	while (viewProvider.readBoolean()) {
	    studentCount = viewProvider.readInt();
	    break;
	}

	return studentCount;
    }
}
