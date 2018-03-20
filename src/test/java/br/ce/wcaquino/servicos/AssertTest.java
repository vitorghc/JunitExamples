package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {
	
	@Test
	public void test(){
		
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		Assert.assertEquals(1, 1);
		Assert.assertEquals(5.12, 5.12, 0.01);
		
		int i = 5;
		Integer i2 = 5;
		Assert.assertEquals(Integer.valueOf(i), i2);
		Assert.assertEquals(i, i2.intValue());
		
		Assert.assertEquals("Erro de Compara��o","bola", "bola");
		Assert.assertNotEquals("bola", "Casa");
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
		Assert.assertTrue("bola".startsWith("bo"));
		
		//implementando o equals na classe usu�rio para comparar strings e n�o somente a instancia
		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");
		Usuario u3 = null;
		
		Assert.assertEquals(u1, u2);
		
		//compara se o objtedo � da mesma instancia
		Assert.assertSame(u2, u2);
		Assert.assertNotSame(u2, u3);
		
		//verifica se objeto � null
		Assert.assertNull(u3);
		//verifica se n�o esta null
		Assert.assertNotNull(u1);
		
		
		
	}

}
