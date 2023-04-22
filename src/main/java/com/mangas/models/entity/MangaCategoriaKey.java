package com.mangas.models.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MangaCategoriaKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "manga_id")
    Integer mangaId;

    @Column(name = "categoria_id")
    Integer categoriaId;

	@Override
	public int hashCode() {
		return Objects.hash(categoriaId, mangaId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MangaCategoriaKey other = (MangaCategoriaKey) obj;
		return Objects.equals(categoriaId, other.categoriaId) && Objects.equals(mangaId, other.mangaId);
	}
    
    
}
