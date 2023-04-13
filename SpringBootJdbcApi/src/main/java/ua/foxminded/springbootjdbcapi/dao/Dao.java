package ua.foxminded.springbootjdbcapi.dao;

import java.util.List;
import ua.foxminded.springbootjdbcapp.exception.DaoException;

public interface Dao<T, ID> {
    void add(T entity) throws DaoException;

    T findById(int id);

    List<T> findAll();

    void update(T entity, int studentId) throws DaoException;

    void deleteById(int id) throws DaoException;
}
