package com.jvsf.simplecrudapi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jvsf.simplecrudapi.models.Pessoa;
import com.jvsf.simplecrudapi.models.Telefone;
import com.jvsf.simplecrudapi.repository.PessoaRepository;

@SpringBootApplication
public class SimpleCrudApiApplication implements CommandLineRunner{
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(SimpleCrudApiApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		carregarDados();
	}
	
	private void carregarDados() {
		List<Pessoa> pessoas = new ArrayList<>();
		pessoas.add(novaPessoa("Valdomiro Filho", "05185795301", "valdomirofilho@email.com", 
				new LocalDate().withYear(1992).withMonthOfYear(10).withDayOfMonth(23).toDate(), "988888888", "977777777"));
		pessoas.add(novaPessoa("Brenieny Moraes", "04423540371", "brenamoraes@email.com", 
				new LocalDate().withYear(1993).withMonthOfYear(5).withDayOfMonth(4).toDate(), "984929308", "943872882"));
		pessoas.add(novaPessoa("Gorete", "02398174123", "gorete@email.com", 
				new LocalDate().withYear(1993).withMonthOfYear(5).withDayOfMonth(4).toDate(), "984738282", "966666666"));
		pessoas.add(novaPessoa("Birro", "01938549522", "birro@email.com", 
				new LocalDate().withYear(1993).withMonthOfYear(5).withDayOfMonth(4).toDate(), "999373882", "998746462"));
		pessoas.add(novaPessoa("Cassio", "01930249123", "cassio@email.com", 
				new LocalDate().withYear(1993).withMonthOfYear(5).withDayOfMonth(4).toDate(), "996373882", "998788993"));
		pessoas.add(novaPessoa("Erico", "04938174123", "erico@email.com", 
				new LocalDate().withYear(1993).withMonthOfYear(5).withDayOfMonth(4).toDate(), "984849113", "989834027", "989834027", "989834027", "989834027", "989834027"));
		pessoas.add(novaPessoa("Jessica", "03289487123", "jessica@email.com", 
				new LocalDate().withYear(1993).withMonthOfYear(5).withDayOfMonth(4).toDate(), "980480284", "984879290", "984879290", "984879290", "984879290", "984879290", "984879290", "984879290", "984879290"));
		pessoas.add(novaPessoa("Gieska", "12365987425", "gieska@email.com", 
				new LocalDate().withYear(1993).withMonthOfYear(5).withDayOfMonth(4).toDate(), "980480284", "984879290", "984879290", "984879290", "984879290", "984879290", "984879290", "984879290", "984879290"));
		
		
		
		
		
		pessoaRepository.saveAll(pessoas);
	}

	private Pessoa novaPessoa(String nome, String cpf, String email, Date dataNascimento, String...telefones) {
		final String ddd = "88";
		Pessoa p1 = new Pessoa();
		p1.setNome(nome);
		p1.setCpf(cpf);
		p1.setEmail(email);
		p1.setDataNascimento(dataNascimento);
		List<Telefone> listaTelefone = new ArrayList<>();
		for (String tel : telefones) {
			listaTelefone.add(new Telefone(ddd, tel, p1));
		}
		p1.setTelefones(listaTelefone);
		return p1;
	}


}
