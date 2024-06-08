package com.testing.service;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.testing.entity.Course;
import com.testing.repository.CourseRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CourseServiceImpl implements CourseService{

	@Autowired
	private CourseRepository courseRepository;
	
	
	@Override
	public String createCourse(Course course) {
		
		courseRepository.save(course);
		return "Course created Successfully";
	}

	@Override
	public List<Course> allCourses() {
		
		List<Course> findAll = courseRepository.findAll();
		return findAll;
	}

	@Override
	public Optional<Course> getCourse(Integer courseId) {
		
		Optional<Course> findById = courseRepository.findById(courseId);
		return findById;
		
	}

	@Override
	public String updateCourse(Integer courseId, Course course) {
		
		Optional<Course> findById = courseRepository.findById(courseId);
		
		if(findById.get() != null) {
			
			course.setCourseName(course.getCourseName());
			course.setCoursePrice(course.getCoursePrice());
			
			courseRepository.save(course);
			
			return "course updated Successfully";
		}
		
		return "Course not found with given course "+ course.getCourseId();
	}

	@Override
	public String deleteCourse(Integer courseId) {
		
		Optional<Course> findById = courseRepository.findById(courseId);
		
		if(findById.get() != null) {
			
			courseRepository.deleteById(courseId);
			return "Course Deleted Successfully";
		
		}
		return "course not found with given courseId "+ findById;
	}

	@Override
	public void generateExcel(HttpServletResponse response) throws Exception{
		
		List<Course> allCourses = courseRepository.findAll();
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Courses Info");
		HSSFRow row = sheet.createRow(0);
		
		row.createCell(0).setCellValue("Course ID");
		row.createCell(1).setCellValue("Course Name");
		row.createCell(2).setCellValue("Course Price");
		
		int dataRowIndex = 1;
		
		for(Course course : allCourses) {
			
			HSSFRow dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(course.getCourseId());
			dataRow.createCell(1).setCellValue(course.getCourseName());
			dataRow.createCell(2).setCellValue(course.getCoursePrice());
			
			dataRowIndex++;
		}
		
		ServletOutputStream ops = response.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close();
		
	}
	
	@Override
	public void generatePdf(HttpServletResponse response) throws Exception {
		
		List<Course> allCourses = courseRepository.findAll();
		
		Document document = new Document(PageSize.A4);
		
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
         
        Paragraph p = new Paragraph("Course Details", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
        
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.5f});
        table.setSpacingBefore(10);
        
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
         
        Font fonts = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase(" Course ID", fonts));
         
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Course Name", fonts));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Course Price", fonts));
        table.addCell(cell);
        
        for(Course course: allCourses) {
        	
        	table.addCell(String.valueOf(course.getCourseId()));
        	table.addCell(course.getCourseName());
        	table.addCell(String.valueOf(course.getCoursePrice()));
        	
        }
        
        document.add(table);
        
        document.close();

	}

}
