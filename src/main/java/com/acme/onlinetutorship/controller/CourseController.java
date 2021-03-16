package com.acme.onlinetutorship.controller;

import com.acme.onlinetutorship.controller.commons.MessageResponse;
import com.acme.onlinetutorship.controller.constants.ResponseConstants;
import com.acme.onlinetutorship.model.Course;
import com.acme.onlinetutorship.service.CourseService;
import com.acme.onlinetutorship.service.exception.ServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Course", description = "API is Ready")
@Slf4j
@RestController
@RequestMapping("/api/course")
@CrossOrigin(origins = "*")
public class CourseController extends GenericController {
    @Autowired
    private CourseService courseService;

    @GetMapping("")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Courses", description = "Get All Courses", tags = {"Course"},security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<MessageResponse> getAll(){
        try {
            List<Course> courses = this.courseService.getAll();

            if(courses == null || courses.isEmpty()){
                return super.getNotContentResponseEntity();
            }

            MessageResponse response = MessageResponse
                    .builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(courses)
                    .build();

            return ResponseEntity.ok(response);

        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/filter")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Courses By Name", description = "Get All Courses By Name", tags = {"Course"},security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<MessageResponse> getAllByName(@RequestParam(required = false) @Parameter(description = "is Optional") String name){

        try {
            List<Course> courses = this.courseService.getCoursesByName(name);

            if(courses == null || courses.isEmpty()){
                return super.getNotContentResponseEntity();
            }

            MessageResponse response = MessageResponse
                    .builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(courses)
                    .build();

            return ResponseEntity.ok(response);

        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Courses paginated", description = "Get All Courses paginated", tags = {"Course"},
            parameters = {
                    @Parameter(in = ParameterIn.QUERY
                            , description = "Page you want to retrieve (0..N)"
                            , name = "page"
                            , content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))),
                    @Parameter(in = ParameterIn.QUERY
                            , description = "Number of records per page."
                            , name = "size"
                            , content = @Content(schema = @Schema(type = "integer", defaultValue = "20"))),
                    @Parameter(in = ParameterIn.QUERY
                            , description = "Sorting criteria in the format: property(,asc|desc). "
                            + "Default sort order is ascending. " + "Multiple sort criteria are supported."
                            , name = "sort"
                            , content = @Content(array = @ArraySchema(schema = @Schema(type = "string"))))
            },security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<MessageResponse> getAllPaginated(@PageableDefault @Parameter(hidden = true) Pageable pageable){
        try {
            Page<Course> courses = this.courseService.getAllCoursesPaginated(pageable);

            if(courses == null || courses.isEmpty()){
                return super.getNotContentResponseEntity();
            }

            MessageResponse response = MessageResponse
                    .builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(courses)
                    .build();

            return ResponseEntity.ok(response);

        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page/filter")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Courses paginated by name", description = "Get All Courses paginated by name. Can filter by name (param optional)", tags = {"Course"},
            parameters = {
                    @Parameter(in = ParameterIn.QUERY
                            , description = "Page you want to retrieve (0..N)"
                            , name = "page"
                            , content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))),
                    @Parameter(in = ParameterIn.QUERY
                            , description = "Number of records per page."
                            , name = "size"
                            , content = @Content(schema = @Schema(type = "integer", defaultValue = "20"))),
                    @Parameter(in = ParameterIn.QUERY
                            , description = "Sorting criteria in the format: property(,asc|desc). "
                            + "Default sort order is ascending. " + "Multiple sort criteria are supported."
                            , name = "sort"
                            , content = @Content(array = @ArraySchema(schema = @Schema(type = "string"))))
            },security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<MessageResponse> getAllPaginatedByName(@PageableDefault @Parameter(hidden = true) Pageable pageable, @RequestParam(required = false) @Parameter(description = "is Optional") String name ){
        try {
            Page<Course> courses = this.courseService.getCoursesPaginatedByName(name,pageable);

            if(courses == null || courses.isEmpty()){
                return super.getNotContentResponseEntity();
            }

            MessageResponse response = MessageResponse
                    .builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(courses)
                    .build();

            return ResponseEntity.ok(response);

        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get Course by Id", description = "Get Course by Id",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"Course"})
    public ResponseEntity<MessageResponse> findById(@PathVariable Long id) {
        if (id <= 0) {
            String json = "id debe ser positivo";

            MessageResponse response = MessageResponse
                    .builder()
                    .code(ResponseConstants.ERROR_CODE)
                    .message(ResponseConstants.MSG_ERROR_CONS)
                    .data(json)
                    .build();

            return ResponseEntity.ok(response);

        }

        try {
            Course course = this.courseService.findById(Course.builder().id(id).build()).orElse(null);
            if (course == null) {
                return super.getNotContentResponseEntity();
            }

            MessageResponse response = MessageResponse
                    .builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(course)
                    .build();

            return ResponseEntity.ok(response);

        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create Course", description = "Create Course",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"Course"})
    public ResponseEntity<Object> create(@RequestBody @Validated Course course, BindingResult result) {

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

            Course newCourse = this.courseService.insert(course);
            MessageResponse response;
            if (newCourse == null){
                response = MessageResponse.builder().code(ResponseConstants.ERROR_CODE).message("").build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            response = MessageResponse.builder().code(ResponseConstants.SUCCESS_CODE).message("").data(newCourse).build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("/{id}")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update Course", description = "Update Course",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"Course"})
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid Course course,
                                         BindingResult result) {
        course.setId(id);
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, super.formatMapMessage(result));
        }

        try {
            MessageResponse response;
            Course updatedCourse = this.courseService.update(course);
            if (updatedCourse == null) {
                response = MessageResponse.builder().code(ResponseConstants.ERROR_CODE).message("").data(null).build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            response = MessageResponse.builder().code(ResponseConstants.SUCCESS_CODE).message("").data(updatedCourse).build();
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (ServiceException e) {
            log.error(e.getMessage());
            String argMsg[] =e.getMessage().split(":");
            MessageResponse msg = MessageResponse.builder().code(ResponseConstants.WARNING_CODE).message(argMsg[1]).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Course", description = "Delete Course",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"Course"})
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            Course delCourse = this.courseService.delete(Course.builder().id(id).build());
            if (delCourse == null) {
                MessageResponse message = MessageResponse.builder().code(ResponseConstants.SUCCESS_CODE).message("Se borró").build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            }

            return ResponseEntity.ok(delCourse);
        } catch (ServiceException e) {
            //log.error("ServiceException "+e.getMessage());
            String argMsg[] = e.getMessage().split(":");
            MessageResponse msg = MessageResponse.builder().code(ResponseConstants.WARNING_CODE).message(argMsg[1]).build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
        }

    }







}
