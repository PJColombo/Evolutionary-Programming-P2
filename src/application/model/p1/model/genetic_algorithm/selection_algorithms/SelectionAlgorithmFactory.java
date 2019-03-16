package application.model.p1.model.genetic_algorithm.selection_algorithms;

public abstract class SelectionAlgorithmFactory {
	private static SelectionAlgorithmFactory selectionAlgorithFactory;
	
	public static SelectionAlgorithmFactory getInstance() {
		if(SelectionAlgorithmFactory.selectionAlgorithFactory == null)
			SelectionAlgorithmFactory.selectionAlgorithFactory = new SelectionAlgorithmFactoryImp();
		return SelectionAlgorithmFactory.selectionAlgorithFactory;
	}
	public abstract SelectionAlgorithm getSelectionAlgorithm(String algorithm, int participants);
}
