package com.mangas.service;

import java.util.List;

import com.mangas.models.entity.Categoria;
import com.mangas.models.entity.Manga;


public interface IMangaService {

	public List<Manga> findAll(Integer page);
	public Manga findById(Integer id);
	public List<Manga> findAllOrderByName(Integer page);
	public List<Manga> findAllMostViewed(Integer page);
	public List<Manga> findAllByAutor(String autor,Integer page);
	public List<Manga> findAllByCategory(List<?> mangaCategoria);
	public List<?> findAllMangasByCategory(List<Categoria> id);
	public List<Manga> findByName(String nombre,Integer page);
	public Integer size();
	public Manga save(Manga manga);
	public void delete(Integer id);
}
