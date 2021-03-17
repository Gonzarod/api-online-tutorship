package com.acme.onlinetutorship.controller;


import com.acme.onlinetutorship.controller.commons.MessageResponse;
import com.acme.onlinetutorship.controller.constants.ResponseConstants;
import com.acme.onlinetutorship.model.Tutorship;
import com.acme.onlinetutorship.model.User;
import com.acme.onlinetutorship.service.TutorshipService;
import com.acme.onlinetutorship.service.UserService;
import com.acme.onlinetutorship.service.exception.ServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "User", description = "API is Ready")
@Slf4j
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController extends GenericController {

    @Autowired
    UserService userService;

    @GetMapping("")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Users", description = "Get All Users", tags = {"User"},security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<MessageResponse> getAll(){
        try {
            List<User> users = this.userService.getAll();

            if(users == null || users.isEmpty()){
                return super.getNotContentResponseEntity();
            }

            MessageResponse response = MessageResponse
                    .builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(users)
                    .build();

            return ResponseEntity.ok(response);

        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
