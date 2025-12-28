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

    // Here we have to define the Many-To-Many Relationship between Courses and Students
    // Course class will be the Parent class
//    @ManyToMany()
//    @JoinTable(
//            name = "enrollment",
//            joinColumns = @JoinColumn(name = "course_model_id"),
//            inverseJoinColumns = @JoinColumn(name = "student_model_id")
//    )
//    private Set<StudentModel> studentModels = new HashSet<>();
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
