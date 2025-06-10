package com.example.projetofinaljavav2.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T extends Serializable> {
    void create(T entity);
    T readById(int id);
    List<T> readAll();
    void update(T entity);
    void delete(int id);
}

