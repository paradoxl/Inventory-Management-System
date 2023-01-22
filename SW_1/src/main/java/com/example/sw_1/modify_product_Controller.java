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
 * This class is used to connect logic to the modify-product.fmxl file
 */
public class modify_product_Controller implements Initializable {
    private Product current;
    private ObservableList<Part> used  = FXCollections.observableArrayList();
//    private ObservableList<Part> inStock = FXCollections.observableArrayList();
    Parent scene;
    Alert cancelButtonAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to leave this panel?", ButtonType.YES, ButtonType.NO);
    Alert removePart = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to remove this part?", ButtonType.YES,ButtonType.NO);
    Alert checkStock = new Alert (Alert.AlertType.ERROR, "Please review min, max, and stock.", ButtonType.OK);
    Alert save = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to save this product?", ButtonType.YES,ButtonType.NO);
    Alert missingPart = new Alert(Alert.AlertType.ERROR, "You have no selected a part", ButtonType.OK);
    Alert word = new Alert(Alert.AlertType.ERROR, "It looks like you are placing a word where a number goes");
    //Textfields
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
    @FXML
    private Button Cancel;
    //Top Table
    @FXML
    private TableView<Part> topTable;
    @FXML
    private TableColumn<Part,Integer> topIdCOL;
    @FXML
    private TableColumn<Part,String> topNameCOL;
    @FXML
    private TableColumn<Part,Integer> topInvCOL;
    @FXML
    private TableColumn<Part,Double> topPriceCOL;
    //Bottom Table
    @FXML
    private TableView<Part> botTable;
    @FXML
    private TableColumn<Part,Integer> botIdCOL;
    @FXML
    private TableColumn<Part,String> botNameCOL;
    @FXML
    private TableColumn<Part,Integer> botInvCOL;
    @FXML
    private TableColumn<Part,Double> botPriceCOL;

    @FXML
    private TextField partSRCH;
    boolean checkAdd = false;
    Boolean containsParts = false;


    // populates both the bottom and top tables with parts that are contained within the system and parts that are
    // associated with one of the products.

    /**
     * Used to populate the tables to the right.
     * @param location
     * @param resoruces
     */

    public void initialize(URL location , ResourceBundle resoruces){
        topTable.setItems(Inventory.getParts());
        topIdCOL.setCellValueFactory(new PropertyValueFactory<Part,Integer>("id"));
        topNameCOL.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
        topInvCOL.setCellValueFactory(new PropertyValueFactory<Part,Integer>("stock"));
        topPriceCOL.setCellValueFactory(new PropertyValueFactory<Part,Double>("price"));


        botTable.setItems(used);
        botIdCOL.setCellValueFactory(new PropertyValueFactory<Part,Integer>("id"));
        botNameCOL.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
        botInvCOL.setCellValueFactory(new PropertyValueFactory<Part,Integer>("stock"));
        botPriceCOL.setCellValueFactory(new PropertyValueFactory<Part,Double>("price"));
    }
    // This method pulls the selected part from the top table and appends
    // it to the bottom one.

    /**
     * This method adds functionality to the search bar.
     * This took me by suprise and I had several issues with the logic here.
     * RUNTIME ERROR:
     * my main issue was creating a method that could functionally search for both a string and integer at the same time.
     * issues with number format occured when typing strings.
     * FUTURE ENHANCEMENT:
     * I feel that it would be a downgrade in functionality however one might be able to add a ID/Name selector to avoid issues with runtime errors.
     * @param actionEvent
     */
    public void searchParts(ActionEvent actionEvent){
        String value = partSRCH.getText();
        ObservableList result = Inventory.lookupPart(value);
        topTable.setItems(result);
        if(result.isEmpty()){    try {
            int helper = Integer.parseInt(partSRCH.getText());
            Part helper2 = Inventory.lookupPart(helper);
            if (helper2 == null) {

                return;
            }
            if (helper2.getId() == helper) {
                ObservableList result2 = Inventory.lookupPart(helper2.getName());
                topTable.setItems(result2);
            }
            else {

            }

        } catch (NumberFormatException e) {
            System.out.println("This one was a doozy");
        }
        catch (NullPointerException e){
            System.out.println("Whoops");
        }
        }
        topTable.setPlaceholder(new Label("Part not Found!"));
    }

