package com.sdms.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sdms.common.annotation.PageRequestCheck;
import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.OperationResult;
import com.sdms.dao.TeachingClassDao;
import com.sdms.entity.TeachingClass;
import com.sdms.qmodel.QTeachingClass;
import com.sdms.service.TeachingClassService;
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
public class TeachingClassServiceImpl implements TeachingClassService {

    @Resource
    private TeachingClassDao teachingClassDao;

    @Resource
    private JPAQueryFactory queryFactory;

    @Override
    @PageRequestCheck
    public Page<TeachingClass> fetchPage(PageRequest request) {
        boolean vague = request.getQueryType() == 0; // 0 表示模糊查询
        val param = request.getParam();
        val teachingClassId = parseLong(param.get("teachingClassId"));
        val keyWord = trimAllWhitespace(param.get("keyWord"));
        val teachingClass = QTeachingClass.teachingClass;
        // 动态拼接查询条件
        val condition = new BooleanBuilder();
        if (teachingClassId != null) {
            condition.or(teachingClass.id.eq(teachingClassId));
        }
        if (vague) {
            if (!isBlankOrNull(keyWord)) {
                // 模糊查询
                condition.or(teachingClass.name.like(String.format("%%%s%%", keyWord)));
            }
        } else {
            if (!isBlankOrNull(keyWord)) {
                // 精确查询
                condition.or(teachingClass.name.eq(keyWord));
            }
        }

        val query = queryFactory
                .select(Projections.bean(TeachingClass.class,
                        teachingClass.id,
                        teachingClass.name))
                .from(teachingClass)
                .orderBy(teachingClass.id.asc())
                .where(condition)
                .offset((request.getPage() - 1) * request.getLimit()).limit(request.getLimit());
        // 执行分页查询
        val result = query.fetchResults();
        return Page.of(result);
    }

    @Override
    public TeachingClass getTeachingClassById(Long id) {
        val optional = teachingClassDao.findById(id);
        if (optional.isPresent()) {
            val teachingClass = optional.get();
            this.fillTransientFields(teachingClass);
            return teachingClass;
        }
        return null;
    }

    @Override
    public OperationResult<TeachingClass> saveTeachingClass(TeachingClass teachingClass) {
        if (teachingClass == null) {
            return failure("班级为null");
        }
        val name = teachingClass.getName();
        if (isBlankOrNull(name)) {
            return failure("班级名称不可以为空或者空白串");
        }
        return success(teachingClassDao.save(teachingClass));
    }

    @Override
    public OperationResult<String> deleteTeachingClassByIds(Collection<Long> ids) {
        try {
            teachingClassDao.deleteTeachingClassesByIdIn(ids);
        } catch (Exception e) {
            log.error("班级删除失败,ids=" + ids + ",error is " + e.getMessage());
            return failure("删除失败");
        }
        return success("删除成功");
    }

    @Override
    public List<TeachingClass> listAllTeachingClasses() {
        return teachingClassDao.findAll();
    }
}
