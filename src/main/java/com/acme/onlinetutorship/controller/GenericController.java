package com.acme.onlinetutorship.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.acme.onlinetutorship.controller.commons.MessageResponse;
import com.acme.onlinetutorship.controller.constants.ResponseConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;


public class GenericController {
	
	protected String formatMapMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
      return errors.toString();

	}
	
	protected MessageResponse getNotContent() {
		
		return MessageResponse
				.builder()
				.code(ResponseConstants.WARNING_CODE)
				.message(ResponseConstants.MSG_WARNING_CONS )
				.data(null)
				.build();
	}
	
	protected ResponseEntity<MessageResponse> getNotContentResponseEntity(){
		
		return ResponseEntity.status(HttpStatus.OK).body(this.getNotContent());
		
	}
}
