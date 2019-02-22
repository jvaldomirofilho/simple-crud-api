package com.jvsf.simplecrudapi.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.jvsf.simplecrudapi.models.dto.Imagem;

@Component
public class Disco {
	
	@Value("${pessoa.disco.raiz}")
	private String raiz;
	
	@Value("${pessoa.disco.diretorio-imagens}")
	private String diretorioImagens;
	
	public Imagem salvarImagem(MultipartFile imagem) {
		this.salvar(this.diretorioImagens, imagem);
		Path diretorioPath = Paths.get(this.raiz, this.diretorioImagens);
		Path arquivoPath = diretorioPath.resolve(imagem.getOriginalFilename());
		return new Imagem(imagem.getOriginalFilename(), arquivoPath.toString());
	}
	
	public void salvar(String diretorio, MultipartFile arquivo) {
		Path diretorioPath = Paths.get(this.raiz, diretorio);
		Path arquivoPath = diretorioPath.resolve(arquivo.getOriginalFilename());
		
		try {
			Files.createDirectories(diretorioPath);
			arquivo.transferTo(arquivoPath.toFile());			
		} catch (IOException e) {
			throw new RuntimeException("Problemas na tentativa de salvar arquivo.", e);
		}		
	}
}
