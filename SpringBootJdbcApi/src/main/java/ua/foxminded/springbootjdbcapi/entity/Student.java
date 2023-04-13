package ua.foxminded.springbootjdbcapi.entity;

import java.util.Objects;

public class Student {
    private Integer studentId;
    private Integer groupId;
    private String firstName;
    private String lastName;
    
    public Student() {
	
    }
    
    public Student(Builder builder) {
	this.studentId = builder.studentId;
	this.groupId = builder.groupId;
	this.firstName = builder.firstName;
	this.lastName = builder.lastName;
    }
    
    public static Builder builder() {
	return new Builder();
    }
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
    
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public int hashCode() {
	return Objects.hash(firstName, groupId, lastName, studentId);
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	Student other = (Student) obj;
	
	return 	Objects.equals(firstName, other.firstName) && 
		Objects.equals(groupId, other.groupId) && 
		Objects.equals(studentId, other.studentId) && 
		Objects.equals(lastName, other.lastName);
    }

    @Override
    public String toString() {
	return "Student [studentId=" + studentId + '\'' + ", groupId=" + groupId + '\'' +
		", firstName=" + firstName + '\'' + ", lastName=" + lastName + "]";
    }
    
    public static class Builder {
	private Integer studentId;
	private Integer groupId;
	private String firstName;
	private String lastName;
	
	private Builder() {
	    
	}
	
	public Builder withStudentId(Integer studentId) {
	    this.studentId = studentId;
	    return this;
	}
	
	public Builder withGroupId(Integer groupId) {
	    this.groupId = groupId;
	    return this;
	}
	
	public Builder withFirstName(String firstName) {
	    this.firstName = firstName;
	    return this;
	}
	
	public Builder withLastName(String lastName) {
	    this.lastName = lastName;
	    return this;
	}
	
	public Student build() {
	    return new Student(this);
	}
    }
}
