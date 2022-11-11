package ppoo.tema1;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Card {
    private String incomeSource;
    private String usagePurpose;

    private String holderName;
    private int number[];
    private int civ[];

    private double balance;
    private String currency;  // only in lei
    private String iban;      // must be unique

    private int pin[];

    private String expirationDate;

    private final int NUMBERLENGTH = 16;
    private final int CIVLENGTH = 3;
    private final int PINLENGTH = 4;



    public Card(final String holderName, final String incomeSource, final String usagePurpose, final String pin) {
        this.holderName = holderName;
        this.incomeSource = incomeSource;
        this.usagePurpose = usagePurpose;

        Random rand = new Random();

        number = new int[NUMBERLENGTH];
        for (int i = 0; i < NUMBERLENGTH; i++) {
            if (i == 0) {
                // Card number is not going to start with number 0
                number[i] = 1 + rand.nextInt(9);
            } else {
                number[i] = rand.nextInt(10);
            }
        }

        civ = new int[CIVLENGTH];
        for (int i = 0; i < CIVLENGTH; i++) {
            civ[i] = rand.nextInt(10);
        }

        balance = 0.0;
        currency = "lei";

        iban = "TODO in the future";

        this.pin = new int[PINLENGTH];
        for (int i = 0; i < 4; i++) {
            this.pin[i] = pin.charAt(i) - '0';
        }

        Calendar calendar = Calendar.getInstance();
        expirationDate = calendar.get(Calendar.MONTH) + "/" + (calendar.get(Calendar.YEAR) + 2);
    }

    public Card() {
        this.incomeSource = null;
        this.usagePurpose = null;
        this.holderName = null;
        this.number = new int[NUMBERLENGTH];
        this.civ = new int[CIVLENGTH];
        this.balance = 0.0;
        this.currency = null;
        this.iban = null;
        this.pin = new int [PINLENGTH];
        this.expirationDate = null;
    }

    public String getHolderName() {
        return holderName;
    }

    public String getCiv() {
        StringBuilder stringNumber = new StringBuilder();

        for (int i = 0; i < CIVLENGTH; i++) {
            stringNumber.append(civ[i]);
        }

        return stringNumber.toString();
    }

    public double getBalance() {
        return balance;
    }

    public String getIban() {
        return iban;
    }

    public String getPin() {
        StringBuilder stringNumber = new StringBuilder();

        for (int i = 0; i < PINLENGTH; i++) {
            stringNumber.append(pin[i]);
        }

        return stringNumber.toString();
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getNumber() {
        StringBuilder stringNumber = new StringBuilder();

        for (int i = 0; i < NUMBERLENGTH; i++) {
            if (i % 4 == 0 && i != 0) {
                stringNumber.append(" ");
            }
            stringNumber.append(number[i]);
        }

        return stringNumber.toString();
    }

    public void setNumber(final String string_number) {
        for (int i = 0, j = 0; i < NUMBERLENGTH; i++, j++) {
            char c = string_number.charAt(j);
            while (c == ' ') {
                j++;
                c = string_number.charAt(j);
            }
            number[i] = c - '0';
        }
    }

    public void setCiv(final String string_civ) {
        for (int i = 0; i < CIVLENGTH; i++) {
            civ[i] = string_civ.charAt(i) - '0';
        }
    }

    public void setPin(final String string_pin) {
        for (int i = 0; i < PINLENGTH; i++) {
            pin[i] = string_pin.charAt(i) - '0';
        }
    }


    public String save() {
        return incomeSource
                + "," + usagePurpose
                + "," + holderName
                + "," + getNumber()
                + "," + getCiv()
                + "," + balance
                + "," + currency
                + "," + iban
                + "," + getPin()
                + "," + expirationDate;
    }

    public void restore(final String incomeSource,
                        final String usagePurpose,
                        final String holderName,
                        final String number,
                        final String civ,
                        final String balance,
                        final String currency,
                        final String iban,
                        final String pin,
                        final String expirationDate) {
        this.incomeSource = incomeSource;
        this.usagePurpose = usagePurpose;
        this.holderName = holderName;
        setNumber(number);
        setCiv(civ);
        this.balance = Double.parseDouble(balance);
        this.currency = currency;
        this.iban = iban;
        setPin(pin);
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "holder name: " + holderName + '\n'
                + "number: " + getNumber() + '\n'
                + "civ: " + getCiv() + '\n'
                + "expirationDate: " + expirationDate + '\n'
                + "balance: " + balance + '\n'
                + "currency: " + currency + '\n';
    }
}
