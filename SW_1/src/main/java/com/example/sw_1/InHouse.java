package com.example.sw_1;

/**
 * This class creates a space for InHouse data.
 */
public class InHouse extends Part {

    private int machineId;

    /**
     * custom constuctor to allocate variables.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        setID(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setMachineId(machineId);
        this.machineId = machineId;
    }

    /**
     * this method sets the machine id
     * @param machineId
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**
     * This method returns the machine id.
     * @return
     */
    public  int getMachineId(){
        return machineId;
    }
}
