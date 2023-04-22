package com.mangas.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mangas.models.entity.Manga;


public interface MangaDao extends CrudRepository<Manga, Integer>{
	@Query(value = "SELECT m FROM Manga m order by m.id desc limit 24 offset :page")
	List<Manga> findAllOrderId(@Param(value = "page") Integer page);
	
	@Query(value = "SELECT m FROM Manga m order by m.nombre limit 24 offset :page")
	List<Manga> findAllOrderByName(@Param(value = "page") Integer page);
	
	@Query(value = "SELECT m FROM Manga m order by m.visitas desc limit 24 offset :page")
	List<Manga> findAllMostViewed(@Param(value = "page") Integer page);
	
	@Query(value = "SELECT m FROM Manga m where m.autor=:autor order by m.nombre asc limit 24 offset :page")
	List<Manga> findAllByAutor(@Param(value = "autor") String autor,@Param(value = "page") Integer page);
	
	@Query(value = "SELECT * FROM manga m where lower(m.nombre) like %:nombre% order by m.nombre limit 24 offset :page",nativeQuery = true)
	List<Manga> findListMangaByName(@Param(value = "nombre") String nombre,@Param(value = "page") Integer page);

	@Query(value = "SELECT count(*) FROM Manga m",nativeQuery = true)
	Integer size();
	
	
	
}

