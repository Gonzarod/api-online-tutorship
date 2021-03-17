package com.acme.onlinetutorship.config;

import com.acme.onlinetutorship.model.*;
import com.acme.onlinetutorship.repository.CourseRepository;
import com.acme.onlinetutorship.repository.RoleRepository;
import com.acme.onlinetutorship.repository.TutorshipRepository;
import com.acme.onlinetutorship.repository.UserRepository;
import com.acme.onlinetutorship.security.payload.request.SignUpRequest;
import com.acme.onlinetutorship.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@Component
public class DataLoad {

    private final RoleRepository roleRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final TutorshipRepository tutorshipRepository;

    @Autowired
    public DataLoad(RoleRepository roleRepository,CourseRepository courseRepository,AuthenticationService authenticationService,UserRepository userRepository,TutorshipRepository tutorshipRepository){
        this.roleRepository=roleRepository;
        this.courseRepository=courseRepository;
        this.authenticationService=authenticationService;
        this.userRepository=userRepository;
        this.tutorshipRepository=tutorshipRepository;
        this.loadData();
    }

    private void loadData() {

        this.addRoles();
        this.addCourses();
        this.registerUserStudent();
        this.registerTeacher();
        this.setTeacherCourses();
        this.addTutorships();

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
                new Course("Francés", "Descripción de Francés avanzado"),
                new Course("Inglés","Descripción de Inglés"),
                new Course("Alemán","Descripción de Alemán"),
                new Course("Portugués","Descripción de Portugués"),
                new Course("Aritmética", "Descripción de Aritmética"),
                new Course("Geometría", "Descripción de Geometría"),
                new Course("Álgebra", "Descripción de Álgebra"),
                new Course("Trigonometría", "Descripción de Trigonometría"),
                new Course("Geografía", "Descripción de Geografía"),
                new Course("Historia Universal", "Descripción de Historia Universal"),
                new Course("Historia del Perú", "Descripción de Historia del Peru"),
                new Course("Química", "Descripción de Química"),
                new Course("Física", "Descripción de Física"),
                new Course("Biología", "Descripción de Biología")
        ));
    }

    private void registerUserStudent() {
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_STUDENT");
        SignUpRequest userStudent1 = new SignUpRequest("jesus.student","password","jesus@gmail.com",roles,"Jesus",
                "Duran","77332215","994093796");

        SignUpRequest userStudent2 = new SignUpRequest("maria.student","password","maria@gmail.com",roles,"Maria",
                "Suarez","88552215","986578231");

        this.authenticationService.registerUser(userStudent1);
        this.authenticationService.registerUser(userStudent2);
    }

    private void registerTeacher() {
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_TEACHER");
        SignUpRequest userTeacher1 = new SignUpRequest("albert.teacher","password","albert@gmail.com",roles,"Albert",
                "Mayta","09987745","999666555");

        SignUpRequest userTeacher2 = new SignUpRequest("roberto.teacher","password","roberto@gmail.com",roles,"Roberto",
                "Villanueva","09822145","987456123");

        SignUpRequest userTeacher3 = new SignUpRequest("pilar.teacher","password","pilar@gmail.com",roles,"Pilar",
                "Lopez","09422448","987456883");

        SignUpRequest userTeacher4 = new SignUpRequest("junnior.teacher","password","junnior@gmail.com",roles,"Junnior",
                "Quispe","07422882","967896258");

        this.authenticationService.registerUser(userTeacher1);
        this.authenticationService.registerUser(userTeacher2);
        this.authenticationService.registerUser(userTeacher3);
        this.authenticationService.registerUser(userTeacher4);
    }

    private void setTeacherCourses() {

        Optional<User> teacher1 = this.userRepository.findByUsername("albert.teacher");
        Optional<User> teacher2 = this.userRepository.findByUsername("roberto.teacher");
        Optional<User> teacher3 = this.userRepository.findByUsername("pilar.teacher");
        Optional<User> teacher4 = this.userRepository.findByUsername("junnior.teacher");


        teacher1.get().getCourses().add(this.courseRepository.findByName("Francés").get());
        teacher1.get().getCourses().add(this.courseRepository.findByName("Inglés").get());
        teacher1.get().getCourses().add(this.courseRepository.findByName("Alemán").get());
        teacher1.get().getCourses().add(this.courseRepository.findByName("Portugués").get());
        userRepository.save(teacher1.get());

        teacher2.get().getCourses().add(this.courseRepository.findByName("Aritmética").get());
        teacher2.get().getCourses().add(this.courseRepository.findByName("Geometría").get());
        teacher2.get().getCourses().add(this.courseRepository.findByName("Álgebra").get());
        teacher2.get().getCourses().add(this.courseRepository.findByName("Trigonometría").get());
        userRepository.save(teacher2.get());

        teacher3.get().getCourses().add(this.courseRepository.findByName("Geografía").get());
        teacher3.get().getCourses().add(this.courseRepository.findByName("Historia Universal").get());
        teacher3.get().getCourses().add(this.courseRepository.findByName("Historia del Perú").get());
        userRepository.save(teacher3.get());

        teacher4.get().getCourses().add(this.courseRepository.findByName("Química").get());
        teacher4.get().getCourses().add(this.courseRepository.findByName("Física").get());
        teacher4.get().getCourses().add(this.courseRepository.findByName("Biología").get());
        userRepository.save(teacher4.get());

    }
    
    private void addTutorships(){
        //Create Session Request (STATUS = OPEN)
        Optional<User> student1 = this.userRepository.findByUsername("jesus.student");
        Optional<Course> course1 = this.courseRepository.findByName("Historia Universal");


        Tutorship tutorship = new Tutorship(LocalDateTime.of(2020, Month.NOVEMBER, 30, 18, 30),
                LocalDateTime.of(2020, Month.NOVEMBER, 30, 20, 30),EStatus.OPEN,"Segunda Guerra Mundial",student1.get(),course1.get());


        this.tutorshipRepository.save(tutorship);


        Optional<User> student2 = this.userRepository.findByUsername("maria.student");
        Optional<Course> course2 = this.courseRepository.findByName("Inglés");


        Tutorship tutorship2 = new Tutorship(LocalDateTime.of(2020, Month.NOVEMBER, 30, 18, 30),
                LocalDateTime.of(2020, Month.NOVEMBER, 30, 20, 30),EStatus.OPEN,"Advanced English Topics",student2.get(),course2.get());


        this.tutorshipRepository.save(tutorship2);


        Optional<User> student3 = this.userRepository.findByUsername("jesus.student");
        Optional<Course> course3 = this.courseRepository.findByName("Trigonometría");


        Tutorship tutorship3 = new Tutorship(LocalDateTime.of(2020, Month.NOVEMBER, 30, 18, 30),
                LocalDateTime.of(2020, Month.NOVEMBER, 30, 20, 30),EStatus.OPEN,"Triángulos Notables",student3.get(),course3.get());


        this.tutorshipRepository.save(tutorship3);


        Optional<User> student4 = this.userRepository.findByUsername("maria.student");
        Optional<User> teacher4 = this.userRepository.findByUsername("junnior.teacher");
        Optional<Course> course4 = this.courseRepository.findByName("Biología");


        Tutorship tutorship4 = new Tutorship(LocalDateTime.of(2020, Month.NOVEMBER, 30, 18, 30),
                LocalDateTime.of(2020, Month.NOVEMBER, 30, 20, 30),EStatus.CLOSED,"Sistema Respiratorio",student4.get(),course4.get());

        this.tutorshipRepository.save(tutorship4);

    }





}
