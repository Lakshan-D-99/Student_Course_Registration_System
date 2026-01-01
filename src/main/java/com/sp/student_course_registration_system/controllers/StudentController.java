package com.sp.student_course_registration_system.controllers;

import com.sp.student_course_registration_system.Utils.ApiResponse;
import com.sp.student_course_registration_system.daos.dtos.StudentDto;
import com.sp.student_course_registration_system.daos.requestdaos.StudentRequest;
import com.sp.student_course_registration_system.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api_v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Get all the Students
    @GetMapping("/all-students")
    ResponseEntity<?> getAllTheStudents() {
        try {
            List<StudentDto> studentDtoList = studentService.getAllTheStudents();

            if (studentDtoList.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse("There are no Students to display"));
            }

            return ResponseEntity.ok(studentDtoList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse("Error occurred while getting all the Students"));
        }
    }

    // Get a single student based on the passed in studentId
    @GetMapping("/single-student/stuId={studentId}")
    ResponseEntity<?> getSingleStudentById(@PathVariable Long studentId) {
        StudentDto studentDto = studentService.getStudentById(studentId);
        return ResponseEntity.ok(studentDto);
    }

    // Get a single student based on the passed in studentEmail
    @GetMapping("/single-student/stuEmail={studentEmail}")
    ResponseEntity<?> getStudentBasedOnEmail(@PathVariable String studentEmail) {
        StudentDto studentDto = studentService.getStudentByEmail(studentEmail);
        return ResponseEntity.ok(studentDto);
    }

    // Create and save a new student
    @PostMapping("/new-student")
    ResponseEntity<?> createNewStudent(@RequestBody StudentRequest studentRequest) {
        studentService.addStudent(studentRequest);
        return ResponseEntity.ok(new ApiResponse("A new Student has been created and saved in to the Database"));
    }

    // Update existing StudentData

    // Delete existing student
    @DeleteMapping("/remove-student/stuId={studentId}")
    ResponseEntity<?> removeExistingStudent(@PathVariable Long studentId) {
        try {
            studentService.deleteStudent(studentId);
            return ResponseEntity.ok(new ApiResponse("Student with the StudentId: " + studentId + " has been removed"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse("Server Error occurred while removing the Student with the StudentId: " + studentId));
        }

    }
}
