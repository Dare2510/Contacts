package contacts;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Person extends Contacts {
    String name = "";
    String surname = "";
    String gender = "";
    String birthday = "";

    public void setName(String name) { this.name = name.isEmpty() ? "[no data]" : name; }
    public void setSurname(String surname) { this.surname = surname.isEmpty() ? "[no data]" : surname; }
    public void setGender(String gender) { this.gender = gender.isEmpty() ? "[no data]" : gender; }
    public void setBirthday(String birthday) { this.birthday = birthday.isEmpty() ? "[no data]" : birthday; }

    @Override
    String getNameandOrganization() { return name + " " + surname; }

    @Override
    void printContact() {
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Birth date: " + (birthday.isEmpty() ? "[no data]" : birthday));
        System.out.println("Gender: " + (gender.isEmpty() ? "[no data]" : gender));
        System.out.println("Number: " + (phoneNumber.isEmpty() ? "[no data]" : phoneNumber));
        System.out.println("Time created: " + created);
        System.out.println("Time last edit: " + (lastUpdated.isEmpty() ? created : lastUpdated));
        System.out.println();
    }

    @Override
    String getFullInfo() { return (name + " " + surname + " " + phoneNumber).toLowerCase(); }

    @Override
    List<String> getEditableFields() {
        List<String> fields = new ArrayList<>();
        fields.add("name");
        fields.add("surname");
        fields.add("birth");
        fields.add("gender");
        fields.add("number");
        return fields;
    }

    @Override
    void changeValue(String newValue, String field) {
        switch (field.toLowerCase()) {
            case "name": setName(newValue); break;
            case "surname": setSurname(newValue); break;
            case "birth": setBirthday(newValue); break;
            case "gender": setGender(newValue); break;
            case "number": phoneNumber = newValue; break;
        }
        lastUpdated = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
        System.out.println("Saved");
    }
}



