package Menu;

import model.User;
import services.OrderItemServices;
import services.OrderServices;
import services.ProductServices;
import services.UserServices;
import view.AdminView;
import view.SignIn;
import view.UserView;

import java.util.Scanner;

public class Menu {
    private static final UserServices userService = new UserServices();
    private static final ProductServices productService = new ProductServices();
    private static final OrderItemServices orderItemService = new OrderItemServices();
    private static final OrderServices orderService = new OrderServices();
    public static Scanner scanner = new Scanner(System.in);
    public static long userId;

    public static void mainmenu() {
        boolean end = true;
        do {
            System.out.println("#########Login#########");
            System.out.println("#                     #");
            System.out.println("#   1.Login by Admin  #");
            System.out.println("#   2.Login by User   #");
            System.out.println("#   3.Sign in         #");
            System.out.println("#   4.Exit            #");
            System.out.println("#                     #");
            System.out.println("#######################");
            System.out.print("Enter your choose: ");
            try {
                int choose=  Integer.parseInt(scanner.nextLine());
                switch (choose) {
                    case 1:
                        Menu.adminlogin();
                        break;
                    case 2:
                        Menu.userlogin();
                        break;
                    case 3:
                        SignIn.addUser();
                        end = false;
                        break;
                    case 4:
                        System.exit(0);
                    default:
                        System.out.println("Choose again");
                }
            }catch (Exception e){
                System.out.println("Choose again");
            }

        } while (end);
    }
    public static void adminmenu() {
        System.out.println("#########Admin#########");
        System.out.println("#                     #");
        System.out.println("#   1.Product List    #");
        System.out.println("#   2.Add Product     #");
        System.out.println("#   3.Edit Product    #");
        System.out.println("#   4.Delete Product  #");
        System.out.println("#   5.User List       #");
        System.out.println("#   6.Order List      #");
        System.out.println("#   7.Sign out        #");
        System.out.println("#                     #");
        System.out.println("#######################");
    }

    public static void usermenu() {
        System.out.println("#########User##########");
        System.out.println("#                     #");
        System.out.println("#   1.Product List    #");
        System.out.println("#   2.Search product  #");
        System.out.println("#   3.Order Product   #");
        System.out.println("#   4.Sign out        #");
        System.out.println("#                     #");
        System.out.println("#######################");
    }

    public static void ordermenu() {
        System.out.println("#########Order#########");
        System.out.println("#                     #");
        System.out.println("#   1.Add order       #");
        System.out.println("#   2.Exit            #");
        System.out.println("#                     #");
        System.out.println("#######################");
    }

    public static void searchmenu() {
        System.out.println("#########Order#########");
        System.out.println("#                     #");
        System.out.println("#   1.By name         #");
        System.out.println("#   2.By price        #");
        System.out.println("#   3.Exit            #");
        System.out.println("#                     #");
        System.out.println("#######################");
    }
    public static void adminlogin() {
        boolean end = true;
        orderService.getOrders();
        orderItemService.getOrderItems();
        orderItemService.getOrdersPaid();
        userService.getuser();
        productService.getProduct();
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            if (username.equals("admin") && password.equals("admin")) {
                System.out.println("Login successful");
                System.out.println("Hello Master");
                end = false;
                AdminView.adminview();
            } else if (username == null || password == null || username != "admin" || password != "admin") {
                System.out.println("Login unsuccessful");
            }
        } while (end);
    }
    public static void userlogin() {
        boolean end = true;
        orderService.getOrders();
        orderItemService.getOrderItems();
        orderItemService.getOrdersPaid();
        userService.getuser();
        productService.getProduct();
        UserServices userServices = new UserServices();
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            if (userServices.loginbyuser(username, password) != null) {
                User user = userServices.getUserByUserName(username);
                userId = user.getId();
                System.out.println("Login successful");
                System.out.println("Hello User");
                UserView.userview();
                end = false;
            } else {
                System.out.println("Login unsuccessful");
            }
        } while (end);
    }
}
