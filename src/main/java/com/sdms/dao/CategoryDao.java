package com.sdms.dao;

import com.sdms.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.Collection;

public interface CategoryDao extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    @Modifying
    @Transactional
    void deleteCategoriesByIdIn(Collection<Long> ids);
}