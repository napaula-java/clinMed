package com.clinicamed.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinicamed.api.medico.DadosAtualizacaoMedico;
import com.clinicamed.api.medico.DadosCadastroMedico;
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
	public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
		repository.save(new Medico(dados));
	}
	
	@GetMapping
	public Page<DadosListagemMedico> listar(@PageableDefault(sort= {"nome"})  Pageable paginacao) { 
		return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
	}

	@PutMapping
	@Transactional 
	public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
		var medico = repository.getReferenceById(dados.id());
		medico.atualizarInformacoes(dados);
	}
	
//	@DeleteMapping("/{id}") 
//	@Transactional
//	public void excluir(@PathVariable Long id) {
//		repository.deleteById(id);
//}
	
	@DeleteMapping("/{id}")
	@Transactional
	public void excluir(@PathVariable Long id) {
	var medico = repository.getReferenceById(id);
	medico.excluir();
	
}
	
}