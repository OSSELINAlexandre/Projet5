package com.safety.savinglives.safetynetapplication.repository;

import java.util.List;

public interface DAOMethods<T> {

	List<T> getAllData();

	Boolean save(T t);

	Boolean delete(T t);

	T update(int i, T t);
}
