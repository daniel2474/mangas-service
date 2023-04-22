package com.mangas.models.entity;

import java.util.Arrays;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Manga {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private byte[] portada;

	private String nombre;

	private String autor;

	private String resumen;

	private Integer visitas;

	@OneToMany(mappedBy = "manga")
	@Cascade(CascadeType.ALL)
	private List<MangaCategoria> categorias;

	@OneToMany(mappedBy = "manga")
	@Cascade(CascadeType.ALL)
	private List<Imagen> imagenes;

	public byte[] obtenerPortada() {
		return portada;
	}

	public void setPortada(byte[] portada) {
		this.portada = portada;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public List<MangaCategoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<MangaCategoria> categorias) {
		this.categorias = categorias;
	}

	public List<Imagen> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public Integer getVisitas() {
		return visitas;
	}

	public void setVisitas(Integer visitas) {
		this.visitas = visitas;
	}

	@Override
	public String toString() {
		return "Manga [id=" + id + ", nombre=" + nombre + ", autor=" + autor + ", categorias=" + categorias
				+ ", imagenes=" + imagenes + "]";
	}

}
