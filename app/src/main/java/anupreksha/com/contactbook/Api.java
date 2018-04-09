package anupreksha.com.contactbook;

public class Api {
    private static final String ROOT_URL="http://192.168.43.213/ContactApi/Api.php?apicall=";
    static final String URL_CREATE_CONTACT = ROOT_URL + "createContact";
    static final String URL_READ_CONTACT = ROOT_URL + "getContact";
    static final String URL_UPDATE_CONTACT = ROOT_URL + "updateContact";
    static final String URL_DELETE_CONTACT = ROOT_URL + "deleteContact&id=";
}
