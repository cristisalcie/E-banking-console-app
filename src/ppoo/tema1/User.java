package ppoo.tema1;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String fullName;    // unique
    private String fullAddress;
    private String occupation;
    private String email;       // unique
    private String phoneNumber; // unique
    private String password;
    private List<Card> cards;
    private Card currentSelectedCard;  // By default this is first existing card in cards list.

    public User (final String fullName,
                 final String fullAddress,
                 final String occupation,
                 final String email,
                 final String phoneNumber,
                 final String password) {
        this.fullName = fullName;
        this.fullAddress = fullAddress;
        this.occupation = occupation;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        currentSelectedCard = null;
        cards = new ArrayList<Card>();
    }

    public User (String data) {
        currentSelectedCard = null;
        cards = new ArrayList<Card>();
        restore(data);
    }

    public void addCard(Card card) {
        if (currentSelectedCard == null) {
            currentSelectedCard = card;
        }
        cards.add(card);
    }
    public void deleteCurrentSelectedCard() {
        cards.remove(currentSelectedCard);
        if (cards.isEmpty()) {
            currentSelectedCard = null;
        }
        else {
            currentSelectedCard = cards.get(0);
        }
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getNumberOfCards() {
        return cards.size();
    }

    public Card getCurrentSelectedCard() {
        return currentSelectedCard;
    }

    public void setCurrentSelectedCard(final int selectedCardIndex) {
        currentSelectedCard = cards.get(selectedCardIndex);
    }

    public String save() {
        StringBuilder output = new StringBuilder();
        int numberOfCards = getNumberOfCards();

        output.append(fullName);
        output.append(",");
        output.append(fullAddress);
        output.append(",");
        output.append(occupation);
        output.append(",");
        output.append(email);
        output.append(",");
        output.append(phoneNumber);
        output.append(",");
        output.append(password);
        output.append(",");
        output.append(cards.size());
        output.append(",");
        for (int i = 0; i < numberOfCards; i++) {
            output.append(cards.get(i).save());
            if (i < numberOfCards - 1) {
                output.append(",");
            }
        }
        output.append("\n");
        return output.toString();
    }

    public void restore(String line) {
        String[] data = line.split(",");
        fullName = data[0];
        fullAddress = data[1];
        occupation = data[2];
        email = data[3];
        phoneNumber = data[4];
        password = data[5];
        int numberOfCards = Integer.parseInt(data[6]);
        for (int i = 0; i < numberOfCards; i++) {
            Card card = new Card();

            card.restore(
                    data[7 + i * 10],
                    data[7 + i * 10 + 1],
                    data[7 + i * 10 + 2],
                    data[7 + i * 10 + 3],
                    data[7 + i * 10 + 4],
                    data[7 + i * 10 + 5],
                    data[7 + i * 10 + 6],
                    data[7 + i * 10 + 7],
                    data[7 + i * 10 + 8],
                    data[7 + i * 10 + 9]
            );

            addCard(card);
        }
    }
}
