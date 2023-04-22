package com.mangas.models.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Categoria {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true)
	private String nombre;
	
	@OneToMany(mappedBy = "categoria")
	private List<MangaCategoria> mangas;
	
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

	public void setMangas(List<MangaCategoria> mangas) {
		this.mangas = mangas;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre +  "]";
	}
	
	

}
