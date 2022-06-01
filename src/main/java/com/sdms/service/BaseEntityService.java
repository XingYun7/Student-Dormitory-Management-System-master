package com.sdms.service;

public interface BaseEntityService<T> {
    @SuppressWarnings("unused")
    default void fillTransientFields(T entity) {
    }
}
