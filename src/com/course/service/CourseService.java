package com.course.service;

import com.course.model.Course;
import com.course.model.Student;
import com.course.exception.*;

import java.io.*;
import java.util.*;

public class CourseService {

    List<Course> courseList = new ArrayList<>();
    Map<Integer, List<Integer>> enrollmentMap = new HashMap<>();

    public void addCourse(Course c) {
        courseList.add(c);
    }

    public void enrollStudent(int courseId, Student s)
            throws CourseNotFoundException, CourseFullException, DuplicateEnrollmentException {

        Course course = null;

        for (Course c : courseList) {
            if (c.getCourseId() == courseId) {
                course = c;
                break;
            }
        }

        if (course == null) {
            throw new CourseNotFoundException("Course not found");
        }

        if (course.getEnrolledStudents() >= course.getMaxSeats()) {
            throw new CourseFullException("Course is full");
        }

        List<Integer> students = enrollmentMap.getOrDefault(courseId, new ArrayList<>());

        if (students.contains(s.getStudentId())) {
            throw new DuplicateEnrollmentException("Student already enrolled");
        }

        students.add(s.getStudentId());
        enrollmentMap.put(courseId, students);

        course.setEnrolledStudents(course.getEnrolledStudents() + 1);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("courses.txt", true));
            bw.write(s.getStudentId() + "," + s.getStudentName() + "," + course.getCourseName());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("File error");
        }

        System.out.println("Student enrolled successfully");
    }

    public void viewCourses() {

        try {
            BufferedReader br = new BufferedReader(new FileReader("courses.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Error reading file");
        }

        for (Course c : courseList) {
            c.displayCourse();
        }
    }
}