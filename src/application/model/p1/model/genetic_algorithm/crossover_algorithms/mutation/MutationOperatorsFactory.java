package application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation;

public abstract class MutationOperatorsFactory {
	private static MutationOperatorsFactory mof = null;
	
	
	public synchronized static MutationOperatorsFactory getInstance(){
	
		if(mof == null) mof = new MutationOperatorsFactoryImp();
			return mof;
	}
	
	public abstract MutationOperator selectAlgorithm(String type, double mutationProbability); 
}
