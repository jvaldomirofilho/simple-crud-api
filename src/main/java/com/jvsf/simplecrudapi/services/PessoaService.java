package com.jvsf.simplecrudapi.services;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jvsf.simplecrudapi.config.exceptions.PessoaInexistenteException;
import com.jvsf.simplecrudapi.models.Pessoa;
import com.jvsf.simplecrudapi.models.dto.PessoaDTO;
import com.jvsf.simplecrudapi.repository.PessoaRepository;
import com.jvsf.simplecrudapi.repository.TelefoneRepository;
import com.jvsf.simplecrudapi.resources.filters.PessoaFilter;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	public Page<PessoaDTO> findByFilters(PessoaFilter filter, Pageable pageable) {
		return pessoaRepository.findByFilter(filter, pageable);
	}
	
	public Pessoa findById(Long id) {
		return pessoaRepository.findById(id).orElse(null);
	}
	
	public Pessoa save(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}
	
	public void delete(Pessoa pessoa) {
		pessoaRepository.delete(pessoa);
	}
	
	public void deleteById(Long pessoa) {
		pessoaRepository.deleteById(pessoa);
	}
	
	public byte[] relatorioPorPessoa(PessoaFilter pessoaFilter) throws Exception {
		List<PessoaDTO> dados = pessoaRepository.findDadosRelatorio(pessoaFilter);
		
		Map<String, Object> parametros = new HashMap<>();
	
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/relatorios/pessoas_pdf.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros,
				new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
	
	public Pessoa atualizar(Long id, Pessoa novaPessoa) {
		Pessoa pessoaOld = pessoaRepository.findById(id).orElseThrow(() -> new PessoaInexistenteException());
		removerTelefonesExlcuidosPeloUsuario(novaPessoa, pessoaOld);
		BeanUtils.copyProperties(novaPessoa, pessoaOld, "id");
		
		return pessoaRepository.save(pessoaOld);
	}

	private void removerTelefonesExlcuidosPeloUsuario(Pessoa novaPessoa, Pessoa pessoaOld) {
		telefoneRepository.deleteAll(pessoaOld.getTelefones());
	}
	
}
