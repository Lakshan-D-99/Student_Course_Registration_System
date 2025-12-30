package com.sp.student_course_registration_system.models;

import com.sp.student_course_registration_system.models.join_models.EnrollmentModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "course_model")
public class CourseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String title;
    private int capacity;

    @OneToMany(
            mappedBy = "courseModel",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<EnrollmentModel> enrollmentModelSet = new HashSet<>();

    public void addEnrollment(EnrollmentModel enrollmentModel) {
        enrollmentModelSet.add(enrollmentModel);
        enrollmentModel.setCourseModel(this);
    }

    public void removeEnrollment(EnrollmentModel enrollmentModel) {
        enrollmentModelSet.remove(enrollmentModel);
        enrollmentModel.setCourseModel(null);
    }


}
