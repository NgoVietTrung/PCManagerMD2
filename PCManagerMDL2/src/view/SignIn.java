package view;

import Menu.Menu;
import model.User;
import services.UserServices;
import utils.ValidateUtils;

import java.util.Scanner;

public class SignIn {
    private static final UserServices userService = new UserServices();

    public static void addUser() {
        Scanner scanner=new Scanner(System.in);
        System.out.println("* * * * * * * * Sign In* * * * * * * *  ");
        System.out.println("Username ( 6-32 character, Starts with an alphanumeric character and contains no special characters): ");
        String username = scanner.nextLine();
        while (userService.checkUserNameExist(username) || !ValidateUtils.isusernamevalid(username)) {
            if (!ValidateUtils.isusernamevalid(username)) {
                System.out.println("Invalid username! Please re-enter: ");
            } else {
                System.out.println("Username available! Enter another name: ");
            }
            username = scanner.nextLine();
        }


        System.out.println("Enter password (at least 8 characters, excluding special characters): ");
        String password = scanner.nextLine();
        while (!ValidateUtils.ispasswordvalid(password)) {
            System.out.println("Invalid password! Please re-enter: ");
            password = scanner.nextLine();
        }
        System.out.println("Enter the name (capitalize the first letter of each word, for example: Viet Trung): ");
        String name = scanner.nextLine();
        while (userService.checkNameExist(name) || !ValidateUtils.isnamevalid(name)) {
            if (!ValidateUtils.isnamevalid(name)) {
                System.out.println("Invalid name! Please re-enter: ");
            } else {
                System.out.println("Name already exists! Enter another name: ");
            }
            name = scanner.nextLine();
        }
        System.out.println("Enter phone number (starts with 0, 10-11 numbers): ");
        String phone = scanner.nextLine();
        while (!ValidateUtils.isphonevalid(phone) || userService.checkPhoneExist(phone)) {

            if(userService.checkPhoneExist(phone)) {
                System.out.println("Phone number already exists! Please re-enter:: ");
            }else{
                System.out.println("Invalid phone number! Please re-enter: ");
            }
            phone = scanner.nextLine();
        }

        System.out.println("Enter email (for example: trung0611@gmail.com): ");
        String email = scanner.nextLine();
        while (!ValidateUtils.isemailvalid(email) || userService.checkEmailExist(email)) {
            if(userService.checkEmailExist(email)) {
                System.out.println("Email already exists! Please re-enter: ");
            }else {
                System.out.println("Invalid email! Please re-enter: ");
            }
            email = scanner.nextLine();
        }
        System.out.println("\n" +
                "Enter the address (this information cannot be left blank, for example Hue): ");
        String address = scanner.nextLine();
        while (address.length() == 0) {
            System.out.println("Address cannot be empty! Please re-enter another address: ");
            address = scanner.nextLine();
        }
        User user = new User(username, password, name, phone, email, address);
        userService.adduser(user);
        System.out.println("Sign in successful");
        Menu.mainmenu();
    }
}
