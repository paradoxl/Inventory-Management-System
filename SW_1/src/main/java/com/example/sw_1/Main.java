package com.example.sw_1;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the main class and will start the main window.
 */
public class Main extends Application {
    /**
     * This method initiates all starting data and loads first screen.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
//    System.out.println(getClass().getResource(main_view_Controller.class.getResource(main-view.fxml)));
    //adding data for testing methods.
    Inventory.addPart(new InHouse(1,"AMD_CPU",299.99,1,1,5,25));
    Inventory.addPart(new InHouse(2,"AMD GPU", 999.99, 3, 1, 10, 26));
    Inventory.addPart(new Outsourced(3,"NVME Drive", 99.99, 51, 1, 100, "keyboard store" ));

    Inventory.addProduct(new Product(1, "AMD Pre-Built", 1999.99,2,1,3));
    Inventory.addProduct(new Product(2,"Das-Keyboard", 99.99, 10,1,100));
    Inventory.addProduct(new Product(3,"Logitech-Mouse",50.99,11,1,100));


        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 900, 400);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Main method to launch.
     * javaDocs can be found at: src/main/java/javaDoc
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}