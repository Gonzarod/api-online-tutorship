package com.acme.onlinetutorship.repository;

import com.acme.onlinetutorship.model.EStatus;
import com.acme.onlinetutorship.model.Tutorship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorshipRepository extends JpaRepository<Tutorship,Long > {

    List<Tutorship> getAllByStudentIdAndStatusEquals(Long studentId, EStatus status);
    List<Tutorship> getAllByStatusEquals(EStatus status);

    Page<Tutorship> getAllByStatusEquals(EStatus status, Pageable pageable);

}
