package com.smetering.out.repositories;

import java.util.Optional;
import java.util.Set;

/**
 * Обобщённый репозиторий сущностей
 * @param <E> тип элементов, хранящихся в репозитории
 * @param <ID> тип идентификатора, уникального для каждого хранящегося элемента
 */
public interface GenericRepository <E, ID> {

    Optional<E> get(ID mRID);
    Set<E> get();
    void persist(E entity);
    void remove(E entity);

}
