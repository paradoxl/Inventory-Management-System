package com.example.sw_1;


/**
 * This class creates backbone for outsourced information.
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Custom constructor to intake variables.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        setID(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setCompanyName(companyName);
    }

    /**
     * Set Company name.
     * @param companyName
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
     * Get companyName;
     * @return
     */
    public String getCompanyName(){
        return companyName;
    }

}
