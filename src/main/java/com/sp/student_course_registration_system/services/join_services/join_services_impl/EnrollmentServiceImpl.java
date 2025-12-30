package com.sp.student_course_registration_system.services.join_services.join_services_impl;

import com.sp.student_course_registration_system.Utils.ModelMapper;
import com.sp.student_course_registration_system.daos.dtos.CourseDto;
import com.sp.student_course_registration_system.daos.dtos.StudentDto;
import com.sp.student_course_registration_system.daos.requestdaos.EnrollmentRequest;
import com.sp.student_course_registration_system.daos.responsetdaos.EnrollmentCourseResponse;
import com.sp.student_course_registration_system.daos.responsetdaos.EnrollmentStudentResponse;
import com.sp.student_course_registration_system.models.CourseModel;
import com.sp.student_course_registration_system.models.StudentModel;
import com.sp.student_course_registration_system.models.join_models.EnrollmentModel;
import com.sp.student_course_registration_system.repositories.CourseRepository;
import com.sp.student_course_registration_system.repositories.StudentRepository;
import com.sp.student_course_registration_system.repositories.join_repositories.EnrollmentRepository;
import com.sp.student_course_registration_system.services.join_services.EnrollmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EnrollmentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.modelMapper = modelMapper;
    }

    //--------------- Helper Methods ---------------//

    private boolean checkEnrollmentRequest(EnrollmentRequest enrollmentRequest){
        if (enrollmentRequest.getStudentId() <= 0 || enrollmentRequest.getCourseId() <= 0){
            System.out.println("Enrollment details are required to enroll in a course");
            return false;
        }
        return true;
    }


    @Override
    public EnrollmentStudentResponse findAllCourseOfStudent(Long studentId) {

        // Find the Student through passed in studentId
        StudentModel studentModel = studentRepository.findById(studentId).orElseThrow(
                ()-> new EntityNotFoundException("Student with the StudentId: " + studentId + " does not exists"));


        // Based on the Student, we can find all the Courses, that this Student is a part of
        Set<EnrollmentModel> enrollmentModelsSet = enrollmentRepository.findByStudentModel(studentModel);

        // Get and convert all the courses from the EnrollmentModel
        Set<CourseDto> courseDtoSet = enrollmentModelsSet
                .stream()
                .map(course -> {
                    CourseDto courseDto = new CourseDto();
                    courseDto.setId(course.getCourseModel().getId());
                    courseDto.setTitle(course.getCourseModel().getTitle());
                    courseDto.setCapacity(course.getCourseModel().getCapacity());
                    return courseDto;
                }).collect(Collectors.toSet());

        return new EnrollmentStudentResponse(studentModel.getId(), studentModel.getUserName(), courseDtoSet);
    }

    @Override
    public EnrollmentCourseResponse findAllStudentOfCourse(Long courseId) {

        // Find the Course through passed in course id
        CourseModel courseModel = courseRepository.findById(courseId).orElseThrow(
                ()-> new EntityNotFoundException("Course with the CourseId: " + courseId + " does not exists"));

        Set<EnrollmentModel> enrollmentModelSet = enrollmentRepository.findByCourseModel(courseModel);

        Set<StudentDto> studentDtoSet = enrollmentModelSet
                .stream()
                .map(e -> new StudentDto(
                        e.getStudentModel().getId(),
                        e.getStudentModel().getUserName(),
                        e.getStudentModel().getEmail()
                )).collect(Collectors.toSet());

        return new EnrollmentCourseResponse(courseModel.getId(),courseModel.getTitle(),studentDtoSet);

    }

    @Override
    @Transactional
    public boolean addStudentToCourse(EnrollmentRequest enrollmentRequest) {

        if (!checkEnrollmentRequest(enrollmentRequest)){
            return false;
        }

        // Check if the Enrolling student exists
        Optional<StudentModel> studentModel = studentRepository.findById(enrollmentRequest.getStudentId());

        if (studentModel.isEmpty()){
            System.out.println("The Student with the StudentId: " + enrollmentRequest.getStudentId() + " does not exists");
            return false;
        }

        // Check if the course is available
        Optional<CourseModel> courseModel = courseRepository.findById(enrollmentRequest.getCourseId());

        if (courseModel.isEmpty()){
            System.out.println("The Course with the CourseId: " + enrollmentRequest.getCourseId() + " does not exists");
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

        enrollmentRepository.save(enrollmentModel);

        courseModel.get().setCapacity(courseModel.get().getCapacity() - 1);

        courseRepository.save(courseModel.get());

        return true;
    }

    @Override
    @Transactional
    public boolean addCourseToStudent(EnrollmentRequest enrollmentRequest) {

        if (!checkEnrollmentRequest(enrollmentRequest)){
            return false;
        }

        // Check if the Enrolling student exists
        Optional<StudentModel> studentModel = studentRepository.findById(enrollmentRequest.getStudentId());

        if (studentModel.isEmpty()){
            System.out.println("The Student with the StudentId: " + enrollmentRequest.getStudentId() + " does not exists");
            return false;
        }

        // Check if the course is available
        Optional<CourseModel> courseModel = courseRepository.findById(enrollmentRequest.getCourseId());

        if (courseModel.isEmpty()){
            System.out.println("The Course with the CourseId: " + enrollmentRequest.getCourseId() + " does not exists");
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

        enrollmentRepository.save(enrollmentModel);

        courseModel.get().setCapacity(courseModel.get().getCapacity() - 1);

        courseRepository.save(courseModel.get());

        return true;
    }

    @Override
    public boolean removeStudentFromCourse(EnrollmentRequest enrollmentRequest) {

        if (!checkEnrollmentRequest(enrollmentRequest)){
            return false;
        }

        // Check if the Enrolling student exists
        Optional<StudentModel> studentModel = studentRepository.findById(enrollmentRequest.getStudentId());

        if (studentModel.isEmpty()){
            System.out.println("The Student with the StudentId: " + enrollmentRequest.getStudentId() + " does not exists");
            return false;
        }

        // Check if the course is available
        Optional<CourseModel> courseModel = courseRepository.findById(enrollmentRequest.getCourseId());

        if (courseModel.isEmpty()){
            System.out.println("The Course with the CourseId: " + enrollmentRequest.getCourseId() + " does not exists");
            return false;
        }

        // Get the specific EnrollmentModel based on the both student and course id
        Optional<EnrollmentModel> enrollmentModel = enrollmentRepository.findEnrollmentModelIdByStudentAndCourseId(enrollmentRequest.getCourseId(), enrollmentRequest.getStudentId());

        if (enrollmentModel.isPresent()){
            enrollmentRepository.delete(enrollmentModel.get());

            courseModel.get().setCapacity(courseModel.get().getCapacity() + 1);

            courseRepository.save(courseModel.get());

            return true;
        }

        return false;


    }

    @Override
    public boolean removeCourseFromStudent(EnrollmentRequest enrollmentRequest) {
        return false;
    }


}
