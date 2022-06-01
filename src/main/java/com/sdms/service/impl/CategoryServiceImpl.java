package com.sdms.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.OperationResult;
import com.sdms.dao.CategoryDao;
import com.sdms.entity.Category;
import com.sdms.qmodel.QCategory;
import com.sdms.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Collection;
import java.util.List;

import static com.sdms.common.result.OperationResult.failure;
import static com.sdms.common.result.OperationResult.success;
import static com.sdms.common.util.StringUtils.isBlankOrNull;
import static com.sdms.common.util.StringUtils.parseLong;
import static com.sdms.common.util.StringUtils.trimAllWhitespace;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryDao categoryDao;

    @Resource
    private JPAQueryFactory queryFactory;

    @Override
    public Page<Category> fetchPage(PageRequest request) {
        boolean vague = request.getQueryType() == 0; // 0 表示模糊查询
        val param = request.getParam();
        val categoryId = parseLong(param.get("categoryId"));
        val categoryName = trimAllWhitespace(param.get("keyWord"));
        val category = QCategory.category;
        // 动态拼接查询条件
        val condition = new BooleanBuilder();
        if (categoryId != null) {
            condition.or(category.id.eq(categoryId));
        }
        if (!isBlankOrNull(categoryName)) {
            if (vague) {
                // 模糊查询
                condition.or(category.name.like(String.format("%%%s%%", categoryName)));
            } else {
                // 精确查询
                condition.or(category.name.eq(categoryName));
            }
        }
        val query = queryFactory
                .select(Projections.bean(Category.class,
                        category.id,
                        category.name,
                        category.maxSize))
                .from(category)
                .orderBy(category.id.asc())
                .where(condition)
                .offset((request.getPage() - 1) * request.getLimit()).limit(request.getLimit());
        // 执行分页查询
        val result = query.fetchResults();
        return Page.of(result);
    }

    @Override
    public Category getCategoryById(Long id) {
        val optional = categoryDao.findById(id);
        if (optional.isPresent()) {
            val category = optional.get();
            this.fillTransientFields(category);
            return category;
        }
        return null;
    }

    @Override
    public OperationResult<Category> saveCategory(Category category) {
        if (category == null) {
            return failure("寝室类型为null");
        }
        val name = category.getName();
        if (isBlankOrNull(name)) {
            return failure("寝室类型名称不可以为空或者空白串");
        }
        return success(categoryDao.save(category));
    }

    @Override
    public OperationResult<String> deleteCategoryByIds(Collection<Long> ids) {
        try {
            categoryDao.deleteCategoriesByIdIn(ids);
        } catch (Exception e) {
            log.error("寝室类型删除失败,ids=" + ids + ",error is " + e.getMessage());
            return failure("删除失败");
        }
        return success("删除成功");
    }

    @Override
    public List<Category> listAllCategories() {
        return categoryDao.findAll();
    }
}
