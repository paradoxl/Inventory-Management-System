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
 * This class is used to connect add-part.fxml to logic.
 */
public class add_part_Controller {
    //Dynamic label for switching inhouse and outsource
    private Parent scene;
    private Stage  stage;
    @FXML
    private Label dynamicInHouseOrOutLABEL;

//     text
    @FXML
    private TextField IDTextFLD;
    @FXML
    private TextField InventoryTextFLD;
    @FXML
    private TextField priceTextFLD;
    @FXML
    private TextField maxTextFLD;
    @FXML
    private TextField dynamicInHouseOrOut;
    @FXML
    private TextField minTextFLD;
    @FXML
    private TextField NameTextFLD;

    // buttons
    @FXML
    private Button saveBTN;
    @FXML
    private Button cancelBTN;
    @FXML
    private RadioButton inHouseRadioBTN;
    @FXML
    private RadioButton outSoruceRadioBTN;

    Alert cancelButtonAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to leave this panel?", ButtonType.YES, ButtonType.NO);
    Alert confirmSave = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to save this part?", ButtonType.YES, ButtonType.NO);
    Alert overAllError = new Alert(Alert.AlertType.ERROR, "It's likely you have placed a word in a placeholder for numbers, or have failed to complete required forms", ButtonType.OK);
    Alert minMaxError = new Alert(Alert.AlertType.ERROR, "Check your min max fields. Those need to correlate with inventory.", ButtonType.OK);

    /**
     * This method is used to send the user back to the main screen
     * @param actionEvent
     * @throws IOException
     */
    public void cancelButton(ActionEvent actionEvent) throws IOException {

        cancelButtonAlert.showAndWait();
        if(cancelButtonAlert.getResult() == ButtonType.YES) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("main-view.fxml"));
            stage.setTitle("MODIFY PART");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        if(cancelButtonAlert.getResult() == ButtonType.NO){
            System.out.println("Easter Egg");
        }
    }

    /**
     * This method will dynamically change a label based off of inhouse/outsourced
     * @throws IOException
     */
    public void dynamicLabelForAdd() throws IOException{
       if(inHouseRadioBTN.isSelected()){
           this.dynamicInHouseOrOutLABEL.setText("Machine ID");
       }
       else{
           this.dynamicInHouseOrOutLABEL.setText("Company Name");
       }
    }

    /**
     * This method will save the new part to the system and display it on the main page.
     * @param event
     */
    public void saveButton(ActionEvent event) {
        try{

            int inv = Integer.parseInt(InventoryTextFLD.getText());
            int minimum = Integer.parseInt(minTextFLD.getText());
            int maximum = Integer.parseInt(maxTextFLD.getText());
            confirmSave.showAndWait();
            if (confirmSave.getResult() == ButtonType.YES){
                if(maximum > minimum){
                   if(inv >= minimum && inv < maximum){

                        String name = NameTextFLD.getText();
                        int stock = inv;
                        int min = minimum;
                        int max = maximum;
                        double price = Double.parseDouble((priceTextFLD.getText()));

                        if (outSoruceRadioBTN.isSelected()){
                            String CompanyName = dynamicInHouseOrOut.getText();
                            Outsourced result = new Outsourced(1000,name,price,stock,min,max,CompanyName);
                            Inventory.addPart(result);

                        }
                        else if (inHouseRadioBTN.isSelected()){
                            int MachineId = Integer.parseInt(dynamicInHouseOrOut.getText());
                            InHouse result = new InHouse(1000,name,price,stock,min,max,MachineId);
                            Inventory.addPart(result);
                        }
                        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                        stage.setTitle("Inventory Management System");
                        stage.setScene(new Scene((Parent) scene));
                        stage.show();
                    }
                    else {
                        minMaxError.showAndWait();
                    }
                }
                else{
                    minMaxError.showAndWait();
                }
            }
        } catch (NumberFormatException e) {
            overAllError.showAndWait();


        } catch (IOException e) {

        }
    }


}
