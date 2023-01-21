package com.example.sw_1;

/**
 * This class creates backbone for all parts.
 */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Default constructor.
     */
    public  Part(){

    }

    /**
     * Custom constructor to set given variables.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Part(int id, String name,double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /**
     * Set ID.
     * @param id
     */
    public void setID(int id){
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
    public void setStock(int stock){
        this.stock = stock;
    }

    /**
     * Set Minimum
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
     * Return ID.
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
     * Return price.
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
     * Return Max.
     * @return
     */
    public int getMax(){
        return max;
    }
}
