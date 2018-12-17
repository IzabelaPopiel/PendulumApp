package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class Controller {


    ObservableList<String> pendulumTypes = FXCollections.observableArrayList();
    @FXML
    private LineChart<Number, Number> chartX;

    @FXML
    private LineChart<Number, Number> chartV;

    @FXML
    private LineChart<Number, Number> chartE;

    @FXML
    private ScatterChart<Number, Number> chartPhaseSpace;

    @FXML
    private TextField txtDt;

    @FXML
    private TextField txtX0;

    @FXML
    private TextField txtV0;

    @FXML
    private TextField txtT0;

    @FXML
    private TextField txtTk;

    @FXML
    private ChoiceBox<String> choosePendulum;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnCzysc;

    @FXML
    private TextField txtPeriod;

    private CalculateAcceleration calculateAcceleration;
    private TotalEnergy totalEnergy;

    public Controller() {
    }

    public void initialize() {
        pendulumTypes.addAll("wahadło", "wahadło proste", "oscylator harmoniczny");
        choosePendulum.getItems().addAll(pendulumTypes);
    }

    @FXML
    void btnCzyscClicked(ActionEvent event) {
        chartX.getData().removeAll(chartX.getData());
        chartV.getData().removeAll(chartV.getData());
        chartE.getData().removeAll(chartE.getData());
        chartPhaseSpace.getData().removeAll(chartPhaseSpace.getData());
    }

    @FXML
    void btnStartClicked(ActionEvent event) {

        try {
            double dt = Double.parseDouble(txtDt.getText());
            double x0 = Double.parseDouble(txtX0.getText());
            double v0 = Double.parseDouble(txtV0.getText());
            double t0 = Double.parseDouble(txtT0.getText());
            double tk = Double.parseDouble(txtTk.getText());
            VerletIntegrator verletIntegrator = new VerletIntegrator(dt);
            PendulumAnalyzer pendulumAnalyzer = new PendulumAnalyzer();
            verletIntegrator.intergrate(calculateAcceleration, totalEnergy, pendulumAnalyzer, t0, tk, x0, v0);
            pendulumAnalyzer.save("wyniki1.txt");

            XYChart.Series<Number, Number> seriesX = new XYChart.Series<>();
            XYChart.Series<Number, Number> seriesV = new XYChart.Series<>();
            XYChart.Series<Number, Number> seriesE = new XYChart.Series<>();
            XYChart.Series<Number, Number> seriesPhaseSpace = new XYChart.Series<>();

            for (int i = 0; i < pendulumAnalyzer.gettValues().size(); i++) {
                seriesX.getData().add(new XYChart.Data<Number, Number>(pendulumAnalyzer.gettValues().get(i), pendulumAnalyzer.getxValues().get(i)));
                seriesV.getData().add(new XYChart.Data<Number, Number>(pendulumAnalyzer.gettValues().get(i), pendulumAnalyzer.getvValues().get(i)));
                seriesE.getData().add(new XYChart.Data<Number, Number>(pendulumAnalyzer.gettValues().get(i), pendulumAnalyzer.geteValues().get(i)));
                seriesPhaseSpace.getData().add(new XYChart.Data<Number, Number>(pendulumAnalyzer.getxValues().get(i), pendulumAnalyzer.getvValues().get(i)));
            }

            chartX.getData().addAll(seriesX);
            chartV.getData().addAll(seriesV);
            chartE.getData().addAll(seriesE);
            chartPhaseSpace.getData().addAll(seriesPhaseSpace);

            txtPeriod.appendText(Double.toString(pendulumAnalyzer.period(dt)));
        } catch (NumberFormatException e) {
            System.out.println("NIEPRAWIDLOWE DANE!");
        }
    }

    @FXML
    void choosePendulumClicked(ActionEvent event) {

        if (choosePendulum.getValue() == "wahadło") {
            calculateAcceleration = new Pendulum();
            totalEnergy = new Pendulum();
        }
        if (choosePendulum.getValue() == "wahadło proste") {
            calculateAcceleration = new Simple();
            totalEnergy = new Simple();
        }
        if (choosePendulum.getValue() == "oscylator harmoniczny") {
            calculateAcceleration = new HarmonicOsscilator();
            totalEnergy = new HarmonicOsscilator();
        }
    }
}
