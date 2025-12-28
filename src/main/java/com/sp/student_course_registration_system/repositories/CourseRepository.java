package com.sp.student_course_registration_system.repositories;

import com.sp.student_course_registration_system.models.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseModel,Long> {

    Optional<CourseModel> findCourseModelByTitle(String title);
}
