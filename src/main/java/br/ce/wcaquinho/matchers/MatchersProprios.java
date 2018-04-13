package br.ce.wcaquinho.matchers;

import java.time.DayOfWeek;
import java.util.Calendar;

public class MatchersProprios {
	
	public static DiaSemanaMatcher caiEm(Integer diaSemana){
		return new DiaSemanaMatcher(diaSemana);
	}

	public static DiaSemanaMatcher caiNumaSegunda(){
		return new DiaSemanaMatcher(Calendar.MONDAY);
	}
	
	public static DataDiferencaDiasMetcher ehHjComDiferecaDeDias(Integer qtdDias){
		return new DataDiferencaDiasMetcher(qtdDias);
		
	}
	
	public static DataDiferencaDiasMetcher ehHoje(){
		return new DataDiferencaDiasMetcher(0);
	}
}
