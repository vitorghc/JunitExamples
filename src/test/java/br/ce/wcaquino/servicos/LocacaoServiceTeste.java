package br.ce.wcaquino.servicos;


import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquinho.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquinho.exceptions.LocadoraException;
import br.ce.wcaquinho.matchers.MatchersProprios;
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
	public void deveAlugarFilme() throws Exception {
//		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		// /cen�rio
		Usuario usuario = new Usuario("Jose");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));
		
		// a��o
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verifica��o
		error.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		error.checkThat(locacao.getDataLocacao(), MatchersProprios.ehHoje());
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), CoreMatchers.is(true));
		error.checkThat(locacao.getDataRetorno(), MatchersProprios.ehHjComDiferecaDeDias(1));
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDEveAlugarFilmeSemEstoque() throws Exception {
		// /cen�rio
		Usuario usuario = new Usuario("Jose");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		// a��o
		service.alugarFilme(usuario, filmes);
	}
	

	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
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
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		// cenario

		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Jose");

		// a��o
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		service.alugarFilme(usuario, null);
	}
	
	@Test
	public void devePagar70pctFilme3() throws FilmeSemEstoqueException,
			LocadoraException {
		// cenario
		Usuario usu = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0),
				new Filme("Filme 2", 2, 4.0), 
				new Filme("Filme 3", 2, 4.0));

		// acao
		Locacao result = service.alugarFilme(usu, filmes);

		// verificacao
		Assert.assertThat(result.getValor(), CoreMatchers.is(11.0));
	}
	
	
	@Test
	public void devePagar50pctFilme4() throws FilmeSemEstoqueException,
			LocadoraException {
		// cenario
		Usuario usu = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0),
				new Filme("Filme 2", 2, 4.0), 
				new Filme("Filme 3", 2, 4.0),
				new Filme("Filme 4", 2, 4.0));

		// acao
		Locacao result = service.alugarFilme(usu, filmes);

		// verificacao
		Assert.assertThat(result.getValor(), CoreMatchers.is(13.0));
	}
	
	
	@Test
	public void devePagar25pctFilme5() throws FilmeSemEstoqueException,
			LocadoraException {
		// cenario
		Usuario usu = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0), 
				new Filme("Filme 2", 2, 4.0), 
				new Filme("Filme 3", 2, 4.0), 
				new Filme("Filme 4", 2, 4.0), 
				new Filme("Filme 5", 2, 4.0));

		// acao
		Locacao result = service.alugarFilme(usu, filmes);

		// verificacao
		Assert.assertThat(result.getValor(), CoreMatchers.is(14.0));
	}
	
	
	@Test
	public void devePagar0pctFilme6() throws FilmeSemEstoqueException,
			LocadoraException {
		// cenario
		Usuario usu = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0), 
				new Filme("Filme 2", 2, 4.0), 
				new Filme("Filme 3", 2, 4.0), 
				new Filme("Filme 4", 2, 4.0), 
				new Filme("Filme 5", 2, 4.0),
				new Filme("Filme 5", 2, 4.0));

		// acao
		Locacao result = service.alugarFilme(usu, filmes);

		// verificacao
		Assert.assertThat(result.getValor(), CoreMatchers.is(14.0));
	}
	
	
	@Test
	public void deveDevolverNaSegundaAoAlugarSabado() throws FilmeSemEstoqueException, LocadoraException{
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//cenario
		Usuario usu = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));
		
		//acao
		Locacao result = service.alugarFilme(usu, filmes);
		
		//verificacao
		Assert.assertThat(result.getDataRetorno(), MatchersProprios.caiEm(Calendar.SUNDAY));
		Assert.assertThat(result.getDataRetorno(), MatchersProprios.caiNumaSegunda());
	}
}
