package application.model.p1_utils;

import application.model.p1.model.genetic_algorithm.solution.genes.BinaryGene;

public class Converser {
	public static double binaryGenesDecode(BinaryGene gene) {
		String binary = "";
		//Calculate gene binary codification.
		for(boolean b : gene.getAlleles())
			binary += b ? 1 : 0;
		return Integer.parseInt(binary, 2);
	}
}
