package com.projeto_final_bloco_02.projeto_final_bloco_02.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.projeto_final_bloco_02.projeto_final_bloco_02.model.Produtos;
import com.projeto_final_bloco_02.projeto_final_bloco_02.repository.CategoriasRepository;
import com.projeto_final_bloco_02.projeto_final_bloco_02.repository.ProdutosRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutosController {

	@Autowired
	private ProdutosRepository produtosRepository;

	@Autowired
	private CategoriasRepository categoriasRepository;
	
	
	@PostMapping
	public ResponseEntity<Produtos> post(@Valid @RequestBody Produtos produto) {
		if (categoriasRepository.existsById(produto.getCategoria().getId()))
			return ResponseEntity.status(HttpStatus.CREATED)
				.body(produtosRepository.save(produto));
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria nao existe!", null);
	}
	
	@GetMapping
	public ResponseEntity<List<Produtos>> getAll() {
		return ResponseEntity.ok(produtosRepository.findAll());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produtos>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(produtosRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produtos> getById(@PathVariable Long id) {
		return  produtosRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
						.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@PutMapping
	public ResponseEntity<Produtos> put(@Valid @RequestBody Produtos produto){
		if (produtosRepository.existsById(produto.getId())) {
			
			if (categoriasRepository.existsById(produto.getCategoria().getId()))
				return ResponseEntity.status(HttpStatus.OK)
						.body(produtosRepository.save(produto));
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria nao existe!", null);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Produtos> produto = produtosRepository.findById(id);
		
		if (produto.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		produtosRepository.deleteById(id);
	}
}
