package application.model.p1_utils;

public class Pair<T, U> {
	private T leftElement;
	private U rightElement;
	
	public Pair() {}
	
	public Pair(T leftEndpoint, U rightEndpoint) {
		super();
		this.leftElement = leftEndpoint;
		this.rightElement = rightEndpoint;
	}

	public T getLeftElement() {
		return leftElement;
	}

	public void setLeftElement(T leftElement) {
		this.leftElement = leftElement;
	}

	public U getRightElement() {
		return rightElement;
	}

	public void setRightElement(U rightElement) {
		this.rightElement = rightElement;
	}
	
}
