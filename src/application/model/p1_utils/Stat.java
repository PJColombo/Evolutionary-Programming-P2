package application.model.p1_utils;

public class Stat {
	private double averagePopulationFitness;
	private double bestGenerationIndividualFitness;
	private double bestIndividualFitness;
	
	public Stat() {}
	
	public Stat(double averagePopulationFitness, double bestGenerationIndividualFitness,
			double bestIndividualFitness) {
		super();
		this.averagePopulationFitness = averagePopulationFitness;
		this.bestGenerationIndividualFitness = bestGenerationIndividualFitness;
		this.bestIndividualFitness = bestIndividualFitness;
	}
	
	public double getAveragePopulationFitness() {
		return averagePopulationFitness;
	}
	public double getBestGenerationIndividualFitness() {
		return bestGenerationIndividualFitness;
	}
	public double getBestIndividualFitness() {
		return bestIndividualFitness;
	}
	public void setAveragePopulationFitness(double averagePopulationFitness) {
		this.averagePopulationFitness = averagePopulationFitness;
	}
	public void setBestGenerationIndividualFitness(double bestGenerationIndividualFitness) {
		this.bestGenerationIndividualFitness = bestGenerationIndividualFitness;
	}
	public void setBestIndividualFitness(double bestIndividualFitness) {
		this.bestIndividualFitness = bestIndividualFitness;
	}

	@Override
	public String toString() {
		return "Average: " + averagePopulationFitness + System.lineSeparator() + "BestGenerationFitness: " + bestGenerationIndividualFitness + 
				System.lineSeparator() + "Best Fitness: " + bestIndividualFitness + System.lineSeparator();
	}
	
	
	
}
