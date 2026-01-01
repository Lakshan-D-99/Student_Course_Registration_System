package com.sp.student_course_registration_system.services;

import com.sp.student_course_registration_system.daos.dtos.StudentDto;
import com.sp.student_course_registration_system.daos.requestdaos.StudentRequest;

import java.util.List;

public interface StudentService {

    // Get all the Students
    List<StudentDto> getAllTheStudents();

    // Get a single student based on the passed in studentId
    StudentDto getStudentById(Long studentId);

    // Get a single student based on the passed in StudentUserName
    StudentDto getStudentByUserName(String userName);

    // Get a single student based on the passed in studentEmail
    StudentDto getStudentByEmail(String studentEmail);

    // Add a new student
    void addStudent(StudentRequest studentRequest);

    // Updated an existing student
    void updateStudent(StudentDto studentDto);

    // Delete an existing student
    void deleteStudent(Long studentId);
}
