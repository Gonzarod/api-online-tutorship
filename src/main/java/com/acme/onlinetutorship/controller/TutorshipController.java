package com.acme.onlinetutorship.controller;

import com.acme.onlinetutorship.controller.commons.MessageResponse;
import com.acme.onlinetutorship.controller.constants.ResponseConstants;
import com.acme.onlinetutorship.dto.TutorshipDTO;
import com.acme.onlinetutorship.model.Course;
import com.acme.onlinetutorship.model.EStatus;
import com.acme.onlinetutorship.model.Tutorship;
import com.acme.onlinetutorship.model.User;
import com.acme.onlinetutorship.service.CourseService;
import com.acme.onlinetutorship.service.TutorshipService;
import com.acme.onlinetutorship.service.UserService;
import com.acme.onlinetutorship.service.exception.ServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Tag(name = "Tutorship", description = "API is Ready")
@Slf4j
@RestController
@RequestMapping("/api/tutorship")
@CrossOrigin(origins = "*")
public class TutorshipController extends GenericController {

    @Autowired
    TutorshipService tutorshipService;

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @GetMapping("")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Tutorships", description = "Get All Tutorships", tags = {"Tutorship"},security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<MessageResponse> getAll(){
        try {
            List<Tutorship> tutorships = this.tutorshipService.getAll();

            if(tutorships == null || tutorships.isEmpty()){
                return super.getNotContentResponseEntity();
            }

            MessageResponse response = MessageResponse
                    .builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(tutorships)
                    .build();

            return ResponseEntity.ok(response);

        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/student/{studentId}/course/{courseId}")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create Tutorship", description = "Create Tutorship",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"Tutorship"})
    public ResponseEntity<Object> create(@RequestBody @Validated TutorshipDTO tutorship, @PathVariable Long studentId, @PathVariable Long courseId, BindingResult result) {

        if (result.hasErrors()) {
            String msgErr = super.formatMapMessage(result);
            log.info("msgErr " + msgErr);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msgErr);
        }

        /*
         * if (result.hasErrors()) { log.info(result.getAllErrors().toString()); return
         * ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors().
         * toString()); }
         */

        try {
            MessageResponse response;
            Optional<User> student = this.userService.findById(User.builder().id(studentId).build());
            if(student.isEmpty()){
                response = MessageResponse.builder().code(ResponseConstants.ERROR_CODE).message("No existe usuario").build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            Optional<Course> course = this.courseService.findById(Course.builder().id(courseId).build());
            if(course.isEmpty()){
                response = MessageResponse.builder().code(ResponseConstants.ERROR_CODE).message("No existe curso").build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Tutorship saveTutorship = Tutorship.builder().status(EStatus.OPEN).build();
            BeanUtils.copyProperties(tutorship,saveTutorship);
            saveTutorship.setStudent(student.get());
            saveTutorship.setCourse(course.get());

            saveTutorship = this.tutorshipService.insert(saveTutorship);

            if (saveTutorship == null){
                response = MessageResponse.builder().code(ResponseConstants.ERROR_CODE).message("Error al guardar").build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            response = MessageResponse.builder().code(ResponseConstants.SUCCESS_CODE).message("Éxito al crear la solicitud").data(saveTutorship).build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }



    @PutMapping("tutorship/{id}/teacher/{teacherId}")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Set Teacher", description = "Set Teacher",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"Tutorship"})
    public ResponseEntity<Object> setTeacher(@PathVariable Long id, @PathVariable Long teacherId) {

        try {
            MessageResponse response;
            Tutorship tutorship = this.tutorshipService.findById(Tutorship.builder().id(id).build()).orElse(null);
            if (tutorship == null) {
                response = MessageResponse.builder().code(ResponseConstants.ERROR_CODE).message("Curso no encontrado").data(null).build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            User user = this.userService.findById(User.builder().id(teacherId).build()).orElse(null);
            if(user == null){
                response = MessageResponse.builder().code(ResponseConstants.ERROR_CODE).message("No existe profesor").build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            tutorship.setTeacher(user);
            tutorship = this.tutorshipService.update(tutorship);

            response = MessageResponse.builder().code(ResponseConstants.SUCCESS_CODE).message("").data(tutorship).build();
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (ServiceException e) {
            log.error(e.getMessage());
            String argMsg[] =e.getMessage().split(":");
            MessageResponse msg = MessageResponse.builder().code(ResponseConstants.WARNING_CODE).message(argMsg[1]).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
        }
    }

}
