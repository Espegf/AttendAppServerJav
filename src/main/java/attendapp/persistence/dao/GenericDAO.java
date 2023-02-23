package attendapp.persistence.dao;

import java.util.Optional;

public interface GenericDAO<T> {

    void create(T entity);

    void save(T entity);

    void deleteById(Long id);

    void delete(T entity);

    //optional se usa por si devuelve null, para evitar el nullpointerException
    Optional<T> findById(Long id);
}
