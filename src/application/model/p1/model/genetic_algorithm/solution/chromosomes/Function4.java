package application.model.p1.model.genetic_algorithm.solution.chromosomes;

import java.util.ArrayList;
import java.util.List;

import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.Pair;

public abstract class Function4<T extends Gene<?>> extends Chromosome<T> {

	protected int nGenes;
	protected double tolerance;
	
	
	public Function4() {}
	
	@SuppressWarnings("unchecked")
	public Function4(Function4<T> f) {
		this.nGenes = f.getnGenes();
		this.tolerance = f.getTolerance();
		this.maximize = f.isMaximize();
		this.genes = new ArrayList<T>(2);
		for (T g : f.getGenes())
			this.genes.add((T) g.clone());
		this.chromosomeLength = f.getChromosomeLength();
		this.fitness = f.getFitness();
		this.normalizedFitness = f.getNormalizedFitness();
		this.fenotype = f.getFenotype();
		this.score = f.getScore();
		this.accuScore = f.getAccuScore();
	}
	
	@SuppressWarnings("unchecked")
	public Function4(int nGenes, double tolerance, boolean maximize, List<T> genes, int chromosomeLength) {
		super();
		this.nGenes = nGenes;
		this.tolerance = tolerance;
		this.maximize = maximize;
		this.genes = new ArrayList<T>(2);
		for (T g : genes) {
			this.genes.add((T) g.clone());
		}
		this.chromosomeLength = chromosomeLength;
		this.fitness = this.calculateFitness();
		this.calculateFenotype();
	}
	
	public Function4(int nGenes, double tolerance, Boolean maximize, List<Pair<Double, Double>> intervals) {
		super();
		this.nGenes = nGenes;
		this.tolerance = tolerance;
		this.maximize = maximize != null ? maximize : false;
		this.genes = new ArrayList<T>(nGenes);
		Pair<Double, Double> inter = intervals != null && intervals.size() == 1 ? intervals.get(0) 
				: new Pair<>(0.0, Math.PI);
		for(int i = 0; i < nGenes; i++) {
			this.genes.add(this.createFunction4Gene(inter));		
			this.chromosomeLength += this.genes.get(i).getSize();
		}
		this.fitness = this.calculateFitness();
		this.calculateFenotype();
	}
	
	protected abstract T createFunction4Gene(Pair<Double, Double> interval);
	
	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	
	public int getnGenes() {
		return nGenes;
	}

	public void setnGenes(int nGenes) {
		this.nGenes = nGenes;
	}

	@Override
	protected void calculateFenotype() {
		this.fenotype = 0;
		for (Gene<?> g : this.genes)
			this.fenotype += g.getDecodedValue();
	}

	@Override
	protected double calculateFitness() {
		double fitness = 0, xi;
		for(int i = 1; i <= this.nGenes; i++) {
			xi = this.getGenes().get(i - 1).getDecodedValue();
			fitness += -1 * (Math.sin(xi) * Math.pow(Math.sin(((i + 1) * Math.pow(xi, 2)) / Math.PI), 20));
		}
		return fitness;
	}

	@Override
	public String toString() {
		String s = "";
		for ( int i = 0; i < this.nGenes; i++)
			s += " | Gene" + i + ": " + genes.get(i).getDecodedValue();
		return "Function4 {"
				+ " Fenotype: " + fenotype
				+ " | Fitness: " + fitness
				+ " | Score: " + score
				+ " | AccuScore: " + accuScore
				+ s
				+ "} ";
	}
}