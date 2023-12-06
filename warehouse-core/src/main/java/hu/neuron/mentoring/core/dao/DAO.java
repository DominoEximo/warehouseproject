package hu.neuron.mentoring.core.dao;

import java.util.List;

public interface DAO<T> {

    T findById(int id);

    List<T> getAll();

    void save(T t);

    T update(T t);

    void delete(T t);
}
