package com.sp.student_course_registration_system.exceptions;

import com.sp.student_course_registration_system.Utils.ApiResponse;
import com.sp.student_course_registration_system.exceptions.courseExps.CourseCapacityException;
import com.sp.student_course_registration_system.exceptions.courseExps.CourseCreatingException;
import com.sp.student_course_registration_system.exceptions.courseExps.CourseNotFoundException;
import com.sp.student_course_registration_system.exceptions.enrollmentExps.StudentCourseException;
import com.sp.student_course_registration_system.exceptions.studentExps.StudentCreatingException;
import com.sp.student_course_registration_system.exceptions.studentExps.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //--------------- Student based Exceptions ---------------//

    // Student not found exception
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<?> handleStudentNotFoundException(StudentNotFoundException studentNotFoundException){
        ApiResponse studentNotFound = new ApiResponse(studentNotFoundException.getMessage());
        return new ResponseEntity<>(studentNotFound, HttpStatus.NOT_FOUND);
    }

    // Error creating student exception
    @ExceptionHandler(StudentCreatingException.class)
    public ResponseEntity<?> handleStudentCreatingException(StudentCreatingException studentCreatingException){
        ApiResponse studentCreatingError = new ApiResponse(studentCreatingException.getMessage());
        return new ResponseEntity<>(studentCreatingError,HttpStatus.NO_CONTENT);
    }

    //--------------- Course based Exceptions ---------------//

    // Course not found exception
    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<?> handleCourseNotFoundException(CourseNotFoundException courseNotFoundException){
        ApiResponse courseNotFound = new ApiResponse(courseNotFoundException.getMessage());
        return new ResponseEntity<>(courseNotFound,HttpStatus.NOT_FOUND);
    }

    // Error creating a new course exception
    @ExceptionHandler(CourseCreatingException.class)
    public ResponseEntity<?> handleCourseCreatingException(CourseCreatingException courseCreatingException){
        ApiResponse courseCreatingError = new ApiResponse(courseCreatingException.getMessage());
        return new ResponseEntity<>(courseCreatingError,HttpStatus.NO_CONTENT);
    }

    // When creating or assign a Student into a Course, it's capacity should be an allowed capacity
    @ExceptionHandler(CourseCapacityException.class)
    public ResponseEntity<?> handleCourseCapacityException(CourseCapacityException courseCapacityException){
        ApiResponse courseCapacityError = new ApiResponse(courseCapacityException.getMessage());
        return new ResponseEntity<>(courseCapacityError,HttpStatus.NO_CONTENT);
    }

    //--------------- Student course enrollment Exceptions ---------------//

    @ExceptionHandler(StudentCourseException.class)
    public ResponseEntity<?> handleStudentCourseException(StudentCourseException studentCourseException){
        ApiResponse studentCourseError = new ApiResponse(studentCourseException.getMessage());
        return new ResponseEntity<>(studentCourseError,HttpStatus.NOT_FOUND);
    }
}
