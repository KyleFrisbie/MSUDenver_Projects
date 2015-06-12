/**
 * Created by Kyle on 12/1/2014.
 * @author Kyle L Frisbie
 * @version 1
 *
 * Pet.java
 * ADT for use with Hash Tables, includes String name for name of pet item,
 * int key for unique key of pet, and int hash, for the calculated hash value
 * based on key.
 */
public class Pet {
    private String name;
    private int key;
    private int hash;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }
}
