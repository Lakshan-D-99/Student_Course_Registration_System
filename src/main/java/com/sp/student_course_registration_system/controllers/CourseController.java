package com.sp.student_course_registration_system.controllers;

import com.sp.student_course_registration_system.Utils.ApiResponse;
import com.sp.student_course_registration_system.daos.dtos.CourseDto;
import com.sp.student_course_registration_system.daos.requestdaos.CourseRequest;
import com.sp.student_course_registration_system.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api_v1/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Get all the Courses
    @GetMapping("/all-courses")
    ResponseEntity<?> getAllTheCourses() {
        try {
            List<CourseDto> courseDtoList = courseService.getAllCourses();

            if (courseDtoList.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse("There are no courses currently available"));
            }

            return ResponseEntity.ok(courseDtoList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse("Server Error occurred while getting all the Courses"));
        }
    }

    // Get a single course based on the passed in id
    @GetMapping("/single-course/couId={courseId}")
    ResponseEntity<?> getSingleCourseById(@PathVariable Long courseId) {
        CourseDto courseDto = courseService.getCourseById(courseId);
        return ResponseEntity.ok(courseDto);
    }

    // Get a courses based on the passed in course title
    @GetMapping("/single-course/couTit={courseTitle}")
    ResponseEntity<?> getSingleCourseByCourseTitle(@PathVariable String courseTitle) {
        CourseDto courseDto = courseService.getCourseByCourseTitle(courseTitle);
        return ResponseEntity.ok(courseDto);
    }

    // Create and save a new course
    @PostMapping("/new-course")
    ResponseEntity<?> createNewCourse(@RequestBody CourseRequest courseRequest) {
        courseService.saveCourse(courseRequest);
        return ResponseEntity.ok(new ApiResponse("New Course has been created and added"));
    }

    // Update existing course

    // Delete existing course
    @DeleteMapping("/remove-course/couId={courseId}")
    ResponseEntity<?> removeExistingCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok(new ApiResponse("Course with the Course Id: " + courseId + " has been removed"));
    }
}
