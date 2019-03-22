package application.view;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


import application.Main;
import application.model.p1.model.genetic_algorithm.GeneticAlgorithm;
import application.model.p1_utils.Stat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ChartController implements Initializable{

	@FXML
    private TextArea errorMsg;

    @FXML
    private TextField mutPorcentage;

    @FXML
    private TextField errorValue;

    @FXML
    private TextField numGenerations;

    @FXML
    private TextField crossPercentage;

    @FXML
    private LineChart<String, Number> chart;

    @FXML
    private TextField tamPopulation;

    @FXML
    private ComboBox<String> crossAlgorithm;

    @FXML
    private ComboBox<String> selAlgorithm;
    
    @FXML
    private ComboBox<String> function;

    @FXML
    private ComboBox<String> mutationAlgorithm;

    @FXML
    private CheckBox elitims;

    @FXML
    private TextField nFunctionVariablesTF;
    
    @FXML
    private Slider mutPerSlid;

    @FXML
    private Slider crossPerSlid;

    @FXML
    private TextField nCrosspointsTF;
    
    
    //P2 Opt
    @FXML
    private TextField crossIntervalMinTF;

    @FXML
    private TextField crossIntervalMaxTF;
    
    @FXML
    private TextField mutIntervalMinTF;
    
    @FXML
    private TextField mutIntervalMaxTF;
    
    @FXML
    private TextField elitismPercentageTF;
    
    @FXML
    private Slider elitPerSlid;
    
    
    private GeneticAlgorithm ge = null;
    private Double[] arg = new Double[6];
    
    
    private Integer nVariables;
    private Integer nCrosspoints;
    
    public ChartController(){}
    
    
    
    ObservableList<String> listFunctions = FXCollections.observableArrayList("Function1", "Function2", "Function3", "BinaryFunction4" , "RealFunction4");
    ObservableList<String> listCrossAlgorithms = FXCollections.observableArrayList("Multipoint", "Uniform", "Onepoint");
    ObservableList<String> listSelAlgorithms = FXCollections.observableArrayList("Roulette", "Tournament", "Probabilistic_tournament", "Stochastic");
    ObservableList<String> listMutationAlgorithms = FXCollections.observableArrayList("Conventional", "Inversion", "Swap");
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	this.nVariables = null; 
    	this.nCrosspoints = null;
        this.mutPorcentage.setText("0.05");
        this.errorValue.setText("0.001");
        this.numGenerations.setText("100");
        this.crossPercentage.setText("0.6");
        this.tamPopulation.setText("100");
        this.errorMsg.setText("Welcome!");
        
        crossAlgorithm.setItems(listCrossAlgorithms);
        crossAlgorithm.getSelectionModel().select(2);
        
        selAlgorithm.setItems(listSelAlgorithms);
        selAlgorithm.getSelectionModel().select(0);
        
        mutationAlgorithm.setItems(listMutationAlgorithms);
        mutationAlgorithm.getSelectionModel().select(0);
        
        function.setItems(listFunctions);
        function.getSelectionModel().select(0);
        
        chart.getStyleClass().add("thick-chart");
        chart.setCreateSymbols(false);
        
        mutPerSlid.valueProperty().addListener((obs, oldValue, newValue) -> {
        	NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        	DecimalFormat df = (DecimalFormat)nf;

        	mutPorcentage.setText(df.format(newValue.doubleValue()));
        });
        
        crossPerSlid.valueProperty().addListener((obs, oldValue, newValue) -> {
        	NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        	DecimalFormat df = (DecimalFormat) nf;

        	crossPercentage.setText(df.format(newValue.doubleValue()));
        });
        
        
        this.elitismPercentageTF.setText("0.2");
        
        elitPerSlid.valueProperty().addListener((obs, oldValue, newValue) -> {
        	NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        	DecimalFormat df = (DecimalFormat) nf;
        	
        	elitismPercentageTF.setText(df.format(newValue.doubleValue()));
        });
        
    }

    public Double[] getData(){
    	if(!nFunctionVariablesTF.getText().isEmpty())
    		this.nVariables = Integer.parseInt(nFunctionVariablesTF.getText());
    	else
    		this.nVariables = null;
    	
    	if(!nCrosspointsTF.getText().isEmpty())
    		this.nCrosspoints = Integer.parseInt(nCrosspointsTF.getText());
    	else
    		this.nCrosspoints = 1;

    	arg[0] = Double.parseDouble(tamPopulation.getText());
    	arg[1] = Double.parseDouble(numGenerations.getText());
    	arg[2] = Double.parseDouble(crossPercentage.getText());
    	arg[3] = Double.parseDouble(mutPorcentage.getText());
    	arg[4] = Double.parseDouble(errorValue.getText());
    	if(elitims.isSelected())
    		arg[5] = 1.0;
    	else 
    		arg[5] = 0.0;

		return arg;
    }


    @FXML
    public void run(ActionEvent e){
    	List<Stat> stats = null;

        //Call the model

        if(isInputValid()){
        	boolean elitism = arg[5] == 1.0;
      
        	System.out.println("Number of variables: " + nVariables);
    		this.ge = new GeneticAlgorithm(nVariables, function.getValue(), arg[0].intValue(), arg[1].intValue(), selAlgorithm.getValue(), 
    				crossAlgorithm.getValue(), arg[2], mutationAlgorithm.getValue(), arg[3], arg[4], elitism);
    		
    		this.ge.setCrosspointsNum(this.nCrosspoints);
    		stats = this.ge.execute();
    	
            //Generate the chart
            chart.getData().clear();
            XYChart.Series<String,Number> absolutBest_series = new XYChart.Series<String,Number>();
            XYChart.Series<String,Number> generationBest_series = new XYChart.Series<String,Number>();
            XYChart.Series<String,Number> avgGeneration_series = new XYChart.Series<String,Number>();

            absolutBest_series.setName("Best of all");
            generationBest_series.setName("Generation best");
            avgGeneration_series.setName("Generation average");
            int generation = 0;
            for (Stat s : stats) {
            	absolutBest_series.getData().add(new Data<String, Number>(Integer.toString(generation), s.getBestIndividualFitness()));
            	generationBest_series.getData().add(new Data<String, Number>(Integer.toString(generation),s.getBestGenerationIndividualFitness()));
                avgGeneration_series.getData().add(new Data<String, Number>(Integer.toString(generation), s.getAveragePopulationFitness()));
                generation++;
			}
            chart.getData().addAll(absolutBest_series, generationBest_series, avgGeneration_series);
            this.errorMsg.appendText(System.lineSeparator() + "RESULTS " + System.lineSeparator() + "________________________________"
            		+ System.lineSeparator() + stats.get(stats.size() - 1));
            this.errorMsg.appendText("Best Individual " + System.lineSeparator() + "_______________________-"
            		+ System.lineSeparator() + ge.getBestSolution());
        }
        this.ge = null;
    }

    @FXML
    void onFunctionSelected(ActionEvent event) {
    	String fSelected = function.getValue();
    	if(fSelected.equalsIgnoreCase("binaryFunction4") || fSelected.equalsIgnoreCase("realFunction4"))
    		nFunctionVariablesTF.setEditable(true);
    	else {
    		nFunctionVariablesTF.setText("");
    		nFunctionVariablesTF.setEditable(false);
    	}
    }
    
    private boolean isInputValid() {
    	Double[] args = getData();
        String errorMessage = "";

        //Tamaño de la poblacion
        if (args[0] < 0) 
            errorMessage += "Population size must be greater than 0.\n";
        
        //Numero de generaciones
        if (args[1] < 0) 
            errorMessage += "The number of generation must be greater than 0.\n";
        

        //Porcentaje de cruces
        if (args[2] < 0 || args[2] > 1) 
            errorMessage += "Invalid crossover percentage!\n";
        

        //Porcentaje de mutacion
        if (args[3] < 0 || args[3] > 1) 
            errorMessage += "Invalid mutation percentage!\n";
   
        //Tolerancia
        if (args[4] < 0 || args[4] > 1) 
                errorMessage += "Valor de error no valido!\n";
        
        if(function.getValue().equalsIgnoreCase("binaryfunction4") || function.getValue().equalsIgnoreCase("realfunction4")) {
        	if(nVariables == null)
        		errorMessage += "Type in the number of variables \n";
        }
        if (crossAlgorithm.getValue() == null)
        	errorMessage += "Algoritmo de cruce no seleccionado\n";
		if (mutationAlgorithm.getValue() == null)
			errorMessage += "Mutación no seleccionada\n";
		if (function.getValue() == null)
			errorMessage += "Función no seleccionada\n";
		if (selAlgorithm.getValue()== null)
			errorMessage += "Algoritmo de selección no seleccionado\n";
		
        if (errorMessage.length() == 0) {
        	errorMsg.setText("Executing algorithm...");
            return true;
        } else{
        	errorMsg.setText(errorMessage);
            return false;
        }
    }

    

    @FXML
    public void clear(ActionEvent e){
    	chart.getData().clear();
        this.mutPorcentage.setText("0.05");
        this.errorMsg.clear();
        this.errorValue.setText("0.001");
        this.numGenerations.setText("100");
        this.crossPercentage.setText("0.60");
        this.tamPopulation.setText("100");
        this.nFunctionVariablesTF.clear();
        this.nCrosspointsTF.clear();
        this.mutPerSlid.setValue(0.05);
        this.crossPerSlid.setValue(0.60);
        this.function.getSelectionModel().select(0);
        this.selAlgorithm.getSelectionModel().select(0);
        this.crossAlgorithm.getSelectionModel().select(2);
        this.mutationAlgorithm.getSelectionModel().select(0);
        
        this.elitPerSlid.setValue(0.20);
        
    }

	public void setMainApp(Main main) {}
}

