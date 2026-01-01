package com.sp.student_course_registration_system.services.impls;

import com.sp.student_course_registration_system.Utils.ModelMapper;
import com.sp.student_course_registration_system.daos.dtos.StudentDto;
import com.sp.student_course_registration_system.daos.requestdaos.StudentRequest;
import com.sp.student_course_registration_system.exceptions.studentExps.StudentCreatingException;
import com.sp.student_course_registration_system.exceptions.studentExps.StudentNotFoundException;
import com.sp.student_course_registration_system.models.StudentModel;
import com.sp.student_course_registration_system.repositories.StudentRepository;
import com.sp.student_course_registration_system.services.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        List<StudentDto> studentDtosList = studentRepository.findAll()
                .stream()
                .map(studentModel -> modelMapper.conModelToDto(studentModel))
                .collect(Collectors.toList());
        return studentDtosList;
    }

    @Override
    public StudentDto getStudentById(Long studentId) {
        StudentModel studentModel = studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException("The Student with the StudentId: " + studentId + " does not exists"));
        return modelMapper.conModelToDto(studentModel);
    }

    @Override
    public StudentDto getStudentByUserName(String userName) {
        StudentModel studentModel = studentRepository.findStudentModelByUserName(userName).orElseThrow(
                () -> new StudentNotFoundException("The Student with the Student userName: " + userName + " does not exists"));

        return modelMapper.conModelToDto(studentModel);
    }

    @Override
    public StudentDto getStudentByEmail(String studentEmail) {
        StudentModel studentModel = studentRepository.findStudentModelByEmail(studentEmail).orElseThrow(
                () -> new StudentNotFoundException("The Student with the Student Email: " + studentEmail + " does not exists"));
        return modelMapper.conModelToDto(studentModel);
    }

    @Override
    public void addStudent(StudentRequest studentRequest) {


        if (studentRequest.getUserName().isEmpty()) {
            throw new StudentCreatingException("A Username is required to register a new Student");
        }

        boolean existStudentUserName = studentRepository.getStudentModelUserName(studentRequest.getUserName()).isEmpty();

        if (existStudentUserName) {
            throw new StudentCreatingException("The UserName is already in use");
        }

        if (studentRequest.getEmail().isEmpty()) {
            throw new StudentCreatingException("An Email is required to register a new student");
        }

        boolean existStudentEmail = studentRepository.getStudentModelEmail(studentRequest.getEmail()).isEmpty();

        if (existStudentEmail) {
            throw new StudentCreatingException("This Email is already in use");
        }

        studentRepository.save(modelMapper.conRequestToModel(studentRequest));

    }

    @Override
    public void updateStudent(StudentDto studentDto) {
        StudentModel studentModel = studentRepository.findById(studentDto.getId()).orElseThrow(
                () -> new StudentNotFoundException("The Student with the StudentId: " + studentDto.getId() + " does not exists"));

        if (studentDto.getUserName().isEmpty()) {
            throw new StudentCreatingException("A Username is required to update a new Student");
        }

        boolean existStudentUserName = studentRepository.getStudentModelUserName(studentDto.getUserName()).isEmpty();

        if (existStudentUserName) {
            throw new StudentCreatingException("The UserName is already in use");
        }

        if (studentDto.getEmail().isEmpty()) {
            throw new StudentCreatingException("An Email is required to update a new student");
        }

        boolean existStudentEmail = studentRepository.getStudentModelEmail(studentDto.getEmail()).isEmpty();

        if (existStudentEmail) {
            throw new StudentCreatingException("This Email is already in use");
        }

        studentModel.setUserName(studentDto.getUserName());
        studentModel.setEmail(studentDto.getEmail());

        studentRepository.save(studentModel);
    }

    @Override
    public void deleteStudent(Long studentId) {
        StudentModel studentModel = studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException("The Student with the StudentId: " + studentId + " does not exists"));
        studentRepository.delete(studentModel);
    }
}
