package ua.foxminded.springbootjdbcapi.dao;

import java.util.List;
import ua.foxminded.springbootjdbcapi.entity.Group;

public interface GroupDao extends Dao<Group, Integer> {
    List<Group> getGroupsWithLessEqualsStudentCount(int studentCount);
}
