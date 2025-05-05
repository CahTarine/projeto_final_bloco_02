package com.projeto_final_bloco_02.projeto_final_bloco_02.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.projeto_final_bloco_02.projeto_final_bloco_02.model.Produtos;

public interface ProdutosRepository extends JpaRepository<Produtos, Long>{

	public List<Produtos> findAllByNomeContainingIgnoreCase(@Param ("nome") String nome);
}
