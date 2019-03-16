package application.model.p1.model.genetic_algorithm.selection_algorithms;

import java.util.List;

import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;

public interface SelectionAlgorithm {

	public abstract <T> List<? extends Chromosome<? extends Gene<T>>> selection(List<? extends Chromosome<? extends Gene<T>>> population);
}
