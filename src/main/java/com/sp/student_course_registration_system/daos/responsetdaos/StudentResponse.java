package com.sp.student_course_registration_system.daos.responsetdaos;

import com.sp.student_course_registration_system.daos.dtos.CourseDto;
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
    private Set<CourseDto> courseResponseSet = new HashSet<>();
}
