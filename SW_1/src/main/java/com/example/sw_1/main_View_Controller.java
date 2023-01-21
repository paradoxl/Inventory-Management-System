package com.example.sw_1;


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
 * This class is the main view of the application.
 * All pages will redirect to this window when closed.
 * contains two panels displaying products and parts.
 */
public class main_View_Controller implements Initializable {
    private Parent scene;
    //tables
    @FXML
    private TableView<Product> ProductTABLE;
    @FXML
    private TableView<Part> partsTABLE;
    @FXML
    private TableColumn<Part,Integer> PartInventoryCOL;
    @FXML
    private TableColumn<Part,Integer> partIDCOL;
    @FXML
    private TableColumn<Part, String> partNameCOL;
    @FXML
    private TableColumn<Part, Double> partPriceCOL;
    @FXML
    private TableColumn<Product, Integer> productInventoryCOL;
    @FXML
    private TableColumn<Product, String> productNameCOL;
    @FXML
    private TableColumn<Product, Integer> productPartIDCOL;
    @FXML
    private TableColumn<Product, Double> productPriceCOL;
    @FXML
    private TableColumn<Product, Boolean> containsParts;

    //buttons
    @FXML
    private Button exitBTN;
    @FXML
    private Button partAddBTN;
    @FXML
    private Button partDeleteBTN;
    @FXML
    private Button partModifyBTN;
    @FXML
    private Button productAddBTN;
    @FXML
    private Button productDeleteBTN;
    @FXML
    private Button productModifyBTN;


    //search bars
    @FXML
    private TextField partSRCH;
    @FXML
    private TextField productSRCH;

    // Alerts
    Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Exit? Any changes will be lost", ButtonType.YES, ButtonType.NO);
    Alert missingPartAlert = new Alert(Alert.AlertType.ERROR, "This part is not in our system", ButtonType.OK);
    Alert missingProductAlert = new Alert(Alert.AlertType.ERROR, "This product is not in our system", ButtonType.OK);
    Alert choosePartAlert = new Alert(Alert.AlertType.ERROR, "You need to select a part to modify", ButtonType.OK);
    Alert chooseProductAlert = new Alert(Alert.AlertType.ERROR, "You need to select a product to modify", ButtonType.OK);
    Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?", ButtonType.YES, ButtonType.NO);
    Alert noPartSelected = new Alert(Alert.AlertType.INFORMATION, "You have no selected any parts ", ButtonType.OK);
    Alert deleteProduct = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this product?", ButtonType.YES,ButtonType.NO);
    Alert noProductSelected = new Alert(Alert.AlertType.CONFIRMATION, "No Product Selected",ButtonType.OK);
    Alert partInside = new Alert(Alert.AlertType.ERROR, "That product contains parts and cannot be removed", ButtonType.OK);

    /**
     * This method is used to populate the tables that are found on main-view.fxml
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources){
        //parts
        partsTABLE.setItems(Inventory.getParts());
        partIDCOL.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameCOL.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        PartInventoryCOL.setCellValueFactory(new PropertyValueFactory<Part,Integer>("stock"));
        partPriceCOL.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        //products
        ProductTABLE.setItems(Inventory.getProduct());
        productPartIDCOL.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productNameCOL.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInventoryCOL.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPriceCOL.setCellValueFactory(new PropertyValueFactory<Product,Double>("price"));
        containsParts.setCellValueFactory(new PropertyValueFactory<Product,Boolean>("containsParts"));
    }

    /**
     * This method is used to search for parts.
     * searches by ID or Name.
     * RUNTIME ERROR: I had several issues here trying to get the method to search for both integers and strings
     * Solution was found when changing the order of the method. string first then int.
     * @param event
     */
    public void search_Part(ActionEvent event){
        String value = partSRCH.getText();
        ObservableList result = Inventory.lookupPart(value);
        partsTABLE.setItems(result);
        if(result.isEmpty()){    try {
            int helper = Integer.parseInt(partSRCH.getText());
            Part helper2 = Inventory.lookupPart(helper);
            if (helper2 == null) {
                partsTABLE.setPlaceholder(new Label("Part not Found!"));
                return;
            }
            if (helper2.getId() == helper) {
                ObservableList result2 = Inventory.lookupPart(helper2.getName());
                partsTABLE.setItems(result2);
            }
            else{
                partsTABLE.setPlaceholder(new Label("Part not Found!"));
            }


        } catch (NumberFormatException e) {
            System.out.println("This one was a doozy");
        }
        }
        partsTABLE.setPlaceholder(new Label("Part not Found!"));





    }

