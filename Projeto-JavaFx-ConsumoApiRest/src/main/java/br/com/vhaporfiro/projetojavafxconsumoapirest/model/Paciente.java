package br.com.vhaporfiro.projetojavafxconsumoapirest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
import java.time.LocalDate;

// Ignora propriedades desconhecidas que podem vir da API e não estão no DTO do cliente
@JsonIgnoreProperties(ignoreUnknown = true)
public class Paciente {
    private Long id;
    private String fullName; // Nome Completo

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate; // Data de Nascimento

    private String cpf;
    private String rg;
    private String email;
    private String phone; // Telefone

    private String addressStreet; // Logradouro (Rua, Avenida, etc.)
    private String addressCity;   // Cidade
    private String addressState;  // Estado (UF)
    private String addressZip;    // CEP

    private String[] allergies;   // Alergias
    private String[] medications; // Medicamentos

    private Boolean isActive;     // Está ativo? (Usar Boolean para permitir nulo se necessário)

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant createdAt;    // Data de Criação

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant updatedAt;    // Data da Última Atualização


    // Construtor padrão é necessário para Jackson
    public Paciente() {
        this.isActive = true; // Padrão para novo paciente
    }

    // Construtor de exemplo
    public Paciente(Long id, String fullName, LocalDate birthDate, String cpf, Boolean isActive, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.cpf = cpf;
        this.isActive = isActive;
        this.phone = phone;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddressStreet() { return addressStreet; }
    public void setAddressStreet(String addressStreet) { this.addressStreet = addressStreet; }
    public String getAddressCity() { return addressCity; }
    public void setAddressCity(String addressCity) { this.addressCity = addressCity; }
    public String getAddressState() { return addressState; }
    public void setAddressState(String addressState) { this.addressState = addressState; }
    public String getAddressZip() { return addressZip; }
    public void setAddressZip(String addressZip) { this.addressZip = addressZip; }
    public String[] getAllergies() { return allergies; }
    public void setAllergies(String[] allergies) { this.allergies = allergies; }
    public String[] getMedications() { return medications; }
    public void setMedications(String[] medications) { this.medications = medications; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean active) { isActive = active; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", birthDate=" + birthDate +
                ", cpf='" + cpf + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
