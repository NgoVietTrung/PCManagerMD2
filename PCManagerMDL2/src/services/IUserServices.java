package services;

import model.User;

import java.util.ArrayList;

public interface IUserServices {
    ArrayList<User> getuser();
    User loginbyuser(String username, String password);
    void adduser(User newuser);
    void deleteuser(long id);
    void update(String value,String options,long id);
    boolean exist(long id);
    boolean checkEmailExist(String email);
    boolean checkPhoneExist(String phone);
    boolean checkUserNameExist(String userName);
    User getUserById(long id);
    User getUserByUserName(String userName);
}
