package hu.neuron.mentoring.core.dao;

import java.util.List;

public interface DAO<T> {

    T findById(long id);

    List<T> getAll();

    void save(T t);

    T update(T t);

    void delete(long id);
}
