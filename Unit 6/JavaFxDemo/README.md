# Unit 6: GUI with JavaFX - Demo Project

## Introduction

### What is JavaFX?
JavaFX is a modern GUI toolkit for Java that replaces Swing as the standard library for building rich desktop applications. It provides a set of graphics and media APIs for creating visually rich user interfaces.

### JavaFX vs Swing

| Feature | JavaFX | Swing |
|---------|--------|-------|
| Architecture | Scene Graph based | Component based |
| Styling | CSS support | Look & Feel (limited) |
| FXML | Supports declarative UI (FXML) | No equivalent |
| Media | Built-in audio/video support | Requires external libraries |
| Animation | Built-in animation API | Manual implementation |
| Modern Controls | Rich modern controls | Dated appearance |
| Threading | Platform.runLater() | SwingUtilities.invokeLater() |

### JavaFX Application Lifecycle

```java
public class MyApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Stage -> Scene -> Root Node -> Child Nodes
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

**Hierarchy:** `Stage` → `Scene` → `Layout (Pane)` → `Controls (Nodes)`

---

## 6.1 JavaFX Layouts

Layouts are containers that manage the positioning and sizing of child nodes.

### FlowPane
Arranges children in a flow — wraps to next row/column when space runs out.

```java
FlowPane flow = new FlowPane(10, 10); // hgap, vgap
flow.setPadding(new Insets(15));
flow.getChildren().addAll(new Button("A"), new Button("B"), new Button("C"));
```

**Use case:** Toolbar buttons, tag lists, dynamic content that wraps.

### BorderPane
Divides the area into 5 regions: TOP, BOTTOM, LEFT, RIGHT, CENTER.

```java
BorderPane bp = new BorderPane();
bp.setTop(menuBar);
bp.setLeft(navigation);
bp.setCenter(content);
bp.setBottom(statusBar);
```

**Use case:** Main application layout with header, sidebar, content, and footer.

### HBox
Arranges children in a single horizontal row.

```java
HBox hbox = new HBox(10); // spacing
hbox.getChildren().addAll(new Button("1"), new Button("2"), new Button("3"));
```

**Use case:** Button bars, horizontal toolbars.

### VBox
Arranges children in a single vertical column.

```java
VBox vbox = new VBox(10); // spacing
vbox.getChildren().addAll(new Label("Item 1"), new Label("Item 2"));
```

**Use case:** Sidebar menus, form fields stacked vertically.

### GridPane
Arranges children in a flexible grid of rows and columns.

```java
GridPane grid = new GridPane();
grid.setHgap(10);
grid.setVgap(10);
grid.add(new Label("Name:"), 0, 0);  // col, row
grid.add(new TextField(), 1, 0);
grid.add(new Label("Email:"), 0, 1);
grid.add(new TextField(), 1, 1);
```

**Use case:** Forms, data entry screens, structured layouts.

---

## 6.2 JavaFX UI Controls

### Label
Displays read-only text.

```java
Label label = new Label("Hello, JavaFX!");
```

### TextField
Single-line text input.

```java
TextField tf = new TextField();
tf.setPromptText("Enter your name");
String value = tf.getText();
```

### Button
Clickable action trigger.

```java
Button btn = new Button("Click Me");
btn.setOnAction(e -> System.out.println("Clicked!"));
```

### RadioButton
Mutually exclusive selection within a ToggleGroup.

```java
ToggleGroup group = new ToggleGroup();
RadioButton rb1 = new RadioButton("Option A");
RadioButton rb2 = new RadioButton("Option B");
rb1.setToggleGroup(group);
rb2.setToggleGroup(group);
```

### CheckBox
Independent on/off selection.

```java
CheckBox cb = new CheckBox("Accept Terms");
boolean isChecked = cb.isSelected();
```

### Hyperlink
Clickable link-styled control.

```java
Hyperlink link = new Hyperlink("Visit Website");
link.setOnAction(e -> System.out.println("Link clicked"));
```

### Menu & MenuBar
Application menu system.

```java
MenuBar menuBar = new MenuBar();
Menu fileMenu = new Menu("File");
MenuItem openItem = new MenuItem("Open");
MenuItem exitItem = new MenuItem("Exit");
exitItem.setOnAction(e -> Platform.exit());
fileMenu.getItems().addAll(openItem, new SeparatorMenuItem(), exitItem);
menuBar.getMenus().add(fileMenu);
```

### Tooltip
Hover hint for any control.

```java
Button btn = new Button("Save");
btn.setTooltip(new Tooltip("Click to save your work"));
```

### FileChooser
Native file selection dialog.

```java
FileChooser fc = new FileChooser();
fc.setTitle("Select a File");
fc.getExtensionFilters().add(
    new FileChooser.ExtensionFilter("Text Files", "*.txt")
);
File file = fc.showOpenDialog(stage);
```

---

## Dialog Boxes (Alerts)

JavaFX provides the `Alert` class for standard dialog boxes.

```java
// Information Dialog
Alert info = new Alert(Alert.AlertType.INFORMATION);
info.setTitle("Info");
info.setContentText("Operation completed.");
info.showAndWait();

// Confirmation Dialog
Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
confirm.setTitle("Confirm");
confirm.setContentText("Are you sure?");
Optional<ButtonType> result = confirm.showAndWait();
if (result.get() == ButtonType.OK) {
    // user confirmed
}

// Error Dialog
Alert error = new Alert(Alert.AlertType.ERROR);
error.setTitle("Error");
error.setContentText("Something went wrong!");
error.showAndWait();
```

---

## Running This Demo Project

### Prerequisites
- Java 21+
- Maven 3.8+

### Run Command
```bash
mvn javafx:run
```

### What the Demo Shows

| Tab | Demonstrates |
|-----|-------------|
| FlowPane | Buttons with tooltips arranged in a wrapping flow |
| BorderPane | TOP, BOTTOM, LEFT, RIGHT, CENTER regions |
| HBox & VBox | Horizontal and vertical arrangements |
| GridPane - Controls | Label, TextField, RadioButton, CheckBox, Hyperlink, Button |

**Menu Bar:** File → Open (FileChooser), Exit | Help → About (Dialog)

**Dialog Boxes:** Triggered by Submit button and menu items.

---

## Key Concepts Summary

1. **Stage** = Window
2. **Scene** = Container for all visual content
3. **Node** = Any element in the scene graph (controls, layouts)
4. **Layout Panes** = Manage child positioning automatically
5. **Controls** = Interactive UI elements (buttons, text fields, etc.)
6. **Events** = User interactions handled via `setOnAction()` or event handlers
7. **Properties** = Observable values that enable data binding

---

## References

- [OpenJFX Official Site](https://openjfx.io)
- [JavaFX API Documentation](https://openjfx.io/javadoc/21/)
- [JavaFX CSS Reference](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/doc-files/cssref.html)
