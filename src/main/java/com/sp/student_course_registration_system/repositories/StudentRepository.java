package com.sp.student_course_registration_system.repositories;

import com.sp.student_course_registration_system.models.StudentModel;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel,Long> {

    Optional<StudentModel> findStudentModelByUserName(String userName);

    Optional<StudentModel> findStudentModelByEmail(String email);

    @Query("SELECT s.email FROM StudentModel s WHERE s.email=:studentMail")
    String getStudentModelEmail(@Param("studentMail") String studentMail);

    @Query("SELECT s.userName FROM StudentModel s WHERE s.userName=:userName")
    String getStudentModelUserName(@Param("userName") String userName);
}
