package br.com.vhaporfiro.projetojavafxconsumoapirest.service;

import br.com.vhaporfiro.projetojavafxconsumoapirest.model.Paciente;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public class ApiService {

    private static final String API_BASE_URL = "http://localhost:8080/pacientes";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    // Construtor: Inicializa HttpClient e ObjectMapper com JavaTimeModule.
    public ApiService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    // Busca todos os pacientes cadastrados na API.
    public List<Paciente> getAllPacientes() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL))
                .GET()
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), new TypeReference<List<Paciente>>() {});
        } else {
            throw new IOException("Falha ao buscar pacientes: " + response.statusCode() + " - " + response.body());
        }
    }

    // Busca um paciente específico pelo seu ID na API.
    public Optional<Paciente> getPacienteById(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/" + id))
                .GET()
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return Optional.of(objectMapper.readValue(response.body(), Paciente.class));
        } else if (response.statusCode() == 404) {
            return Optional.empty();
        } else {
            throw new IOException("Falha ao buscar paciente por ID: " + response.statusCode() + " - " + response.body());
        }
    }

    // Cria um novo paciente na API.
    public Paciente createPaciente(Paciente paciente) throws IOException, InterruptedException {
        String jsonBody = objectMapper.writeValueAsString(paciente);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200 || response.statusCode() == 201) {
            return objectMapper.readValue(response.body(), Paciente.class);
        } else {
            throw new IOException("Falha ao criar paciente: " + response.statusCode() + " - " + response.body());
        }
    }

    // Atualiza um paciente existente na API.
    public Paciente updatePaciente(Long id, Paciente paciente) throws IOException, InterruptedException {
        String jsonBody = objectMapper.writeValueAsString(paciente);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/" + id))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), Paciente.class);
        } else {
            throw new IOException("Falha ao atualizar paciente: " + response.statusCode() + " - " + response.body());
        }
    }

    // Deleta um paciente da API pelo seu ID.
    public boolean deletePaciente(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/" + id))
                .DELETE()
                .build();
        HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        if (response.statusCode() == 204 || response.statusCode() == 200) {
            return true;
        } else {
            System.err.println("Falha ao deletar paciente: " + response.statusCode() + " (ID: " + id + ")");
            return false;
        }
    }
}