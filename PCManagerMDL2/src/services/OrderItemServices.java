package services;

import Menu.Menu;
import model.OrderItem;
import model.Product;
import model.User;
import utils.CSVUtils;
import view.OrderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderItemServices implements IOrderItemServices {
    public static ArrayList<OrderItem> listOrderItem = new ArrayList<>();

    public static ArrayList<OrderItem> listOrderPaid = new ArrayList<>();
    private static final UserServices userServices = new UserServices();
    private static final String fileName = "data\\orderItem.csv";
    private static final String path = "data\\orderPaid.csv";
    @Override
    public List<OrderItem> getOrderItems() {
        if (listOrderItem.size() == 0) {
            List<String> records = CSVUtils.read(fileName);

            for (String record : records) {
                listOrderItem.add(new OrderItem(record));
            }
            return listOrderItem;
        }
        return null;
    }

    public  List<OrderItem> getOrdersPaid() {
        if (listOrderPaid.size() == 0) {
            List<String> records = CSVUtils.read(path);

            for (String record : records) {
                listOrderPaid.add(new OrderItem(record));
            }
            return listOrderPaid;
        }
        return null;
    }

    public static void addOrderPaid(OrderItem newOrder) {
        listOrderPaid.add(newOrder);
        updateOrderListPaid();
    }

    public static void updateOrderListPaid() {
        CSVUtils.write(path, listOrderPaid);
    }

    @Override
    public void add(OrderItem newOrderItem) {
        listOrderItem.add(newOrderItem);
        CSVUtils.write(fileName, listOrderItem);
    }

    @Override
    public void update() {
        CSVUtils.write(fileName, listOrderItem);
    }

    @Override
    public OrderItem getOrderItemById(long id) {
        for (OrderItem orderItem : listOrderItem) {
            if (orderItem.getOrderId() == id) {
                return orderItem;
            }
        }
        return null;
    }

    public OrderItem getOrderItemByName(String name) {
        for (OrderItem orderItem : listOrderItem) {
            if (Objects.equals(orderItem.getName(), name)) {
                return orderItem;
            }
        }
        return null;
    }

    public List<OrderItem> getListOrderItemByName(String name) {
        List<OrderItem> list = new ArrayList<>();
        for (OrderItem orderItem : listOrderItem) {
            if (Objects.equals(orderItem.getName(), name)) {
                list.add(orderItem);
            }
        }
        return list;
    }

    public boolean isExistProduct(Product product) {
        for (OrderItem orderItem : listOrderItem) {
            if (orderItem.getProductId() == product.getId()) {
                return true;
            }
        }
        return false;
    }

    public void resetListOrderItem(String name) {
        listOrderItem.removeIf(orderItem -> (orderItem.getName().equals(name)));
        CSVUtils.write(fileName, listOrderItem);
    }

    public static boolean isExistNameOfOrderItemList(String name) {
        for(OrderItem orderItem : listOrderItem){
            if(orderItem.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkNameOnList(String name) {
        for (OrderItem orderItem : listOrderItem) {
            if (orderItem.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static void orderlist() {
        User user = userServices.getUserById(Menu.userId);
        if (!checkNameOnList(user.getName())) {
            System.out.println("----------  Empty List!  ----------");
            System.out.println(" ");
        }
        int totalMoney = 0;
        System.out.println("------------------------------------------------- Your Order -------------------------------------------------");
        System.out.printf("*             %-15s %-18s %-12s %-15s %-20s %-16s *\n", "ID", "Product Name", "Price", "Quantity", "Total", "Date");

        for (OrderItem orderItem : listOrderItem) {
            if (user.getName().equals(orderItem.getName())) {
                totalMoney += orderItem.getTotal();
                System.out.printf("*        %-16s %-20s %-15s  %-13s %-20s %-16s *\n", orderItem.getId(),
                        orderItem.getProductName(), orderItem.getQuantity(), ProductServices.addCharacters((int) orderItem.getPrice()), ProductServices.addCharacters((int) orderItem.getTotal()),
                        orderItem.getDate());
            }
        }
        System.out.println("-----------------------------------------------------------------------------------  TỔNG TIỀN: " + ProductServices.addCharacters(totalMoney) + "  --------------------");
        OrderView.showbill(totalMoney);
    }

}
