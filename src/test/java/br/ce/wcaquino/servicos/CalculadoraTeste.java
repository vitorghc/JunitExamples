package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquinho.exceptions.NaoPodeDividirPorZeroException;



public class CalculadoraTeste {
	
	private Calculadora calc;
	
	//iniciar antes da execucao dos metodos
	@Before
	public void setUp(){
		calc = new Calculadora();
	}
	
	@Test
	public void somarDoisNumeros(){
		//cenario
		int a = 5;
		int b = 3;
		
		//acao
		int result = calc.soma(a, b);
		
		//verificacao
		Assert.assertEquals(8, result);
	}
	
	@Test
	public void subtraiDoisValores(){
		//cenario
		int a = 5;
		int b = 3;
		
		//acao
		int result = calc.subtrai(a, b);
		
		//verificacao
		Assert.assertEquals(2, result);
	}

	
	@Test
	public void divideDoisValores() throws NaoPodeDividirPorZeroException{
		//cenario
		int a = 6;
		int b = 3;
		
		//acao
		int result = calc.divide(a, b);
		//verificacao
		
		Assert.assertEquals(2, result);
	}
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void lancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException{
		//cenario
		int a = 10;
		int b = 0;
		
		//acao
		calc.divide(a, b);
	}
}
