package com.sp.student_course_registration_system.services;

import com.sp.student_course_registration_system.daos.dtos.CourseDto;

import java.util.List;

public interface CourseService {

    // Get all the available courses
    List<CourseDto> getAllCourses();

    // Get a course based on the passed in course id
    CourseDto getCourseById(Long courseId);

    // Get a single course based on the passed in course title
    List<CourseDto> getAllCoursesByTitle(String courseTitle);

    // Add a new course into the system
    boolean saveCourse(CourseDto courseDto);

    // Update an existing course in the system
    boolean updateCourse(CourseDto courseDto);

    // Delete an existing course
    boolean deleteCourse(Long courseId);
}
