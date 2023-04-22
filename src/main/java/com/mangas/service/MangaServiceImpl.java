package com.mangas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mangas.models.dao.MangaDao;
import com.mangas.models.entity.Categoria;
import com.mangas.models.entity.Manga;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class MangaServiceImpl implements IMangaService {
	@PersistenceContext
    private EntityManager entityManager;
	@Autowired
	private MangaDao mangaDao;

	@Override
	@Transactional(readOnly=true)
	public List<Manga> findAll(Integer page) {	
		List<Manga> mangas= mangaDao.findAllOrderId(page);
		for(int i=0;i<mangas.size();i++) {
			mangas.get(i).setImagenes(null);
		}
		return mangas;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Manga> findAllOrderByName(Integer page){
		List<Manga> mangas=mangaDao.findAllOrderByName(page);
		for(int i=0;i<mangas.size();i++) {
			mangas.get(i).setImagenes(null);
		}
		return mangas;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Manga> findAllMostViewed(Integer page){
		List<Manga> mangas=mangaDao.findAllMostViewed(page);
		for(int i=0;i<mangas.size();i++) {
			mangas.get(i).setImagenes(null);
		}
		return mangas;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Manga> findAllByAutor(String autor,Integer page){
		List<Manga> mangas=mangaDao.findAllByAutor(autor, page);
		for(int i=0;i<mangas.size();i++) {
			mangas.get(i).setImagenes(null);
		}
		return mangas;
	}

	public List<?> findAllMangasByCategory(List<Categoria>categoria){
		String query="select manga_id from manga_Categoria where categoria_id="+categoria.get(0).getId();
		for (Categoria item : categoria) {
			query="select manga_id from manga_Categoria where manga_id in ("+query+")and categoria_id="+item.getId();			
		}
		List<?>mangaCategoria=entityManager.createNativeQuery(query)
				.getResultList();
		return mangaCategoria;
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<Manga> findAllByCategory(List<?> mangaCategoria){
		Query mangaQuery=entityManager.createQuery("from Manga m where m.id in :lista order by m.id desc ",Manga.class);
		
		List <Manga> mangas=mangaQuery.setParameter("lista", mangaCategoria)
				.getResultList();
		return mangas;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Manga> findByName(String nombre,Integer page){
		List<Manga> mangas=mangaDao.findListMangaByName(nombre,page);
		for(int i=0;i<mangas.size();i++) {
			mangas.get(i).setImagenes(null);
		}
		return mangas;
	}

	@Override
	@Transactional(readOnly=true)
	public Manga findById(Integer id) {
		return mangaDao.findById(id).orElse(null);
	}

	@Override
	public Manga save(Manga manga) {
		return mangaDao.save(manga);
	}

	@Override
	public void delete(Integer id) {
		 mangaDao.delete(mangaDao.findById(id).orElse(null));
	}

	@Override
	public Integer size() {
		Integer size=mangaDao.size();
		return  size;
	}

}
