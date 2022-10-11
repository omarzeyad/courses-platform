package user;

import io.*;
import platform.Database;

public abstract class User {

    public enum Type {
        ADMIN(1), INSTRUCTOR(2), STUDENT(3), BAD_CHOICE(0);

        int val;

        Type(int val) {
            this.val = val;
        }

        public int getValue() {
            return val;
        }

        public static Type fromInt(int i) {
            for (Type a : Type.values()) {
                if (a.getValue() == i) {
                    return a;
                }
            }
            return BAD_CHOICE;
        }
    }

    public static User login() {
        Type loggedUserType = LoginMenus.loginType();

        User user = switch (loggedUserType) {
            case ADMIN      -> LoginMenus.adminLogin();
            case INSTRUCTOR -> LoginMenus.instructorLogin();
            case STUDENT    -> LoginMenus.studentLogin();
            default         -> LoginMenus.Exitting(); // case BAD_CHOICE
        };

        return user;
    }

    protected boolean loggedIn;

    public void setLoggedIn(boolean state) {
        loggedIn = state;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void logout() {
        LoginMenus.loggingOut();
        loggedIn = false;
    }

    protected String username;
    protected String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }

    protected Database db;

    public void grantAccessTo(Database db) {
        this.db = db;
    }

    public abstract String whoAmI();

    public abstract void displayActionsMenu();

    public abstract void takeAction();

    protected void changePassword() {
        System.out.println();
        System.out.print("Old ");
        String oldPassword = InputSystem.passwordPrompt();
        if (oldPassword.equals(password)) {
            System.out.print("New ");
            String newPassword = InputSystem.passwordPrompt();

            setPassword(newPassword);
            db.updateUser(this);

            ActionMenus.actionCompleted();
        } else {
            System.out.println("Incorrect old password!!!");
        }
    }

}
