package ua.foxminded.springbootjdbcapi.dao;

import java.util.List;
import ua.foxminded.springbootjdbcapi.entity.Student;
import ua.foxminded.springbootjdbcapp.exception.DaoException;

public interface StudentDao extends Dao<Student, Student> {
    void addStudentCourse(int studentId, int courseId) throws DaoException;

    void removeStudentFromCourse(int studentId, int courseId) throws DaoException;

    List<Student> getStudentsWithCourseName(String courseName);
    
    void createStudent(String firstName, String lastName) throws DaoException;
}
