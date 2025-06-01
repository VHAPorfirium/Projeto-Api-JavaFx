package br.com.vhaporfiro.projetojavafxconsumoapirest.controller;

import br.com.vhaporfiro.projetojavafxconsumoapirest.MainApp;
import br.com.vhaporfiro.projetojavafxconsumoapirest.model.Paciente;
import br.com.vhaporfiro.projetojavafxconsumoapirest.service.ApiService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PatientListController {

    @FXML private TableView<Paciente> patientTable;
    @FXML private TableColumn<Paciente, String> nameCol;
    @FXML private TableColumn<Paciente, String> cpfCol;
    @FXML private TableColumn<Paciente, String> birthDateCol;
    @FXML private TableColumn<Paciente, Boolean> statusCol;
    @FXML private TableColumn<Paciente, Void> actionsCol;

    @FXML private TextField searchFieldMain;

    private ProgressIndicator loadingIndicator;

    private ObservableList<Paciente> patientMasterData = FXCollections.observableArrayList();
    private FilteredList<Paciente> filteredData;
    private MainApp mainApp;
    private ApiService apiService;

    // Construtor: Inicializa o serviço da API.
    public PatientListController() {
        this.apiService = new ApiService();
    }

    // Define a referência para a aplicação principal.
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    // Inicializa o controller após o FXML ser carregado.
    @FXML
    private void initialize() {
        setupLoadingIndicator();
        setupTableColumns();
        loadPatientsFromApi();

        filteredData = new FilteredList<>(patientMasterData, p -> true);
        patientTable.setItems(filteredData);

        searchFieldMain.textProperty().addListener((obs, oldVal, newVal) -> applyFilter(newVal));
    }

    // Configura o indicador de carregamento para a tabela.
    private void setupLoadingIndicator() {
        loadingIndicator = new ProgressIndicator();
        loadingIndicator.setVisible(false);
        patientTable.setPlaceholder(loadingIndicator);
    }

    // Controla a visibilidade do indicador de carregamento.
    private void showLoading(boolean show) {
        if (loadingIndicator != null) {
            loadingIndicator.setVisible(show);
        }
    }

    // Configura as colunas da tabela de pacientes.
    private void setupTableColumns() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        cpfCol.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        birthDateCol.setCellValueFactory(cellData -> {
            LocalDate date = cellData.getValue().getBirthDate();
            return date != null ? new javafx.beans.property.SimpleStringProperty(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))) : new javafx.beans.property.SimpleStringProperty("");
        });

        statusCol.setCellValueFactory(new PropertyValueFactory<>("isActive"));
        statusCol.setCellFactory(column -> new TableCell<>() {
            private final Button statusButton = new Button();
            { statusButton.getStyleClass().add("status-button"); }
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    statusButton.setText(item ? "Ativo" : "Inativo");
                    statusButton.getStyleClass().removeAll("status-button-active", "status-button-inactive");
                    statusButton.getStyleClass().add(item ? "status-button-active" : "status-button-inactive");
                    setGraphic(statusButton);
                }
            }
        });

        actionsCol.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Editar");
            private final Button deleteButton = new Button("Excluir");
            private final HBox pane = new HBox(5, editButton, deleteButton);
            {
                editButton.getStyleClass().addAll("button", "action-button", "action-button-edit");
                deleteButton.getStyleClass().addAll("button", "action-button", "action-button-delete");
                editButton.setOnAction(event -> {
                    Paciente paciente = getTableView().getItems().get(getIndex());
                    handleEditPatient(paciente);
                });
                deleteButton.setOnAction(event -> handleDeletePatient(getTableView().getItems().get(getIndex())));
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }

    // Carrega os dados dos pacientes da API de forma assíncrona.
    public void loadPatientsFromApi() {
        showLoading(true);
        patientTable.setPlaceholder(loadingIndicator);

        Task<List<Paciente>> loadTask = new Task<>() {
            @Override
            protected List<Paciente> call() throws Exception {
                return apiService.getAllPacientes();
            }
        };

        loadTask.setOnSucceeded(event -> {
            patientMasterData.setAll(loadTask.getValue());
            Platform.runLater(() -> {
                showLoading(false);
                if(patientMasterData.isEmpty()){
                    patientTable.setPlaceholder(new Label("Nenhum paciente encontrado."));
                }
            });
        });

        loadTask.setOnFailed(event -> {
            Platform.runLater(() -> {
                showLoading(false);
                patientTable.setPlaceholder(new Label("Falha ao carregar pacientes."));
                showErrorAlert("Erro de API", "Não foi possível carregar os dados dos pacientes.", loadTask.getException().getLocalizedMessage());
                loadTask.getException().printStackTrace();
            });
        });
        new Thread(loadTask).start();
    }

    // Aplica o filtro de busca na lista de pacientes.
    private void applyFilter(String searchText) {
        String lowerCaseFilter = searchText.toLowerCase().trim();
        filteredData.setPredicate(paciente -> {
            if (searchText == null || searchText.isEmpty()) return true;
            if (paciente.getFullName().toLowerCase().contains(lowerCaseFilter)) return true;
            return paciente.getCpf() != null && paciente.getCpf().toLowerCase().contains(lowerCaseFilter);
        });
    }

    // Manipula o evento de clique no botão Novo Paciente.
    @FXML
    private void handleNewPatient() {
        if (mainApp != null) {
            mainApp.showPatientFormScreen(null);
        }
    }

    // Prepara e exibe a tela de formulário para editar um paciente.
    private void handleEditPatient(Paciente paciente) {
        if (mainApp != null) {
            mainApp.showPatientFormScreen(paciente);
        }
    }

    // Manipula o evento de clique no botão Excluir para um paciente.
    private void handleDeletePatient(Paciente paciente) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja excluir " + paciente.getFullName() + "?", ButtonType.YES, ButtonType.NO);
        if (mainApp != null) alert.initOwner(mainApp.getPrimaryStage());
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                Task<Boolean> deleteTask = new Task<>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return apiService.deletePaciente(paciente.getId());
                    }
                };
                deleteTask.setOnSucceeded(e -> {
                    if (deleteTask.getValue()) {
                        Platform.runLater(() -> {
                            patientMasterData.remove(paciente);
                            showSuccessAlert("Sucesso", "Paciente excluído com sucesso!");
                        });
                    } else {
                        Platform.runLater(() -> showErrorAlert("Erro ao Excluir", "Não foi possível excluir o paciente.", "A API retornou uma falha ou o paciente não foi encontrado."));
                    }
                });
                deleteTask.setOnFailed(e -> {
                    Platform.runLater(() -> showErrorAlert("Erro de API", "Falha ao tentar excluir o paciente.", deleteTask.getException().getLocalizedMessage()));
                    deleteTask.getException().printStackTrace();
                });
                new Thread(deleteTask).start();
            }
        });
    }

    // Exibe um alerta de sucesso.
    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        if (mainApp != null) alert.initOwner(mainApp.getPrimaryStage());
        alert.showAndWait();
    }

    // Exibe um alerta de erro.
    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (mainApp != null) alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Retorna a lista mestre de dados dos pacientes.
    public ObservableList<Paciente> getPatientMasterData() {
        return patientMasterData;
    }

    // Atualiza a tabela de pacientes (recarregando da API).
    public void refreshTableAfterFormAction() {
        loadPatientsFromApi();
    }
}