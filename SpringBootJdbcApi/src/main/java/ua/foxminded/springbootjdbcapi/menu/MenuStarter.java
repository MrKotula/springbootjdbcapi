package ua.foxminded.springbootjdbcapi.menu;

import ua.foxminded.springbootjdbcapp.exception.DaoException;

public interface MenuStarter {
    void startMenu() throws DaoException;

    void findAllGroupsWithStudentCount() throws DaoException;

    void findAllStudentsToCourseName() throws DaoException;

    void addStudent() throws DaoException;

    void deleteStudentById() throws DaoException;

    void addStudentToCourse() throws DaoException;

    void removeStudentFromCourse() throws DaoException;
}
