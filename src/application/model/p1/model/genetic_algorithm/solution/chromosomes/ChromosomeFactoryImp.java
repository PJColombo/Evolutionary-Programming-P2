package application.model.p1.model.genetic_algorithm.solution.chromosomes;

import java.util.ArrayList;
import java.util.List;

import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.Pair;

public class ChromosomeFactoryImp extends ChromosomeFactory {

	@Override
	public Chromosome<? extends Gene<?>> createChromosome(String chromosome, double tolerance, Boolean maximize,
			List<Pair<Double, Double>> intervals, Integer genesNumber, int[][] distances, int distancesSize, int initialFinalCity) {
		switch (chromosome.toLowerCase()) {
		case "function1":
			return new Function1(tolerance, maximize, intervals);
		case "function2":
			return new Function2(tolerance, maximize, intervals);
		case "function3":
			return new Function3(tolerance, maximize, intervals);
		case "binaryfunction4":
			return new BinaryFunction4(genesNumber, tolerance, maximize, intervals);
		case "realfunction4" :
			return new RealFunction4(genesNumber, tolerance, maximize, intervals);
		case "tsp":
			return new TSPFunction(distancesSize, distances, initialFinalCity);
		default:
			//Generate function 1 as default function. 
			List<Pair<Double, Double>> defaultIntervals = new ArrayList<>(2);
			defaultIntervals.add(new Pair<>(-3.0, 12.1));
			defaultIntervals.add(new Pair<>(4.1, 5.8));
			return new Function1(0.001, true, defaultIntervals);
		}
	}


	
}
