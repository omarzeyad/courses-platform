package platform;

import io.*;
import user.User;

public class Platform {

    private User user;
    
    private static Database db;
    private boolean isFirstRun;

    public Platform() {
        db = new Database();
        isFirstRun = true;
    }

    public void run() {
        while (true) {
            InputSystem.clearConsole();

            db.connect();

            if (isFirstRun && db.hasNoUsers()) {
                LoginMenus.howToUseGuide();
                isFirstRun = false;
            }

            // Login phase
            user = User.login();
            if (user != null) {
                if (db.userExists(user)) {
                    LoginMenus.loginSucceded();
                    user.setLoggedIn(true);
                    user.grantAccessTo(db);
                } else {
                    LoginMenus.authenticationError();
                    LoginMenus.loginFailed();
                    continue;
                }
            } else {
                System.out.println();
                LoginMenus.loginFailed();
                continue;
            }

            // user session
            while (user.isLoggedIn()) {
                InputSystem.clearConsole();

                // Each user displays & takes his own actions
                user.displayActionsMenu();
                user.takeAction();

                InputSystem.waitForKeyPress();
            }

            db.disconnect();
        }
    }

}
