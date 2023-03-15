/**
 *
 * {@code Main}
 *
 * This is the main class of the program. We can also call it as driver class of the Program
 *
 * @author Purvisha Patel (B00912611)
 * Created on 2022-04-06
 * @version 1.0.0
 * @since 1.0,0
 */

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws OrderException, SQLException {

    String shipOrder = "shipOrder";
    String issueReorder = "issueReorder";
    String receiveOrder = "receiveOrder";
    String quit = "quit";

    String userCommand = "";
    int userArgument = 0;
    Scanner userInput = new Scanner( System.in );

    inventoryControlManagement inventoryControlManagement = new inventoryControlManagement();

    System.out.println("Following are the Operations available to perform: ");
    System.out.println(shipOrder+" <order_number>");
    System.out.println(issueReorder+" <Year> <Month> <Day>");
    System.out.println(receiveOrder+" <internal_order_reference>");
    System.out.println(quit);

        do {
            userCommand = userInput.next();

            //To call ship order method
            if (userCommand.equalsIgnoreCase(shipOrder)) {
                int orderNumber = userInput.nextInt();
                inventoryControlManagement.Ship_order(orderNumber);
            }
            //to call issue reorder method
            else if (userCommand.equalsIgnoreCase(issueReorder)) {
                int year = userInput.nextInt();
                int month = userInput.nextInt();
                int day = userInput.nextInt();

                System.out.println("idejmn--"+inventoryControlManagement.Issue_reorders(year, month, day));
            }
            //to call receive order method
            else if(userCommand.equalsIgnoreCase(receiveOrder)){
                int purchaseId = userInput.nextInt();
                inventoryControlManagement.Receive_order(purchaseId);
            }
            //for invalid commands
            else {
                System.out.println ("Bad command: " + userCommand);
            }
        } while (!userCommand.equalsIgnoreCase(quit));
    }
}

