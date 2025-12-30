package com.sp.student_course_registration_system.daos.requestdaos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnrollmentRequest {
    private Long studentId;
    private Long courseId;
}
