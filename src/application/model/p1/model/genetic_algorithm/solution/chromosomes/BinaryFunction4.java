package application.model.p1.model.genetic_algorithm.solution.chromosomes;

import java.util.List;

import application.model.p1.model.genetic_algorithm.solution.genes.BinaryGene;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.Pair;

public class BinaryFunction4 extends Function4<BinaryGene> {

	public BinaryFunction4(BinaryFunction4 f) {
		super(f);
	}
	
	@SuppressWarnings("unchecked")
	public BinaryFunction4(int nGenes, double tolerance, boolean maximize, List<? extends Gene<?>> genes, int chromosomeLenght) {
		super(nGenes, tolerance, maximize, (List<BinaryGene>) genes, chromosomeLenght);
	}
	
	public BinaryFunction4(int nGenes, double tolerance, Boolean maximize, List<Pair<Double, Double>> intervals) {
		super(nGenes, tolerance, maximize, intervals);
	}
	
	@Override
	protected BinaryGene createFunction4Gene(Pair<Double, Double> interval) {
		return new BinaryGene(this.tolerance, interval);
	}

	@Override
	public <U> Chromosome<BinaryGene> createChildren(List<Gene<U>> childGenes) {
		return new BinaryFunction4(this.nGenes, this.tolerance, this.maximize, childGenes, this.chromosomeLength);
	}

	@Override
	public Chromosome<BinaryGene> clone() {
		return new BinaryFunction4(this);
	}

}