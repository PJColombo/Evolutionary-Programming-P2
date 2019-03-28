package application.model.p1_utils;

import java.util.List;

public class TSPDistancesCalculator {

	public static <T> double Calculator(int initialFinalCity, List<T> alleles) {
		double fitness = 0;
		double coste;
		int firstCity = (int) alleles.get(0), lastCity;
		int cityI, cityJ;
		if(initialFinalCity > firstCity)
			fitness += coste = TSPDistances._DISTANCES[initialFinalCity][firstCity];
		else
			fitness += coste = TSPDistances._DISTANCES[firstCity][initialFinalCity];
		
		for(int i = 0, j =  i + 1; i < alleles.size() - 1; i++, j++) {
			cityI = (int) alleles.get(i);
			cityJ = (int) alleles.get(j);
			
			if(cityI < cityJ)
				coste = TSPDistances._DISTANCES[cityJ][cityI];
			else
				coste = TSPDistances._DISTANCES[cityI][cityJ];
			
			//System.out.println("De " + cityI + "hasta " + cityJ + " tiene un coste de : " + coste);
				fitness += coste;				
		}
		
		lastCity = (int) alleles.get(alleles.size() - 1);
		
		if(lastCity > initialFinalCity)
			fitness +=  coste = TSPDistances._DISTANCES[lastCity][initialFinalCity];
		else 
			fitness += coste = TSPDistances._DISTANCES[initialFinalCity][lastCity];
		
		//System.out.println("De " + lastCity + "hasta " + initialFinalCity + " tiene un coste de : " + coste);
		return fitness;
	}
}
