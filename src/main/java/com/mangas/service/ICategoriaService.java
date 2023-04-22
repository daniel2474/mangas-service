package com.mangas.service;

import java.util.List;

import com.mangas.models.entity.Categoria;

public interface ICategoriaService {

	public List<Categoria> findAll();
	public Categoria findById(Integer id);
	public Categoria findByName(String name);
}
