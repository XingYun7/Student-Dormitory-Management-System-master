package com.sdms.service;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.OperationResult;
import com.sdms.entity.TeachingClass;

import java.util.Collection;
import java.util.List;

public interface TeachingClassService extends BaseEntityService<TeachingClass> {

    Page<TeachingClass> fetchPage(PageRequest pageRequest);

    TeachingClass getTeachingClassById(Long id);

    OperationResult<TeachingClass> saveTeachingClass(TeachingClass teachingClass);

    OperationResult<String> deleteTeachingClassByIds(Collection<Long> ids);

    List<TeachingClass> listAllTeachingClasses();
}