    /**
     * This method is used to add parts to the bottom table. This method will also allocate a boolean to whether the product has associated parts.
     * @param actionEvent
     */
    public void addPart(ActionEvent actionEvent){
        Part selected = topTable.getSelectionModel().getSelectedItem();
//        if(topTable.getSelectionModel().getSelectedItem() != null) {
//            used.add(selected);
//            //TODO add associated part
////            Product.addAssociatedPart(selected);
//            used.add(current.addAssociatedPart(selected));
//            checkAdd = true;
//            containsParts = true;
//        }
//        else{
//            missingPart.showAndWait();
//        }

        if(topTable.getSelectionModel().getSelectedItem() != null) {
            used.add(selected);


        }
    }


    /**
     * This method is used to save the modifations made to the currently selected product.
     * There were some logical errors here as I tried to determine where the product had associated parts or not.
     * The solution was simple and required another observable list to store parts that were associated.
     * @param actionEvent
     * @throws IOException
     */
    public void saveProduct(ActionEvent actionEvent) throws IOException {
        //todo REMOVE associated part
        try {
            if (current.getAllAssociatedParts().isEmpty()) {
                containsParts = false;
            }
            int id = Integer.parseInt(idTextFLD.getText());
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
                    current.setName(name);
                    current.setStock(inv);
                    current.setPrice(price);
                    current.setMin(min);
                    current.setMax(max);
                    current.addAssociatedPart(used);
                    System.out.println("in save" + current.getAllAssociatedParts());
                    System.out.println(current.getId());
                    Inventory.updateProduct(current.getId(), current);
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                    stage.setTitle("Inventory Management System");
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }
        catch (NumberFormatException e){
            word.showAndWait();
        }


    }

    /**
     * This method is used to delete items from the lower table. Will ask for confirmation upon removal.
     * @param actionEvent
     */
    public void deletePart(ActionEvent actionEvent){
        // TODO delete part
//

        Alert alert = new Alert(Alert.AlertType.ERROR, "No parts have been selected to remove", ButtonType.OK);
        Part selected = botTable.getSelectionModel().getSelectedItem();
        if (botTable.getSelectionModel().isEmpty()) {
            alert.showAndWait();
            return;
        }
        removePart.showAndWait();
        if (removePart.getResult() == ButtonType.YES) {
            botTable.getItems().remove(botTable.getSelectionModel().getSelectedIndex());
            used.remove(selected);
            current.deleteAssociatedPart(selected);
            botTable.refresh();
        }
    }

    // Method to leave the view and return the user to the main-view.fxml page.

    /**
     * This method will return the user to the main page unless the user selects cancel. From the user will remain on current page.
     * @param actionEvent
     * @throws IOException
     */
    public void cancelButton(ActionEvent actionEvent) throws IOException {
        cancelButtonAlert.showAndWait();
        if(cancelButtonAlert.getResult() == ButtonType.YES) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("main-view.fxml"));
            stage.setTitle("Main View");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        if(cancelButtonAlert.getResult() == ButtonType.NO){
            System.out.println("Easter Egg");
        }

    }
    public void setAssociated(Product current){
        used.addAll(current.getAllAssociatedParts());
    }
    /**
     * This method is used to populate data on the left hand side of the modify product pane.
     * The Id selection should be greyed out and the user will not be able to edit this.
     * ID is set to auto increment.
     * @param current
     */

    public void populateLeftPane(Product current){
        this.current = current;
       int id = current.getId();
       String intButString = String.valueOf(id);
       idTextFLD.setText(intButString);
       nameTextFLD.setText(current.getName());
       int inventory = current.getStock();
       String inventoryButAString = String.valueOf(inventory);
       invTextFLD.setText(inventoryButAString);
       Double price = current.getPrice();
       String priceButString = String.valueOf(price);
       priceTextFLD.setText(priceButString);
       int min = current.getMin();
       String minButString = String.valueOf(min);
       minTextFLD.setText(minButString);
       int max = current.getMax();
       String maxButString = String.valueOf(max);
       maxTextFLD.setText(maxButString);
       used.addAll(current.getAllAssociatedParts());

    }
}
