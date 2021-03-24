package com.acme.onlinetutorship.dto;

import com.acme.onlinetutorship.model.Course;
import com.acme.onlinetutorship.model.EStatus;
import com.acme.onlinetutorship.model.User;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class TutorshipDTO {

    @NotNull(message = "Topic cannot be null")
    @NotBlank(message = "Topic cannot be blank")
    @Size(max = 150)
    private String topic;

    @Column(nullable = false, updatable = false)
    private LocalDateTime start_at;

    @Column(nullable = false, updatable = false)
    private LocalDateTime end_at;

}
