import java.util.*;
import java.io.*;

public class User {
    //stores all information on the user
    
    private int id;                                     //stores the id of the user
    private String name;                                //stores the name of the user
    private String password;                            //stores the password of the user
    private List<Float> owes = new ArrayList<Float>();  //stores the amount of money the user owes to each of the other users

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        //isActive = true;

        for (int i = 0; i <= id; i ++)
            owes.add(0.0f);

    }

    // Getter Methods-----------------------------

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getOwes(int index) {
        return owes.get(index);
    }

    //----------------------------------------------

    //Setter Methods---------------------------------

    public void setID(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwes(int index, float value) {
        if (index < owes.size())
            owes.remove(index);
        owes.add(index,value);
    }

    //-------------------------------------------------

    public boolean authenticate(){

        //chekcs if the password entered by the user is correct

        BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
        String input = "";

        System.out.println("Enter the password for user with ID: " + id + " (name = " + name + ").");

        try {

            input = br.readLine();
            
            if (input.equals(password)) {
                System.out.println("Authentication Successful");
                return true;
            }

            else {
                System.out.println("Wrong Password");
                return false;
            }
        }
        catch (IOException e){
            System.out.println("Wrong Password");
            return false;
        }
    }
}