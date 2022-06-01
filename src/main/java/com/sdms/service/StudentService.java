package com.sdms.service;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.OperationResult;
import com.sdms.entity.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService extends BaseEntityService<Student> {

    Page<Student> fetchPage(PageRequest pageRequest);

    Student getStudentById(String id);

    OperationResult<Student> saveStudent(Student student);

    OperationResult<String> deleteStudentByIds(Collection<String> ids);

    List<Student> listAllStudents();
}