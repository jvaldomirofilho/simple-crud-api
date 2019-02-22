package com.jvsf.simplecrudapi.repository.pessoa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jvsf.simplecrudapi.models.dto.PessoaDTO;
import com.jvsf.simplecrudapi.resources.filters.PessoaFilter;

public interface PessoaRepositoryQuery {
	
	Page<PessoaDTO> findByFilter(PessoaFilter filter, Pageable pageable);
	
	Long countByFilter(PessoaFilter filter);
	
	List<PessoaDTO> findDadosRelatorio(PessoaFilter pessoaFilter);
	
}
