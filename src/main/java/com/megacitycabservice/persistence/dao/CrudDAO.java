package com.megacitycabservice.persistence.dao;
import java.util.List;

public interface CrudDAO<T,ID> {
    String insert(T model);
    List<T> getAll();
    String update(T model);
    Boolean delete(ID id);
}
