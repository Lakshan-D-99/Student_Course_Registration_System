package com.sp.student_course_registration_system.daos.responsetdaos;

import com.sp.student_course_registration_system.daos.dtos.StudentDto;
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
public class EnrollmentCourseResponse {
    private Long id;
    private String courseName;
    private Set<StudentDto> studentDtoSet = new HashSet<>();
}
