/**
 * {@code inventoryControlManagement}
 *
 * This is the main class which connectes the main class with rest of the program
 *
 * @author Purvisha Patel (B00912611)
 * Created on 2022-04-06
 * @version 1.0.0
 * @since 1.0,0
 *
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

public class inventoryControlManagement implements inventoryControl {

    private Connection conn; // it is the database connection object

    public inventoryControlManagement() {
        DbConnection dbConnection = new DbConnection();

        //Creatring the connection with the database
        conn = dbConnection.createDbConnection();
    }


    /**
     * {@code Ship_order} It the Ship_order method
     *
     * @param orderNumber takes order number as parameter
     *
     * @throws OrderException when there is anyerror
     *
     */
    @Override
    public void Ship_order(int orderNumber) throws OrderException {
        int x; // A temp variable
        ResultSet rs = null; // result set to store data
        PreparedStatement ps1;
        PreparedStatement ps2;
        PreparedStatement ps3;

        // Query to update shipped date
        String updateShippedDate = "update orders set ShippedDate = SYSDATE() where ShippedDate is null and OrderID = " + orderNumber;

        // query for inventory checeking
        String invetoryChecking = "select p.unitsinstock, od.quantity, p.productname, p.productid, p.supplierid from products p, orderdetails od where p.productid = od.productid and od.orderid = " + orderNumber;

        // Date pattern
        String datePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);

        // Query to update Invenotry
        String updateInventory = "update products p, orderdetails od set p.unitsinstock = p.unitsinstock-od.quantity "
                .concat(" where p.productid = od.productid and orderid = '")
                .concat(String.valueOf(orderNumber)).concat("' ; ");

        try {
            ps1 = conn.prepareStatement(updateShippedDate);
            x = ps1.executeUpdate();

            // One recoded must be updated else an exception is thrown
            if (x != 1) {
                throw new OrderException("Ship order failed. Order Number " + orderNumber + " is already shipped " +
                        "or order number is is not availabe in the database", orderNumber);
            }

            ps2 = conn.prepareStatement(invetoryChecking);
            rs = ps2.executeQuery();

            while (rs.next()) {
                int updatedStockAfterShipping = rs.getInt("quantity");

                // We must hve enough invenotory to shi[
                if (updatedStockAfterShipping < 0) {
                    PurchaseOrder pOrder = new PurchaseOrder();
                    pOrder.setProductId(rs.getInt("productid"));
                    pOrder.setQuantity(updatedStockAfterShipping);
                    pOrder.setSupplierId(rs.getInt("supplierid"));
                    pOrder.setShipOrderReference(orderNumber);
                    String date = simpleDateFormat.format(new Date());
                    x = reorder(date, pOrder);
                }


                if (x > 0) {
                    // Reorder have been placed as stock is not availabe
                    throw new OrderException("Does not have enough stock of the product \"" + rs.getString("productname") + "\" to ship."
                            + "Hence Reorder of the product have been placed with the current date that supplier", orderNumber);
                } else {

                    //Wating to receive order as Stock is not availabe
                    throw new OrderException("Does not have enough stock of the product \"" + rs.getString("productname") + "\"Hence shipping failed", orderNumber);
                }

            }

            ps3 = conn.prepareStatement(updateInventory);
            // Updated the inventory after shipping
            x = ps3.executeUpdate();

            if (x < 1) {
                // Produsct does not exists
                throw new OrderException("Ordered products does not exists in the system", orderNumber);
            }
        } catch (Exception e) {
            // Throws custom exception
            throw new OrderException("Exception ", orderNumber);
        }

        // The order is shippped successfully
        System.out.println("Order shipped successfully");
    }


    /**
     * {@code validateDate} It validates the entered dates's Year, Month and Date
     *
     * @param year takes year as input
     * @param month takes month as input
     * @param day tales day as input
     *
     * @return boolean values as if the date is valid else retrun false
     *
     */
    public boolean validateDate(int year, int month, int day) {

        // Find current date details
        Calendar current = Calendar.getInstance();
        int presentYear = current.get(Calendar.YEAR);
        int presentMonth = 1 + current.get(Calendar.MONTH);
        int presentDate = current.get(Calendar.DATE);

        // check if entered year is greater then present year
        if (year > presentDate) {
            System.out.println("Invalid Date");
            return false;
        }

        // check if the entered year if negative or not
        if (year < 1) {
            System.out.println("Invalid Date");
            return false;
        }

        // Checek if the entered month is negative or not
        if (month < 1) {
            System.out.println("Invalid Month");
            return false;
        }

        // checek if the enetered month is greater then present month
        if (month > 12) {
            System.out.println("Invalid Month");
            return false;
        }

        // check if the entered day is negative or not
        if (day < 1) {
            System.out.println("Invalid Date");
            return false;
        }


        // For the following given months day can 30 only
        if ((month == 11 || month == 6 || month == 9 || month == 4 || month == 2) && day > 30) {
            System.out.println("Invalid day");
            return false;
        }


        // chcek if the date is greater then current date or not
        if (year == presentYear && month == presentMonth && day > presentDate) {
            System.out.println("Invalid Date");
            return false;
        }

        // Returns True if the date is valid
        return true;
    }


    /**
     * {@code Issue_reorders} Used to place the reorder
     *
     * @param year takes year as input
     * @param month takes month as input
     * @param day tales day as input
     *
     * @return the total number of supplier
     *
     */
    @Override
    public int Issue_reorders(int year, int month, int day) {

        boolean validateDate = validateDate(year, month, day);// Checks if the date is valid or not

        // Retruns 0 if the date is not valid
        if (!validateDate){
            return 0;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Converting the input into a date format
        Date createReorderDate = new Date((year-1900), (month-1), day);

        int totalSuppliers = 0;

        try {
            String date = simpleDateFormat.format(createReorderDate);
            // Calling the reorder method to get the total number of suppiler
            totalSuppliers = reorder(date, null);
        } catch (Exception e){

            // IF there is any error then exception is thrown
            System.out.println(e.getMessage());
        }

        // Retrun the total number of suppilers
        return totalSuppliers;
    }

    /**
     * {@code Receive_order} This method used to receive the order
     *
     * @param internal_order_reference takes purchaseId as input
     *
     */
    @Override
    public void Receive_order(int internal_order_reference) throws OrderException {

        int x = 0;// temporary variable
        String updateReeceiveDeatilsInPurchase = "update purchases set receivedate = sysdate() where receivedate is null and purchaseid = " + internal_order_reference;
        String updateReceiveOrderInventory = "update purchases p, products pd set pd.unitsonorder = pd.unitsonorder - p.quantity, \n" +
                "pd.unitsinstock = pd.unitsinstock + p.quantity where p.productid = pd.productid and p.supplierid = pd.supplierid\n" +
                "and p.purchaseid = "+internal_order_reference;

        try {

            PreparedStatement ps1 = conn.prepareStatement(updateReeceiveDeatilsInPurchase);
            PreparedStatement ps2 = conn.prepareStatement(updateReceiveOrderInventory);

            // Updatint the date
            x = ps1.executeUpdate();

            /// throws exception if there is any error in update
            if(x < 1){
                throw new OrderException("Order reference is invalied or Purchase order has been alreday received", internal_order_reference);
            }

            // Update the inventory
            x = ps2.executeUpdate();

            // Throws exception if there is eerror in the update
            if(x < 1){
                throw new OrderException("Faced Exception while when receive order inventory is updating ", internal_order_reference);
            }

        } catch (OrderException e){

            // Throws exception if there is any error
            throw e;
        } catch (Exception e) {

            // Throws exception is there is any error
            throw new OrderException("Unexpected Exception ", internal_order_reference);
        }

        // Order received sucessfully
        System.out.println("Order is received successfully");
    }



    /**
     * {@code Issue_reorders} Used to place the reorder
     *
     * @param date
     * @param purchaseOrder
     *
     * @return the total number of supplier
     *
     */
    public int reorder(String date, PurchaseOrder purchaseOrder) throws Exception {

        int totalSuppliers = 0; // Store the total number of suppiler
        ResultSet resultSet = null; // Result set to store data
        int x = 0; // Temporary Variable

        // Query to know the reorder status
        String reorderStatus = "select purchaseId from purchases where date(purchasedate) = date('"+date+"');";

        // Query tp get the reorder details
        String getReorderDetailsquery = "select p.productid, p.supplierid, (p.unitprice * 0.85) as perUnitPrice, p.productname, case when p.reorderlevel = '0' then '5' else p.reorderlevel end as quantity from product p where p.discontinued = 0 \n" +
                "and (p.unitsinstock + p.unitsonorder <= p.reorderlevel and p.reorderlevel > 0) or (p.unitsinstock + p.unitsonorder <= 5 and p.reorderlevel = 0);";

        // Query tp get the reorder details
        String getReorderDetailsquery1 = "select p.productid, p.supplierid, (prod.unitprice * 0.85) as unitBuyPrice, prod.productname, case when p.reorderlevel = '0' then '5' else p.reorderlevel end as quantity from products p where p.discontinued = 0 and p.SupplierID = "+purchaseOrder.getSupplierId()+
                "and (p.unitsinstock + p.unitsonorder <= p.reorderlevel) and p.reorderlevel > 0 or p.unitsonorder = 0 and p.productid = "+purchaseOrder.getProductId();

        // Query to update the reorder Inventory
        String updateReorderInventory = "update purchases p, products pd set pd.unitsonorder = pd.unitsonorder + p.quantity where p.productid \n" +
                "= pd.productid and p.supplierid = pd.supplierid and p.receivedate is null and date(p.purchasedate) = \n" +
                "date("+date+");";

        String insertPurOrder = "insert into purchases (purchaseid, productid, unitprice, supplierid, purchasedate, quantity) values ";


        List<PurchaseOrder> purchaseOrders = new ArrayList<>();
        List<Integer> purchaseIds = new ArrayList<>();
        Set<Integer> suppliers = new HashSet<>();

        PreparedStatement ps1 = conn.prepareStatement(reorderStatus);
        PreparedStatement ps2 = conn.prepareStatement(getReorderDetailsquery);
        PreparedStatement ps3 = conn.prepareStatement(getReorderDetailsquery1);
        PreparedStatement ps4 = conn.prepareStatement(insertPurOrder);
        PreparedStatement ps5 = conn.prepareStatement(updateReorderInventory);;

        try {
            resultSet = ps1.executeQuery();
            while (resultSet.next()){
                x = resultSet.getInt("purchaseId");
                if(x > 0){
                    purchaseIds.add(x);
                }
            }

            if(purchaseOrder == null) {
                resultSet =ps2.executeQuery();
            } else {
                resultSet =ps3.executeQuery();
            }

            while (resultSet.next()){
                PurchaseOrder pOrder = new PurchaseOrder();
                pOrder.setProductId(resultSet.getInt("productid"));
                pOrder.setSupplierId(resultSet.getInt("supplierid"));
                pOrder.setUnitPrice(resultSet.getDouble("unitBuyPrice"));
                pOrder.setPurchaseDate(date);

                int purchaseId = Integer.parseInt(purchaseOrder.getPurchaseDate().substring(0, 4)
                        + purchaseOrder.getPurchaseDate().substring(5, 7)
                        + purchaseOrder.getPurchaseDate().substring(8, 10)
                        + purchaseOrder.getSupplierId());

                // Throw exception if purchase ID is 0
                if(purchaseId == 0){
                    throw new Exception("Unexpected exception");
                }

                // Placed reorder even the stock is less then reorder level
                else if(purchaseIds.contains(purchaseId) && purchaseOrder == null){
                    System.out.println("The below shown product is out of stock. However reorder of the below product is already placed");
                    System.out.println("Supplier ID: "+pOrder.getSupplierId());
                    System.out.println("Product Name: "+resultSet.getString("productname"));
                    System.out.println("User will receive the order if the order is not received. User can also manually place the reorder for the differeent date.");
                    continue;
                }

                // Waiting to receive the reorder
                else if(purchaseIds.contains(purchaseId) && purchaseOrder != null){
                    throw new OrderException("Does not have stoek to ship the product "+resultSet.getString("productname")+". Shipping Failed. Reorder not possible as waiting for the product from suppiler", purchaseOrder.getShipOrderReference());
                }
                pOrder.setPurchaseId(purchaseId);
                pOrder.setQuantity(resultSet.getInt("quantity"));
                suppliers.add(pOrder.getSupplierId());
                purchaseOrders.add(pOrder);
            }

            // Throw exception if purchase order is empty or null
            if((purchaseOrders == null || purchaseOrders.isEmpty()) && purchaseOrder == null){
                throw new OrderException("Reorder failed as no product to reorder");
            }

            ps4 = conn.prepareStatement(insertPurOrder);
            x = ps4.executeUpdate();

            //if the insert query fails
            if(x < 1){
                throw new OrderException("No products to reorder. Reorder failed.");
            }
            x = ps5.executeUpdate();

            // If the update query fails
            if(x < 1){
                throw new OrderException("Reorder inventory update failed.");
            }
            totalSuppliers = suppliers.size();

        } catch (OrderException e){
            // throws exception if there is any error
            throw e;
        } catch (Exception e){

            // Throws exception if there is any error
            throw new OrderException("Unexpected Exception ");
        }
        System.out.println(totalSuppliers);

        // Retrun the total suppiler
        return totalSuppliers;
    }
}
