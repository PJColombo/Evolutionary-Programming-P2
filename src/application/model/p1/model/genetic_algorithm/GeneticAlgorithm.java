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
			this.population.add(ChromosomeFactory.getInstance().createChromosome(this.function, this.tolerance, null, null, this.nVariables, _DISTANCES));
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
		CrossoverOperator crossOperator = CrossoverOperatorFactory.getInstance().selectAlgorithm(this.crossoverOperator, this.crossoverProbability, this.crosspointsNum);
		Random r = new Random();
		double prop; 
		//Get selected solutions.
		for (int i = 0; i < this.population.size(); i++) {
			prop = r.nextDouble();
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
	
	private final static int[][] _DISTANCES = {
            {},
            {171},
            {369, 294},
            {366,  537,   633},
            {525,  696,   604,   318},
            {540,  515,   809,   717,   1022},
            {646,  817,   958,   401,   694,   620},
            {488,  659,   800,   243,   536,   583,   158},
            {504,  675,   651,   229,   89,    918,   605,   447},
            {617,  688,   484,   618,   342,   1284,  1058,  900,   369},
            {256,  231,   525,   532,   805,   284,   607,   524,   701,   873},
            {207,  378,   407,   256,   318,   811,   585,   427,   324,   464,   463},
            {354,  525,   332,   457,   272,   908,   795,   637,   319,   263,   610,   201},
            {860,  1031,  1172,  538,   772,   1118,  644,   535,   683,   1072,  1026,  799,   995},
            {142,  313,   511,   282,   555,   562,   562,   404,   451,   708,   305,   244,   445,   776},
            {640,  615,   909,   817,   1122,  100,   720,   683,   1018,  1384,  384,   911,   1008,  1218,  662},
            {363,  353,   166,   534,   438,   868,   829,   671,   485,   335,   584,   278,   166,   1043,  479,   968},
            {309,  480,   621,   173,   459,   563,   396,   238,   355,   721,   396,   248,   458,   667,   486,   663,   492},
            {506,  703,   516,   552,   251,   1140,  939,   781,   323,   219,   856,   433,   232,   1006,  677,   1240,  350,   690},
            {495,  570,   830,   490,   798,   274,   322,   359,   694,   1060,  355,   587,   797,   905,   406,   374,   831,   339,   1029},
            {264,  415,   228,   435,   376,   804,   730,   572,   423,   367,   520,   179,   104,   944,   380,   904,   99,    393,   336,   732},
            {584,  855,   896,   255,   496,   784,   359,   201,   407,   796,   725,   511,   733,   334,   500,   884,   761,   391,   730,   560,   668},
            {515,  490,   802,   558,   866,   156,   464,   427,   762,   1128,  259,   655,   865,   973,   472,   256,   861,   407,   1097,  118,   779,   628},
            {578,  653,   899,   358,   676,   468,   152,   115,   595,   999,   455,   526,   736,   650,   464,   568,   770,   278,   968,   244,   671,   316,   312},
            {762,  933,   1074,  440,   674,   1020,  546,   437,   585,   974,   928,   696,   897,   98,    678,   1120,  945,   569,   908,   807,   846,   236,   875,   352},
            {251,  422,   563,   115,   401,   621,   395,   237,   297,   663,   417,   190,   400,   609,   167,   721,   434,   58,    632,   397,   335,   333,   465,   336,   551},
            {473,  482,   219,   644,   436,   997,   939,   781,   506,   265,   713,   388,   187,   1153,  615,   1097,  129,   602,   313,   941,   209,   877,   1009,  880,   1055,  544},
            {150,  75,    219,   516,   675,   590,   796,   638,   654,   613,   306,   357,   444,   1010,  292,   690,   278,   459,   628,   611,   340,   734,   583,   694,   912,  401,  407}

            };
}
