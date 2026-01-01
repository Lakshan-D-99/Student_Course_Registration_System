package com.sp.student_course_registration_system.exceptions.courseExps;

public class CourseNotFoundException extends RuntimeException{

    public CourseNotFoundException(String message) {
        super(message);
    }
}
