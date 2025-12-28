package com.sp.student_course_registration_system.services.join_services;

import com.sp.student_course_registration_system.daos.dtos.EnrollmentDto;

import java.util.List;

public interface EnrollmentService {

    // Get all the Courses, a student has assigned into

    // Get all the Students of a specific Course, based on the passed in courseId

    // Assign a Student to a Course
    boolean addStudentToCourse(EnrollmentDto enrollmentDto);

    // Assign a course to a student
    boolean addCourseToStudent(EnrollmentDto enrollmentDto);

    // Remove a student from a course
    boolean removeStudentFromCourse(EnrollmentDto enrollmentDto);

    // Remove a course from a student
    boolean removeCourseFromStudent(EnrollmentDto enrollmentDto);


}
