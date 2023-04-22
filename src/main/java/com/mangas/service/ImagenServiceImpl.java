package com.mangas.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mangas.models.dao.ImagenDao;
import com.mangas.models.entity.Imagen;
@Service
public class ImagenServiceImpl implements IImagenService {
	
	@Autowired
	private ImagenDao imagenDao;



	@Override
	public Imagen findById(Integer id) {
		return imagenDao.findById(id).get();
	}

}
