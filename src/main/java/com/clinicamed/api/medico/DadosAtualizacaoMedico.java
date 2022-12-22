package com.clinicamed.api.medico;

import com.clinicamed.api.endereco.DadosEndereco;

import jakarta.validation.constraints.NotNull;


public record DadosAtualizacaoMedico(
		@NotNull
		Long id,
		String nome,
		String telefone,
		DadosEndereco endereco) {

}
 