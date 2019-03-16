package application.model.p1.model.genetic_algorithm.solution.genes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1_utils.Converser;
import application.model.p1_utils.Pair;

public class BinaryGene extends Gene<Boolean> {
	
	
	@SuppressWarnings("unchecked")
	public BinaryGene(List<?> alleles, Pair<Double, Double> interval, int size) {
		super();
		this.interval = interval;
		this.size = size;
		this.alleles = new ArrayList<Boolean>((List<Boolean>) alleles);
		this.decodeGene();
		
	}
	public BinaryGene(double tolerance, Pair<Double, Double> interval) {
		super();
		this.interval = interval;
		this.size = this.calculateLength(tolerance);
		this.alleles = new ArrayList<>(this.size);
		this.initializeGene();
		this.decodeGene();
	}
	

	public BinaryGene(Gene<Boolean> gene) {
		super();
		this.interval = gene.getInterval();
		this.size = gene.getSize();
		this.alleles = new ArrayList<Boolean>(gene.getAlleles());
		this.decodeGene();
	}
	
	private int calculateLength(double tolerance) {	
		Pair<Double, Double> inter = this.getInterval();
		double res = this.log2(1 + (inter.getRightElement() - inter.getLeftElement()) / tolerance);
		return (int) Math.ceil(res);
	}

	private double log2(double n) {
		return Math.log(n) / Math.log(2);
	}
	
	@Override
	protected void initializeGene() {
		for(int i = 0; i < this.size; i++)
			this.alleles.add(ThreadLocalRandom.current().nextBoolean());
	}

	@Override
	public void decodeGene() {
		double xmin = this.interval.getLeftElement(), xmax = this.interval.getRightElement();
		this.decodedValue =  xmin + ((xmax - xmin) / (Math.pow(2, this.size) - 1)) * Converser.binaryGenesDecode(this);
	}

	@Override
	public String toString() {
		return "BooleanGene [alleles=" + alleles + "]";
	}


	@Override
	public Gene<Boolean> createGene(List<Boolean> alleles) {
		return new BinaryGene(alleles, this.interval, this.size);
	}
	@Override
	public Gene<Boolean> clone() {
		return new BinaryGene(this);
	}
	@Override
	public void mutate(int allelePos) {
		this.alleles.set(allelePos, !this.alleles.get(allelePos));
	}
}
