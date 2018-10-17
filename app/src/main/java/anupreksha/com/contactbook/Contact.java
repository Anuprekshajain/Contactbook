package anupreksha.com.contactbook;

public class Contact {
    private int id;
    private String firstName;
    private String lastName;
    private String Address;
    private String Phone;
    private String email;
    private String nickName;
    
    public Contact( int id,String firstName , String lastName, String Address, String Phone, String email, String nName)
    {
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.Address=Address;
        this.Phone=Phone;
        this.email=email;
        this.nickName=nName;    
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getNick() {
        return nickName;
    }

    public void setNick(String nName) {
        this.nickName = nName;
    }

}
