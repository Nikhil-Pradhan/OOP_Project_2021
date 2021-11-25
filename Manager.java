import java.util.*;

public class Manager {
    // handles all activities of the software

    private List<User> users = new ArrayList<User>();       //stores the list of all users
    private int noOfUsers = 0;                              //stores the total no. of users
    private List<Group> groups = new ArrayList<Group>();    //stores all the groups
    private int noOfGroups = 0;                             //stores the number of groups

    
    //getter method for noOfUsers
    public int getNoOfUsers() {

        return noOfUsers;
    }

    //getter method for noOfGroups
    public int getNoOfGroups() {

        return noOfGroups;
    }

    //method that creates a new user
    public void createUser(String name, String password) {

        noOfUsers ++;
        users.add(new User(noOfUsers - 1,name,password));
        System.out.println("Your ID is " + (noOfUsers - 1));
        this.modifyOwesDuringInitialisation();
    }

    //method that adds 0 at the end of the "owes" array of every pre-existing user whenever a new user is create
    private void modifyOwesDuringInitialisation(){

        for (int i = 0; i < noOfUsers - 1; i ++)
            users.get(i).setOwes(noOfUsers - 1,0.0f);   
    }

    // method that creates a new group
    public void createGroup(List<Integer> IDs) {

        int len = IDs.size();
        
        Collections.sort(IDs);

        // checking if all user IDs are valid
        if (IDs.get(0) < 0 || IDs.get(len - 1) >= noOfUsers){
            System.out.println("User does not exist");
            return;
        }

        noOfGroups++;
        groups.add(new Group(noOfGroups-1,IDs));
        System.out.println("Your group ID is " + (noOfGroups - 1));
    }

    // method that adds a new user to the group
    public void joinGroup(int group_ID, int user_ID) {

        // checking if user and group IDs are valid
        if (group_ID >= noOfGroups || group_ID < 0)
            System.out.println("Group with this id does not exist");

        else if (user_ID >= noOfUsers || user_ID < 0)
            System.out.println("User with this id does not exist");

        else if (users.get(user_ID).authenticate()) {
            groups.get(group_ID).addUser(user_ID);
        }
    }

    // method that removes a user from the group
    public void leaveGroup(int group_ID, int user_ID) {

        //checking if user and group IDS are valid
        if (group_ID >= noOfGroups || group_ID < 0)
            System.out.println("Group with this id does not exist");

        else if (user_ID >= noOfUsers || user_ID < 0)
            System.out.println("User with this id does not exist");
            
        else if (users.get(user_ID).authenticate()) {
            groups.get(group_ID).removeUser(user_ID);
            System.out.println("New List of Group member's IDs " + groups.get(group_ID).getMembers());
        }
    }

    // method that showes who owes who how much for every user
    public void showBalance() {

        int i,j;

        for (i = 0; i < noOfUsers - 1; i ++) {

            for (j = i + 1; j < noOfUsers; j ++) {

                System.out.println(users.get(i).getName() + " (ID = " + users.get(i).getID() + ") owes " + users.get(j).getName() + " (ID = " + users.get(j).getID() + ") " + users.get(i).getOwes(j));
            }
        }
    }

    // method that shows how much a particular user owes all the other users
    public void showBalance(int id) {

        //checking if user ID is valid
        if (id >= noOfUsers || id < 0)
            System.out.println("User with this id does not exist");

        else {
            int i;

            for (i = 0; i < noOfUsers; i ++) {

                if (i == id)
                    continue;
                
                System.out.println(users.get(id).getName() + " (ID = " + users.get(id).getID() + ") owes " + users.get(i).getName() + " (ID = " + users.get(i).getID() + ") " + users.get(id).getOwes(i));
            }
        }
    }

    // method that displays the name and ID of all users
    public void displayAllUsers() {

        System.out.println("ID\tName");

        for (int i = 0; i < noOfUsers; i ++) {

            System.out.println(i + "\t" + users.get(i).getName());
        }
    }

