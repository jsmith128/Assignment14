import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    // How many m in each unit. m is the "linking unit"
    enum Unit {
        μm (0.000001),
        mm (0.001 ),
        cm (0.01 ),
        dm (0.1),
        m  (1.0),
        dam(10.0),
        hm (100.0),
        km (1000.0),
        Mm (1000000.0),
        Gm (1000000000.0),
        
        in (0.0254),
        ft (0.3048),
        yd (0.9144),
        mi (1609.34);

        double length_in_m;
        Unit(double length_in_m) {
            this.length_in_m = length_in_m;
        }
        
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Unit Converter");
        stage.show();


        Label label1 = new Label("Input:");
        Label label2 = new Label("Output:");


        // Create the list of units from the Unit enum above
        ObservableList<Unit> units = FXCollections.observableArrayList(Unit.values());
        
        // Text for input and displaying converted units
        TextField measureInput = new TextField("10");
        measureInput.setMaxWidth(100);
        Label measureOutput = new Label("0.1");

        
        // Unit selector dropdown boxes
        ComboBox<Unit> unitSelector1 = new ComboBox<Unit>(units);
        ComboBox<Unit> unitSelector2 = new ComboBox<Unit>(units);
        unitSelector1.setMaxSize(75, 160);
        unitSelector1.setValue(Unit.cm);
        unitSelector2.setMaxSize(75, 160);
        unitSelector2.setValue(Unit.m);


        // Callbacks for updating the converted text
        ChangeListener<String> update1 = (observable, oldValue, newValue) -> {
            measureOutput.setText(convert(measureInput.getText(), unitSelector1.getValue(), unitSelector2.getValue()));
        };
        ChangeListener<Unit> update2 = (observable, oldValue, newValue) -> {
            measureOutput.setText(convert(measureInput.getText(), unitSelector1.getValue(), unitSelector2.getValue()));
        };

        // Add the listener functions
        measureInput.textProperty().addListener(update1);
        unitSelector1.getSelectionModel().selectedItemProperty().addListener(update2); 
        unitSelector2.getSelectionModel().selectedItemProperty().addListener(update2); 

    
        // LAYOUT
        GridPane gridPane = new GridPane();    
        
        gridPane.setMinSize(350, 200); 
        
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        
        gridPane.setVgap(10); 
        gridPane.setHgap(15);       
        
        gridPane.setAlignment(Pos.CENTER); 
        
        gridPane.add(label1, 0, 0);        gridPane.add(label2, 1, 0); 
        gridPane.add(measureInput, 0, 1);  gridPane.add(measureOutput, 1, 1); 
        gridPane.add(unitSelector1, 0, 2); gridPane.add(unitSelector2, 1, 2);  


        Scene scene = new Scene(gridPane, 350, 200);
            
        stage.setScene(scene);
        
        stage.show(); 
    }

    // Converts from one unit to another
    public String convert(String input, Unit from, Unit to) {
        double output, measure;
        // If input cannot be cast to a double, then just output 0 and don't try to convert
        try {
            measure = Double.parseDouble(input);

            double fromFactor = from.length_in_m;
        double toFactor = to.length_in_m;
            
            output = (measure * fromFactor) / toFactor;

        } catch (Exception e) {
            output = 0.0;
        }
        
        return Double.toString(output);
    }
}
