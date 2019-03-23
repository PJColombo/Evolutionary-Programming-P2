package application.model.p1.model.genetic_algorithm.solution.chromosomes;

import java.util.ArrayList;
import java.util.List;

import application.model.p1.model.genetic_algorithm.solution.genes.BinaryGene;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.Pair;

public class Function1 extends Chromosome<BinaryGene>{

	private double tolerance; 
	
	public Function1(Function1 f) {
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
	//Need to be generic because is used in crossover algorithms
	public Function1(double tolerance, boolean maximize, List<? extends Gene<?>> genes, int chromosomeLenght) {
		super();
		this.tolerance = tolerance;
		this.maximize = maximize;
		
		this.genes = new ArrayList<BinaryGene>(2);
		for (Gene<?> g : genes)
			this.genes.add((BinaryGene) g.clone());		
		
		this.chromosomeLength = chromosomeLenght;
		this.fitness = this.calculateFitness();
		this.calculateFenotype();
	}
	
	public Function1(double tolerance, Boolean maximize, List<Pair<Double, Double>> intervals) {
		super();
		this.tolerance = tolerance;
		System.out.println(maximize);
		this.maximize = maximize != null ? maximize : true;
		this.genes = new ArrayList<BinaryGene>(2);
		List<Pair<Double, Double>> inter;
		if(intervals != null && intervals.size() == 2)
			inter = intervals;
		else {
			inter = new ArrayList<>(2);
			inter.add(new Pair<>(-3.0, 12.1));
			inter.add(new Pair<>(4.1, 5.8));
		}
		for(int i = 0; i < 2; i++) {
			this.genes.add(new BinaryGene(this.tolerance, inter.get(i)));
			this.chromosomeLength += this.genes.get(i).getSize();
		}
		//Calculate fitness.
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
		double x1, x2;
		x1 = this.genes.get(0).getDecodedValue();
		x2 = this.genes.get(1).getDecodedValue();
		
		return 21.5 + x1 * Math.sin(4 * Math.PI * x1) + x2 * Math.sin(20 * Math.PI * x2);
	}

	@Override
	public String toString() {
		return "Function1 {"
				+ " Fenotype: " + fenotype
				+ " | Fitness: " + fitness
				+ " | Score: " + score
				+ " | AccuScore: " + accuScore
				+ " | decodedGene1 : " + genes.get(0).getDecodedValue()
				+ " | decodedGene2 : " + genes.get(1).getDecodedValue()
				+ "} ";
	}


	@Override
	public <U> Chromosome<BinaryGene> createChildren(List<Gene<U>> childGenes) {
		return  new Function1(this.tolerance, this.maximize, childGenes, this.chromosomeLength); 
	}

	@Override
	public Chromosome<BinaryGene> clone() {
		return new Function1(this);
	}
}
