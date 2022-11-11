package ppoo.tema1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Singleton
public class RamDatabase {
    private static RamDatabase instance = null;
    private final List<User> users;

    private RamDatabase() {
        users = new ArrayList<User>();
    }

    public static RamDatabase getInstance() {
        if (instance == null) {
            instance = new RamDatabase();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }
    public User getUserByFullName(final String fullName) {
        for (User user : users) {
            if (user.getFullName().equals(fullName)) {
                return user;
            }
        }
        return null;
    }
    public User getUserByEmail(final String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
    public User getUserByPhoneNumber(final String phoneNumber) {
        for (User user : users) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return user;
            }
        }
        return null;
    }

    public boolean fullNameExists(final String fullName) {
        for (User user : users) {
            if (user.getFullName().equals(fullName)) {
                return true;
            }
        }
        return false;
    }
    public boolean emailExists(final String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    public boolean phoneNumberExists(final String phoneNumber) {
        for (User user : users) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void save() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("database.csv"));

            for (User user : users) {
                bufferedWriter.write(user.save());
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void restore() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("database.csv"));

            String line = bufferedReader.readLine();
            while (line != null) {
                User user = new User(line);
                users.add(user);
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
