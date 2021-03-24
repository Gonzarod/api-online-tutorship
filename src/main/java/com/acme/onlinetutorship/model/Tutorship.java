package com.acme.onlinetutorship.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tutorship")
@Data
@AllArgsConstructor
@Builder
public class Tutorship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Topic cannot be null")
    @NotBlank(message = "Topic cannot be blank")
    @Size(max = 150)
    private String topic;

    @Column(nullable = false, updatable = false)
    private LocalDateTime start_at;

    @Column(nullable = false, updatable = false)
    private LocalDateTime end_at;

    @NotNull(message = "Status cannot be null")
    @NotBlank(message = "Status cannot be blank")
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private EStatus status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    private User student;


    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "teacher_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    private User teacher;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    private Course course;

    public Tutorship(LocalDateTime start_at,LocalDateTime end_at,EStatus status,String topic,User student,Course course){
        this.start_at=start_at;
        this.end_at=end_at;
        this.status=status;
        this.topic=topic;
        this.student=student;
        this.course=course;
    }

    public Tutorship() {

    }

}