    // method that displays the members of all groups
    public void displayAllGroups() {

        System.out.println("ID\tMembers");

        for (int i = 0; i < noOfGroups; i ++) {

            System.out.println(i + "\t" + groups.get(i).getMembers());
        }
    }

    // method that modifies the owes field when one user returns some money to another user
    public void pay (int payer_ID, int receiver_ID, float amt) {

        //checking if user ID is valid
        if (payer_ID >= noOfUsers || payer_ID < 0 || receiver_ID >=noOfUsers || receiver_ID < 0)
            System.out.println("User with this id does not exist");

        else if (users.get(payer_ID).authenticate() && users.get(receiver_ID).authenticate()){
            users.get(payer_ID).setOwes(receiver_ID, users.get(payer_ID).getOwes(receiver_ID) - amt);
            users.get(receiver_ID).setOwes(payer_ID, users.get(receiver_ID).getOwes(payer_ID) + amt);
            System.out.println("Payment successful");
        }
    }

    //converts ArrayList of IDs to ArrayList of Users
    private List<User> convertIDToUser (List<Integer> IDs) {
        
        List<User> ListOfUsers = new ArrayList<User>(IDs.size());

        for (int i = 0; i < IDs.size(); i ++) {
            ListOfUsers.add(users.get(IDs.get(i)));
        }

        return ListOfUsers;
    }

    // method to split a bill evenly among all the people. The first user in the ArrayList is the one who paid
    public void split (ArrayList<Integer> IDs, float total_amt) {
        
        List<User> ListOfUsers = new ArrayList<User>(this.convertIDToUser(IDs));

        if (total_amt < 0){
            System.out.println("Total amount cannot be negative");
            return;
        }

        int i, n = ListOfUsers.size();
        float final_amt = total_amt/(float)(n - 1);

        for (i = 0; i < n; i ++) {

            if (!ListOfUsers.get(i).authenticate()) {
                System.out.println("Split Failed");
                return;
            }
        }

        for (i = 1; i < n; i ++) {

            ListOfUsers.get(i).setOwes(ListOfUsers.get(0).getID(),ListOfUsers.get(i).getOwes(ListOfUsers.get(0).getID()) + final_amt);
            ListOfUsers.get(0).setOwes(ListOfUsers.get(i).getID(),ListOfUsers.get(0).getOwes(ListOfUsers.get(i).getID()) - final_amt);
        }

        System.out.println("Split was successful");
    }

    // method to split a bill on the basis of exact amount provided. The first user in ListOfUsers is the one who paid.
    // The nth element in exact_amt corresponds to the (n+1)th element of LisOfUsers.
    public void split(ArrayList<Integer> IDs, ArrayList<Float> exact_amt) {

        List<User> ListOfUsers = new ArrayList<User>(this.convertIDToUser(IDs));
        
        int i, n = ListOfUsers.size();

        if (exact_amt.size() != (n - 1)) {
            System.out.println("Wrong input");
            return;
        }

        for (i = 0; i < n; i ++) {

            if (!ListOfUsers.get(i).authenticate()) {
                System.out.println("Split Failed");
                return;
            }
        }

        for (i = 0; i < n - 1; i ++) {

            if (exact_amt.get(i) < 0) {
                System.out.println("Exact amount has to be positive");
                return;
            }
        }

        for (i = 1; i < n; i ++) {

            ListOfUsers.get(i).setOwes(ListOfUsers.get(0).getID(),ListOfUsers.get(i).getOwes(ListOfUsers.get(0).getID()) + exact_amt.get(i - 1));
            ListOfUsers.get(0).setOwes(ListOfUsers.get(i).getID(),ListOfUsers.get(0).getOwes(ListOfUsers.get(i).getID()) - exact_amt.get(i - 1));
        }

        System.out.println("Split was successful");
    }

