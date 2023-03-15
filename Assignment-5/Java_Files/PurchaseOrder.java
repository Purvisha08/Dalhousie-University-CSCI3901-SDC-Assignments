/**
 * {@code PurchaseOrder}
 *
 * This is the purchase order class that is used to set and get the values
 *
 * @author Purvisha Patel (B00912611)
 * Created on 2022-04-06
 * @version 1.0.0
 * @since 1.0,0
 *
 */


public class PurchaseOrder {

    private int productId; // used to store the product ID
    private double unitPrice; // used to store the unit price
    private String purchaseDate; // used to stpore the purchase
    private int quantity; // Usedto store the quantity
    private int supplierId; // userd to store the supplier id
    private int purchaseId; // used to stire purchase id
    private int shipOrderReference; // used to store the shipOrderReferences


    public int getShipOrderReference(){
        return this.shipOrderReference;
    }

    public void setShipOrderReference(int shipOrderReference){
        this.shipOrderReference = shipOrderReference;
    }

    public int getPurchaseId() {
        return this.purchaseId;
    }


    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }


    public String getPurchaseDate() {
        return this.purchaseDate;
    }


    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }


    public double getUnitPrice() {
        return this.unitPrice;
    }


    public void setUnitPrice(double unitBuyPrice) {
        this.unitPrice = unitPrice;
    }


    public int getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
