package com.sp.student_course_registration_system.services;

import com.sp.student_course_registration_system.daos.dtos.StudentDto;
import com.sp.student_course_registration_system.daos.requestdaos.StudentRequest;
import com.sp.student_course_registration_system.daos.responsetdaos.StudentResponse;

import java.util.List;

public interface StudentService {

    // Get all the Students
    List<StudentDto> getAllTheStudents();

    // Get a single student based on the passed in studentId
    StudentDto getStudentById(Long studentId);

    // Get a single student based on the passed in studentEmail
    StudentDto getStudentByEmail(String studentEmail);

    // Add a new student
    boolean addStudent(StudentRequest studentRequest);

    // Updated an existing student
    boolean updateStudent(StudentDto studentDto);

    // Delete an existing student
    boolean deleteStudent(Long studentId);
}
