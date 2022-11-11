package ppoo.tema1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;


public class Main {
    private static final RamDatabase ramDatabase = RamDatabase.getInstance();
    private static final BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));
    private static Menu.Status currentMenuStatus = Menu.Status.INTRO;
    private static User loggedInUser = null;

    public static void handleLogin() throws Exception {
        System.out.println("Introduce email: ");
        String email = keyboardReader.readLine();
        if (email == null) {
            currentMenuStatus = Menu.Status.INTRO;
            throw new MyException("Email is invalid\n");
        }

        User tmpUser = ramDatabase.getUserByEmail(email);
        if (tmpUser == null) {
            currentMenuStatus = Menu.Status.INTRO;
            throw new MyException("User with email does not exist!\n");
        }
        else {
            System.out.println("Introduce password: ");
            String password = keyboardReader.readLine();

            if (password.equals(tmpUser.getPassword())) {
                System.out.println("Success!\n");
                loggedInUser = tmpUser;
                currentMenuStatus = Menu.Status.LOGGED_IN;
            }
            else {
                currentMenuStatus = Menu.Status.INTRO;
                throw new MyException("Password is incorrect!\n");
            }
        }
    }
    public static void handleRegister() throws Exception {
        System.out.println("Introduce full name: ");
        String fullName = keyboardReader.readLine();
        if (fullName.isEmpty()) {
            currentMenuStatus = Menu.Status.INTRO;
            throw new MyException("Empty name\n");
        }
        if (ramDatabase.fullNameExists(fullName)) {
            currentMenuStatus = Menu.Status.INTRO;
            throw new MyException("" + fullName + " already exists!\n");
        }
        System.out.println("Introduce full address:");
        String fullAddress = keyboardReader.readLine();
        if (fullAddress.isEmpty()) {
            currentMenuStatus = Menu.Status.INTRO;
            throw new MyException("Empty address\n");
        }

        System.out.println("Introduce phone number:");
        String phoneNumber = keyboardReader.readLine();
        if (phoneNumber.isEmpty()) {
            currentMenuStatus = Menu.Status.INTRO;
            throw new MyException("Empty phone number\n");
        }
        if (ramDatabase.phoneNumberExists(phoneNumber)) {
            currentMenuStatus = Menu.Status.INTRO;
            throw new MyException("" + phoneNumber + " already associated with an account!\n");
        }

        System.out.println("Introduce email:");
        String email = keyboardReader.readLine();
        if (email.isEmpty()) {
            currentMenuStatus = Menu.Status.INTRO;
            throw new MyException("Empty email\n");
        }
        if (ramDatabase.emailExists(email)) {
            currentMenuStatus = Menu.Status.INTRO;
            throw new MyException("" + email + " already associated with an account!\n");
        }

        System.out.println("Introduce occupation:");
        String occupation = keyboardReader.readLine();
        if (occupation.isEmpty()) {
            currentMenuStatus = Menu.Status.INTRO;
            throw new MyException("Empty occupation\n");
        }

        System.out.println("Introduce password:");
        String password = keyboardReader.readLine();
        if (password.length() < 4) {
            currentMenuStatus = Menu.Status.INTRO;
            throw new MyException("Password length must be at least 4 characters!\n");
        }

        loggedInUser = new User(fullName, fullAddress, phoneNumber, email, occupation, password);
        ramDatabase.addUser(loggedInUser);

        System.out.println("Add card ? (yes/no)");
        switch (keyboardReader.readLine()) {
            case "y", "ye", "yes" -> currentMenuStatus = Menu.Status.ADD_CARD;
            case "n", "no" -> currentMenuStatus = Menu.Status.LOGGED_IN;
            default -> {
                currentMenuStatus = Menu.Status.LOGGED_IN;
                throw new MyException("Invalid add card option. Redirecting to logged in menu.\n");
            }
        }
    }
    public static void handleExit() {
        System.out.println("Bye!");
    }

    public static void handleAddCard() throws Exception {
        System.out.println("Introduce card income source: ");
        String cardIncomeSource = keyboardReader.readLine();

        System.out.println("Introduce card usage purpose: ");
        String cardUsagePurpose = keyboardReader.readLine();

        System.out.println("Introduce card pin: ");
        String cardPin = keyboardReader.readLine();
        if (cardPin.length() == 4) {
            for (int i = 0; i < 4; i++) {
                if (cardPin.charAt(i) < '0' || cardPin.charAt(i) > '9') {
                    currentMenuStatus = Menu.Status.LOGGED_IN;
                    throw new MyException("Pin must have 4 numbers!\n");
                }
            }
        }
        else {
            currentMenuStatus = Menu.Status.LOGGED_IN;
            throw new MyException("Pin must have 4 numbers!\n");
        }

        Card card = new Card(loggedInUser.getFullName(), cardIncomeSource, cardUsagePurpose, cardPin);
        loggedInUser.addCard(card);
        currentMenuStatus = Menu.Status.LOGGED_IN;
    }
    public static void handleDeleteCurrentSelectedCard() throws Exception {
        if (loggedInUser.getCurrentSelectedCard() == null) {
            currentMenuStatus = Menu.Status.LOGGED_IN;
            throw new MyException("Card wallet is empty\n");
        }
        else {
            currentMenuStatus = Menu.Status.LOGGED_IN;
            loggedInUser.deleteCurrentSelectedCard();
        }
    }
    public static void handleSelectCard() throws Exception {
        int numberOfCards = loggedInUser.getNumberOfCards();

        if (numberOfCards < 2) {
            currentMenuStatus = Menu.Status.LOGGED_IN;
            throw new MyException("Add more than two cards first!\n");
        }
        else {
            currentMenuStatus = Menu.Status.LOGGED_IN;
            List<Card> cards = loggedInUser.getCards();

            for (int i = 0; i < numberOfCards; i++) {
                System.out.print((i + 1) + ". " + cards.get(i).getNumber());
                if (cards.get(i) == loggedInUser.getCurrentSelectedCard()) {
                    System.out.print(" (currently selected)");
                }
                System.out.println();
            }

            int selectedCardIndex = Integer.parseInt(keyboardReader.readLine());
            if (selectedCardIndex > numberOfCards || selectedCardIndex < 1) {
                currentMenuStatus = Menu.Status.LOGGED_IN;
                throw new MyException("Incorrect card selection input index!\n");
            }

            loggedInUser.setCurrentSelectedCard(selectedCardIndex - 1);
            currentMenuStatus = Menu.Status.LOGGED_IN;
        }
    }
    public static void handleShowCurrentSelectedCardDetails() throws Exception {
        Card card = loggedInUser.getCurrentSelectedCard();
        if (card == null) {
            currentMenuStatus = Menu.Status.LOGGED_IN;
            throw new MyException("Card wallet is empty\n");
        }

        System.out.println(card);
        currentMenuStatus = Menu.Status.LOGGED_IN;
    }
    public static void handleShowCurrentSelectedCardIban() throws Exception {
        Card card = loggedInUser.getCurrentSelectedCard();
        if (card == null) {
            currentMenuStatus = Menu.Status.LOGGED_IN;
            throw new MyException("Card wallet is empty\n");
        }

        System.out.println("IBAN: " + card.getIban());
        currentMenuStatus = Menu.Status.LOGGED_IN;
    }

    public static void main(String[] args) {
        ramDatabase.restore();
        while(true) {
            try {
                Menu.print(currentMenuStatus);

                if (currentMenuStatus == Menu.Status.INTRO) {
                    String line = keyboardReader.readLine();
                    switch (line.toLowerCase()) {
                        case "1", "login" -> currentMenuStatus = Menu.Status.LOGIN;
                        case "2", "register" -> currentMenuStatus = Menu.Status.REGISTER;
                        case "3", "exit" -> currentMenuStatus = Menu.Status.EXIT;
                    }
                }
                else if (currentMenuStatus == Menu.Status.LOGGED_IN) {
                    String line = keyboardReader.readLine();
                    switch (line.toLowerCase()) {
                        case "1", "add new card" -> currentMenuStatus = Menu.Status.ADD_CARD;
                        case "2", "delete card" -> currentMenuStatus = Menu.Status.DELETE_SELECTED_CARD;
                        case "3", "select card" -> currentMenuStatus = Menu.Status.SELECT_CARD;
                        case "4", "show current card details" -> currentMenuStatus = Menu.Status.SHOW_CURRENT_SELECTED_CARD_DETAILS;
                        case "5", "show current card iban" -> currentMenuStatus = Menu.Status.SHOW_CURRENT_SELECTED_CARD_IBAN;
                        case "6", "logout" -> currentMenuStatus = Menu.Status.INTRO;
                    }
                }
                else if (currentMenuStatus == Menu.Status.LOGIN) {
                    handleLogin();
                }
                else if (currentMenuStatus == Menu.Status.REGISTER) {
                    handleRegister();
                }
                else if (currentMenuStatus == Menu.Status.ADD_CARD) {
                    handleAddCard();
                }
                else if (currentMenuStatus == Menu.Status.DELETE_SELECTED_CARD) {
                    handleDeleteCurrentSelectedCard();
                }
                else if (currentMenuStatus == Menu.Status.SELECT_CARD) {
                    handleSelectCard();
                }
                else if (currentMenuStatus == Menu.Status.SHOW_CURRENT_SELECTED_CARD_DETAILS) {
                    handleShowCurrentSelectedCardDetails();
                }
                else if (currentMenuStatus == Menu.Status.SHOW_CURRENT_SELECTED_CARD_IBAN) {
                    handleShowCurrentSelectedCardIban();
                }
                else if (currentMenuStatus == Menu.Status.EXIT) {
                    handleExit();
                    break;
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        ramDatabase.save();
    }
}
