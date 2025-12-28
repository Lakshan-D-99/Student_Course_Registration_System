package com.sp.student_course_registration_system.Utils;

import com.sp.student_course_registration_system.daos.dtos.CourseDto;
import com.sp.student_course_registration_system.daos.dtos.StudentDto;
import com.sp.student_course_registration_system.daos.requestdaos.StudentRequest;
import com.sp.student_course_registration_system.models.CourseModel;
import com.sp.student_course_registration_system.models.StudentModel;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {

    //---------- Converting Models into DTOs and vice versa ----------//

    // Convert a Course into a CourseDto
    public CourseDto conModelToDto(CourseModel courseModel){
        return new CourseDto(courseModel.getId(), courseModel.getTitle(), courseModel.getCapacity());
    }

    // Convert a CourseDto into a Course
    public CourseModel conDtoToModel(CourseDto courseDto){
        CourseModel courseModel = new CourseModel();
        courseModel.setTitle(courseDto.getTitle());
        courseModel.setCapacity(courseDto.getCapacity());
        return courseModel;
    }

    // Convert a Student into a StudentDto
    public StudentDto conModelToDto(StudentModel studentModel){
        return new StudentDto(studentModel.getId(), studentModel.getUserName(),studentModel.getEmail());
    }

    // Convert a StudentDto into a Student
    public StudentModel conDtoToModel(StudentDto studentDto){
        StudentModel studentModel = new StudentModel();
        studentModel.setUserName(studentDto.getUserName());
        studentModel.setEmail(studentDto.getEmail());
        return studentModel;
    }

    //---------- Converting RequestDtos into Models and vice versa ----------//

    // Convert StudentRequest into a StudentModel
    public StudentModel conRequestToModel(StudentRequest studentRequest){
        StudentModel studentModel = new StudentModel();
        studentModel.setUserName(studentRequest.getUserName());
        studentModel.setEmail(studentRequest.getEmail());
        return studentModel;
    }



}
