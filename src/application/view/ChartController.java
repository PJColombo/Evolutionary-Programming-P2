package application.view;


import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import application.Main;
import application.model.p1.model.genetic_algorithm.GeneticAlgorithm;
import application.model.p1_utils.RoundDecimal;
import application.model.p1_utils.Stat;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

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
    private CheckBox comparativeStudyCB;
    
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
    
    @FXML
    private ComboBox<String> showFunctionComboBox;
    
    @FXML
    private Label crossoverIntervalL;
    
    @FXML
    private Label mutationIntervalL;
    
    @FXML
    private Label showL;
    
    @FXML
    private Label elitismPercentageL;
    
    //------------------------------------------
    private GeneticAlgorithm ge = null;
    private Double[] arg = new Double[6];
    
    
    private Integer nVariables;
    private Integer nCrosspoints;
    
    //P2
    private double intMinCross;
    private double intMaxCross;
    private double intMinMut;
    private double intMaxMut;
    private double elitPerc;
    
    private static final String[] RGB_COLORS = {"rgba(51, 102, 255, 1.0)", "rgba(255, 26, 26, 1.0)", "rgba(41, 163, 41, 1.0)", "rgb(204, 0, 255)", 
    		"rgb(0, 51, 0)", "rgb(102, 153, 153)", "rgb(153, 255, 51)", "rgb(153, 0, 153)", "rgb(102, 0, 102)", "rgb(153, 102, 51)",
    		"rgb(102, 0, 204)", "rgb(153, 255, 204)", "rgb(204, 204, 255)", "rgb(0, 255, 204)", "rgb(255, 51, 153)", "rgb(255, 255, 102)",
    		"rgb(102, 153, 0)", "rgb(0, 0, 0)", "rgb(230, 230, 230)", "rgb(153, 102, 255)", "rgb(171, 156, 201)", "rgb(69, 54, 99)", "rgb(0, 51, 153)",
    		"rgb(204, 0, 153)"};
    
    private final double INTERVAL_UNIT = 0.05;
    
    public ChartController(){}
    
    
    private final ObservableList<String> listFunctions = FXCollections.observableArrayList("Function1", "Function2", "Function3", "BinaryFunction4" , "RealFunction4");
    private final ObservableList<String> listCrossAlgorithms = FXCollections.observableArrayList("Multipoint", "Uniform", "Onepoint");
    private final ObservableList<String> listSelAlgorithms = FXCollections.observableArrayList("Roulette", "Tournament", "Probabilistic_tournament", "Stochastic", "Truncation");
    private final ObservableList<String> listMutationAlgorithms = FXCollections.observableArrayList("Conventional", "Inversion", "Swap");
    
    private final ObservableList<String> showFunctionList = FXCollections.observableArrayList("General best individual", "Generation best individual", "Average generation fitness");
    
    
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
        
        //P2------------
        
        comparativeStudyCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                ChartController.this.disableComparativeStudySection(!newValue);
            }
        });
        
        showFunctionComboBox.setItems(showFunctionList);
        showFunctionComboBox.getSelectionModel().select(0);
        
        elitismPercentageTF.setText("0.2");
        
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
    	ArrayList<XYChart.Series<String, Number>> series = new ArrayList<>();
    	double crossPerc, mutPerc;
    	boolean elitism, validParameters = true;
        //Call the model
    	
    	if(isInputValid()) {  
    		chart.getData().clear();
    		
    		chart.getXAxis().setLabel("Generations");
            chart.getYAxis().setLabel("Fitness");
            
            
    		if(comparativeStudyCB.isSelected()) {
    			validParameters = this.checkComparativeStudyFields();
    			if(validParameters) {
    				XYChart.Series<String,Number> s; 
    				String seriesName; 
    				crossPerc = intMinCross; mutPerc = intMinMut;
    				boolean crossPercCompleted, mutPercCompleted;
    				crossPercCompleted = mutPercCompleted = false;
    				while(!crossPercCompleted || !mutPercCompleted) {  
    					elitism = ThreadLocalRandom.current().nextDouble() < elitPerc;
    					System.out.println("Elitism" + elitism);
    					crossPerc = RoundDecimal.twoDecimalNumber(crossPerc);
    					mutPerc = RoundDecimal.twoDecimalNumber(mutPerc);
    					ge = new GeneticAlgorithm(nVariables, function.getValue(), arg[0].intValue(), arg[1].intValue(), selAlgorithm.getValue(), 
    							crossAlgorithm.getValue(), crossPerc, mutationAlgorithm.getValue(), mutPerc, arg[4], elitism);
    					ge.setCrosspointsNum(nCrosspoints);
    		    		stats = this.ge.execute();

    		    		seriesName = "C" + crossPerc + "M" + mutPerc;
    		    		if(elitism)
    		    			seriesName += "E";
    		    		s = this.createSeries(seriesName, showFunctionComboBox.getSelectionModel().getSelectedItem(), stats);
    		    		series.add(s);
    		    		chart.getData().add(s);
    		    		
    					crossPerc += INTERVAL_UNIT;
    					mutPerc += INTERVAL_UNIT;
    					
    					if(crossPerc > intMaxCross) {
    						crossPercCompleted = true;
    						crossPerc = intMaxCross;
    					}
    					if(mutPerc > intMaxMut) {
    						mutPercCompleted = true;
    						mutPerc = intMaxMut;
    					}
    					
    	    	        this.errorMsg.appendText(System.lineSeparator() + seriesName + System.lineSeparator() + "________________________________"
    	                		+ System.lineSeparator() + stats.get(stats.size() - 1));
    	                this.errorMsg.appendText("Best Individual " + System.lineSeparator() + "_______________________-"
    	                		+ System.lineSeparator() + ge.getBestSolution() + System.lineSeparator() + "==========================================================================");
    	                
    				}
    			}
    		}
    		else {
    			elitism = arg[5] == 1.0;
    			ge = new GeneticAlgorithm(nVariables, function.getValue(), arg[0].intValue(), arg[1].intValue(), selAlgorithm.getValue(), 
        				crossAlgorithm.getValue(), arg[2], mutationAlgorithm.getValue(), arg[3], arg[4], elitism);
        		ge.setCrosspointsNum(this.nCrosspoints);
        		
        		stats = this.ge.execute();
       	
                XYChart.Series<String,Number> absolutBest_series = new XYChart.Series<String,Number>();
                XYChart.Series<String,Number> generationBest_series = new XYChart.Series<String,Number>();
                XYChart.Series<String,Number> avgGeneration_series = new XYChart.Series<String,Number>();
                absolutBest_series.setName("Best of all");
                generationBest_series.setName("Generation best");
                avgGeneration_series.setName("Generation average");
                
                chart.getData().addAll(absolutBest_series, generationBest_series, avgGeneration_series);
                
                int generation = 0;
                for (Stat s : stats) {
                	
                	absolutBest_series.getData().add(new Data<String, Number>(Integer.toString(generation), s.getBestIndividualFitness()));
                	generationBest_series.getData().add(new Data<String, Number>(Integer.toString(generation),s.getBestGenerationIndividualFitness()));
                    avgGeneration_series.getData().add(new Data<String, Number>(Integer.toString(generation), s.getAveragePopulationFitness()));
                    generation++;
    			}
                

    	        this.errorMsg.appendText(System.lineSeparator() + "RESULTS " + System.lineSeparator() + "________________________________"
                		+ System.lineSeparator() + stats.get(stats.size() - 1));
                this.errorMsg.appendText("Best Individual " + System.lineSeparator() + "_______________________-"
                		+ System.lineSeparator() + ge.getBestSolution());
                
                series.add(absolutBest_series);
                series.add(generationBest_series);
                series.add(avgGeneration_series);
    		}
    		
    		if(validParameters) {    			
    			this.setSeriesStyle(series);
    			
    			Platform.runLater(() -> {
    				for (Node node: chart.lookupAll(".chart-legend-item-symbol"))
    					for (String styleClass: node.getStyleClass())
    						if (styleClass.startsWith("series")) {
    							final int i = Integer.parseInt(styleClass.substring(6));
    							node.setStyle("-fx-background-color: " + RGB_COLORS[i] + ";");
    							break;
    						}
    			});
    		}
    	}
    	
    	
        
