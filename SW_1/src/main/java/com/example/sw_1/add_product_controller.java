package com.example.sw_1;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is used to connect the add-product.fxml to logic.
 */
public class add_product_controller implements Initializable {
    // variables for the left panel.
    @FXML
    private TextField idTextFLD;
    @FXML
    private TextField nameTextFLD;
    @FXML
    private TextField invTextFLD;
    @FXML
    private TextField priceTextFLD;
    @FXML
    private TextField maxTextFLD;
    @FXML
    private TextField minTextFLD;

    // variables for the top table
    @FXML
    private TableView<Part> topTABLE;
    @FXML
    private TableColumn<Part, Integer> topIDCOL;
    @FXML
    private TableColumn<Part, String> topNameCOL;
    @FXML
    private TableColumn<Part, Integer> topInvCOL;
    @FXML
    private TableColumn<Part, Double> topPriceCOL;

    // variables for the bottom table

    @FXML
    private TableView<Part> botTABLE;
    @FXML
    private TableColumn<Part, Integer> botIDCOL;
    @FXML
    private TableColumn<Part, String> botNameCOL;
    @FXML
    private TableColumn<Part, Integer> botInvCOL;
    @FXML
    private TableColumn<Part, Double> botPriceCOL;

    // Search bar
    @FXML
    private TextField partSRCH;

    private Parent scene;
    Product current;
    Boolean checkAdd = false;
    Boolean containsParts = false;
    private ObservableList<Part> used = FXCollections.observableArrayList();

    Alert cancelButtonAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to leave this panel?", ButtonType.YES, ButtonType.NO);
    Alert removePart = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to remove this part?", ButtonType.YES, ButtonType.NO);
    Alert missingPart = new Alert(Alert.AlertType.ERROR, "You have no selected a part", ButtonType.OK);
    Alert confirmSave = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to save this part?", ButtonType.YES, ButtonType.NO);
    Alert minMaxError = new Alert(Alert.AlertType.ERROR, "Check your min max fields. Those need to correlate with inventory.", ButtonType.OK);
    Alert save = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to save this product?", ButtonType.YES,ButtonType.NO);
    Alert checkStock = new Alert (Alert.AlertType.ERROR, "Please review min, max, and stock.", ButtonType.OK);
    Product product = new Product();

    /**
     * This method is used to populate the tables.
     * @param location
     * @param resoruces
     */

    public void initialize(URL location, ResourceBundle resoruces) {

        System.out.println(used);

        topTABLE.setItems(Inventory.getParts());
        topIDCOL.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        topNameCOL.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        topInvCOL.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        topPriceCOL.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        botTABLE.setItems(used);
        botIDCOL.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        botNameCOL.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        botInvCOL.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        botPriceCOL.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
    }

    /**
     * This method will bring you back to the main page.
     * @param actionEvent
     * @throws Exception
     */
    public void cancelButton(ActionEvent actionEvent) throws Exception {
        cancelButtonAlert.showAndWait();
        if (cancelButtonAlert.getResult() == ButtonType.YES) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("main-view.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        if (cancelButtonAlert.getResult() == ButtonType.NO) {
            System.out.println("Easter Egg");
        }
    }

    /**
     * This method will add a part to the bottom table.
     * this method will take a part from the top pane and move it to "selected" in the bottom.
     * @param actionEvent
     */
    public void addPart(ActionEvent actionEvent) {
        Part selected = topTABLE.getSelectionModel().getSelectedItem();
        if(topTABLE.getSelectionModel().getSelectedItem() != null) {
//            used.add(selected);
//            product.addAssociatedPart(selected);
//            used.add(selected);
//            containsParts = true;
            used.add(topTABLE.getSelectionModel().getSelectedItem());
            System.out.println(used);

        }
        else{
            missingPart.showAndWait();

        }
    }

    /**
     * This method will remove a part from the bottom pane.
     * @param actionEvent
     */
    public void deletePart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "No parts have been selected to remove", ButtonType.OK);
        Part selected = botTABLE.getSelectionModel().getSelectedItem();
        if (botTABLE.getSelectionModel().isEmpty()) {
            alert.showAndWait();
            return;
        }
        removePart.showAndWait();
        if (removePart.getResult() == ButtonType.YES) {
            botTABLE.getItems().remove(botTABLE.getSelectionModel().getSelectedItem());
            used.remove(botTABLE.getSelectionModel().getSelectedCells());
            botTABLE.refresh();
        }
    }

    /**
     * This method will search for parts in the top pane.
     * RUNTIME ERROR: difficult to search for int and string simultaneously
     * @param actionEvent
     */
    public void searchPart(ActionEvent actionEvent) {
        String value = partSRCH.getText();
        ObservableList result = Inventory.lookupPart(value);
        topTABLE.setItems(result);
        if (result.isEmpty()) {
            try {
                int helper = Integer.parseInt(partSRCH.getText());
                Part helper2 = Inventory.lookupPart(helper);
                if (helper2 == null) {

                    return;
                }
                if (helper2.getId() == helper) {
                    ObservableList result2 = Inventory.lookupPart(helper2.getName());
                    topTABLE.setItems(result2);
                } else {
                    missingPart.show();
                }

            } catch (NumberFormatException e) {
                System.out.println("This one was a doozy");
            }
            topTABLE.setPlaceholder(new Label("Part not Found!"));

        }
    }

    /**
     * This method will save the product to the main screen and set all appropriate fields.
     * @param actionEvent
     * @throws IOException
     */
    public void saveProduct(ActionEvent actionEvent) throws IOException {
        try {
            String name = nameTextFLD.getText();
            int inv = Integer.parseInt(invTextFLD.getText());
            double price = Double.parseDouble(priceTextFLD.getText());
            int min = Integer.parseInt(minTextFLD.getText());
            int max = Integer.parseInt(maxTextFLD.getText());

            save.showAndWait();
            if (save.getResult() == ButtonType.YES) {
                if (min > max) {
                    checkStock.showAndWait();
                } else if (inv < min && inv > max) {
                    checkStock.showAndWait();
                } else {

                        System.out.println("HERE");
                        Boolean containsParts = true;
                        Product current = new Product(1, name, price, inv, min, max);
                        for(Part part: used){
                            current.addAssociatedPart(part);
                        }
                        System.out.println("Part has been added" + current.getAllAssociatedParts());
                        Inventory.addProduct(current);
                        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                        stage.setTitle("Inventory Management System");
                        stage.setScene(new Scene(scene));
                        stage.show();

//                    else {
//                        Product current = new Product(1, name, price, inv, min, max);
//                        Inventory.addProduct(current);
//                        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
//                        scene = FXMLLoader.load(getClass().getResource("main-view.fxml"));
//                        stage.setTitle("Inventory Management System");
//                        stage.setScene(new Scene(scene));
//                        stage.show();
//                    }
                }
            }

            }
        catch (NumberFormatException e){
            Alert format = new Alert (Alert.AlertType.ERROR, "it looks like your trying to place a word where a number needs to go. Or have failed to fill out required forms.", ButtonType.OK);
            format.showAndWait();

        }



    }
}

