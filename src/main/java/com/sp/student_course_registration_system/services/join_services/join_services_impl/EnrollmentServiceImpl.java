package com.sp.student_course_registration_system.services.join_services.join_services_impl;

import com.sp.student_course_registration_system.Utils.ModelMapper;
import com.sp.student_course_registration_system.daos.dtos.EnrollmentDto;
import com.sp.student_course_registration_system.models.CourseModel;
import com.sp.student_course_registration_system.models.StudentModel;
import com.sp.student_course_registration_system.models.join_models.EnrollmentModel;
import com.sp.student_course_registration_system.repositories.CourseRepository;
import com.sp.student_course_registration_system.repositories.StudentRepository;
import com.sp.student_course_registration_system.services.join_services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EnrollmentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional
    public boolean addStudentToCourse(EnrollmentDto enrollmentDto) {

        if (enrollmentDto.getStudentId() <= 0 || enrollmentDto.getCourseId() <= 0){
            System.out.println("Enrollment details are required to enroll in a course");
            return false;
        }

        // Check if the Enrolling student exists
        Optional<StudentModel> studentModel = studentRepository.findById(enrollmentDto.getStudentId());

        if (studentModel.isEmpty()){
            System.out.println("The Student with the StudentId: " + enrollmentDto.getStudentId() + " does not exists");
            return false;
        }

        // Check if the course is available
        Optional<CourseModel> courseModel = courseRepository.findById(enrollmentDto.getCourseId());

        if (courseModel.isEmpty()){
            System.out.println("The Course with the CourseId: " + enrollmentDto.getCourseId() + " does not exists");
            return false;
        }

        // Check if the Course capacity does not exceed
        if (courseModel.get().getCapacity() == 0){
            System.out.println("The Course capacity is at a maximum");
            return false;
        }

        EnrollmentModel enrollmentModel = new EnrollmentModel();
        enrollmentModel.setStudentModel(studentModel.get());
        enrollmentModel.setCourseModel(courseModel.get());

        courseModel.get().setCapacity(courseModel.get().getCapacity() - 1);

        courseRepository.save(courseModel.get());

        return true;
    }

    @Override
    public boolean addCourseToStudent(EnrollmentDto enrollmentDto) {
        return false;
    }

    @Override
    public boolean removeStudentFromCourse(EnrollmentDto enrollmentDto) {
        return false;
    }

    @Override
    public boolean removeCourseFromStudent(EnrollmentDto enrollmentDto) {
        return false;
    }
}
