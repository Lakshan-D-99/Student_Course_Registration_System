package com.sp.student_course_registration_system.models.join_models;

import com.sp.student_course_registration_system.models.CourseModel;
import com.sp.student_course_registration_system.models.StudentModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
    This class is the join Table between Students and Courses, because one Student can assign themselves into many Courses and
    one Course can have multiple Students.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_model_id", "course_model_id"})
        }
)
public class EnrollmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "student_model_id")
    private StudentModel studentModel;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "course_model_id")
    private CourseModel courseModel;
}
