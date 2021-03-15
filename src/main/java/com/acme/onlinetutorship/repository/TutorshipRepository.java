package com.acme.onlinetutorship.repository;

import com.acme.onlinetutorship.model.EStatus;
import com.acme.onlinetutorship.model.Tutorship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorshipRepository {

    List<Tutorship> getAllByStudentIdAndStatusEquals(Long studentId, EStatus status);
    List<Tutorship> getAllByStatusEquals(EStatus status);

    Page<Tutorship> getAllByStatusEqualsPage(EStatus status);

}
