module Projet_fx.Flappy {
    requires javafx.controls;
    requires javafx.fxml;
    requires norm;
	requires jdk.compiler;
	requires jdk.javadoc;
	requires persistence.api;
	requires java.sql;
	requires jdk.jdi;
	requires javafx.graphics;

    opens Projet_fx.Flappy to javafx.fxml;
    exports Projet_fx.Flappy;
}
