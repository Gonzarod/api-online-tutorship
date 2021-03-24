package com.acme.onlinetutorship.service.impl;

import com.acme.onlinetutorship.model.User;
import com.acme.onlinetutorship.repository.UserRepository;
import com.acme.onlinetutorship.service.UserService;
import com.acme.onlinetutorship.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAll() throws ServiceException {

        try{
            return this.userRepository.findAll();
        }catch (Exception e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findById(User user) throws ServiceException {
        try {
            return this.userRepository.findById(user.getId());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User insert(User user) throws ServiceException {
        return null;
    }

    @Override
    public User update(User user) throws ServiceException {
        return null;
    }

    @Override
    public User delete(User user) throws ServiceException {
        return null;
    }
}
