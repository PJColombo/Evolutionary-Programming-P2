package application.model.p1.model.genetic_algorithm.solution.genes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1_utils.Pair;

public class RealGene extends Gene<Double> {
	
	
	@SuppressWarnings("unchecked")
	public RealGene(List<?> alleles, Pair<Double, Double> interval) {
		super();
		this.interval = interval;
		this.size = alleles.size();
		this.alleles = (List<Double>) alleles;
		this.decodeGene();
	}
	
	public RealGene(double tolerance, Pair<Double, Double> interval) {
		super();
		this.interval = interval;
		this.size = 1;
		this.alleles = new ArrayList<Double>(1);
		this.initializeGene();
		this.decodeGene();
	}
	
	public RealGene(Gene<Double> gene) {
		super();
		this.interval = gene.getInterval();
		this.size = gene.getSize();
		this.alleles = new ArrayList<Double>(gene.getAlleles());
		this.decodeGene();
	}
	@Override
	protected void initializeGene() {
		double min = this.interval.getLeftElement(),
				max = this.interval.getRightElement();
		for(int i = 0; i < this.size; i++)
			this.alleles.add(ThreadLocalRandom.current().nextDouble(min, max));
	}

	@Override
	public void decodeGene() {
		double decodedValue = 0;
		for (Double a : alleles) {
			decodedValue += a;
		}
		this.decodedValue = decodedValue;
	}

	@Override
	public Gene<Double> createGene(List<Double> alleles) {
		return new RealGene(alleles, this.interval);
	}	
	
	@Override
	public String toString() {
		return "RealGene [alleles=" + alleles + "]";
	}

	@Override
	public Gene<Double> clone() {
		return new RealGene(this);
	}

	@Override
	public void mutate(int allelePos) {
		double newAllele = ThreadLocalRandom.current().nextDouble(this.interval.getLeftElement(), this.interval.getRightElement());
		this.alleles.set(allelePos, newAllele);
	}
}