package com.sp.student_course_registration_system.daos.responsetdaos;

import com.sp.student_course_registration_system.daos.dtos.CourseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnrollmentStudentResponse {
    private Long studentId;
    private String studentName;
    private Set<CourseDto> courseDtoList = new HashSet<>();
}
