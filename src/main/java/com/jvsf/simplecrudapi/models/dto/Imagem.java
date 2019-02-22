package com.jvsf.simplecrudapi.models.dto;

public class Imagem {
	
	private String imagem;
	
	private String url;
	
	public Imagem(String imagem, String url) {
		this.imagem = imagem;
		this.url = url;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getUrl() {
		return url;
	}

	public void setUrlImagem(String url) {
		this.url = url;
	}
	

}
