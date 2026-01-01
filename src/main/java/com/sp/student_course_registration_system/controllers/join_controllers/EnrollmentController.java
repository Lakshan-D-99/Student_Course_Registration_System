package com.sp.student_course_registration_system.controllers.join_controllers;

import com.sp.student_course_registration_system.Utils.ApiResponse;
import com.sp.student_course_registration_system.daos.requestdaos.EnrollmentRequest;
import com.sp.student_course_registration_system.daos.responsetdaos.EnrollmentCourseResponse;
import com.sp.student_course_registration_system.daos.responsetdaos.EnrollmentStudentResponse;
import com.sp.student_course_registration_system.services.join_services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api_v1/enroll")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // Get all the Courses, a student has assigned into
    @GetMapping("/all-student-course/stuId={studentId}")
    ResponseEntity<?> getAllCoursesOfStudent(@PathVariable Long studentId){
        EnrollmentStudentResponse enrollmentStudentResponse = enrollmentService.findAllCourseOfStudent(studentId);
        return ResponseEntity.ok(enrollmentStudentResponse);
    }

    // Get all the Students of a specific Course, based on the passed in courseId
    @GetMapping("/all-course-student/couId={courseId}")
    ResponseEntity<?> getAllStudentsOfCourse(@PathVariable Long courseId){
        EnrollmentCourseResponse enrollmentCourseResponse = enrollmentService.findAllStudentOfCourse(courseId);
        return ResponseEntity.ok(enrollmentCourseResponse);
    }

    // Assign a Student to a Course
    @PostMapping("/student/assign-course")
    public ResponseEntity<?> assignStudentToCourse(@RequestBody EnrollmentRequest enrollmentRequest){
        enrollmentService.addStudentToCourse(enrollmentRequest);
        return ResponseEntity.ok(new ApiResponse("The Student with the Student Id: " + enrollmentRequest.getStudentId() + " has been assigned to the Course with the Course Id: " + enrollmentRequest.getCourseId()));
    }

    // Remove a student from a course
    @PutMapping("/student/remove-course")
    public ResponseEntity<?> removeStudentFromCourse(@RequestBody EnrollmentRequest enrollmentRequest){
        enrollmentService.removeStudentFromCourse(enrollmentRequest);
        return ResponseEntity.ok(new ApiResponse("The Student with the StudentId: " + enrollmentRequest.getStudentId() + " has been removed from the Course with the CourseId: " + enrollmentRequest.getCourseId()));
    }

}
