package com.sp.student_course_registration_system.repositories;

import com.sp.student_course_registration_system.models.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseModel,Long> {
}
