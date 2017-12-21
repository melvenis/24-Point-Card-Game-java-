package project3;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Class            Person
 * File              Person.java
 * Description     Class for a person, specifically one with their name and full address
 * @author        Mel Leggett
 * Environment   PC, Windows 10, jdk 1.8, NetBeans 8.2
 * Date         02/13/2017
 * @version         1.0
 * History Log:  02/13/2017
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class Person {
    private String name;
    private String address;
    private String city;
    private String state;
    private int zip;
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Person()
     * Default constructor of the Person
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    Person() {   
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Overloaded constructor
     * @param name name of the person
     * @param add street address
     * @param city city of person
     * @param state state of the person
     * @param zip  zipcode of the person
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    Person(String name, String add, String city, String state, int zip) {
        this.name = name;
        address = add;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Overloaded Constructor with only name
     * @param name name of the person
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    Person(String name) {
        this.name = name;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Person()
     * Copy constructor of another Person object
     * @param per another Person object to be copied
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    Person(Person per) {
        this(per.getName(), per.getAddress(), per.getCity(), per.getState(), per.getZip());
    }

    /**
     * getName()
     * @return returns the name of the person
     */
    public String getName() {
        return name;
    }

    /**
     * setName()
     * @param name allows access to edit the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getAddress()
     * @return  the address of the person
     */
    public String getAddress() {
        return address;
    }

    /**
     * setAddress()
     * @param address allows access to edit the persons address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * getCity
     * @return the city the person lives in
     */
    public String getCity() {
        return city;
    }

    /**
     * setCity()
     * @param city where the person resides
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * getState()
     * @return the state the person lives
     */
    public String getState() {
        return state;
    }

    /**
     * setState()
     * @param state allows changing of where the person resides
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * getZip()
     * @return the zip code the person lives at
     */
    public int getZip() {
        return zip;
    }

    /**
     * setZip()
     * @param zip code where the person lives
     */
    public void setZip(int zip) {
        this.zip = zip;
    }
    
}
