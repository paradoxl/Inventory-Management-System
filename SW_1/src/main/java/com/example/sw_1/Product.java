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

//    private ObservableList<Part> addedPart = FXCollections.observableArrayList();
    private ObservableList<Part> associatedPart = FXCollections.observableArrayList();
    /**
     * Custom constructor to set variables.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }

    /**
     * custom empty constructor
     */
    public Product(){

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
    public void addAssociatedPart(Part part){

      associatedPart.add(part);
    }

    /**
     * This method removes associated parts to the product.
     * @param part
     * @return
     */
    public  boolean deleteAssociatedPart(Part part){
    associatedPart.remove(part);
       return true;
    }

    /**
     * This returns all associatedParts.
     * @return
     */
    public  ObservableList<Part> getAllAssociatedParts(){
       return associatedPart;
    }


}
