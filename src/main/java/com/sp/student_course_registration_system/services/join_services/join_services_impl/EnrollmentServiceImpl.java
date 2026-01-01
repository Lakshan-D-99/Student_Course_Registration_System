package com.sp.student_course_registration_system.services.join_services.join_services_impl;

import com.sp.student_course_registration_system.Utils.ModelMapper;
import com.sp.student_course_registration_system.daos.dtos.CourseDto;
import com.sp.student_course_registration_system.daos.dtos.StudentDto;
import com.sp.student_course_registration_system.daos.requestdaos.EnrollmentRequest;
import com.sp.student_course_registration_system.daos.responsetdaos.EnrollmentCourseResponse;
import com.sp.student_course_registration_system.daos.responsetdaos.EnrollmentStudentResponse;
import com.sp.student_course_registration_system.exceptions.courseExps.CourseCapacityException;
import com.sp.student_course_registration_system.exceptions.courseExps.CourseNotFoundException;
import com.sp.student_course_registration_system.exceptions.enrollmentExps.StudentCourseException;
import com.sp.student_course_registration_system.exceptions.studentExps.StudentNotFoundException;
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
                ()-> new StudentNotFoundException("Student with the StudentId: " + studentId + " does not exists"));


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
                ()-> new CourseNotFoundException("Course with the CourseId: " + courseId + " does not exists"));

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
    public void addStudentToCourse(EnrollmentRequest enrollmentRequest) {

        if (!checkEnrollmentRequest(enrollmentRequest)){
            throw new StudentCourseException("Student and a Course is required for enrollment");
        }

        StudentModel studentModel = studentRepository.findById(enrollmentRequest.getStudentId()).orElseThrow(
                ()-> new StudentNotFoundException("Student with the StudentId: " + enrollmentRequest.getStudentId() + " does not exists"));

        CourseModel courseModel = courseRepository.findById(enrollmentRequest.getCourseId()).orElseThrow(
                ()-> new CourseNotFoundException("Course with the CourseId: " + enrollmentRequest.getCourseId() + " does not exists"));

        // Check if the Course capacity does not exceed
        if (courseModel.getCapacity() == 0){
            System.out.println("The Course capacity is at a maximum");
            throw new CourseCapacityException("The Course capacity is at a maximum");
        }

        EnrollmentModel enrollmentModel = new EnrollmentModel();
        enrollmentModel.setStudentModel(studentModel);
        enrollmentModel.setCourseModel(courseModel);

        enrollmentRepository.save(enrollmentModel);

        courseModel.setCapacity(courseModel.getCapacity() - 1);

        courseRepository.save(courseModel);
    }

    @Override
    public void removeStudentFromCourse(EnrollmentRequest enrollmentRequest) {

        if (!checkEnrollmentRequest(enrollmentRequest)) {
            throw new StudentCourseException("Student and a Course is required to perform this operation");
        }

        StudentModel studentModel = studentRepository.findById(enrollmentRequest.getStudentId()).orElseThrow(
                ()-> new StudentNotFoundException("Student with the StudentId: " + enrollmentRequest.getStudentId() + " does not exists"));

        CourseModel courseModel = courseRepository.findById(enrollmentRequest.getCourseId()).orElseThrow(
                ()-> new CourseNotFoundException("Course with the CourseId: " + enrollmentRequest.getCourseId() + " does not exists"));


        EnrollmentModel enrollmentModel = enrollmentRepository.findEnrollmentModelIdByStudentAndCourseId(courseModel.getId(), studentModel.getId()).orElseThrow(
                ()-> new StudentCourseException("Error getting Student and Course information"));

        courseModel.removeEnrollment(enrollmentModel);
        courseModel.setCapacity(courseModel.getCapacity() + 1);
        courseRepository.save(courseModel);

    }


}
