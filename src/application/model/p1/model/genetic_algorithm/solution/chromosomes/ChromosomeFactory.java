package application.model.p1.model.genetic_algorithm.solution.chromosomes;

import java.util.List;

import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.Pair;

public abstract class ChromosomeFactory {
	private static ChromosomeFactory chromosomeFactory;
	
	public synchronized static ChromosomeFactory getInstance() {
		if(chromosomeFactory == null)
			chromosomeFactory = new ChromosomeFactoryImp();
		return chromosomeFactory;
	}
	
	public abstract Chromosome<? extends Gene<?>> createChromosome(String chromosome, double tolerance, Boolean maximize, List<Pair<Double, 
				Double>> intervals, Integer genesNumber, int[][] distances);
	
}
