package com.acme.onlinetutorship.service.impl;

import com.acme.onlinetutorship.model.Course;
import com.acme.onlinetutorship.repository.CourseRepository;
import com.acme.onlinetutorship.service.CourseService;
import com.acme.onlinetutorship.service.exception.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Override
    public List<Course> getAll() throws ServiceException {
        try{
            return this.courseRepository.findAll();
        }catch (Exception e){
            throw new ServiceException(e);
        }

    }

    @Override
    public Optional<Course> findById(Course course) throws ServiceException {
        try {
            return this.courseRepository.findById(course.getId());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Course insert(Course course) throws ServiceException {
        try {
            return this.courseRepository.save(course);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public Course update(Course course) throws ServiceException {
        try {

            Course updatedCourse = this.findById(course).orElse(null);
            if (updatedCourse == null) {
                return null;
            }
            BeanUtils.copyProperties(course, updatedCourse);

            return this.courseRepository.save(updatedCourse);

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Course delete(Course course) throws ServiceException {
        try {
            Course deletedCourse = this.findById(course).orElse(null);
            if (deletedCourse == null) {
                throw new ServiceException("Id no valido");
            }
            this.courseRepository.delete(deletedCourse);
            return course;
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public Page<Course> getAllCoursesPaginated(Pageable pageable) throws ServiceException{
        try {
            return this.courseRepository.findAll(pageable);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public Page<Course> getCoursesPaginatedByName(String name, Pageable pageable) throws ServiceException {
        try{
            Page<Course> coursePage;
            if (name == null) {
                coursePage = this.courseRepository.findAll(pageable);
            } else {
                coursePage = this.courseRepository.findByNameContaining(name,pageable);
            }

            return coursePage;
        } catch (Exception e){
            throw new ServiceException(e);
        }

    }

    @Override
    public List<Course> getCoursesByName(String name) throws ServiceException{
        List<Course> courses;
        //Course name is optional
        if (name==null){
            courses=this.courseRepository.findAll();
        }else{
            courses=this.courseRepository.findByNameContaining(name);
        }
        return courses;
    }
}
