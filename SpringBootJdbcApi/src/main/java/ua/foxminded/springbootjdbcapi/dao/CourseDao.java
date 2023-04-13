package ua.foxminded.springbootjdbcapi.dao;

import java.util.List;
import ua.foxminded.springbootjdbcapi.entity.Course;

public interface CourseDao extends Dao<Course, Integer> {
    List<Course> getCoursesForStudentId(int studentId);

    List<Course> getCoursesMissingForStudentId(int studentId);
}
