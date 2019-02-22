package com.jvsf.simplecrudapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import com.jvsf.simplecrudapi.models.Pessoa;
import com.jvsf.simplecrudapi.repository.pessoa.PessoaRepositoryQuery;


public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery {

	Page<Pessoa> findByNomeAndCpfOrderByIdAsc(@Nullable String nome, @Nullable String cpf, Pageable pageable);
	
	
}
