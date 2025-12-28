package com.sp.student_course_registration_system.services.impls;

import com.sp.student_course_registration_system.Utils.ModelMapper;
import com.sp.student_course_registration_system.daos.dtos.CourseDto;
import com.sp.student_course_registration_system.models.CourseModel;
import com.sp.student_course_registration_system.repositories.CourseRepository;
import com.sp.student_course_registration_system.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        List<CourseDto> courseDtoList = new ArrayList<>();

        List<CourseModel> courseModelList = courseRepository.findAll();

        if (courseModelList.isEmpty()){
            System.out.println("Currently, there are no courses");
            return courseDtoList;
        }

        courseModelList.forEach(courseModel -> {
            courseDtoList.add(modelMapper.conModelToDto(courseModel));
        });

        return courseDtoList;
    }

    @Override
    public CourseDto getCourseById(Long courseId) {

        Optional<CourseModel> courseModel = courseRepository.findById(courseId);

        if (courseModel.isEmpty()){
            System.out.println("The Course with the courseId:" + courseId + " does not exists");
            return new CourseDto();
        }

        return modelMapper.conModelToDto(courseModel.get());
    }

    @Override
    public List<CourseDto> getAllCoursesByTitle(String courseTitle) {

        List<CourseDto> courseDtoList = new ArrayList<>();

        List<CourseModel> courseModelList = courseRepository.findCourseModelByTitle(courseTitle);

        if (courseModelList.isEmpty()){
            System.out.println("Currently, there are no courses with the course name: " + courseTitle + " available");
            return courseDtoList;
        }

        courseModelList.forEach(courseModel -> {
            courseDtoList.add(modelMapper.conModelToDto(courseModel));
        });

        return courseDtoList;
    }

    @Override
    public boolean saveCourse(CourseDto courseDto) {

        if (courseDto.getTitle().isEmpty() || courseDto.getCapacity() <= 0){
            System.out.println("A Course should have a Name and a Capacity");
            return false;
        }
        courseRepository.save(modelMapper.conDtoToModel(courseDto));
        return true;
    }

    @Override
    public boolean updateCourse(CourseDto courseDto) {
        return false;
    }

    @Override
    public boolean deleteCourse(Long courseId) {

        Optional<CourseModel> courseModel = courseRepository.findById(courseId);

        if (courseModel.isEmpty()){
            System.out.println("The Course with the Id: " + courseId + " does not exists");
            return false;
        }

        courseRepository.delete(courseModel.get());

        return true;

    }
}
