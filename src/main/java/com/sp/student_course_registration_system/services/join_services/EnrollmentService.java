package com.sp.student_course_registration_system.services.join_services;

import com.sp.student_course_registration_system.daos.requestdaos.EnrollmentRequest;
import com.sp.student_course_registration_system.daos.responsetdaos.EnrollmentCourseResponse;
import com.sp.student_course_registration_system.daos.responsetdaos.EnrollmentStudentResponse;

public interface EnrollmentService {

    // Get all the Courses, a student has assigned into
    EnrollmentStudentResponse findAllCourseOfStudent(Long studentId);

    // Get all the Students of a specific Course, based on the passed in courseId
    EnrollmentCourseResponse findAllStudentOfCourse(Long courseId);

    // Assign a Student to a Course
    void addStudentToCourse(EnrollmentRequest enrollmentRequest);

    // Remove a student from a course
    void removeStudentFromCourse(EnrollmentRequest enrollmentRequest);




}
