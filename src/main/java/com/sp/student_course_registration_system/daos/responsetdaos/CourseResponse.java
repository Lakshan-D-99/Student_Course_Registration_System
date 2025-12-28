package com.sp.student_course_registration_system.daos.responsetdaos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseResponse {
    private Long id;
    private String title;
    private int capacity;
    private Set<StudentResponse> studentResponseSet = new HashSet<>();
}
