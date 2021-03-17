package com.acme.onlinetutorship.service.impl;

import com.acme.onlinetutorship.model.Tutorship;
import com.acme.onlinetutorship.repository.TutorshipRepository;
import com.acme.onlinetutorship.service.TutorshipService;
import com.acme.onlinetutorship.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorshipServiceImpl implements TutorshipService {

    @Autowired
    TutorshipRepository tutorshipRepository;

    @Override
    public List<Tutorship> getAll() throws ServiceException {
        try{
            return this.tutorshipRepository.findAll();
        }catch (Exception e){
            throw new ServiceException(e);
        }

    }

    @Override
    public Optional<Tutorship> findById(Tutorship tutorship) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public Tutorship insert(Tutorship tutorship) throws ServiceException {
        return null;
    }

    @Override
    public Tutorship update(Tutorship tutorship) throws ServiceException {
        return null;
    }

    @Override
    public Tutorship delete(Tutorship tutorship) throws ServiceException {
        return null;
    }
}
