package application.model.p1.model.genetic_algorithm.solution.chromosomes;

import java.util.ArrayList;
import java.util.List;

import application.model.p1.model.genetic_algorithm.solution.genes.BinaryGene;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.Pair;

public class Function3 extends Chromosome<BinaryGene> {

	private double tolerance;
	
	
	public Function3(Function3 f) {
		this.tolerance = f.getTolerance();
		this.maximize = f.isMaximize();
		
		this.genes = new ArrayList<BinaryGene>(2);
		for (BinaryGene g : f.genes) 
			this.genes.add((BinaryGene) g.clone());
		this.chromosomeLength = f.getChromosomeLength();
		this.fitness = f.getFitness();
		this.normalizedFitness = f.getNormalizedFitness();
		this.fenotype = f.getFenotype();
		this.score = f.getScore();
		this.accuScore = f.getAccuScore();
	}
	
	public Function3(double tolerance, boolean maximize, List<? extends Gene<?>> genes, int chromosomeLength) {
		super();
		this.tolerance = tolerance;
		this.maximize = maximize;
		this.genes = new ArrayList<BinaryGene>(2);
		for(Gene<?> g: genes)
			this.genes.add((BinaryGene) g.clone());
		this.chromosomeLength = chromosomeLength;
		this.fitness = this.calculateFitness();
		this.calculateFenotype();
	}
	public Function3(double tolerance, Boolean maximize, List<Pair<Double, Double>> intervals) {
		super();
		this.tolerance = tolerance;
		this.maximize = maximize != null ? maximize : false;
		this.genes = new ArrayList<BinaryGene>(2);
		Pair<Double, Double> inter = intervals != null && intervals.size() == 1 ? intervals.get(0) 
				: new Pair<>(-10.0, 10.0);
		for(int i = 0; i < 2; i++) {
			this.genes.add(new BinaryGene(this.tolerance, inter));
			this.chromosomeLength += this.genes.get(i).getSize();
		}
		this.fitness = this.calculateFitness();
		this.calculateFenotype();
	}
	
	public double getTolerance() {
		return tolerance;
	}


	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}


	@Override
	protected void calculateFenotype() {
		this.fenotype = 0;
		for(BinaryGene g : this.genes)
			this.fenotype += g.getDecodedValue();
	}

	@Override
	protected double calculateFitness() {
		double x1 = this.getGenes().get(0).getDecodedValue();
		double x2 = this.getGenes().get(1).getDecodedValue();
		double first, second;
		first = second = 0;
		for(int i = 1; i <= 5; i++) {
			first += i * Math.cos((i + 1) * x1 + i);
			second += i * Math.cos((i + 1) * x2 + i);		
		}
		return first * second;
	}

	@Override
	public <U> Chromosome<BinaryGene> createChildren(List<Gene<U>> childGenes) {
		return new Function3(this.tolerance, this.maximize, childGenes, this.chromosomeLength);
	}

	@Override
	public Chromosome<BinaryGene> clone() {
		return new Function3(this);
	}
	
	@Override
	public String toString() {
		return "Function3 {"
				+ " Fenotype: " + fenotype
				+ " | Fitness: " + fitness
				+ " | Score: " + score
				+ " | AccuScore: " + accuScore
				+ " | Gene1 : " + genes.get(0).getDecodedValue()
				+ " | Gene2 : " + genes.get(1).getDecodedValue()
				+ "} ";
	}

}
