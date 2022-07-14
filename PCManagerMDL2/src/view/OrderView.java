package view;

import Menu.Menu;
import model.Order;
import model.OrderItem;
import model.Product;
import model.User;
import services.OrderItemServices;
import services.OrderServices;
import services.ProductServices;
import services.UserServices;

import java.util.Scanner;

public class OrderView {
    private static final UserView userview = new UserView();
    private static final ProductServices productservices = new ProductServices();
    private static final OrderServices orderservices = new OrderServices();
    private static final UserServices userservices = new UserServices();
    private static final OrderItemServices orderitemservices = new OrderItemServices();
    public static Scanner scanner = new Scanner(System.in);

    public static void orderview() {
        boolean end = true;
        do {
            Menu.ordermenu();
            OrderView orderView = new OrderView();
            System.out.print("Enter your choose: ");
            int choose = Integer.parseInt(scanner.nextLine());
            switch (choose) {
                case 1:
                    orderView.addorder();
                    break;
                case 2:
                    UserView.userview();
                    break;
                default:
                    System.out.println("Choose again");
            }
        } while (end);
    }

    private static void changelistafterorder(String name) {
        productservices.update();
        for (OrderItem orderitem : OrderItemServices.listOrderItem) {
            if (orderitem.getName().equals(name)) {
                OrderItemServices.addOrderPaid(orderitem);
            }

        }
    }

    public void addorder() {
        AdminView adminView = new AdminView();
        adminView.productlist();
//        userview.productlist();
        System.out.println("Enter ID want to order(Enter 0 to back Ordermenu) ");
        long id = Long.parseLong(scanner.nextLine());
        while (!productservices.existbyId(id)) {
            if (id == 0) {
                orderview();
            }
            System.out.println("ID does not exist! Please re-enter");
            id = Long.parseLong(scanner.nextLine());
        }
        Product product = productservices.getbyId(id);
        User user = userservices.getUserById(Menu.userId);
        System.out.println("Enter quantity want to order(Enter 0 to back Ordermenu) ");
        int amount = Integer.parseInt(scanner.nextLine());
        while (amount <= 0 || amount > product.getQuantity()) {
            if (amount == 0) {
                orderview();
            }
            if (amount < 0) {
                System.out.println("Invalid Quantity!Please re-enter");
            }
            if (amount > product.getQuantity()) {
                System.out.println("The quantity you bought exceeds the amount of products left in the store! Right now we only have" + product.getQuantity());
                System.out.println("Please re-enter the appropriate quantity");
            }
            amount = Integer.parseInt(scanner.nextLine());
        }
        OrderItem orderItem = new OrderItem(System.currentTimeMillis() / 1000, user.getName(), product.getPrice(), amount, System.currentTimeMillis() / 100, product.getId(), product.getName(), product.getPrice() * amount, OrderServices.formatDatetime(System.currentTimeMillis()));
        orderitemservices.add(orderItem);
        if (!OrderServices.checkExistNameOfOrder(user.getName())) {
            Order order = new Order(System.currentTimeMillis() / 100, user.getName(), user.getPhone(), user.getAddress());
            orderservices.add(order);
        }
        orderitemservices.update();
        orderservices.update();
        product.setQuantity(product.getQuantity() - amount);
        OrderItemServices.orderlist();
    }

    public static void showbill(int total) {
        boolean end=true;
        do {
            try {
                System.out.println("Are you sure you want to pay this bill??");
                System.out.println("( y - YES, n - NO)");
                String choice = scanner.nextLine().toLowerCase();
                User user = userservices.getUserById(Menu.userId);
                switch (choice) {
                    case "y":
                        System.out.println("You have successfully paid the bill!:" + ProductServices.addCharacters(total) + "!");
                        changelistafterorder(user.getName());
                        end=false;
                        break;
                    case "n":
                        orderitemservices.resetListOrderItem(user.getName());
                        System.out.println("Order has been successfully canceled!");
                        orderview();
                        end=false;
                        break;
                    default:
                        System.out.println("Choose again!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("INVALID!");
            }
        } while (end);
    }
}
