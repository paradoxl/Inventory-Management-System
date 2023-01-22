package com.example.sw_1;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This Class is used to add logic to the modify-part.fxml file.
 */
public class modify_part_controller {

    Parent scene;
    public Part current;
    private int id;

    @FXML
    public Label dynamiclabel;
    @FXML
    private Button cancelButton;
    @FXML
    private RadioButton inHouseRadioBTN;
    @FXML
    private RadioButton outSoruceRadioBTN;
    @FXML
    private Label dynamicInHouseOrOutLABEL;
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
    private TextField dynamicTextFLD;
    @FXML
    private TextField minTextFLD;

    // Alerts to be used for edge cases and menu movement.
    Alert cancelButtonAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to leave this panel?", ButtonType.YES, ButtonType.NO);
    Alert saveConfirm = new Alert(Alert.AlertType.CONFIRMATION, "Continue with save?",ButtonType.YES, ButtonType.NO);
    Alert numberError = new Alert(Alert.AlertType.ERROR, "Plese check Machine ID only numbers allowed.", ButtonType.OK);
    Alert idError = new Alert(Alert.AlertType.ERROR, "You must select a valid ID number",ButtonType.OK);
    Alert minMaxError = new Alert(Alert.AlertType.ERROR, "Check your min max fields. Those need to correlate with inventory.", ButtonType.OK);
    Alert Word = new Alert(Alert.AlertType.ERROR,"It looks like you've placed a word in a number value. Or have failed to complete required forms.");

    Parent Stage;

    // this method will change the way the dynamic label is displayed.

    /**
     * This method is used to dynamically labels.
     * Machine ID and Company Name need to be changed based off of radio buttons.
     * This method achieves this.
     *RUNTIME ERROR: Silly mistakes made in learning scenebuilder.
     * @throws IOException
     */
    public void dynamiclabel() throws IOException{
        if(inHouseRadioBTN.isSelected()){
            this.dynamiclabel.setText("Machine ID");
        }
        else{
            this.dynamiclabel.setText("Company Name");
        }
    }
    // simple method that will display confirmation when cancel is pressed. Upon confirmation user will be relocated to
    // main-view.fxml Future additions: show modifications in progress vs removing all changes.

    /**
     * This method is used to add functionality to the cacnel button.
     * Upon confirmation user will be relocated to main-view.fxml
     * FUTURE ENHANCEMENT: show modifications in progress vs removing all changes.
     * @param actionEvent
     * @throws IOException
     */
    public void cancelButton(ActionEvent actionEvent) throws IOException {
        cancelButtonAlert.showAndWait();
        if(cancelButtonAlert.getResult() == ButtonType.YES) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("main-view.fxml"));
            stage.setTitle("main-view.fxml");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        if(cancelButtonAlert.getResult() == ButtonType.NO){
            System.out.println("Easter Egg");
        }
    }

    /**
     * This method pulls data from the selected element in the main-view.fxml table.
     * @param current
     */
    public void loadData(Part current){
        //Pulls values from the part that was selected on main-view.fxml
        this.current = current;
        id = current.getId();
        String idButString = String.valueOf(id);
        idTextFLD.setText(idButString);
        nameTextFLD.setText(current.getName());
        String inventory = String.valueOf(current.getStock());
        invTextFLD.setText(inventory);
        String price = String.valueOf(current.getPrice());
        priceTextFLD.setText(price);
        String min = String.valueOf(current.getMin());
        minTextFLD.setText(min);
        String max = String.valueOf(current.getMax());
        maxTextFLD.setText(max);
       // determines if the dynamically allocated button is selected to inhouse or outsourced. Will then change the text field accordingly.
        if(current instanceof InHouse){
            InHouse dynamIH = (InHouse) current;
            inHouseRadioBTN.setSelected(true);
            this.dynamiclabel.setText("Machine Id");
            dynamicTextFLD.setText(Integer.toString(dynamIH.getMachineId()));
        }
        // same as above but represents outsourced option.
        else {
            Outsourced dynamOS = (Outsourced) current;
            outSoruceRadioBTN.setSelected(true);
            this.dynamiclabel.setText("Comapny Name");
            dynamicTextFLD.setText(dynamOS.getCompanyName());
        }
    }

    /**
     * This method is used to save changes to a part.
     * It checks for min max and inventory mismatch and applies proper inhouse/outsourced requirements.
     * @param actionEvent
     * @throws IOException
     */
    public void saveChanges(ActionEvent actionEvent) throws IOException {
        // initial values that are required to check for min max and inventory edge cases.
        try {
            int inv = Integer.parseInt(invTextFLD.getText());
            int max = Integer.parseInt(maxTextFLD.getText());
            int min = Integer.parseInt(minTextFLD.getText());


            if (max >= min) {
                if (inv >= min || inv <= max) {
                    int id = Integer.parseInt(idTextFLD.getText());
                    String name = nameTextFLD.getText();
                    Double price = Double.parseDouble(priceTextFLD.getText());
                    int stock = Integer.parseInt(invTextFLD.getText());


                    javafx.stage.Stage stage;
                    if (inHouseRadioBTN.isSelected()) {
                        System.out.println("Are we making it here?");
                        try {
                            saveConfirm.showAndWait();
                            if (saveConfirm.getResult() == ButtonType.YES) ;
                            int macID = Integer.parseInt(dynamicTextFLD.getText());

                            InHouse newPart = new InHouse(id, name, price, stock, min, max, macID);
                            Inventory.updatePart(id, newPart);

                            System.out.println(Inventory.getParts());
                            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                            scene = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                            stage.setTitle("Inventory Management System");
                            stage.setScene(new Scene(scene));
                            stage.show();


                        } catch (NumberFormatException e) {
                            // catches the edge case that someone has applied numbers to the TextField of MACHINE ID
                            // when it should have been only numbers.
                            numberError.showAndWait();
                            System.out.println("Error thrown");
                        }
                    } else if (outSoruceRadioBTN.isSelected()) {
                        saveConfirm.showAndWait();
                        if (saveConfirm.getResult() == ButtonType.YES) {
                            String comName = dynamicTextFLD.getText();
                            // Allocate changes to the part in the observable list
                            Outsourced newPart = new Outsourced(id, name, price, stock, min, max, comName);
                            Inventory.updatePart(id, newPart);
                            // return the user to the main-view.fxml
                            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                            scene = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                            stage.setTitle("Inventory Management System");
                            stage.setScene(new Scene(scene));
                            stage.show();
                        }
                    }

                } else {
                    minMaxError.showAndWait();
                }

            } else {
                minMaxError.showAndWait();
            }
        }
        catch (NumberFormatException e){
            Word.showAndWait();
        }

    }
}
