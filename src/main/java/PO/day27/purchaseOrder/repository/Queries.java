package PO.day27.purchaseOrder.repository;

public interface Queries {

    public static final String SQL_INSERT_PO = 
        "insert into purchase_order (name, email) values (?, ?)";

    public static final String SQL_INSERT_LINE_ITEMS = 
        "insert into line_items (description, quantity, unit_price, order_id) values (?, ?, ?, ?)";

    public static final String SQL_GET_ORDER_ID = 
        "select order_id from purchase_order where name = ?";
    
}
