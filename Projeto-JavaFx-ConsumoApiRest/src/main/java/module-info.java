module br.com.vhaporfiro.projetojavafxconsumoapirest {
    // Módulos JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    // Bibliotecas externas de UI (se usadas, como no seu exemplo anterior)
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    // Módulos Jackson para JSON
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310; // Para tipos de data/hora do Java Time
    requires com.fasterxml.jackson.core;

    // Cliente HTTP do Java
    requires java.net.http;

    // Abre pacotes para reflexão pelo JavaFX e Jackson
    opens br.com.vhaporfiro.projetojavafxconsumoapirest to javafx.fxml, javafx.graphics;
    opens br.com.vhaporfiro.projetojavafxconsumoapirest.controller to javafx.fxml;
    opens br.com.vhaporfiro.projetojavafxconsumoapirest.model to javafx.base, com.fasterxml.jackson.databind;

    // Exporta o pacote principal da aplicação
    exports br.com.vhaporfiro.projetojavafxconsumoapirest;
}
