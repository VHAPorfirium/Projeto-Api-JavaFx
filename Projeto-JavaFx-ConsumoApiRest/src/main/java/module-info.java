module br.com.vhaporfiro.projetojavafxconsumoapirest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens br.com.vhaporfiro.projetojavafxconsumoapirest to javafx.fxml;
    exports br.com.vhaporfiro.projetojavafxconsumoapirest;
}