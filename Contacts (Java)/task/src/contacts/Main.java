package contacts;

import java.io.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Contacts> phoneBook;
        File file = null;

        if (args.length > 0) {
            file = new File(args[0]);
            if (file.exists()) {
                phoneBook = loadFromFile(file);
            } else {
                phoneBook = new ArrayList<>();
                saveToFile(phoneBook, file);
            }
        } else {
            phoneBook = new ArrayList<>();
        }

        String menu;
        do {
            System.out.print("[menu] Enter action (add, list, search, count, exit): ");
            menu = sc.nextLine();

            switch (menu.toLowerCase().trim()) {
                case "add":
                    addRecord(sc, phoneBook, file);
                    break;
                case "list":
                    listRecords(sc, phoneBook, file);
                    break;
                case "search":
                    searchRecords(sc, phoneBook, file);
                    break;
                case "count":
                    System.out.println("The Phone Book has " + phoneBook.size() + " records.\n");
                    break;
            }

        } while (!menu.equalsIgnoreCase("exit"));
    }

    private static void addRecord(Scanner sc, List<Contacts> phoneBook, File file) {
        System.out.print("Enter the type (person, organization): ");
        String type = sc.nextLine();

        if (type.equalsIgnoreCase("person")) {
            Person p = new Person();

            System.out.print("Enter the name: ");
            p.setName(sc.nextLine());

            System.out.print("Enter the surname: ");
            p.setSurname(sc.nextLine());

            System.out.print("Enter the birth date: ");
            String birth = sc.nextLine();
            p.setBirthday(birth.isEmpty() ? "[no data]" : birth);
            if (birth.isEmpty()) System.out.println("Bad Birthday!");

            System.out.print("Enter the gender (M, F): ");
            String g = sc.nextLine();
            p.setGender(g.isEmpty() ? "[no data]" : g);
            if (g.isEmpty()) System.out.println("Bad gender!");

            System.out.print("Enter the number: ");
            p.phoneNumber = sc.nextLine();
            if (p.phoneNumber.isEmpty()) System.out.println("Error in the Number");

            p.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
            p.setLastUpdated(p.getCreated());
            phoneBook.add(p);

            System.out.println("The record added.\n");

        } else if (type.equalsIgnoreCase("organization")) {
            Organization o = new Organization();

            System.out.print("Enter the organization name: ");
            o.setOrganization(sc.nextLine());

            System.out.print("Enter the address: ");
            o.setAddress(sc.nextLine());

            System.out.print("Enter the number: ");
            o.phoneNumber = sc.nextLine();
            if (o.phoneNumber.isEmpty()) System.out.println("Error in the Number");

            o.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
            o.setLastUpdated(o.getCreated());
            phoneBook.add(o);

            System.out.println("The record added.\n");
        }

        saveIfNeeded(phoneBook, file);
    }

    private static void listRecords(Scanner sc, List<Contacts> phoneBook, File file) {
        if (phoneBook.isEmpty()) {
            System.out.println("No records to list!\n");
            return;
        }

        for (int i = 0; i < phoneBook.size(); i++) {
            System.out.println((i + 1) + ". " + phoneBook.get(i).getNameandOrganization());
        }

        System.out.print("[list] Enter action ([number], back): ");
        String input = sc.nextLine();

        if (!input.equalsIgnoreCase("back")) {
            try {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < phoneBook.size()) {
                    Contacts c = phoneBook.get(index);
                    c.printContact();
                    recordMenu(sc, phoneBook, c, file);
                }
            } catch (NumberFormatException ignored) {}
        }
        System.out.println();
    }

    private static void searchRecords(Scanner sc, List<Contacts> phoneBook, File file) {
        System.out.print("Enter search query: ");
        String query = sc.nextLine().toLowerCase();

        List<Contacts> results = new ArrayList<>();
        for (Contacts c : phoneBook) {
            if (c.getFullInfo().contains(query)) results.add(c);
        }

        System.out.println("Found " + results.size() + " results:");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i).getNameandOrganization());
        }

        System.out.print("[search] Enter action ([number], back, again): ");
        String action = sc.nextLine();

        if (action.equalsIgnoreCase("again")) {
            searchRecords(sc, phoneBook, file);
        } else if (!action.equalsIgnoreCase("back")) {
            try {
                int index = Integer.parseInt(action) - 1;
                if (index >= 0 && index < results.size()) {
                    Contacts c = results.get(index);
                    c.printContact();
                    recordMenu(sc, phoneBook, c, file);
                }
            } catch (NumberFormatException ignored) {}
        }
        System.out.println();
    }

    private static void recordMenu(Scanner sc, List<Contacts> phoneBook, Contacts c, File file) {
        while (true) {
            System.out.print("[record] Enter action (edit, delete, menu): ");
            String action = sc.nextLine();
            switch (action.toLowerCase()) {
                case "edit":
                    System.out.println(c.getEditableFields());
                    System.out.print("Select a field: ");
                    String field = sc.nextLine();
                    System.out.print("Enter " + field + ": ");
                    String value = sc.nextLine();
                    c.changeValue(value, field);
                    saveIfNeeded(phoneBook, file);
                    System.out.println();
                    break;
                case "delete":
                    phoneBook.remove(c);
                    System.out.println("The record removed!\n");
                    saveIfNeeded(phoneBook, file);
                    return;
                case "menu":
                    System.out.println();
                    return;
            }
        }
    }

    private static void saveIfNeeded(List<Contacts> phoneBook, File file) {
        if (file != null) saveToFile(phoneBook, file);
    }

    private static void saveToFile(List<Contacts> phoneBook, File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(phoneBook);
        } catch (IOException ignored) {}
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<Contacts> loadFromFile(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Contacts>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
