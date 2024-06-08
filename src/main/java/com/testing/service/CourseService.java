package com.testing.service;

import java.util.List;
import java.util.Optional;

import com.testing.entity.Course;

import jakarta.servlet.http.HttpServletResponse;

public interface CourseService {

	public String createCourse(Course course);
	
	public List<Course> allCourses();
	
	public Optional<Course> getCourse(Integer courseId);
	
	public String updateCourse(Integer courseId, Course course);
	
	public String deleteCourse(Integer courseId);
	
	public void generateExcel(HttpServletResponse response) throws Exception;
	
	public void generatePdf(HttpServletResponse response) throws Exception;
}
