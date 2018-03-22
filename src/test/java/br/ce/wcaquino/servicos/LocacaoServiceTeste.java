package br.ce.wcaquino.servicos;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquinho.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquinho.exceptions.LocadoraException;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTeste {
	
	private LocacaoService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup(){
		service = new LocacaoService();
	}

	@Test
	public void testeLocacao() throws Exception {

		// /cen�rio
		Usuario usuario = new Usuario("Jose");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));
		
		// a��o
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verifica��o
		error.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), CoreMatchers.is(true));
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		// /cen�rio
		Usuario usuario = new Usuario("Jose");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		// a��o
		service.alugarFilme(usuario, filmes);
	}
	

	@Test
	public void teteLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		// cenario
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));

		// a��o
			try {
				service.alugarFilme(null, filmes);
				Assert.fail();
			} catch (LocadoraException e) {
				// affeterThat mensagem atual x mensagem esperada
				Assert.assertThat(e.getMessage(), CoreMatchers.is("Usuario vazio"));
			}
	}
	
	@Test
	public void teteLocacao_filmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		// cenario

		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Jose");

		// a��o
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		service.alugarFilme(usuario, null);
	}
}
