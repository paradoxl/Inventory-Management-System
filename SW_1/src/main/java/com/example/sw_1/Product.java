package com.example.sw_1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is used to create the backbone for products.
 */
public class Product {
    private  int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private Boolean containsParts;
    private ObservableList<Part> addedPart = FXCollections.observableArrayList();
    private static ObservableList<Part> associatedPart = FXCollections.observableArrayList();
    /**
     * Custom constructor to set variables.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param containsParts
     */
    public Product(int id, String name, double price, int stock, int min, int max, Boolean containsParts){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.containsParts = containsParts;
    }

    /**
     * Set Id.
     * @param id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Set Name.
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Set price.
     * @param price
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     * Set Stock.
     * @param stock
     */
    public  void setStock(int stock){
        this.stock = stock;
    }

    /**
     * Set minimum.
     * @param min
     */
    public void setMin(int min){
        this.min = min;
    }

    /**
     * Set Maximum.
     * @param max
     */
    public void setMax(int max){
        this.max = max;
    }

    public void setContainsParts(Boolean containsParts) {
        this.containsParts = containsParts;
    }

    public Boolean getContainsParts() {
        return containsParts;
    }

    /**
     * Return Id.
     * @return
     */
    public int getId(){
        return id;
    }

    /**
     * Return Name.
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * Return Price.
     * @return
     */
    public double getPrice(){
        return price;
    }

    /**
     * Return Stock.
     * @return
     */
    public int getStock(){
        return stock;
    }

    /**
     * Return Minimum.
     * @return
     */
    public int getMin(){
        return min;
    }

    /**
     * Return Maximum.
     * @return
     */
    public int getMax(){
        return max;
    }



    /**
     * This method adds associated parts to the product.
     * @param part
     */
    public static void addAssociatedPart(Part part){

      associatedPart.add(part);
    }

    /**
     * This method removes associated parts to the product.
     * @param partID
     * @return
     */
    public static boolean deleteAssociatedPart(int partID){
        for(int i = 0; i < associatedPart.size(); i++){
            if(associatedPart.get(i).getId() == partID){
                associatedPart.remove(i);
            }
        }
       return true;
    }

    /**
     * This returns all associatedParts.
     * @return
     */
    public static ObservableList<Part> getAllAssociatedParts(){
       return associatedPart;
    }


}
