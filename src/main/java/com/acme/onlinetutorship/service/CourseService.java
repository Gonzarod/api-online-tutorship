package com.acme.onlinetutorship.service;

import com.acme.onlinetutorship.model.Course;
import com.acme.onlinetutorship.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService extends GenericService<Course> {
    Page<Course> getAllCoursesPaginated(Pageable pageable) throws ServiceException;
    Page<Course> getCoursesPaginatedByName(String name, Pageable pageable) throws ServiceException;   //Course name is optional
    List<Course> getCoursesByName(String name) throws ServiceException;   //Course name is optional


}
