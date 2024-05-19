package Mizdooni.Model;

public class Address {
    public String city;
    public String country;
    public String street;

    public Address(String city, String country) {
        this.city = city;
        this.country = country;
        this.street = " ";
    }
    public Address(String city, String country, String street) {
        this.city = city;
        this.country = country;
        this.street = street;
    }

    public Address(){}

    public String toString(){
        return country + ", " +  city + ", " + street;
    }
    public Address toAddress(String addressStr){
        String[] a = addressStr.split(",");
        if(a.length == 2)return new Address(a[1], a[0]);
        else return new Address(a[1], a[0], a[2]);
    }
}
