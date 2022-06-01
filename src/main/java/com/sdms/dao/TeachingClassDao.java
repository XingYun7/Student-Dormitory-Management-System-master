package com.sdms.dao;

import com.sdms.entity.TeachingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.Collection;

public interface TeachingClassDao extends JpaRepository<TeachingClass, Long>, JpaSpecificationExecutor<TeachingClass> {

    @Modifying
    @Transactional
    void deleteTeachingClassesByIdIn(Collection<Long> ids);

}
