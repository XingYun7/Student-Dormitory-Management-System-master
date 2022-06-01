package com.sdms.service;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.OperationResult;
import com.sdms.entity.Student;

import java.util.Collection;

public interface RoomAllocationService {

    Page<Student> fetchPage(PageRequest pageRequest);

    OperationResult<String> releaseStudentByIds(Collection<String> ids);
}
