package br.com.vhaporfiro.projetojavafxconsumoapirest;

import br.com.vhaporfiro.projetojavafxconsumoapirest.controller.PatientFormController;
import br.com.vhaporfiro.projetojavafxconsumoapirest.controller.PatientListController;
import br.com.vhaporfiro.projetojavafxconsumoapirest.model.Paciente;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale; // Import para Locale

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private PatientListController patientListControllerInstance;

    @Override
    public void start(Stage primaryStage) {

        Locale.setDefault(new Locale("pt", "BR"));

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestor de Pacientes FXML");
        this.primaryStage.setMinWidth(900);
        this.primaryStage.setMinHeight(650);

        initRootLayout();
        showPatientListScreen();
    }

    public void initRootLayout() {
        rootLayout = new BorderPane();

        Scene scene = new Scene(rootLayout);
        URL cssUrl = getClass().getResource("styles/styles.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("AVISO: Arquivo styles.css não encontrado.");
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showPatientListScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL fxmlUrl = getClass().getResource("view/PatientListScreen.fxml");
            if (fxmlUrl == null) {
                System.err.println("ERRO: Não foi possível encontrar PatientListScreen.fxml.");
                return;
            }
            loader.setLocation(fxmlUrl);
            BorderPane patientListPane = loader.load();

            rootLayout.setCenter(patientListPane);

            patientListControllerInstance = loader.getController();
            patientListControllerInstance.setMainApp(this);
            primaryStage.setTitle("Gestor de Pacientes - Lista");

        } catch (IOException e) {
            System.err.println("Falha ao carregar PatientListScreen.fxml:");
            e.printStackTrace();
        }
    }

    public void showPatientFormScreen(Paciente paciente) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL fxmlUrl = getClass().getResource("view/PatientFormScreen.fxml");
            if (fxmlUrl == null) {
                System.err.println("ERRO: Não foi possível encontrar PatientFormScreen.fxml.");
                return;
            }
            loader.setLocation(fxmlUrl);
            Parent formPage = loader.load();

            rootLayout.setCenter(formPage);

            PatientFormController controller = loader.getController();
            controller.setPatient(paciente);
            controller.setMainApp(this);
            if (patientListControllerInstance != null) {
                controller.setPatientListController(patientListControllerInstance);
            }

            primaryStage.setTitle(paciente == null || paciente.getId() == null ? "Gestor de Pacientes - Novo Paciente" : "Gestor de Pacientes - Editar Paciente");

        } catch (IOException e) {
            System.err.println("Falha ao carregar PatientFormScreen.fxml:");
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
