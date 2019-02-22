package com.jvsf.simplecrudapi.resources;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jvsf.simplecrudapi.config.exceptions.ApiExceptionHandler.Erro;
import com.jvsf.simplecrudapi.config.exceptions.PessoaInexistenteException;
import com.jvsf.simplecrudapi.models.Pessoa;
import com.jvsf.simplecrudapi.models.dto.Imagem;
import com.jvsf.simplecrudapi.models.dto.PessoaDTO;
import com.jvsf.simplecrudapi.resources.filters.PessoaFilter;
import com.jvsf.simplecrudapi.services.PessoaService;
import com.jvsf.simplecrudapi.storage.Disco;


@RestController
@RequestMapping("pessoa")
public class PessoaResource {

	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private Disco disco;
	
	@PostMapping("/imagem")
	public Imagem uploadImagem(@RequestParam MultipartFile imagem) throws IOException {
		return disco.salvarImagem(imagem);
	}
	
	@GetMapping("/relatorio")
	public ResponseEntity<byte[]> relatorioPorPessoa(PessoaFilter pessoaFilter) throws Exception {
	
		byte[] relatorio = pessoaService.relatorioPorPessoa(pessoaFilter);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
	}

	@GetMapping
	public ResponseEntity<Page<PessoaDTO>> findByFilters(PessoaFilter filter, Pageable pageable) {
		Page<PessoaDTO> pessoas = pessoaService.findByFilters(filter, pageable);
		return null != pessoas && pessoas.hasContent() ? ResponseEntity.ok(pessoas) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> findById(@PathVariable("id") Long idPessoa) {
		Pessoa pessoa = pessoaService.findById(idPessoa);
		return null != pessoa ? ResponseEntity.ok(pessoa) : ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> salvar(@RequestBody @Valid Pessoa pessoa) throws URISyntaxException {
		Pessoa pessoaCriada = pessoaService.save(pessoa);
		carregarPessoaParaTelefones(pessoaCriada);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
			.path("/{codigo}").buildAndExpand(pessoaCriada.getId()).toUri();
		return null != pessoaCriada ? ResponseEntity.created(uri).body(pessoaCriada) : null;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Pessoa> delete(@PathVariable("id") Long pessoa) {
		pessoaService.deleteById(pessoa);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable("id") Long id, @RequestBody @Valid Pessoa p) {
		carregarPessoaParaTelefones(p);
		Pessoa pessoaAtualizada = pessoaService.atualizar(id, p);
		return ResponseEntity.ok(pessoaAtualizada);
	}
	
	@ExceptionHandler({ PessoaInexistenteException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteException ex) {
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistentea", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	private void carregarPessoaParaTelefones(Pessoa pessoa) {
		if (null != pessoa.getTelefones() && !pessoa.getTelefones().isEmpty()) {
			pessoa.getTelefones().parallelStream().forEach(t -> {
				t.setPessoa(pessoa);
			});
		}
		pessoaService.save(pessoa);
	}
	
}
