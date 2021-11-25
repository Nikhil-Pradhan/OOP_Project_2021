import java.util.*;

public class Group {
    // stores information on all groups
    
    private List<Integer> members; //ArrayList of IDs of group members

    // Constructor used for initialising the instance variables of a new group object
    public Group(int grp_ID, List<Integer> IDs){

        members = new ArrayList<Integer>(IDs);

        //if the same ID is accidentaly entered multiple times, the redundancy will be removed
        for (int i = 0; i < IDs.size() - 1; i ++) {            
            
            if (IDs.get(i) == IDs.get(i + 1)) {
                IDs.remove(i+1);
            }
        }
    }

    //returns the ids of all members
    public List<Integer> getMembers() {
        
        return members;
    }

    //returns the number of members in the group
    public int size() {
        
        return members.size();
    }

    //adds a user to the group
    public void addUser(int ID) {

        //checking if a user is already in the group
        if (Collections.binarySearch(members,ID) >= 0)
            System.out.println("User is already in the group");

        else {
            members.add(ID);
            Collections.sort(members);
            System.out.println("New List of Group member's IDs " + this.members);
        }
    }

    //removes a user from the group
    public void removeUser(int ID) {

        int index = Collections.binarySearch(members,ID);

        //checking if user is in the group. If he is not in the group, it is impossible to remove him from the group
        if (index < 0)
            System.out.println("User is not in the group");

        else
            members.remove(index);
    }
}