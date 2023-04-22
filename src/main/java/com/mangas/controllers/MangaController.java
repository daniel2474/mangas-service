package com.mangas.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mangas.models.entity.Categoria;
import com.mangas.models.entity.Imagen;
import com.mangas.models.entity.Manga;
import com.mangas.models.entity.MangaCategoria;
import com.mangas.service.CategoriaImpl;
import com.mangas.service.ImagenServiceImpl;
import com.mangas.service.MangaServiceImpl;

@RestController
@CrossOrigin(origins = "*")
public class MangaController {

	Logger log = LoggerFactory.getLogger(MangaController.class);

	@Autowired
	private MangaServiceImpl mangaService;

	@Autowired
	private CategoriaImpl categoriaService;

	@Autowired
	private ImagenServiceImpl imagenService;

	public final static Integer MANGA_FOR_PAGE = 24;

	@GetMapping("/manga/page/{filename}")
	public ResponseEntity<byte[]> getImageMangaPage(@PathVariable("filename") Integer filename) {
		Imagen imagen = imagenService.findById(filename);
		byte[] image = new byte[0];
		try {
			image = imagen.obtenerImagen();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
	}

	@GetMapping("/listar/categories")
	public ResponseEntity<List<Categoria>> getCategories() {
		List<Categoria> categorias = categoriaService.findAll();
		return ResponseEntity.ok().body(categorias);
	}

	@GetMapping("/photos/{filename}")
	public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
		try {

		} catch (Exception e) {

		}
		Manga manga = mangaService.findById(Integer.parseInt(filename));
		byte[] image = new byte[0];
		try {
			image = manga.obtenerPortada();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
	}

	@GetMapping("/listar/{page}")
	public HashMap<String, Object> listar(@PathVariable Integer page) {
		page = page - 1;
		Integer size = mangaService.size();
		Integer pages = size / MANGA_FOR_PAGE;
		Integer mod = size % MANGA_FOR_PAGE;
		if (mod != 0) {
			pages = pages + 1;
		}
		HashMap<String, Object> response = new HashMap<String, Object>();
		List<Manga> listaMangas = mangaService.findAll(page * MANGA_FOR_PAGE);
		response.put("totalPaginas", pages);
		response.put("listaMangas", listaMangas);
		response.put("prevPage", page);
		response.put("nextPage", page + 2);
		return response;
	}

	@GetMapping("/listarPorNombre/{page}")
	public List<Manga> listarPorNombre(@PathVariable Integer page) {
		page = page - 1;
		return mangaService.findAllOrderByName(page * MANGA_FOR_PAGE);
	}

	@GetMapping("/listarMasVistas/{page}")
	public HashMap<String, Object> listarPorNumVistas(@PathVariable Integer page) {
		page = page - 1;
		Integer size = mangaService.size();
		Integer pages = size / MANGA_FOR_PAGE;
		Integer mod = size % MANGA_FOR_PAGE;
		if (mod != 0) {
			pages = pages + 1;
		}
		List<Manga> listaMangas = mangaService.findAllMostViewed(page * MANGA_FOR_PAGE);
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("totalPaginas", pages);
		response.put("listaMangas", listaMangas);
		response.put("prevPage", page);
		response.put("nextPage", page + 2);
		response.put("pagina", page + 1);
		return response;
	}

	@RequestMapping(value = "/manga/categories", method = RequestMethod.GET)
	public ResponseEntity<HashMap<String, Object>> filterBy(@RequestParam(name = "categorias") List<Integer> categorias,
			@RequestParam(name = "page") Integer page) {

		page = page - 1;
		List<Manga> manga = new ArrayList<Manga>();
		List<Categoria> categoriasObject = new ArrayList<Categoria>();
		categorias.forEach(s -> {
			Categoria categoria = categoriaService.findById(s);
			categoriasObject.add(categoria);
		});
		List<?> mangaCategoria = mangaService.findAllMangasByCategory(categoriasObject);
		Integer size = mangaCategoria.size();
		Integer pages = size / MANGA_FOR_PAGE;
		Integer mod = size % MANGA_FOR_PAGE;
		if (mod != 0) {
			pages = pages + 1;
		}
		manga = mangaService.findAllByCategory(mangaCategoria);
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("totalPaginas", pages);
		response.put("listaMangas", manga);
		response.put("prevPage", page);
		response.put("nextPage", page + 2);
		response.put("pagina", page + 1);

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/listarAutor{autor}{page}")
	@CrossOrigin(origins = "*")
	public List<Manga> listarAutor(@RequestParam String autor, @RequestParam Integer page) {
		page = page - 1;
		return mangaService.findAllByAutor(autor, page * MANGA_FOR_PAGE);
	}

	@GetMapping("/listarPorNombre/{titulo}/{page}")
	public List<Manga> listarPorNombre(@PathVariable String titulo, @PathVariable Integer page) {
		page = page - 1;
		return mangaService.findByName(titulo.toLowerCase(), page * MANGA_FOR_PAGE);
	}

	@GetMapping("/ver/{id}")
	public Manga ver(@PathVariable Integer id) {
		Manga manga = mangaService.findById(id);
		List<Imagen> imagenes = manga.getImagenes();

		Collections.sort(imagenes, (o1, o2) -> o1.getOrden().compareTo(o2.getOrden()));
		manga.setVisitas(manga.getVisitas() + 1);
		mangaService.save(manga);
		return manga;
	}

	@PostMapping("/manga")
	@ResponseBody
	public Manga save(@RequestBody Manga manga) {
		log.info(manga.toString());
		try {
			Integer size = manga.getCategorias().size();
			for (int i = 0; i < size; i++) {
				MangaCategoria categoria = manga.getCategorias().get(i);
				String nombreCategoria = categoria.getCategoria().getNombre();
				Categoria categoriaExiste = categoriaService.findByName(nombreCategoria);
				if (categoriaExiste != null) {
					manga.getCategorias().get(i).setCategoria(categoriaExiste);
				}
			}
			manga.setVisitas(0);
			manga = mangaService.save(manga);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return manga;
	}

	@DeleteMapping("/manga/{id}")
	@ResponseBody
	public void delete(@PathVariable Integer id) {
		mangaService.delete(id);
	}

	@PostMapping("/images/{idManga}")
	@ResponseBody
	public HashMap<String, Object> save(@PathVariable Integer idManga, @RequestBody List<Imagen> imagenes) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		HashMap<String, Object> objetoRespuesta = new HashMap<String, Object>();
		try {
			Manga manga = mangaService.findById(idManga);
			if (manga != null) {
				manga.setImagenes(imagenes);
				for (int i = 0; i < imagenes.size(); i++) {
					imagenes.get(i).setManga(manga);
				}
				mangaService.save(manga);

			}
			response.put("status", "200");
			objetoRespuesta.put("respuesta", "Informacion guardada correctamente");
			response.put("codigoRespuesta", objetoRespuesta);
			return response;
		} catch (Exception e) {
			System.out.println(e);
		}

		response.put("status", "500");
		objetoRespuesta.put("respuesta", "Error guardando la informacion");
		response.put("codigoRespuesta", objetoRespuesta);
		return response;
	}

	@PutMapping("/images/{idManga}")
	@ResponseBody
	public ResponseEntity<?> save(@PathVariable Integer idManga, @RequestBody Manga manga) {
		HashMap<String, String> response = new HashMap<String, String>();
		try {
			Manga mangaDes = mangaService.findById(idManga);
			mangaDes.setPortada(manga.obtenerPortada());
			mangaService.save(mangaDes);
			response.put("status", "200");
			response.put("respuesta", "Portada actualizada correctamente");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {

			response.put("status", "500");
			response.put("respuesta", "Error al actualizar la portada");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
