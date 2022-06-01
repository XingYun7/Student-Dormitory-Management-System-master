package com.sdms.dao;

import com.sdms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.Collection;

public interface StudentDao extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student> {

    @Modifying
    @Transactional
    void deleteStudentsByIdIn(Collection<String> ids);

}
