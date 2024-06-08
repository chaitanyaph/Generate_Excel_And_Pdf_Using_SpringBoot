package com.testing.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.testing.entity.Course;
import com.testing.service.CourseService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@GetMapping("/excel")
	public void generateExcelReport(HttpServletResponse response) throws Exception {
		
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=courses.xls";
		
		response.setHeader(headerKey, headerValue);
		
		courseService.generateExcel(response);
	}
	
	
	@GetMapping("/pdf")
	public void pdfReport(HttpServletResponse response) throws Exception{
		
		response.setContentType("application/pdf");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;fileName=data.pdf";
		
		response.setHeader(headerKey, headerValue);
		
		courseService.generatePdf(response);
	}
	
	
	
	
	
	@PostMapping("/create")
	public ResponseEntity<String> createCourse(@RequestBody Course course){
		
		String createCourse = courseService.createCourse(course);
		return new ResponseEntity<>(createCourse, HttpStatus.CREATED);
	}
	
	@GetMapping("/courses")
	public ResponseEntity<List<Course>> getAllCourses(){
		
		 List<Course> allCourses = courseService.allCourses();
		return new ResponseEntity<>(allCourses, HttpStatus.OK);
	}
	
	@GetMapping("/courses/{courseId}")
	public ResponseEntity<Optional<Course>> getCourse(@PathVariable Integer courseId){
		
		Optional<Course> course = courseService.getCourse(courseId);
		return new ResponseEntity<>(course, HttpStatus.OK);
	}
	
	@PutMapping("/courses/{courseId}")
	public ResponseEntity<String> updateCourse(@RequestBody Course course, @PathVariable Integer courseId){
		
		 String updateCourse = courseService.updateCourse(courseId, course);
		return new ResponseEntity<>(updateCourse, HttpStatus.OK);
	}
	
	@DeleteMapping("/courses/{courseId}")
	public ResponseEntity<String> deleteCourse(@PathVariable Integer courseId){
		
		String deleteCourse = courseService.deleteCourse(courseId);
		return new ResponseEntity<>(deleteCourse, HttpStatus.OK);
		
	}
}
