package com.acme.onlinetutorship.config;

import com.acme.onlinetutorship.model.Course;
import com.acme.onlinetutorship.model.ERole;
import com.acme.onlinetutorship.model.Role;
import com.acme.onlinetutorship.model.User;
import com.acme.onlinetutorship.repository.CourseRepository;
import com.acme.onlinetutorship.repository.RoleRepository;
import com.acme.onlinetutorship.repository.UserRepository;
import com.acme.onlinetutorship.security.payload.request.SignUpRequest;
import com.acme.onlinetutorship.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataLoad {
    private final RoleRepository roleRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Autowired
    public DataLoad(RoleRepository roleRepository,CourseRepository courseRepository,AuthenticationService authenticationService,UserRepository userRepository){
        this.roleRepository=roleRepository;
        this.courseRepository=courseRepository;
        this.authenticationService=authenticationService;
        this.userRepository=userRepository;
        this.loadData();
    }

    private void loadData() {

        this.addRoles();
        this.addCourses();
        this.registerUserStudent();
        this.registerTeacher();
        this.setTeacherCourses();


    }

    private void addRoles() {
        //User Roles
        this.roleRepository.saveAll(Arrays.asList(
                new Role(ERole.ROLE_STUDENT),
                new Role(ERole.ROLE_TEACHER),
                new Role(ERole.ROLE_ADMIN)
        ));
    }

    private void addCourses() {
        this.courseRepository.saveAll(Arrays.asList(
                new Course("Spanish", "Spanish"),
                new Course("History", "History"),
                new Course("Arithmetics", "Arithmetics"),
                new Course("Geometry", "Geometry"),
                new Course("Geography", "Geography"),
                new Course("Algebra", "Algebra"),
                new Course("Chemistry", "Chemistry"),
                new Course("Physics", "Physics"),
                new Course("Biology", "Biology")
        ));
    }

    private void registerUserStudent() {
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_STUDENT");
        SignUpRequest userStudent = new SignUpRequest("jesus.student","password","jesus@gmail.com",roles,"Jesus",
                "Duran","77332215","994093796");

        SignUpRequest userStudent2 = new SignUpRequest("maria.student","password","maria@gmail.com",roles,"Maria",
                "Suarez","88552215","986578231");

        this.authenticationService.registerUser(userStudent);
        this.authenticationService.registerUser(userStudent2);
    }

    private void registerTeacher() {
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_TEACHER");
        SignUpRequest userTeacher = new SignUpRequest("albert.teacher","password","albert@gmail.com",roles,"Albert",
                "Mayta","09987745","999666555");

        SignUpRequest userTeacher1 = new SignUpRequest("roberto.teacher","password","roberto@gmail.com",roles,"Roberto",
                "Villanueva","09822145","987456123");

        this.authenticationService.registerUser(userTeacher);
        this.authenticationService.registerUser(userTeacher1);
    }

    private void setTeacherCourses() {
        List<Course> allCourses = courseRepository.findAll();
        Optional<User> teacher1 = this.userRepository.findByUsername("albert.teacher");
        Optional<User> teacher2 = this.userRepository.findByUsername("roberto.teacher");

        Random rand = new Random();
        for (int i = 0; i < allCourses.size(); i++) {
            int randomIndex = rand.nextInt(allCourses.size());
            Course randomCourse = allCourses.get(randomIndex);
            if(i%2==0){
                teacher1.get().getCourses().add(randomCourse);
                userRepository.save(teacher1.get());
            }else {
                teacher2.get().getCourses().add(randomCourse);
                userRepository.save(teacher2.get());
            }

            allCourses.remove(randomIndex);
        }
    }





}
