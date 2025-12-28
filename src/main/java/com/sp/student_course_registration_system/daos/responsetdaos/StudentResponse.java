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
public class StudentResponse {
    private Long id;
    private String userName;
    private String email;
    private Set<CourseResponse> courseResponseSet = new HashSet<>();
}
