package com.sp.student_course_registration_system.services.impls;

import com.sp.student_course_registration_system.Utils.ModelMapper;
import com.sp.student_course_registration_system.daos.dtos.StudentDto;
import com.sp.student_course_registration_system.daos.requestdaos.StudentRequest;
import com.sp.student_course_registration_system.daos.responsetdaos.StudentResponse;
import com.sp.student_course_registration_system.models.StudentModel;
import com.sp.student_course_registration_system.repositories.StudentRepository;
import com.sp.student_course_registration_system.services.StudentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<StudentDto> getAllTheStudents() {

        List<StudentDto> studentDtosList = new ArrayList<>();

        List<StudentModel> studentModelList = studentRepository.findAll();

        if (!studentModelList.isEmpty()){
            for (StudentModel studentModel:studentModelList){
                studentDtosList.add(modelMapper.conModelToDto(studentModel));

            }

            return studentDtosList;
        }

        return new ArrayList<>();
    }

    @Override
    public StudentDto getStudentById(Long studentId) {

        Optional<StudentModel> studentModel = studentRepository.findById(studentId);

        if (studentModel.isPresent()){
            return modelMapper.conModelToDto(studentModel.get());
        }

        return new StudentDto();
    }

    @Override
    public StudentDto getStudentByEmail(String studentEmail) {

        Optional<StudentModel> studentModel = studentRepository.findStudentModelByEmail(studentEmail);

        if (studentModel.isPresent()){
            return modelMapper.conModelToDto(studentModel.get());
        }

        return new StudentDto();
    }

    @Override
    public boolean addStudent(StudentRequest studentRequest) {

        if (studentRequest.getUserName().isEmpty() || studentRequest.getEmail().isEmpty()){
            System.out.println("An Email and a Username is required as credentials to register");
            return false;
        }

        studentRepository.save(modelMapper.conRequestToModel(studentRequest));

        return true;
    }

    @Override
    public boolean updateStudent(StudentDto studentDto) {
        return false;
    }

    @Override
    public boolean deleteStudent(Long studentId) {

        Optional<StudentModel> studentModel = studentRepository.findById(studentId);

        if (studentModel.isPresent()){
            studentRepository.delete(studentModel.get());
            return true;
        }

        return false;
    }
}
