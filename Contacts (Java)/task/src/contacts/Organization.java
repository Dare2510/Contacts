package contacts;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Organization extends Contacts {
    String organization = "";
    String address = "";

    public void setOrganization(String organization) { this.organization = organization.isEmpty() ? "[no data]" : organization; }
    public void setAddress(String address) { this.address = address.isEmpty() ? "[no data]" : address; }

    @Override
    String getNameandOrganization() { return organization; }

    @Override
    void printContact() {
        System.out.println("Organization name: " + organization);
        System.out.println("Address: " + (address.isEmpty() ? "[no data]" : address));
        System.out.println("Number: " + (phoneNumber.isEmpty() ? "[no data]" : phoneNumber));
        System.out.println("Time created: " + created);
        System.out.println("Time last edit: " + (lastUpdated.isEmpty() ? created : lastUpdated));
        System.out.println();
    }

    @Override
    String getFullInfo() { return (organization + " " + address + " " + phoneNumber).toLowerCase(); }

    @Override
    List<String> getEditableFields() {
        List<String> fields = new ArrayList<>();
        fields.add("name");
        fields.add("address");
        fields.add("number");
        return fields;
    }

    @Override
    void changeValue(String newValue, String field) {
        switch (field.toLowerCase()) {
            case "name": setOrganization(newValue); break;
            case "address": setAddress(newValue); break;
            case "number": phoneNumber = newValue; break;
        }
        lastUpdated = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
        System.out.println("Saved");
    }
}


