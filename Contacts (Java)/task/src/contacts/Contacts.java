package contacts;

import java.io.Serializable;
import java.util.List;

abstract class Contacts implements Serializable {

    String phoneNumber = "";
    String created = "";
    String lastUpdated = "";

    public void setCreated(String created) { this.created = created; }
    public String getCreated() { return created; }

    public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }

    abstract void printContact();
    abstract String getFullInfo();
    abstract String getNameandOrganization();
    abstract List<String> getEditableFields();
    abstract void changeValue(String newValue, String fieldName);
}


