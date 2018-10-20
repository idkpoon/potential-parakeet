package example.android.cally.data;

/**
 * Created by 21poonkw1 on 3/10/2018.
 */

public class Contact {

    private String id;
    private String name;
    private String number;
    private String image;

    public Contact(){

    }
    public Contact(String name, String number, String image){
        this.name = name;
        this.number = number;
        this.image = image;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }




}
