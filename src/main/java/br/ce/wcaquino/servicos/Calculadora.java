package br.ce.wcaquino.servicos;

import br.ce.wcaquinho.exceptions.NaoPodeDividirPorZeroException;

public class Calculadora {

	public int soma(int a, int b) {
		return a + b;
	}

	public int subtrai(int a, int b) {
		return a - b;
	}

	public int divide(int a, int b) throws NaoPodeDividirPorZeroException {
		if (a == 0 || b == 0) {
			throw new NaoPodeDividirPorZeroException();
		} else {
			return a / b;
		}
	}

}