    // method to split a bill on the basis of percentage. The first user in ListOfUsers is the one who paid.
    // The nth element in exact_amt corresponds to the (n+1)th element of LisOfUsers.
    public void split (ArrayList<Integer> IDs, float total_amt, ArrayList<Float> percentage) {

        List<User> ListOfUsers = new ArrayList<User>(this.convertIDToUser(IDs));
        
        if (total_amt < 0){
            System.out.println("Total amount cannot be negative");
            return;
        }

        int i, n = ListOfUsers.size(), total_percent = 0;
        float exact_amt;

        if (percentage.size() != (n - 1)) {
            System.out.println("Wrong input");
            return;
        }

        for (i = 0; i < n; i ++) {

            if (!ListOfUsers.get(i).authenticate()) {
                System.out.println("Split Failed");
                return;
            }
        }

        for (i = 0; i < n - 1; i ++) {

            if (percentage.get(i) < 0) {
                System.out.println("Percentage has to be positive");
                return;
            }
            total_percent += percentage.get(i);
        }

        if (total_percent > 100) {
            System.out.println("Total percentage has to be less than 100");
            return;
        }

        for (i = 1; i < n; i ++) {

            exact_amt = total_amt * (percentage.get(i - 1) / (float)100);
            ListOfUsers.get(i).setOwes(ListOfUsers.get(0).getID(),ListOfUsers.get(i).getOwes(ListOfUsers.get(0).getID()) + exact_amt);
            ListOfUsers.get(0).setOwes(ListOfUsers.get(i).getID(),ListOfUsers.get(0).getOwes(ListOfUsers.get(i).getID()) - exact_amt);
        }

        System.out.println("Split was successful");
    }

    // converts a group into a list of users so that we can avoid rewriting code
    public ArrayList<Integer> convertToNonGroup(Group g, int userWhoPaid) {

        ArrayList<Integer> IDs = new ArrayList<Integer>();

        IDs.add(userWhoPaid);

        for (int i = 0; i < g.size(); i ++) {
            
            if (g.getMembers().get(i) != userWhoPaid)
                IDs.add(g.getMembers().get(i)); 
        }

        return IDs;
    } 

    // method to split a bill evenly among all the people in the group.
    public void split (int group_ID, int userWhoPaid, float total_amt) {

        // checking if group ID is valid
        if (group_ID < 0 || group_ID >= noOfGroups) {
            System.out.println("Group does not exist");
            return;
        }

        Group g = groups.get(group_ID);

        ArrayList<Integer> IDs = new ArrayList<Integer>(this.convertToNonGroup(g,userWhoPaid));

        if (g.size() != IDs.size()) {
            System.out.println("The given id does not belong to the group");
            return;
        }

        this.split(IDs,total_amt);
    }

    // method to split a bill on the basis of exact amount provided.
    // exact_amt is in ascending order of ids of group members
    public void split(int group_ID, int userWhoPaid, ArrayList<Float> exact_amt) {

        
        // checking if group ID is valid
        if (group_ID < 0 || group_ID >= noOfGroups) {
            System.out.println("Group does not exist");
            return;
        }

        Group g = groups.get(group_ID);

        ArrayList<Integer> IDs = new ArrayList<Integer>(this.convertToNonGroup(g,userWhoPaid));

        if (g.size() != IDs.size()) {
            System.out.println("The given id does not belong to the group");
            return;
        }

        this.split(IDs,exact_amt);
    }

    // method to split a bill on the basis of exact amount provided.
    // percentage is in ascending order of ids of group members
    public void split (int group_ID, int userWhoPaid, float total_amt, ArrayList<Float> percentage) {

        // checking if group ID is valid
        if (group_ID < 0 || group_ID >= noOfGroups) {
            System.out.println("Group does not exist");
            return;
        }

        Group g = groups.get(group_ID);

        ArrayList<Integer> IDs = new ArrayList<Integer>(this.convertToNonGroup(g,userWhoPaid));

        if (g.size() != IDs.size()) {
            System.out.println("The given id does not belong to the group");
            return;
        }

        this.split(IDs,total_amt,percentage);
    }
}