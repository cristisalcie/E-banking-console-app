package ppoo.tema1;


public class Menu {
    public enum Status {
        INTRO,
        LOGIN,
        REGISTER,
        EXIT,
        LOGGED_IN,
        ADD_CARD,
        DELETE_SELECTED_CARD,
        SELECT_CARD,
        SHOW_CURRENT_SELECTED_CARD_DETAILS,
        SHOW_CURRENT_SELECTED_CARD_IBAN
    }

    public static void print(final Menu.Status whichMenu) {
        if (whichMenu == Status.INTRO) {
            printIntro();
        } else if (whichMenu == Status.LOGGED_IN) {
            printLoggedIn();
        }
    }

    private static void printIntro() {
        System.out.println("Welcome to e-banking!");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
    }

    private static void printLoggedIn() {
        System.out.println("1. Add new card");
        System.out.println("2. Delete card");
        System.out.println("3. Select card");
        System.out.println("4. Show current card details");
        System.out.println("5. Show current card IBAN");
        System.out.println("6. Logout");
    }
}
