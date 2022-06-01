package com.sdms.service;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.OperationResult;
import com.sdms.entity.Category;

import java.util.Collection;
import java.util.List;

public interface CategoryService extends BaseEntityService<Category> {

    Page<Category> fetchPage(PageRequest pageRequest);

    Category getCategoryById(Long id);

    OperationResult<Category> saveCategory(Category category);

    OperationResult<String> deleteCategoryByIds(Collection<Long> ids);

    List<Category> listAllCategories();
}