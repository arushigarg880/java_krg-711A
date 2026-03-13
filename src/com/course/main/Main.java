package com.course.main;

import com.course.model.*;
import com.course.service.CourseService;
import com.course.exception.*;

public class Main {

    public static void main(String[] args) {

        CourseService service = new CourseService();

        Course c1 = new Course(1, "Java", 2);
        Course c2 = new Course(2, "DSA", 2);

        service.addCourse(c1);
        service.addCourse(c2);

        Student s1 = new Student(101, "Arushi");
        Student s2 = new Student(102, "Rahul");
        Student s3 = new Student(103, "Priya");

        try {

            service.enrollStudent(1, s1);
            service.enrollStudent(1, s2);
            service.enrollStudent(1, s3);

        } catch (CourseFullException e) {
            System.out.println(e.getMessage());

        } catch (CourseNotFoundException e) {
            System.out.println(e.getMessage());

        } catch (DuplicateEnrollmentException e) {
            System.out.println(e.getMessage());
        }

        service.viewCourses();
    }
}