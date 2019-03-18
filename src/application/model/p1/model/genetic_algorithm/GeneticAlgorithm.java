package application.model.p1.model.genetic_algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.CrossoverOperator;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.CrossoverOperatorFactory;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.MutationOperator;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.MutationOperatorsFactory;
import application.model.p1.model.genetic_algorithm.selection_algorithms.SelectionAlgorithm;
import application.model.p1.model.genetic_algorithm.selection_algorithms.SelectionAlgorithmFactory;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.ChromosomeFactory;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.FitnessComparator;
import application.model.p1_utils.Pair;
import application.model.p1_utils.Stat;
import application.model.p1_utils.TSPDistances;



public class GeneticAlgorithm {
	private final double ELITISIM_PERCENTAGE = 0.02;
	
	private List<Chromosome<? extends Gene<?>>> population;
	private int popSize;
	private int maxGenNumber;
	private Chromosome<? extends Gene<?>> bestSolution;
	private  double crossoverProbability;
	private double mutationProbability;
	private double tolerance;
	private int generations;
	private int eliteSize;
	private boolean elitism;
	
	private String function; 
	private Integer nVariables;
	private String selectionAlgorithm;
	private String crossoverOperator;
	private String mutationOperator;
	private Integer crosspointsNum;
	
	
	public GeneticAlgorithm(Integer nVariables, String function, int popSize, int maxGenNumber, String selectionAlgorithm, String crossoverOperator,
			double crossoverProbability, String mutationOperator, double mutationProbability, double tolerance, boolean elitism) {
		super();
		this.nVariables = nVariables;
		this.function = function;
		this.population = new ArrayList<>(popSize);
		this.popSize = popSize;
		this.maxGenNumber = maxGenNumber;
		this.selectionAlgorithm = selectionAlgorithm;
		this.crossoverOperator = crossoverOperator;
		this.crossoverProbability = crossoverProbability;
		this.mutationOperator = mutationOperator;
		this.mutationProbability = mutationProbability;
		this.tolerance = tolerance;
		this.elitism = elitism;
		if(this.elitism)
			this.eliteSize = (int) Math.ceil(popSize * this.ELITISIM_PERCENTAGE);
		
	}

	
	
	public void setCrosspointsNum(Integer crosspointsNum) {
		this.crosspointsNum = crosspointsNum;
	}



	public int getMaxGenNumber() {
		return maxGenNumber;
	}
	public void setMaxGenNumber(int maxGenNumber) {
		this.maxGenNumber = maxGenNumber;
	}
	public double getCrossoverProbability() {
		return crossoverProbability;
	}

	public void setCrossoverProbability(double crossoverProbability) {
		this.crossoverProbability = crossoverProbability;
	}

	public double getMutationProbability() {
		return mutationProbability;
	}

	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	public List<Stat> execute() {	
		List<Stat> stats = new ArrayList<>(this.maxGenNumber);
		List<Chromosome<? extends Gene<?>>> elite = new ArrayList<>(eliteSize);

		this.createInitialPopulation();		
		stats.add(this.evaluatePopulation());
		while(this.generations < this.maxGenNumber) {
			this.generations++;
			if(this.elitism)
				this.extractElite(elite);
			
			this.select();
			this.reproduce();
			this.mutate();
			
			if(this.elitism)
				this.includeElite(elite);
			Stat s = this.evaluatePopulation();
			if(this.generations == this.maxGenNumber) {		
				System.out.println("Generation " + this.generations + "|| " + s);
			}
				
			stats.add(s);
		}
		
		return stats;
	}
	private void createInitialPopulation() {
		/*int distances[][] = new int[27][27];
		File file = new File("PATH"); 
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st;
			while ((st = br.readLine()) != null) {
			    st.split(",");
			} 
		} catch (IOException e) {
			e.printStackTrace();
		} */
		
		for (int i = 0; i < this.popSize; i++)
			this.population.add(ChromosomeFactory.getInstance().createChromosome(this.function, this.tolerance, false, null, this.nVariables, TSPDistances._DISTANCES, TSPDistances.size, 25));
		
		for(int i = 0; i < this.popSize; i++) {
			System.out.println(this.population.get(i));
		}
	}
	
	public Stat evaluatePopulation() {
		Stat stat = new Stat(); 
		double accuScore, bestFitness, accuNFitness, avgFitness = 0,
		//We use it to normalize negative fitness solutions.
			extremeFitness;
		int bestPos = -1;
		boolean foundBest = false, isMaximize = this.population.get(0).isMaximize();
		Chromosome<? extends Gene<?>> solution;
		
		accuScore = accuNFitness = 0;
		extremeFitness = isMaximize ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
		bestFitness = isMaximize ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
		for(int i = 0; i < this.population.size(); i++) {
			solution = this.population.get(i);
			
			avgFitness += solution.getFitness();
			
			if(!isMaximize && solution.getFitness() > extremeFitness)
				extremeFitness = solution.getFitness();
			else if(isMaximize && solution.getFitness() < extremeFitness)
				extremeFitness = solution.getFitness();
			
			foundBest = (isMaximize && solution.getFitness() > bestFitness) || (!isMaximize && solution.getFitness() < bestFitness);
			if(foundBest) {
				bestPos = i;
				bestFitness = solution.getFitness();
			}
		}
		extremeFitness *= 1.05;
		//Normalize fitness of every solution;
		accuNFitness = this.normalizePopulationFitness(extremeFitness);
		for(Chromosome<? extends Gene<?>> c : this.population) {
			c.setScore(c.getNormalizedFitness() / accuNFitness);
			accuScore += c.getScore(); 	
			c.setAccuScore(accuScore);
		}
		//Set stats. 
		stat.setAveragePopulationFitness(avgFitness / this.popSize);
		stat.setBestGenerationIndividualFitness(bestFitness);
		
		if(this.bestSolution == null)
			this.bestSolution = this.population.get(bestPos).clone();
		else if(isMaximize && bestFitness > this.bestSolution.getFitness())
			this.bestSolution = this.population.get(bestPos).clone();
		else if(!isMaximize && bestFitness < this.bestSolution.getFitness())
			this.bestSolution = this.population.get(bestPos).clone();
		
		stat.setBestIndividualFitness(this.bestSolution.getFitness());
		
		return stat; 
	}
	
