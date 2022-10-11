package user;

import io.*;


public class Admin extends User {

    public Admin(String name, String pass) {
        username = name;
        password = pass;
    }

    public String whoAmI() {
        return "[Admin] " + username;
    }

    public void displayActionsMenu() {
        LoginMenus.greetUser(whoAmI());
        ActionMenus.adminActions();
    }

    public void takeAction() {
        int actionChoice = InputSystem.userActionPrompt();
        switch (actionChoice) {
        case 1  -> addUser();
        case 2  -> removeUser();
        case 3  -> resetDB();
        case 4  -> changePassword();
        case 0  -> logout();
        default -> ActionMenus.unknownAction();
        }
    }
    

    // 1.
    public void addUser() {
        int accountType = InputSystem.accountTypePrompt();
        if(accountType != 0) { // user choosed to go back
            String username = InputSystem.usernamePrompt();
            String password = InputSystem.passwordPrompt();

            boolean userAdded = (accountType == 1) ?
                db.addInstructor(username, password) : db.addStudent(username, password);

            if(userAdded) {
                ActionMenus.actionCompleted();
            }
            else {
                ActionMenus.actionFailed(username + " already exists");
            }
        }
    }

    // 2.
    public void removeUser() {
        int accountType = InputSystem.accountTypePrompt();
        if(accountType != 0) { // user choosed to go back
            String username = InputSystem.usernamePrompt();

            boolean userRemoved = (accountType == 1) ?
                db.removeInstructor(username) : db.removeStudent(username);

            if(userRemoved) {
                ActionMenus.actionCompleted();
            }
            else {
                ActionMenus.actionFailed(username + " does NOT exist");
            }
        }
    }

    // 3.
    public void resetDB() {
        boolean doReset = InputSystem.confirmActionPrompt();
        if(doReset)  {
            db.reset();
            ActionMenus.actionCompleted();
        }
    }

}