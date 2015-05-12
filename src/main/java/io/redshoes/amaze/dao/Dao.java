package io.redshoes.amaze.dao;

import java.util.List;

import io.redshoes.amaze.entity.Entity;


public interface Dao<T extends Entity, I>
{

	List<T> findAll();


	T find(I id);


	T save(T entity);


	void delete(I id);

}