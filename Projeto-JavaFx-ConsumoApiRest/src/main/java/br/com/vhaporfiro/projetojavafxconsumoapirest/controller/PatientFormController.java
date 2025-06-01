package br.com.vhaporfiro.projetojavafxconsumoapirest.controller;

import br.com.vhaporfiro.projetojavafxconsumoapirest.MainApp;
import br.com.vhaporfiro.projetojavafxconsumoapirest.model.Paciente;
import br.com.vhaporfiro.projetojavafxconsumoapirest.service.ApiService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PatientFormController {

    @FXML private Label formTitleLabel;
    @FXML private TextField fullNameField;
    @FXML private DatePicker birthDateField;
    @FXML private TextField cpfField;
    @FXML private TextField rgField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField addressStreetField;
    @FXML private TextField addressCityField;
    @FXML private ComboBox<String> addressStateComboBox;
    @FXML private TextField addressZipField;
    @FXML private TextArea allergiesArea;
    @FXML private TextArea medicationsArea;
    @FXML private CheckBox isActiveCheckBox;
    @FXML private Button saveButton;

    private Paciente paciente;
    private MainApp mainApp;
    private PatientListController patientListController;
    private ApiService apiService;

    // Construtor: Inicializa o serviço da API.
    public PatientFormController() {
        this.apiService = new ApiService();
    }

    // Inicializa o controller após o FXML ser carregado.
    @FXML
    private void initialize() {
        addressStateComboBox.setItems(FXCollections.observableArrayList(
                "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS",
                "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR",
                "SC", "SP", "SE", "TO"
        ));
    }

    // Define a referência para a aplicação principal.
    public void setMainApp(MainApp mainApp) { this.mainApp = mainApp; }
    // Define a referência para o controller da lista de pacientes.
    public void setPatientListController(PatientListController patientListController) { this.patientListController = patientListController; }

    // Define o paciente a ser exibido/editado no formulário.
    public void setPatient(Paciente paciente) {
        this.paciente = (paciente == null) ? new Paciente() : paciente;

        if (this.paciente.getId() != null) {
            formTitleLabel.setText("Editar Dados do Paciente");
            populateFormFields(this.paciente);
        } else {
            formTitleLabel.setText("Cadastrar Novo Paciente");
            clearFormFields();
            isActiveCheckBox.setSelected(true);
        }
    }

    // Limpa todos os campos do formulário.
    private void clearFormFields() {
        fullNameField.clear();
        birthDateField.setValue(null);
        cpfField.clear();
        rgField.clear();
        emailField.clear();
        phoneField.clear();
        addressStreetField.clear();
        addressCityField.clear();
        addressStateComboBox.getSelectionModel().clearSelection();
        addressZipField.clear();
        allergiesArea.clear();
        medicationsArea.clear();
    }

    // Preenche os campos do formulário com os dados de um paciente existente.
    private void populateFormFields(Paciente p) {
        fullNameField.setText(p.getFullName());
        birthDateField.setValue(p.getBirthDate());
        cpfField.setText(p.getCpf());
        rgField.setText(p.getRg());
        emailField.setText(p.getEmail());
        phoneField.setText(p.getPhone());
        addressStreetField.setText(p.getAddressStreet());
        addressCityField.setText(p.getAddressCity());
        addressStateComboBox.setValue(p.getAddressState());
        addressZipField.setText(p.getAddressZip());
        isActiveCheckBox.setSelected(p.getIsActive() != null && p.getIsActive());

        allergiesArea.setText( (p.getAllergies() != null && p.getAllergies().length > 0) ? String.join(", ", p.getAllergies()) : "" );
        medicationsArea.setText( (p.getMedications() != null && p.getMedications().length > 0) ? String.join(", ", p.getMedications()) : "" );
    }

    // Manipula o evento de clique no botão Salvar.
    @FXML
    private void handleSave() {
        if (!isInputValid()) return;

        paciente.setFullName(fullNameField.getText());
        paciente.setBirthDate(birthDateField.getValue());
        paciente.setCpf(cpfField.getText());
        paciente.setRg(rgField.getText());
        paciente.setEmail(emailField.getText());
        paciente.setPhone(phoneField.getText());
        paciente.setAddressStreet(addressStreetField.getText());
        paciente.setAddressCity(addressCityField.getText());
        paciente.setAddressState(addressStateComboBox.getValue());
        paciente.setAddressZip(addressZipField.getText());
        paciente.setIsActive(isActiveCheckBox.isSelected());

        paciente.setAllergies(allergiesArea.getText().trim().isEmpty() ? new String[0] : allergiesArea.getText().split("\\s*,\\s*"));
        paciente.setMedications(medicationsArea.getText().trim().isEmpty() ? new String[0] : medicationsArea.getText().split("\\s*,\\s*"));

        saveButton.setDisable(true);

        Task<Paciente> saveTask = new Task<>() {
            @Override
            protected Paciente call() throws Exception {
                if (paciente.getId() == null) {
                    return apiService.createPaciente(paciente);
                } else {
                    return apiService.updatePaciente(paciente.getId(), paciente);
                }
            }
        };

        saveTask.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                showSuccessAlert("Sucesso", "Paciente salvo com sucesso!");
                if (mainApp != null) {
                    mainApp.showPatientListScreen();
                }
            });
        });

        saveTask.setOnFailed(event -> {
            Platform.runLater(() -> {
                showErrorAlert("Erro ao Salvar", "Não foi possível salvar o paciente.", saveTask.getException().getLocalizedMessage());
                saveTask.getException().printStackTrace();
                saveButton.setDisable(false);
            });
        });
        new Thread(saveTask).start();
    }

    // Manipula o evento de clique no botão Cancelar.
    @FXML
    private void handleCancel() {
        if (mainApp != null) {
            mainApp.showPatientListScreen();
        }
    }

    // Valida os campos de entrada do formulário.
    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();
        if (fullNameField.getText() == null || fullNameField.getText().trim().isEmpty()) errorMessage.append("Nome completo é obrigatório!\n");
        if (birthDateField.getValue() == null) errorMessage.append("Data de nascimento é obrigatória!\n");
        if (cpfField.getText() == null || cpfField.getText().trim().isEmpty()) errorMessage.append("CPF é obrigatório!\n");

        if (errorMessage.isEmpty()) return true;

        showErrorAlert("Campos Inválidos", "Por favor, corrija os campos inválidos:", errorMessage.toString());
        return false;
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
}