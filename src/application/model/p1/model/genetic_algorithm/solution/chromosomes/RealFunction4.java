package application.model.p1.model.genetic_algorithm.solution.chromosomes;

import java.util.List;

import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1.model.genetic_algorithm.solution.genes.RealGene;
import application.model.p1_utils.Pair;

public class RealFunction4 extends Function4<RealGene> {

	public RealFunction4(RealFunction4 f) {
		super(f);
	}
	
	@SuppressWarnings("unchecked")
	public RealFunction4(int nGenes, double tolerance, boolean maximize, List<? extends Gene<?>> genes, int chromosomeLength) {
		super(nGenes, tolerance, maximize, (List<RealGene>) genes, chromosomeLength);
	}
	public RealFunction4(int nGenes, double tolerance, Boolean maximize, List<Pair<Double, Double>> intervals) {
		super(nGenes, tolerance, maximize, intervals);
	}
	@Override
	protected RealGene createFunction4Gene(Pair<Double, Double> interval) {
		return new RealGene(this.tolerance, interval);
	}

	@Override
	public <U> Chromosome<RealGene> createChildren(List<Gene<U>> childGenes) {
		
		return new RealFunction4(this.nGenes, this.tolerance, this.maximize, childGenes, this.chromosomeLength);
	}

	@Override
	public Chromosome<RealGene> clone() {
		return new RealFunction4(this);
	}

}