//        if(isInputValid()){      
//    		this.ge = new GeneticAlgorithm(nVariables, function.getValue(), arg[0].intValue(), arg[1].intValue(), selAlgorithm.getValue(), 
//    				crossAlgorithm.getValue(), arg[2], mutationAlgorithm.getValue(), arg[3], arg[4], elitism);
//    	
//    		this.ge.setCrosspointsNum(this.nCrosspoints);
//    		stats = this.ge.execute();
//    	
//            //Generate the chart
//            chart.getData().clear();
//            XYChart.Series<String,Number> absolutBest_series = new XYChart.Series<String,Number>();
//            XYChart.Series<String,Number> generationBest_series = new XYChart.Series<String,Number>();
//            XYChart.Series<String,Number> avgGeneration_series = new XYChart.Series<String,Number>();
//            absolutBest_series.setName("Best of all");
//            generationBest_series.setName("Generation best");
//            avgGeneration_series.setName("Generation average");
//            chart.getData().addAll(absolutBest_series, generationBest_series, avgGeneration_series);
//            int generation = 0;
//            for (Stat s : stats) {
//            	
//            	absolutBest_series.getData().add(new Data<String, Number>(Integer.toString(generation), s.getBestIndividualFitness()));
//            	generationBest_series.getData().add(new Data<String, Number>(Integer.toString(generation),s.getBestGenerationIndividualFitness()));
//                avgGeneration_series.getData().add(new Data<String, Number>(Integer.toString(generation), s.getAveragePopulationFitness()));
//                generation++;
//			}
//            
//            chart.getXAxis().setLabel("Generations");
//            chart.getYAxis().setLabel("Fitness");
//            
//
//	        this.errorMsg.appendText(System.lineSeparator() + "RESULTS " + System.lineSeparator() + "________________________________"
//            		+ System.lineSeparator() + stats.get(stats.size() - 1));
//            this.errorMsg.appendText("Best Individual " + System.lineSeparator() + "_______________________-"
//            		+ System.lineSeparator() + ge.getBestSolution());
//            
//            series.add(absolutBest_series);
//            series.add(generationBest_series);
//            series.add(avgGeneration_series);
//           
//            
//        }
//        this.ge = null;
    }
    
    private void setSeriesStyle(ArrayList<XYChart.Series<String,Number>> series) {
    	int i = 0;
    	for (Series<String, Number> s : series) {
			s.nodeProperty().get().setStyle("-fx-stroke: " + RGB_COLORS[i] + ";");
			i++;
		}
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
        
        this.clearComparativeStudyFields();
    }

    private void clearComparativeStudyFields() {
    	this.elitPerSlid.setValue(0.20);
        this.crossIntervalMinTF.setText("");
        this.crossIntervalMaxTF.setText("");
        this.mutIntervalMinTF.setText("");
        this.mutIntervalMaxTF.setText("");
    }
    
    private void disableComparativeStudySection(boolean disable) {
    	elitPerSlid.setDisable(disable);
    	crossIntervalMinTF.setDisable(disable);
    	crossIntervalMaxTF.setDisable(disable);
    	mutIntervalMinTF.setDisable(disable);
    	mutIntervalMaxTF.setDisable(disable);
    	showFunctionComboBox.setDisable(disable);
    	elitismPercentageTF.setDisable(disable);
    	
    	crossoverIntervalL.setDisable(disable);
    	mutationIntervalL.setDisable(disable);
    	showL.setDisable(disable);
    	elitismPercentageL.setDisable(disable);
    	
    }
    
    private Boolean checkComparativeStudyFields() {
    	Boolean res = true;
    	String intervalName = "";
    	boolean crossEmpty = false;
    	if(this.comparativeStudyCB.isSelected()) {
    		try {
    			if(crossIntervalMinTF.getText().length() > 0 && crossIntervalMaxTF.getText().length() > 0) {    				
    				intMinCross = Double.parseDouble(crossIntervalMinTF.getText());
    				intMaxCross = Double.parseDouble(crossIntervalMaxTF.getText());
    			}
    			else {
    				if(crossIntervalMaxTF.getText().length() > 0 && crossIntervalMinTF.getText().length() == 0 ||
    						crossIntervalMaxTF.getText().length() == 0 && crossIntervalMinTF.getText().length() > 0)
    					return false;
    				else {
    					intMinCross = intMaxCross = Double.parseDouble(crossPercentage.getText());
    					crossEmpty = true;
    				}
    			}
    			if(mutIntervalMinTF.getText().length() > 0 && mutIntervalMaxTF.getText().length() > 0) {
    				intMinMut = Double.parseDouble(mutIntervalMinTF.getText());
    				intMaxMut = Double.parseDouble(mutIntervalMaxTF.getText());    				
    			}
    			else {
    				if(mutIntervalMaxTF.getText().length() > 0  && mutIntervalMinTF.getText().length() == 0 ||
    						mutIntervalMaxTF.getText().length() == 0 && mutIntervalMinTF.getText().length() > 0)
    					return false;
    				else {
    					if(crossEmpty)
    						return false;
    					else
    						intMinMut = intMaxMut = Double.parseDouble(mutPorcentage.getText());
    				}
    			}
    			
    			elitPerc = Double.parseDouble(elitismPercentageTF.getText()); 
    			
    			if(intMinCross > intMaxCross || intMinCross < 0 || intMinCross > 1 ||
    					intMaxCross < 0 || intMaxCross > 1) {
    				intervalName = "crossover";
    				res = false;
    			}
    			else if (intMinMut > intMaxMut || intMinMut < 0 || intMinMut > 1 ||
    					intMaxMut < 0 || intMaxMut > 1) {
    				intervalName = "mutation";
    				res = false;
    			}
    			if(!res) {
    				errorMsg.appendText("Please type in a valid " + intervalName + " interval.");
    				return false;
    			}
    		}catch(NumberFormatException e) {
    			errorMsg.appendText("Please type in numerical parameters for the study comparative.");
    			return false;
    		}
    		res = true;
    	}
    	return res;
    }
    
    private XYChart.Series<String,Number> createSeries(String seriesName, String dataType, List<Stat> stats) {
    	XYChart.Series<String,Number> s = new XYChart.Series<>();
    	int generation = 0; 
    	double data;
    	s.setName(seriesName);
    	for (Stat st : stats) {			
    		switch(dataType.toLowerCase()) {
    		case "general best individual":
    			data = st.getBestIndividualFitness();
    			break;
    		case "generation best individual":
    			data = st.getBestGenerationIndividualFitness();
    			break;
    		case "average generation fitness":
    			data = st.getAveragePopulationFitness();
    			break;
    		default:
    			data = st.getBestIndividualFitness();
    			break;
    		}
    		s.getData().add(new Data<String, Number>(Integer.toString(generation), data));
    		generation++;
		}
    	return s;
    }
    public void setMainApp(Main main) {}
}

