package com.jvsf.simplecrudapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jvsf.simplecrudapi.models.Pessoa;
import com.jvsf.simplecrudapi.models.Telefone;


public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
	
	List<Telefone> findByPessoa(Pessoa pessoa);

}