    /**
     * This method is used to search for products.
     * Searches ID and Name
     * RUNTIME ERROR: this method was built off of the one above and shared issues.
     * @param event
     */
    public void search_Product(ActionEvent event) {
        String value = productSRCH.getText();
        ObservableList result = Inventory.lookupProduct(value);
        ProductTABLE.setItems(result);
        if(result.isEmpty()){
            try {
            int helper = Integer.parseInt(productSRCH.getText());
            Product helper2 = Inventory.lookupProduct(helper);
            if (helper2 == null) {
                ProductTABLE.setPlaceholder(new Label("Product not Found!"));
            }
                assert helper2 != null;
                if (helper2.getId() == helper) {
                ObservableList result2 = Inventory.lookupProduct(helper2.getName());
                ProductTABLE.setItems(result2);
            }
            else {
                ProductTABLE.setPlaceholder(new Label("Product not Found!"));
            }

        } catch (NumberFormatException e) {
            System.out.println("This one was a doozy");
        }
            catch (NullPointerException e){
                System.out.println("Whoops");
            }
        }

        ProductTABLE.setPlaceholder(new Label("Product not Found!"));

    }

    /**
     * This method closes the application.
     * Before closing the application will make sure that's what you want to do.
     * @param event
     */
    @FXML public void exitButton(ActionEvent event){
        exitAlert.showAndWait();

        if (exitAlert.getResult() == ButtonType.YES) {
            System.exit(0);
        }
        else{
            System.out.println("More east eggs. Do you look at the code?");
            System.out.println("hope your day is going well");
        }

    }

    /**
     * This method will add products to the table.
     * It will bring you to a secondary page to input data and return you to the main page when finished.
     * main page will then display the new information.
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    public void addProduct(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("add-Product.fxml"));
        stage.setTitle("Add Product");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method is used to delete a product from the system.
     * the system will not allow you to delete a product if it has associated parts.
     * @param actionEvent
     */
    @FXML
    public void deleteProduct(ActionEvent actionEvent) {
        Product current = ProductTABLE.getSelectionModel().getSelectedItem();
        try {
            if (current.getContainsParts()) {
                partInside.showAndWait();
                return;
            }
        }
        catch (NullPointerException e){
            System.out.println("Throws error below");
        }
        if (ProductTABLE.getSelectionModel().isEmpty()) {
            noProductSelected.showAndWait();
            return;
        }
        deleteProduct.showAndWait();
        if (deleteProduct.getResult() == ButtonType.YES){
            ProductTABLE.getItems().remove(ProductTABLE.getSelectionModel().getSelectedIndex());
        }

    }

    /**
     * This method allows you to modify existing Products.
     * System will require you to have a product selected.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void modifyProduct(ActionEvent actionEvent) throws IOException {
        Product selected = ProductTABLE.getSelectionModel().getSelectedItem();
        if (selected == null){
            chooseProductAlert.showAndWait();
            return;
        }
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("modify-product.fxml"));
        scene = loader.load();
        modify_product_Controller helper = loader.getController();
        helper.populateLeftPane(selected);
        stage.setTitle("MODIFY PRODUCT");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method will allow you to remove a part from the system.
     * @param actionEvent
     */
    @FXML
    public void deletePart(ActionEvent actionEvent) {

        //edge case placed below in method created logic error. Above rectified issue.
        if(partsTABLE.getSelectionModel().isEmpty()){
            noPartSelected.showAndWait();
            return;
        }
        deleteAlert.showAndWait();
        if(deleteAlert.getResult() == ButtonType.YES){
            partsTABLE.getItems().remove(partsTABLE.getSelectionModel().getSelectedIndex());
        }

    }

    /**
     * This method will bring you to the add parts pane.
     * this will allow you to add parts to the system.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void addPart(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("add-part.fxml"));
        stage.setTitle("ADD PART");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method will bring you to modify parts pane
     * This method will allow you to modify parts within the system.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void modifyPart(ActionEvent actionEvent) throws IOException {

        Part selected = partsTABLE.getSelectionModel().getSelectedItem();
        if (selected == null){
            choosePartAlert.showAndWait();
            return;
        }
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("Modify-part.fxml"));
        scene = loader.load();
        modify_part_controller helper = loader.getController();
        helper.loadData(selected);
        stage.setTitle("MODIFY PART");
        stage.setScene(new Scene(scene));
        stage.show();

    }
}
