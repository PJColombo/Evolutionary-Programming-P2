package application.model.p1.model.genetic_algorithm.solution.chromosomes;

import java.util.List;

import application.model.p1.model.genetic_algorithm.solution.genes.Gene;

public abstract class Chromosome<T extends Gene<?>> implements Comparable<Chromosome<T>> {

	protected List<T> genes;
	protected double fenotype;
	protected double fitness;
	protected double normalizedFitness; 
	protected double score;
	protected double accuScore;
	protected int chromosomeLength;
	protected boolean maximize;
	protected boolean elite;
	
	public Chromosome() {};
	public Chromosome(List<T> genes, float fitness, float score, float accuScore, int chromosomeLength, boolean maximize) {
		super();
		this.genes = genes;
		this.fitness = fitness;
		this.score = score;
		this.accuScore = accuScore;
		this.chromosomeLength = chromosomeLength;
		this.maximize = maximize;
	}
	
	protected abstract void calculateFenotype(); 
	protected abstract double calculateFitness();
	//protected abstract double calculateFitness(int[][] distances);
	public abstract <U> Chromosome<T> createChildren(List<Gene<U>> childGenes);
	public abstract Chromosome<T> clone();
	
	public void evaluate() {
		for (T g : genes)
			g.decodeGene();
		this.fitness = this.calculateFitness();
		this.calculateFenotype();
	}
	
	@Override
	public int compareTo(Chromosome<T> o) {
		return Double.compare(this.getFitness(), o.getFitness());
	}
	
	public List<T> getGenes() {
		return this.genes;
	}
	public void setGenes(List<T> genes) {
		this.genes = genes;
	}
	public double getFenotype() {
		return fenotype;
	}
	public void setFenotype(double fenotype) {
		this.fenotype = fenotype;
	}
	public double getFitness() {
		return fitness;
	}
	
	public double getNormalizedFitness() {
		return normalizedFitness;
	}

	public void setNormalizedFitness(double normalizedFitness) {
		this.normalizedFitness = normalizedFitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public double getAccuScore() {
		return accuScore;
	}
	public void setAccuScore(double accu_score) {
		this.accuScore = accu_score;
	}
	public int getChromosomeLength() {
		return chromosomeLength;
	}
	public void setChromosomeLength(int chromosomeLength) {
		this.chromosomeLength = chromosomeLength;
	}
	public boolean isMaximize() {
		return maximize;
	}
	public boolean isElite() {
		return elite;
	}

	public void setElite(boolean elite) {
		this.elite = elite;
	}
}