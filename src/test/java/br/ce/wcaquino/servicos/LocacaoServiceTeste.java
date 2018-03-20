package br.ce.wcaquino.servicos;


import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
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
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testeLocacao() throws Exception {

		// /cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Jose");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// ação
		Locacao locacao = service.alugarFilme(usuario, filme);

		// verificação
		error.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), CoreMatchers.is(true));
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		// /cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Jose");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// ação
		service.alugarFilme(usuario, filme);
	}
	

	@Test
	public void teteLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		// cenario

		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 1", 1, 5.0);

		// ação
			try {
				service.alugarFilme(null, filme);
				Assert.fail();
			} catch (LocadoraException e) {
				Assert.assertThat(e.getMessage(), CoreMatchers.is("Usuario vazio"));
			}
	}
	
	@Test
	public void teteLocacao_filmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		// cenario

		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Jose");

		// ação
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		service.alugarFilme(usuario, null);
	}
}
