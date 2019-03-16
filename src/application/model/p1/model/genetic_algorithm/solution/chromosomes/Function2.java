package application.model.p1.model.genetic_algorithm.solution.chromosomes;

import java.util.ArrayList;
import java.util.List;

import application.model.p1.model.genetic_algorithm.solution.genes.BinaryGene;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.Pair;

public class Function2 extends Chromosome<BinaryGene> {
	
	private double tolerance;
	
	public Function2(Function2 f) {
		this.tolerance = f.getTolerance();
		this.maximize = f.isMaximize();
		this.genes = new ArrayList<BinaryGene>(2);
		for (BinaryGene g : f.getGenes())
			this.genes.add((BinaryGene) g.clone());
		this.chromosomeLength = f.getChromosomeLength();
		this.fitness = f.getFitness();
		this.normalizedFitness = f.getNormalizedFitness();
		this.fenotype = f.getFenotype();
		this.score = f.getScore();
		this.accuScore = f.getAccuScore();
	}
	
	public Function2(double tolerance, boolean maximize, List<? extends Gene<?>> genes, int chromosomeLenght) {
		super();
		this.tolerance = tolerance;
		this.maximize = maximize;
		this.genes = new ArrayList<BinaryGene>(2);
		for (Gene<?> g : genes) {
			this.genes.add((BinaryGene) g.clone());
		}
		//this.genes = (List<BinaryGene>) genes;
		
		this.chromosomeLength = chromosomeLenght;
		this.fitness = this.calculateFitness();
		this.calculateFenotype();
	}
	
	public Function2(double tolerance, Boolean maximize, List<Pair<Double, Double>> intervals) {
		super();
		this.tolerance = tolerance;
		this.maximize = maximize != null ? maximize : false;
		this.genes = new ArrayList<BinaryGene>(2);
		Pair<Double, Double> interval = intervals  != null && intervals.size() == 1  ? intervals.get(0) 
				: new Pair<>(-512.0, 512.0);
		for(int i = 0; i < 2; i++) {
			this.genes.add(new BinaryGene(this.tolerance, interval));
			this.chromosomeLength += this.genes.get(i).getSize();
		}
		//Calculate fitness.
		this.fitness = this.calculateFitness();
		System.out.println(fitness);
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
		for (BinaryGene g : this.genes)
			this.fenotype += g.getDecodedValue();
	}
	
	@Override
	protected double calculateFitness() {
		double x1, x2;
		x1 = this.genes.get(0).getDecodedValue();
		x2 = this.genes.get(1).getDecodedValue();
		return -(x2 + 47) * Math.sin(Math.sqrt(Math.abs(x2 + x1 / 2 + 47))) - x1 * Math.sin(Math.sqrt(Math.abs(x1 - (x2 + 47))));
	}

	@Override
	public <U> Chromosome<BinaryGene> createChildren(List<Gene<U>> childGenes) {
		return  new Function2(this.tolerance, this.maximize, childGenes, this.chromosomeLength); 
	}

	@Override
	public Chromosome<BinaryGene> clone() {
		return new Function2(this);
	}

	@Override
	public String toString() {
		return "Function2 {"
				+ " Fenotype: " + fenotype
				+ " | Fitness: " + fitness
				+ " | NFitness: " + normalizedFitness
				+ " | Score: " + score
				+ " | AccuScore: " + accuScore
				+ " | decodedGene1 : " + genes.get(0).getDecodedValue()
				+ " | decodedGene2 : " + genes.get(1).getDecodedValue()
				+ "} ";
	}	
}
