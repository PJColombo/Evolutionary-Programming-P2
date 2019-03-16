package application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover;


public abstract class CrossoverOperatorFactory{
	private static CrossoverOperatorFactory coof = null;
		
	public synchronized static CrossoverOperatorFactory getInstance(){
	
		if(coof == null) coof = new CrossoverOperatorFactoryImp();
			return coof;
	}
	
	
	public abstract CrossoverOperator selectAlgorithm(String type, double crossoverProbability, Integer crossPoints);
	
}
