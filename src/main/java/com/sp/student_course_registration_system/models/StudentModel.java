package com.sp.student_course_registration_system.models;

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
public class StudentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String userName;
    @Column(unique = true)
    private String email;

    // We need to create a relationship between Student and Courses.
    // One Student can enroll in many courses and a Course can have multiple Students.
    // So it will be a Many-to-Many Relationship between Students and Courses
    // Student will be the child class, because a Course can exists even without having any Students
    @ManyToMany(mappedBy = "studentModels")
    private Set<CourseModel> courseModelSet = new HashSet<>();
}
