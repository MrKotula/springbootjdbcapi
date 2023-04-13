package ua.foxminded.springbootjdbcapi.entity;

import java.util.Objects;

public class Course {
    private Integer courseId;
    private String courseName;
    private String courseDescription;
    
    public Course() {
	
    }
    
    private Course(Builder builder) {
	this.courseId = builder.courseId;
	this.courseName = builder.courseName;
	this.courseDescription = builder.courseDescription;
    }
    
    public static Builder builder() {
	return new Builder();
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }  

    @Override
    public int hashCode() {
	return Objects.hash(courseDescription, courseId, courseName);
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	Course other = (Course) obj;
	
	return 	Objects.equals(courseDescription, other.courseDescription) &&
		Objects.equals(courseId, other.courseId) &&
		Objects.equals(courseName, other.courseName);
    }

    @Override
    public String toString() {
	return "Course [courseId=" + courseId + '\'' + ", courseName=" + courseName + '\'' + ", courseDescription="
		+ courseDescription + "]";
    }
    
    public static class Builder {
	private Integer courseId;
	private String courseName;
	private String courseDescription;
	
	private Builder() {
	    
	}
	
	public Builder withCourseId(Integer courseId) {
	    this.courseId = courseId;
	    return this;
	}
	
	public Builder withCourseName(String courseName) {
	    this.courseName = courseName;
	    return this;
	}
	
	public Builder withCourseDescription(String courseDescription) {
	    this.courseDescription = courseDescription;
	    return this;
	}
	
	public Course build() {
	    return new Course(this);
	}
    }
}