	@SuppressWarnings("unchecked")
	private void select() {
		SelectionAlgorithmFactory algFactory = SelectionAlgorithmFactory.getInstance();
		SelectionAlgorithm alg = algFactory.getSelectionAlgorithm(this.selectionAlgorithm, 0);
		this.population = (List<Chromosome<? extends Gene<?>>>) alg.selection(this.population);
		
	}
	
	private void reproduce() {
		Pair<? extends Chromosome<? extends Gene<?>>, ? extends Chromosome<? extends Gene<?>>> childChromosomes = new Pair<>();
		List<Integer> selectedCrossoverIndexSol = new ArrayList<>();
		CrossoverOperator crossOperator = CrossoverOperatorFactory.getInstance().selectAlgorithm(this.crossoverOperator, this.crossoverProbability, this.crosspointsNum, 25);
		double prop; 
		//Get selected solutions.
		for (int i = 0; i < this.population.size(); i++) {
			prop = ThreadLocalRandom.current().nextDouble();
			if(prop < this.crossoverProbability)
				selectedCrossoverIndexSol.add(i);
		}
		
		//Need an even number of solutions.
		if(selectedCrossoverIndexSol.size() % 2 != 0)
			selectedCrossoverIndexSol.remove(selectedCrossoverIndexSol.size() - 1);
		int parentPos1, parentPos2;
		for(int i = 0; i < selectedCrossoverIndexSol.size() - 1; i += 2) {
			
			parentPos1 = selectedCrossoverIndexSol.get(i);
			parentPos2 = selectedCrossoverIndexSol.get(i + 1);
			//Cross chromosomes
			childChromosomes = crossOperator.chromosomeCrossover(this.population.get(parentPos1),
					this.population.get(parentPos2));
			
			//TODO Pick replacement algorithm. Call replacement factory
			this.population.set(parentPos1, childChromosomes.getLeftElement());
			this.population.set(parentPos2, childChromosomes.getRightElement());
		}
	}
	
	private void mutate() {
		MutationOperator mutationOperator = MutationOperatorsFactory.getInstance().selectAlgorithm(this.mutationOperator, this.mutationProbability);
		
		for (int i = 0; i < this.population.size(); i++)
			this.population.set(i, mutationOperator.mutation(this.population.get(i)));
		
	}
		
	public void printPopulation() {
		for (int i = 0; i < this.population.size(); i++) {
			System.out.println(i + ")   " + this.population.get(i));
			System.out.println("------------------------------------------");
		}
	}
	
	private double normalizePopulationFitness(double extremeFitness) {
		double accuNFitness = 0;
		for (Chromosome<? extends Gene<?>> c : population) {
			if(c.isMaximize())
				c.setNormalizedFitness(extremeFitness + c.getFitness());
			else
				c.setNormalizedFitness(extremeFitness - c.getFitness());
			accuNFitness += c.getNormalizedFitness();
		}
		return accuNFitness;
	}

	private List<Chromosome<? extends Gene<?>>> extractElite(List<Chromosome<? extends Gene<?>>> elite) {
		List<Integer> selectedEliteIndex = new ArrayList<>(eliteSize);
		FitnessComparator c = new FitnessComparator(population);
		//Add some initial individuals to the list.
		for(int i = 0; i < eliteSize; i++)
			selectedEliteIndex.add(i);		
		Collections.sort(selectedEliteIndex, c);
		int j;
		//Iterate over the rest of the population looking for better individuals.
		for(int i = eliteSize; i < population.size(); i++) {
			j = 0;
			while(j < eliteSize && population.get(selectedEliteIndex.get(j)).getNormalizedFitness() >= population.get(i).getNormalizedFitness())
				j++;
			if(j < eliteSize) {
				selectedEliteIndex.set(j, i);
				Collections.sort(selectedEliteIndex, c);
			}
		}
		//Set elite list
		for(int i = 0; i < selectedEliteIndex.size(); i++)
			elite.add((Chromosome<? extends Gene<?>>) population.get(selectedEliteIndex.get(i)).clone());		
		return elite;
	}
	
	private void includeElite(List<Chromosome<? extends Gene<?>>> elite) {
		int worstSize = eliteSize;
		List<Integer> worstIndividualsIndex = new ArrayList<>(worstSize);
		FitnessComparator c = new FitnessComparator(population);
		for(int i = 0; i < worstSize; i++)
			worstIndividualsIndex.add(i);
		Collections.sort(worstIndividualsIndex, c);
		int j;
		for(int i = eliteSize; i < population.size(); i++) {
			j = 0;
			while(j < eliteSize && population.get(worstIndividualsIndex.get(j)).getNormalizedFitness() <= population.get(i).getNormalizedFitness())
				j++;
			if(j < worstSize) {
				worstIndividualsIndex.set(j, i);
				Collections.sort(worstIndividualsIndex, c);
			}
		}
		for(int i = 0; i < worstIndividualsIndex.size(); i++)
			population.set(worstIndividualsIndex.get(i), elite.get(i));
		//Prepare elite list for the next iteration.
		elite.clear();
	}
	
	public Chromosome<? extends Gene<?>> getBestSolution() {
		return this.bestSolution;
	}
}
