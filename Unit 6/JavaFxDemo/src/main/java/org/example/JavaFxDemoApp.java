package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Unit 6: GUI with JavaFX - Complete Demo
 * Covers: Layouts (FlowPane, BorderPane, HBox, VBox, GridPane),
 * Controls (Label, TextField, Button, RadioButton, CheckBox, Hyperlink, Menu, Tooltip, FileChooser),
 * Menu Bar, and Dialog Boxes.
 */
public class JavaFxDemoApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Demo - Unit 6");

        // === MENU BAR ===
        MenuBar menuBar = createMenuBar(primaryStage);

        // === TAB PANE to demonstrate different layouts ===
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(
                new Tab("FlowPane", createFlowPaneDemo()),
                new Tab("BorderPane", createBorderPaneDemo()),
                new Tab("HBox & VBox", createHBoxVBoxDemo()),
                new Tab("GridPane - Controls", createGridPaneDemo(primaryStage))
        );
        tabPane.getTabs().forEach(tab -> tab.setClosable(false));

        // Root layout: BorderPane with menu on top
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --- MENU BAR with Dialog Box demos ---
    private MenuBar createMenuBar(Stage stage) {
        Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open File...");
        openItem.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Open File");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
            var file = fc.showOpenDialog(stage);
            if (file != null) {
                showAlert(Alert.AlertType.INFORMATION, "File Selected", file.getAbsolutePath());
            }
        });
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> stage.close());
        fileMenu.getItems().addAll(openItem, new SeparatorMenuItem(), exitItem);

        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(e -> showAlert(Alert.AlertType.INFORMATION, "About",
                "JavaFX Demo App\nUnit 6: GUI with JavaFX"));
        helpMenu.getItems().add(aboutItem);

        return new MenuBar(fileMenu, helpMenu);
    }

    // --- FLOWPANE DEMO ---
    private FlowPane createFlowPaneDemo() {
        FlowPane flow = new FlowPane(10, 10);
        flow.setPadding(new Insets(15));
        for (int i = 1; i <= 8; i++) {
            Button btn = new Button("Button " + i);
            btn.setTooltip(new Tooltip("This is Button " + i));
            flow.getChildren().add(btn);
        }
        return flow;
    }

    // --- BORDERPANE DEMO ---
    private BorderPane createBorderPaneDemo() {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10));
        bp.setTop(new Label("TOP"));
        bp.setBottom(new Label("BOTTOM"));
        bp.setLeft(new Label("LEFT"));
        bp.setRight(new Label("RIGHT"));
        bp.setCenter(new Label("CENTER"));
        BorderPane.setAlignment(bp.getTop(), Pos.CENTER);
        BorderPane.setAlignment(bp.getBottom(), Pos.CENTER);
        return bp;
    }

    // --- HBOX & VBOX DEMO ---
    private VBox createHBoxVBoxDemo() {
        HBox hbox = new HBox(10, new Label("HBox:"), new Button("A"), new Button("B"), new Button("C"));
        hbox.setPadding(new Insets(10));
        hbox.setAlignment(Pos.CENTER_LEFT);

        VBox vbox = new VBox(10, new Label("VBox:"), new Button("X"), new Button("Y"), new Button("Z"));
        vbox.setPadding(new Insets(10));

        return new VBox(20, hbox, new Separator(), vbox);
    }

    // --- GRIDPANE with all UI Controls ---
    private GridPane createGridPaneDemo(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        // Label & TextField
        grid.add(new Label("Name:"), 0, 0);
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        grid.add(nameField, 1, 0);

        // RadioButtons
        grid.add(new Label("Gender:"), 0, 1);
        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton rbMale = new RadioButton("Male");
        RadioButton rbFemale = new RadioButton("Female");
        rbMale.setToggleGroup(genderGroup);
        rbFemale.setToggleGroup(genderGroup);
        grid.add(new HBox(10, rbMale, rbFemale), 1, 1);

        // CheckBoxes
        grid.add(new Label("Hobbies:"), 0, 2);
        CheckBox cbReading = new CheckBox("Reading");
        CheckBox cbCoding = new CheckBox("Coding");
        grid.add(new HBox(10, cbReading, cbCoding), 1, 2);

        // Hyperlink
        Hyperlink link = new Hyperlink("Visit JavaFX Docs");
        link.setOnAction(e -> showAlert(Alert.AlertType.INFORMATION, "Hyperlink", "https://openjfx.io"));
        grid.add(link, 1, 3);

        // Submit Button with Tooltip
        Button submitBtn = new Button("Submit");
        submitBtn.setTooltip(new Tooltip("Click to submit the form"));
        submitBtn.setOnAction(e -> {
            String name = nameField.getText().isEmpty() ? "N/A" : nameField.getText();
            Toggle sel = genderGroup.getSelectedToggle();
            String gender = sel != null ? ((RadioButton) sel).getText() : "N/A";
            String hobbies = (cbReading.isSelected() ? "Reading " : "") + (cbCoding.isSelected() ? "Coding" : "");
            showAlert(Alert.AlertType.CONFIRMATION, "Form Data",
                    "Name: " + name + "\nGender: " + gender + "\nHobbies: " + hobbies.trim());
        });
        grid.add(submitBtn, 1, 4);

        return grid;
    }

    // --- DIALOG BOX utility ---
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
