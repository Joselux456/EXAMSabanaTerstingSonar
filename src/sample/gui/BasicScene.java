package sample.gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.logic.PersonaException;
import sample.logic.entities.Persona;
import sample.logic.services.IPersonaServices;
import sample.logic.services.impl.PersonaService;


import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class BasicScene extends Application {

    //Propiedades Visuales - Las propiedades utilizadas como botones, declaradas para aparecer en la interfaz
    private Scene scene;
    private TableView<Persona> personasTable;
    private TextField nameInput;
    private TextField lastnameInput;
    private TextField ageInput;
    private TextField estadoInput;
    private TextField trabajoInput;
    private TextField cedInput;
    private Button addPersona;
    private Button editPersona;
    private Button deletePersona;
    private Button openReport;

    //Para el menú - Derivada de las propiedades para el menu desplegable
    private MenuBar menuBar;
    private Map<String, MenuItem> fileMenuItems;

    //Propiedades Lógicas - Son las propiedades que proveen servicios como añadir, eliminar, etc...
    private IPersonaServices personaServices;

    //CRUD - Se crea un CRUD para la interfaz gráfica
    @Override
    public void start(Stage primaryStage) throws Exception{
        setUp();
        behavior(primaryStage);

        //Stage - Se crea la ventana de stage que hace display de información básica
        primaryStage.setTitle("Situación en Colombia");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    //Behavior - Una separación dedicada a todo el comportamiento de nuestro archivo, desde aqui se llama a personaServices y se codifican botones entre otros

    private void behavior(Stage stage){
        this.personaServices = new PersonaService();
        try {
            this.personaServices.insert(new Persona("44", "Ivan", "Duque", "Conocido", "Presidente", "85890669"));
            this.personaServices.insert(new Persona("51", "Claudia", "Lopez", "Conocido", "Alcaldesa", "85890670"));
            this.personaServices.insert(new Persona("61", "Gustavo", "Petro", "Conocido", "Senador", "85890671"));
            this.personaServices.insert(new Persona("68", "Alvaro", "Uribe", "Conocido", "Expresidente", "85890672"));
        }catch (PersonaException e) {
            e.printStackTrace();
        }

        personasTable.setItems((ObservableList<Persona>) this.personaServices.getAll());
        addPersona.setOnAction(e-> {
            try {
                Persona p = new Persona(ageInput.getText(), nameInput.getText(), lastnameInput.getText(), estadoInput.getText(), trabajoInput.getText(), cedInput.getText());
                this.personaServices.insert(p);
                ageInput.clear();
                nameInput.clear();
                lastnameInput.clear();
                estadoInput.clear();
                trabajoInput.clear();
                cedInput.clear();
            } catch (PersonaException personaException) {
                personaException.printStackTrace();
            }
        });

        editPersona.setOnAction(e ->{
            try {
                Persona p = new Persona(ageInput.getText(), nameInput.getText(), lastnameInput.getText(), estadoInput.getText(), trabajoInput.getText(), cedInput.getText());
                this.personaServices.edit(personasTable.getSelectionModel().getSelectedItem(),p);
                ageInput.clear();
                nameInput.clear();
                lastnameInput.clear();
                estadoInput.clear();
                trabajoInput.clear();
                cedInput.clear();
            } catch (PersonaException personaException) {
                personaException.printStackTrace();
            }
        });

        deletePersona.setOnAction(e ->{
            this.personaServices.delete(personasTable.getSelectionModel().getSelectedItems());
        });

        fileMenuItems.get("Export").setOnAction(e -> {
            try {
                this.personaServices.export();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        fileMenuItems.get("Import").setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccione su tabla a importar");
            File file = fileChooser.showOpenDialog(stage);
            if(file == null){
                System.out.println("No file");
            }else{
                try {
                    this.personaServices.importPersonas(file);
                    this.personaServices.getAll().stream().forEach(p->System.out.println(p));
                } catch (IOException | PersonaException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        openReport.setOnAction(e -> {
            new ReportScene();
        });

    }
    //SetUp - Otra separación hecha con el fin de organizar, esta se encarga de hacer el "Setting" de los objetos que es hacerlos visuales para el usuario
    private void setUp(){

        setupTable();
        setupInputs();
        setupMenu();
        setUpCrud();

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(ageInput,nameInput,lastnameInput,estadoInput,trabajoInput,cedInput,addPersona,editPersona,deletePersona,openReport);

        //Border Pane - Para el menu de importar y exportar
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        menuBar.setStyle("-fx-background-color: NAVAJOWHITE");
        borderPane.setMaxWidth(75);

        //Layout - Se crea para la escena
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(borderPane, personasTable, hBox);


        //Scene - Se crea como punto de partida para la vista
        layout.setStyle("-fx-background-color: LIGHTSLATEGRAY;");
        scene = new Scene(layout, 900,550);

    }

    //setUpCrud - A diferencia de nuestro de setUp, este configura cono se verán las cosas sin sacarlas en display
    private void setUpCrud() {
        addPersona = new Button();
        addPersona.setStyle("-fx-background-color: Orange");
        addPersona.setText("¡Adiciona!");
        addPersona.setMinWidth(70);

        editPersona = new Button();
        editPersona.setStyle("-fx-background-color: Orange");
        editPersona.setText("¡Edita!");
        editPersona.setMinWidth(70);

        deletePersona = new Button();
        deletePersona.setStyle("-fx-background-color: Orange");
        deletePersona.setText("¡Elimina!");
        deletePersona.setMinWidth(70);

        openReport = new Button("Vista en resumen");
        openReport.setStyle("-fx-background-color: Orange");
        openReport.setMinWidth(130);

    }

    //setUpInputs - A diferencia de nuestro de setUpCrud, este configura cono se verán nuestros campos de texto
    private void setupInputs(){

        ageInput = new TextField();
        ageInput.setStyle("-fx-background-color: NAVAJOWHITE");
        ageInput.setPromptText("edad");
        ageInput.setMinWidth(30);

        nameInput = new TextField();
        nameInput.setStyle("-fx-background-color: NAVAJOWHITE");
        nameInput.setPromptText("nombre");
        nameInput.setMinWidth(30);

        lastnameInput = new TextField();
        lastnameInput.setStyle("-fx-background-color: NAVAJOWHITE");
        lastnameInput.setPromptText("apellido");
        lastnameInput.setMinWidth(30);


        estadoInput = new TextField();
        estadoInput.setStyle("-fx-background-color: NAVAJOWHITE");
        estadoInput.setPromptText("estado");
        estadoInput.setMinWidth(30);

        trabajoInput = new TextField();
        trabajoInput.setStyle("-fx-background-color: NAVAJOWHITE");
        trabajoInput.setPromptText("trabajo");
        trabajoInput.setMinWidth(30);

        cedInput = new TextField();
        cedInput.setStyle("-fx-background-color: NAVAJOWHITE");
        cedInput.setPromptText("cédula");
        cedInput.setMinWidth(30);
    }

    //SetupTable - Se encarga de dar las propiedades a la tabla y a sus columnas
    private void setupTable(){

        //Columna de edad
        TableColumn<Persona, Integer> ageColumn = new TableColumn<>("Edad");
        ageColumn.setMaxWidth(400);
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        //Columna de nombre
        TableColumn<Persona, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setMaxWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Columna de apellido
        TableColumn<Persona, String> lastnameColumn = new TableColumn<>("Apellido");
        lastnameColumn.setMaxWidth(200);
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));

        //Columna de estado
        TableColumn<Persona, String> estadoColumn = new TableColumn<>("Estado");
        estadoColumn.setMaxWidth(200);
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));

        //Columna de trabajo
        TableColumn<Persona, String> trabajoColumn = new TableColumn<>("Trabajo");
        trabajoColumn.setMaxWidth(200);
        trabajoColumn.setCellValueFactory(new PropertyValueFactory<>("trabajo"));

        //Columna de cédula
        TableColumn<Persona, String> cedColumn = new TableColumn<>("Cédula");
        cedColumn.setMaxWidth(300);
        cedColumn.setCellValueFactory(new PropertyValueFactory<>("ced"));

        //Se settea la tabla
        personasTable= new TableView<>();
        personasTable.setStyle("-fx-background-color: NAVAJOWHITE");
        personasTable.getColumns().add(ageColumn);
        personasTable.getColumns().add(nameColumn);
        personasTable.getColumns().add(lastnameColumn);
        personasTable.getColumns().add(estadoColumn);
        personasTable.getColumns().add(trabajoColumn);
        personasTable.getColumns().add(cedColumn);
        personasTable.setMaxWidth(500);

    }

    //setupMenu - Este es el encargado de dar las propiedades a nuestra barra de import/export
    private void setupMenu() {
        Menu fileMenu = new Menu("Archivo");

        fileMenuItems = new HashMap<>();
        fileMenuItems.put("Import", new MenuItem("Import"));
        fileMenuItems.put("Export", new MenuItem("Export"));

        fileMenu.getItems().add(fileMenuItems.get("Import"));
        fileMenu.getItems().add(fileMenuItems.get("Export"));

        menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
