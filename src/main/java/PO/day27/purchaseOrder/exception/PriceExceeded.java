package PO.day27.purchaseOrder.exception;

public class PriceExceeded extends Exception{

    public PriceExceeded() {
        super();
    }

    public PriceExceeded(String message) {
        super(message);
    }
    
}
