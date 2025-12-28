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
    ResponseEntity<?> getAllTheStudents(){
        try{
           List<StudentDto> studentDtoList = studentService.getAllTheStudents();

           if (studentDtoList.isEmpty()){
               return ResponseEntity.ok(new ApiResponse("There are no Students to display"));
           }

           return ResponseEntity.ok(studentDtoList);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse("Error occurred while getting all the Students"));
        }
    }

    // Get a single student based on the passed in studentId
    @GetMapping("/single-student/stuId={studentId}")
    ResponseEntity<?> getSingleStudentById(@PathVariable Long studentId){
        try {
           StudentDto studentDto = studentService.getStudentById(studentId);

           if (studentDto.getId() == null){
               return ResponseEntity.ok(new ApiResponse("Student with the StudentId: " + studentId + " does not exists"));
           }

           return ResponseEntity.ok(studentDto);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse("Server Error occurred while getting the Student with the Student Id"));
        }
    }

    // Get a single student based on the passed in studentEmail
    @GetMapping("/single-student/stuEmail={studentEmail}")
    ResponseEntity<?> getStudentBasedOnEmail(@PathVariable String studentEmail){
        try {
            StudentDto studentDto = studentService.getStudentByEmail(studentEmail);

            if (studentDto.getId() == null){
                return ResponseEntity.ok(new ApiResponse("Student with the StudentEmail: " + studentEmail + " does not exists"));
            }

            return ResponseEntity.ok(studentDto);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse("Server Error occurred while getting the Student with the Student Email"));
        }
    }

    // Create and save a new student
    @PostMapping("/new-student")
    ResponseEntity<?> createNewStudent(@RequestBody StudentRequest studentRequest){
        try {
            boolean studentCreated = studentService.addStudent(studentRequest);

            if (studentCreated){
                return ResponseEntity.ok(new ApiResponse("A new Student has been created and saved in to the Database"));
            }

            return ResponseEntity.ok(new ApiResponse("Error creating a new Student"));
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse("Server Error occurred while creating a new Student"));
        }
    }

    // Update existing StudentData

    // Delete existing student
    @DeleteMapping("/remove-student/stuId={studentId}")
    ResponseEntity<?> removeExistingStudent(@PathVariable Long studentId){
        try {
            boolean studentRemoved = studentService.deleteStudent(studentId);

            if (studentRemoved){
                return ResponseEntity.ok(new ApiResponse("Student with the StudentId: " + studentId + " has been removed"));
            }

            return ResponseEntity.ok(new ApiResponse("Error removing the student with the StudentId: " + studentId));
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse("Server Error occurred while removing the Student with the StudentId: " + studentId));
        }

    }
}
