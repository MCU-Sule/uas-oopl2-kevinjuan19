module com.uas_1972002 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.persistence;
    requires java.naming;
    requires org.hibernate.orm.core;


    opens Model to javafx.fxml;
    exports Model;
}