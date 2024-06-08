package com.testing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testing.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer>{

}
