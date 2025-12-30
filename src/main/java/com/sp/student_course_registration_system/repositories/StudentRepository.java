package com.sp.student_course_registration_system.repositories;

import com.sp.student_course_registration_system.models.StudentModel;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel,Long> {

    Optional<StudentModel> findStudentModelByEmail(String email);
}
