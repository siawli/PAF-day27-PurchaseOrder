package PO.day27.purchaseOrder.model;

import java.util.LinkedList;
import java.util.List;

public class PurchaseOrder {
    private String name;
    private String email;
    private List<LineItems> listLineItems = new LinkedList<>();

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<LineItems> getListLineItems() {
        return listLineItems;
    }
    public void setListLineItems(List<LineItems> listLineItems) {
        this.listLineItems = listLineItems;
    }
    
}
