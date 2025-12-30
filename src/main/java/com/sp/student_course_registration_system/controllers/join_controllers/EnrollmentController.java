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
        try {
           EnrollmentStudentResponse enrollmentStudentResponse = enrollmentService.findAllCourseOfStudent(studentId);

           if (enrollmentStudentResponse.getCourseDtoList().isEmpty()){
               return ResponseEntity.ok(new ApiResponse("This Student: " + enrollmentStudentResponse.getStudentName() + " has not joined to any courses"));
           }

           return ResponseEntity.ok(enrollmentStudentResponse);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse("Server Error while getting all the Courses of a student"));
        }
    }

    // Get all the Students of a specific Course, based on the passed in courseId
    @GetMapping("/all-course-student/couId={courseId}")
    ResponseEntity<?> getAllStudentsOfCourse(@PathVariable Long courseId){
        try {
           EnrollmentCourseResponse enrollmentCourseResponse = enrollmentService.findAllStudentOfCourse(courseId);

           if (enrollmentCourseResponse.getStudentDtoSet().isEmpty()){
               return ResponseEntity.ok(new ApiResponse("This Course: " + enrollmentCourseResponse.getCourseName() + " does not have any Students"));
           }

            return ResponseEntity.ok(enrollmentCourseResponse);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse("Server Error while getting all the Students of a course"));
        }
    }

    // Assign a Student to a Course
    @PostMapping("/student/assign-course")
    ResponseEntity<?> assignStudentToCourse(@RequestBody EnrollmentRequest enrollmentRequest){
        try {
            boolean studentAddedToCourse = enrollmentService.addStudentToCourse(enrollmentRequest);

            if (studentAddedToCourse){
                return ResponseEntity.ok(new ApiResponse("The Student with the Student Id: " + enrollmentRequest.getStudentId() + " has been assigned to the Course with the Course Id: " + enrollmentRequest.getCourseId()));
            }

            return ResponseEntity.ok(new ApiResponse("Error assigning Student into a Course"));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse("Server Error occurred while assigning a Student into a Course"));
        }
    }

    // Assign a course to a student

    // Remove a student from a course

    // Remove a course from a student
}
