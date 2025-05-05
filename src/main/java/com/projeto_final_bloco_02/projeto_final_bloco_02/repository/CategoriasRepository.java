package com.projeto_final_bloco_02.projeto_final_bloco_02.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.projeto_final_bloco_02.projeto_final_bloco_02.model.Categorias;

public interface CategoriasRepository extends JpaRepository<Categorias, Long> {

	public List<Categorias> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);
}
