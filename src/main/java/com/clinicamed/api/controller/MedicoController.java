package com.clinicamed.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.clinicamed.api.medico.DadosAtualizacaoMedico;
import com.clinicamed.api.medico.DadosCadastroMedico;
import com.clinicamed.api.medico.DadosDetalhadosMedico;
import com.clinicamed.api.medico.DadosListagemMedico;
import com.clinicamed.api.medico.Medico;
import com.clinicamed.api.medico.MedicoRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	
	@Autowired
	private MedicoRepository repository;
	
	@PostMapping
	@Transactional
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
		var medico = new Medico(dados);
		repository.save(medico);
		var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosDetalhadosMedico(medico));
	}
	
	@GetMapping
	public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(sort= {"nome"})  Pageable paginacao) { 
//		return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
		var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
		return ResponseEntity.ok(page);
	}

	@PutMapping
	@Transactional 
	public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
		var medico = repository.getReferenceById(dados.id());
		medico.atualizarInformacoes(dados);
		return ResponseEntity.ok(new DadosDetalhadosMedico(medico));
	}
	
//	@DeleteMapping("/{id}") 
//	@Transactional
//	public void excluir(@PathVariable Long id) {
//		repository.deleteById(id);
//}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id) {
	var medico = repository.getReferenceById(id);
	medico.excluir();
	return ResponseEntity.noContent().build();
}
	
	@GetMapping("/{id}")
	public ResponseEntity detalharPorId(@PathVariable Long id) {
	var medico = repository.getReferenceById(id);
	return ResponseEntity.ok(new DadosDetalhadosMedico(medico));
}
	
}