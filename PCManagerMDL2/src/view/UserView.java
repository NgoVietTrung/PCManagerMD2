package view;

import Menu.Menu;
import services.ProductServices;

import java.util.Scanner;

public class UserView {
    private static final ProductServices productservice = new ProductServices();
    public static Scanner scanner = new Scanner(System.in);

    public static void userview() {
        boolean end = true;
        do {
            UserView userView = new UserView();
            Menu.usermenu();
            System.out.println("Enter your choose: ");
            int choose =Integer.parseInt(scanner.nextLine()) ;
            switch (choose) {
                case 1:
                    userView.productlist();
                    break;
                case 2:
                    //userView.search();
                    break;
                case 3:
                    OrderView.orderview();
                    break;
                case 4:
                    end = false;
                    Menu.mainmenu();
                    break;
                default:
                    System.out.println("Choose again");
            }

        } while (end);
    }
    public void productlist() {
        try {
            productservice.productsList();
        } catch (Exception e) {
            System.out.println("Choose again");
        }
    }
}
