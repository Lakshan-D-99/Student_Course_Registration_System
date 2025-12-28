package com.sp.student_course_registration_system.repositories.join_repositories;

import com.sp.student_course_registration_system.models.join_models.EnrollmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentModel,Long> {

}
