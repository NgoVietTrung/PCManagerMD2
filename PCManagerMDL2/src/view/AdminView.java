package view;

import Menu.Menu;
import model.Product;
import services.OrderServices;
import services.ProductServices;
import services.UserServices;

import java.util.Scanner;

public class AdminView {
    private static final ProductServices productservice = new ProductServices();
    public static Scanner scanner = new Scanner(System.in);

    public static void adminview() {

        boolean end = true;
        do {
            AdminView managerAdminView = new AdminView();
//            Menu.adminmenu();
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
            System.out.println("Enter your choose: ");
            int choose =Integer.parseInt(scanner.nextLine()) ;
            switch (choose) {
                case 1:
                    managerAdminView.productlist();
                    break;
                case 2:
                    managerAdminView.addproduct();
                    break;
                case 3:
                    managerAdminView.editproduct();
                    break;
                case 4:
                    managerAdminView.deleteproduct();
                    break;
                case 5:
                    managerAdminView.userlist();
                    break;
                case 6:
                    managerAdminView.orderlist();
                    break;
                case 7:
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
    public void userlist() {
        try {
            UserServices.userlist();
        } catch (Exception e) {
            System.out.println("Choose again");
        }
    }
    public void orderlist() {
        try {
            OrderServices.showListOrder();
        } catch (Exception e) {
            System.out.println("Choose again");
        }
    }
    public void addproduct() {
        String productname;
        int quantity = 0;
        long price = 0;
        do {
            System.out.println("Enter Product name:");
            productname = scanner.nextLine();

            while (productservice.existByName(productname.toLowerCase())) {
                if (productservice.existByName(productname.toLowerCase())) {
                    System.out.println("Name already exists! Enter another name: ");
                }
                productname = scanner.nextLine();
            }
        } while (productservice.existByName(productname));
        do {
            try {
                System.out.println("Enter price: ");
                price = Long.parseLong(scanner.nextLine());
                while (price <= 0) {
                    System.out.println("Invalid Price! Please Re-enter : ");
                    price = Long.parseLong(scanner.nextLine());
                }
            } catch (Exception e) {
                System.out.println("Giá không hợp lý! Vui lòng nhập lại giá!");
            }
        } while (price <= 0);

        do {
            try {
                System.out.println("Enter quantity: ");
                quantity = Integer.parseInt(scanner.nextLine());
                while (quantity < 0) {
                    System.out.println("Invalide Price!Please Re-enter: ");
                    quantity = Integer.parseInt(scanner.nextLine());
                }
                Product product = new Product(productname, quantity, price);
                productservice.add(product);
                System.out.println("Add product successful!");
                productlist();

            } catch (Exception e) {
                System.out.println("Invalid quantity! Please re-enter");
            }
        } while (quantity < 0);

    }
    public void editproduct() {
        try {
            productlist();
            int id = 0;
            do {
                try {
                    System.out.println("Enter ProductID(Enter 0 to go back Adminmenu) ");
                    id = Integer.parseInt(scanner.nextLine());
                    while (!productservice.existbyId(id)) {
                        if (id == 0) {
                            adminview();
                        } else {
                            System.out.println("Id does not exist! Please Re-enter");
                        }
                        id = Integer.parseInt(scanner.nextLine());
                    }
                } catch (Exception e) {
                    System.out.println("Invalid ID");
                }
            } while (!productservice.existbyId(id));
            Product product = productservice.getbyId(id);
            String name = checkNameProduct(product);
            long price = checkPriceProduct(product);
            int quantity = checkQuantity(product);
            if (name.equals("0") || Long.toString(price).equals("0") || Integer.toString(quantity).equals("0")) {
                System.out.println("Product is unchanged");
            } else {
                product.setName(name);
                product.setPrice(price);
                product.setQuantity(quantity);
                System.out.println("Edit product successful!");
                productlist();
                productservice.update();
            }
        } catch (Exception e) {
            System.out.println("Invalid Info");
        }
    }
    private int checkQuantity(Product product) {
        int quantity = 0;
        do {
            try {
                System.out.println("Enter quantity(Enter 0 to go back Adminmenu): ");
                quantity = Integer.parseInt(scanner.nextLine());
                while (quantity <= 0) {
                    if (quantity == 0) {
                        adminview();
                    } else if (product.getQuantity() == quantity) {
                        System.out.println("Quantity must change!Please Re-enter: ");
                    }else {
                        System.out.println("Quantity cannot be negative! Please re-enter quantity: ");
                    }
                    quantity = Integer.parseInt(scanner.nextLine());
                }
            } catch (Exception e) {
                System.out.println("Invalid quantity!Please Re-enter");
            }
        } while (quantity <= 0 || product.getQuantity() == quantity);
        product.setQuantity(quantity);
        return quantity;
    }

    private long checkPriceProduct(Product product) {
        long price = 0;
        do {
            try {
                System.out.println("Enter new price (Enter 0 to go back Adminmenu): ");
                price = Long.parseLong(scanner.nextLine());

                while (price <= 0 || product.getPrice() == price) {
                    if (price == 0) {
                        adminview();
                    } else {
                        System.out.println("Price cannot be negative! Please re-enter quantity: ");
                    }

                    price = Long.parseLong(scanner.nextLine());
                }
            } catch (Exception e) {
                System.out.println("Invalid Price");
            }
        } while (price <= 0 || product.getPrice() == price);
        product.setPrice(price);
        return price;
    }

    private String checkNameProduct(Product product) {
        System.out.println("Enter new product name (Enter 0 to go back Adminmenu): ");
        String name = scanner.nextLine();

        while (product.getName().equals(name)) {
            if (name.equals("0")) {
                adminview();
            } else {
                System.out.println("Invalid name!");
            }
            name = scanner.nextLine();
        }
        return name;
    }
    public void deleteproduct() {
        productlist();
        boolean end = true;
        do {
            try {
                System.out.println("Enter ID(Enter 0 to back Adminmenu)");
                int id = Integer.parseInt(scanner.nextLine());
                if (id == 0) {
                    adminview();
                } else {
                    while (!productservice.existbyId(id)) {
                        System.out.println("This ID is not exist!Please Re-enter");
                        id = Integer.parseInt(scanner.nextLine());
                    }
                    productservice.removebyId(id);
                    System.out.println("Delete product successful");
                    productlist();
                    end = false;
                }
            } catch (Exception e) {
                System.out.println("Invalid ID");
            }
        } while (end);

    }
}
