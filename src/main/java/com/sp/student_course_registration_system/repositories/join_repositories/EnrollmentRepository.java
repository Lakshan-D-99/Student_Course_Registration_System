package com.sp.student_course_registration_system.repositories.join_repositories;

import com.sp.student_course_registration_system.daos.dtos.CourseDto;
import com.sp.student_course_registration_system.daos.dtos.StudentDto;
import com.sp.student_course_registration_system.models.CourseModel;
import com.sp.student_course_registration_system.models.StudentModel;
import com.sp.student_course_registration_system.models.join_models.EnrollmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentModel,Long> {

    @Query(
            value = "SELECT e FROM EnrollmentModel e WHERE e.studentModel.id=?1 AND e.courseModel.id=?2"

    )
    Optional<EnrollmentModel> findEnrollmentModelIdByStudentAndCourseId(Long courseId, Long studentId);

    Set<EnrollmentModel> findByStudentModel(StudentModel studentModel);

    Set<EnrollmentModel> findByCourseModel(CourseModel courseModel);

}
