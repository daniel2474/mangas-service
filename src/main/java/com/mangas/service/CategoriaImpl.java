package com.mangas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mangas.models.dao.CategoriaDao;
import com.mangas.models.entity.Categoria;

@Service
public class CategoriaImpl implements ICategoriaService {

	@Autowired
	private CategoriaDao categoriaDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Categoria> findAll() {
		return (List<Categoria>)categoriaDao.findAllByOrderByNombre();
	}

	@Override
	@Transactional(readOnly=true)
	public Categoria findById(Integer id) {
		return categoriaDao.findById(id).orElse(null);
	}
	 
	public Categoria findByName(String name){
		return categoriaDao.findByNombre(name);
	}

}
