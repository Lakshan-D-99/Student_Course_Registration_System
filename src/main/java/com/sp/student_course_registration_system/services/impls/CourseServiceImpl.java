package com.sp.student_course_registration_system.services.impls;

import com.sp.student_course_registration_system.Utils.ModelMapper;
import com.sp.student_course_registration_system.daos.dtos.CourseDto;
import com.sp.student_course_registration_system.daos.requestdaos.CourseRequest;
import com.sp.student_course_registration_system.exceptions.courseExps.CourseCreatingException;
import com.sp.student_course_registration_system.exceptions.courseExps.CourseNotFoundException;
import com.sp.student_course_registration_system.models.CourseModel;
import com.sp.student_course_registration_system.repositories.CourseRepository;
import com.sp.student_course_registration_system.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CourseDto> getAllCourses() {
        List<CourseDto> courseDtoList = courseRepository.findAll()
                .stream()
                .map(courseModel -> modelMapper.conModelToDto(courseModel))
                .collect(Collectors.toList());
        return courseDtoList;
    }

    @Override
    public CourseDto getCourseById(Long courseId) {
        CourseModel courseModel = courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException("The Course with the CourseId: " + courseId + " was not found"));
        return modelMapper.conModelToDto(courseModel);
    }

    @Override
    public CourseDto getCourseByCourseTitle(String courseTitle) {
        CourseModel courseModel = courseRepository.findCourseModelByTitle(courseTitle).orElseThrow(
                () -> new CourseNotFoundException("The Course with the Course Title: " + courseTitle + " was not found"));
        return modelMapper.conModelToDto(courseModel);
    }

    @Override
    public void saveCourse(CourseRequest courseRequest) {

        if (courseRequest.getTitle().isEmpty()){
            throw new CourseCreatingException("A Course Title is required to create a course");
        }

        if (courseRequest.getCapacity() <= 0){
            throw new CourseCreatingException("A Course should have a capacity greater than 0");
        }

        courseRepository.save(modelMapper.conRequestToModel(courseRequest));
    }

    @Override
    public void updateCourse(CourseDto courseDto) {

        if (courseDto.getTitle().isEmpty()){
            throw new CourseCreatingException("A Course Title is required to update a course");
        }

        if (courseDto.getCapacity() <= 0){
            throw new CourseCreatingException("A Course should have a capacity greater than 0");
        }

        CourseModel courseModel = courseRepository.findById(courseDto.getId()).orElseThrow(
                ()->new CourseNotFoundException("The Course with the CourseId: " + courseDto.getId() + " does not exists"));

        courseModel.setTitle(courseDto.getTitle());

        courseModel.setCapacity(courseDto.getCapacity());

        courseRepository.save(courseModel);
    }

    @Override
    public void deleteCourse(Long courseId) {
        CourseModel courseModel = courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException("The Course with the CourseId: " + courseId + " was not found"));
        courseRepository.delete(courseModel);

    }
}
