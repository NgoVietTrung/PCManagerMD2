package services;

import model.User;
import utils.CSVUtils;

import java.util.ArrayList;
import java.util.List;

public class UserServices implements IUserServices{
    private static ArrayList<User> listuser = new ArrayList<>();
    private final String filename = "data\\user.csv";

    @Override
    public ArrayList<User> getuser() {
        if (listuser.size() == 0) {
            List<String> records = CSVUtils.read(filename);
            for (String record : records) {
                listuser.add(new User(record));

            }
            return listuser;
        }
        return null;
    }

    @Override
    public User loginbyuser(String username, String password) {
        for (User user : listuser) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void adduser(User newuser) {
        listuser.add(newuser);
        update();

    }
    public boolean checkNameExist (String name) {
        for (User product : listuser) {
            if (name.equals(product.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteuser(long id) {
        int index = findindexbyid(id);
        listuser.remove(index);
        update();
    }

    @Override
    public void update(String value, String options, long id) {
    }

    public void update() {
        CSVUtils.write(filename, listuser);
    }

    public int findindexbyid(long id) {
        for (int i = 0; i < listuser.size(); i++) {
            if (listuser.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public boolean exist(long id) {
        for (User user : listuser) {
            if (id == user.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkEmailExist(String email) {
        for (User user : listuser) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkPhoneExist(String phone) {
        for (User user : listuser) {
            if (user.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkUserNameExist(String userName) {
        for (User user : listuser) {
            if (user.getUsername().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUserById(long id) {
        for (User user : listuser) {
            if (id == user.getId()) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserByUserName(String userName) {
        for (User user : listuser) {
            if (user.getUsername().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    public static void userlist() {
        if (listuser.size() == 0) {
            System.out.println("User list is empty");
        } else {
            System.out.println("--------------------------------------------------------  USER LIST  ---------------------------------------------------");
            System.out.printf("     %-10s %-15s %-15s %-15s %-23s %-24s %-15s \n", "ID", "Username", "Password", "Name", "Phone Number", "Email", "Address");
            for (User user : listuser) {
                System.out.printf("%-10s %-15s %-15s %-15s %-23s %-24s %-15s\n", user.getId(), user.getUsername(), user.getPassword(), user.getName(), user.getPhone(), user.getEmail(), user.getAddress());
            }
            System.out.println("--------------------------------------------------------------------------------------------------------------------");
            System.out.println(" ");
        }
    }
}
