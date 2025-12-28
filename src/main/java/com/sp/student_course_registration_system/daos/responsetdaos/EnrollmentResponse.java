package com.sp.student_course_registration_system.daos.responsetdaos;

import com.sp.student_course_registration_system.daos.dtos.CourseDto;
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
public class EnrollmentResponse {
    private Long studentId;
    private Long courseId;
    private Set<StudentDto> studentDtoSet = new HashSet<>();
    private Set<CourseDto> courseDtoSet = new HashSet<>();
}
