package application.model.p1.model.genetic_algorithm.solution.genes;

import java.util.List;

import application.model.p1_utils.Pair;

public abstract class Gene<T> {
	protected List<T> alleles;
	protected double decodedValue; 
	protected int size;
	protected Pair<Double, Double> interval; 
	
	public Gene() {}
	
	protected abstract void initializeGene();
	
	public abstract void decodeGene();
	public abstract Gene<T> createGene(List<T> alleles);
	public abstract Gene<T> clone();
	public abstract void mutate(int allelePos);
	
	public double getDecodedValue() {
		return decodedValue;
	}
	public void setDecodedValue(double decodeValue) {
		this.decodedValue = decodeValue;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public List<T> getAlleles() {
		return this.alleles;
	}
	
	public void setAlleles(List<T> alleles) {
		this.alleles = alleles;
	}
	
	public void setAllele(int i, T allele) {
		this.alleles.set(i, allele);
	}

	public Pair<Double, Double> getInterval() {
		return interval;
	}

	public void setInterval(Pair<Double, Double> interval) {
		this.interval = interval;
	}		
}