package com.mangas.models.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name="manga_categoria")
public class MangaCategoria {
	@EmbeddedId
    MangaCategoriaKey id;
	
	@ManyToOne 
    @MapsId("mangaId")
    @JoinColumn(name = "manga_id")
	private Manga manga;
	
	@ManyToOne
    @MapsId("categoriaId")
    @JoinColumn(name = "categoria_id")
	private Categoria categoria;

	
	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	@Override
	public String toString() {
		return "MangaCategoria [manga=" + manga + ", categoria=" + categoria + "]";
	}

}
