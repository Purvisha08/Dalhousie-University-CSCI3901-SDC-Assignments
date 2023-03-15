/**
 *
 * {@code inventoryControl}
 *
 * This is the interface which conatins defination of three methods
 * 1. Ship_Order
 * 2. Issue_reorders
 * 3. Receive_order
 *
 * This interface is implemented by inventorycontrolManagement
 *
 * @author Purvisha Patel (B00912611)
 * Created on 2022-04-06
 * @version 1.0.0
 * @since 1.0,0
 */

public interface inventoryControl {
    public void Ship_order(int orderNumber) throws OrderException;
    public int Issue_reorders(int year, int month, int day);
    public void Receive_order(int internal_order_reference) throws OrderException;
}
