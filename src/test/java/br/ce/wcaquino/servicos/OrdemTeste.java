package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


/**
 * classe teste para testarmos como ordenar o teste,
 * pois o Junit não garante que os testes sejam executados
 * da mesma forma como estão na sequencia da Classe
 * 
 * @author vfeltrin
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdemTeste {
	
	public static int contador = 0;
	
	@Test
	public void inicia(){
		contador = 1;
	}

	@Test
	public void verifica(){
		Assert.assertEquals(1, contador);
	}
}
