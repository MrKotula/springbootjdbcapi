package ua.foxminded.springbootjdbcapi.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.foxminded.springbootjdbcapi.dao.GroupDao;
import ua.foxminded.springbootjdbcapi.entity.Group;
import ua.foxminded.springbootjdbcapp.exception.DaoException;

@Repository
public class GroupDaoImpl implements GroupDao {
    private static final String PROPERTY_GROUP_ADD = "INSERT INTO schedule.groups(group_name) VALUES (?)";
    private static final String PROPERTY_GROUP_GET_BY_ID = "SELECT group_id, group_name FROM schedule.groups WHERE group_id = ";
    private static final String PROPERTY_GROUP_GET_ALL = "SELECT * FROM schedule.groups";
    private static final String PROPERTY_GROUP_UPDATE = "UPDATE schedule.groups SET group_name = ? WHERE group_id = ?";
    private static final String PROPERTY_GROUP_DELETE = "DELETE FROM schedule.groups WHERE group_id = ?";
    private static final String NOT_FOUND = " not found!";
    private static final String Group_WITH_ID = "Group with id ";
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public GroupDaoImpl(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void add(Group entity) throws DaoException {
	String sql = PROPERTY_GROUP_ADD;
	Object[] params = {entity.getGroupName()};
	int result = jdbcTemplate.update(sql, params);
	
	if (result > 0) {
	    System.out.println("Created new group in base!");
	} else {
	    throw new DaoException("Group" + entity.getGroupName() + NOT_FOUND);
	}
    }
    
    @Override
    public Group findById(int id) {
	Group group = jdbcTemplate.queryForObject(
		PROPERTY_GROUP_GET_BY_ID + id, new BeanPropertyRowMapper<Group>(Group.class));
	System.out.println(group);
	
	return group;
    }
    
    @Override
    public List<Group> findAll() {
	List<Group> listOfgroups = jdbcTemplate.query(PROPERTY_GROUP_GET_ALL,
		new BeanPropertyRowMapper<Group>(Group.class));

	for (Group group : listOfgroups) {
	    System.out.println(group);
	}

	return listOfgroups;
    }
    
    @Override
    public void update(Group entity, int studentId) throws DaoException {
	String sql = PROPERTY_GROUP_UPDATE;
	Object[] params = {entity.getGroupName(), studentId};
	int result = jdbcTemplate.update(sql, params);
	
	if (result > 0) {
	    System.out.println(Group_WITH_ID + studentId + " updated");
	} else {
	    throw new DaoException(Group_WITH_ID + studentId + NOT_FOUND);
	}
    }
    
    @Override
    public void deleteById(int id) throws DaoException {
	String sql = PROPERTY_GROUP_DELETE;
	Object[] params = {id};
	int result = jdbcTemplate.update(sql, params);
	
	if (result > 0) {
	    System.out.println(Group_WITH_ID + id + " removed");
	} else {
	    throw new DaoException(Group_WITH_ID + id + NOT_FOUND);
	}
    }
    
    @Override
    public List<Group> getGroupsWithLessEqualsStudentCount(int studentCount) {
	List<Group> listOfGroups = jdbcTemplate.query("SELECT groups.group_id, groups.group_name, COUNT(student_id) "
		    + "FROM schedule.groups " + "LEFT JOIN schedule.students ON groups.group_id = students.group_id "
		    + "GROUP BY groups.group_id, groups.group_name " + "HAVING COUNT(student_id) <= " + studentCount,
		    new BeanPropertyRowMapper<Group>(Group.class));

	for (Group group : listOfGroups) {
	    System.out.println(group);
	}

	return listOfGroups;
    }
}
