package com.mangas.models.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mangas.models.entity.Categoria;

public interface CategoriaDao extends CrudRepository<Categoria, Integer>{
	List<Categoria> findAllByOrderByNombre();
	Categoria findByNombre(String name);
}
