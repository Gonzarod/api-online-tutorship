package com.acme.onlinetutorship.service;

import com.acme.onlinetutorship.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface GenericService<T> {
    List<T> getAll() throws ServiceException;

    Optional<T> findById(T t) throws ServiceException;

    T insert(T t) throws ServiceException;

    T update(T t) throws ServiceException;

    T delete(T t) throws ServiceException;


}
