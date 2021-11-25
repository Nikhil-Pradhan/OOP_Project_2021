import java.io.*;
import java.util.*;

public class Main {
    //takes input from the user and calls relevant methods

    public static void main(String args[]){
        
        BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
        Manager mgr = new Manager();

        System.out.println("-------------------------------------------------------------- COMMAND SYNTAX ------------------------------------------------------------");
        System.out.println("CREATE USER <Name> <Password>");
        System.out.println("CREATE GROUP <Space separated list of user ids>");
        System.out.println("JOIN <Group ID> <User ID>");
        System.out.println("LEAVE <Group ID> <User ID>");
        System.out.println("SHOW ALL");
        System.out.println("SHOW <User ID>");
        System.out.println("SHOW ALL USERS");
        System.out.println("SHOW ALL GROUPS");
        System.out.println("PAY <ID of Payer> <ID of Receiver> <amount>");
        System.out.println("EXPENSE <ID of User who paid> <No. of users> <space separated list of user ids> EQUAL <Total Amount>");
        System.out.println("EXPENSE <ID of User who paid> <No. of users> <space separated list of user ids> EXACT <Space separated exact amounts>");
        System.out.println("EXPENSE <ID of User who paid> <No. of users> <space separated list of user ids> PERCENT <Total Amount> <Space separated percentage (excluding user who paid)>");
        System.out.println("EXPENSE GROUP <Group ID> <ID of user who paid> EQUAL <Total Amount>");
        System.out.println("EXPENSE GROUP <Group ID> <ID of user who paid> EXACT <Space separated exact amounts (excluding user who paid)>");
        System.out.println("EXPENSE GROUP <Group ID> <ID of user who paid> PERCENT <Total Amount> <Space separated list of percentages (excluding user who paid)>");
        System.out.println("END\n");

        while (true) {

            aa: //breakpoint for going to the next input if the current input is invalid

            //try catch block used for catching inconsistencies in input
            try {
                String[] input = br.readLine().split(" ");  // command is stored as an array of strings
                
                //if else-if block for checking validiy of commands
                if (input[0].equals("CREATE")) {

                    if (input[1].equals("USER")) {

                        //checking if the command is valid
                        if (input.length != 4) {
                            System.out.println("Invalid command");
                            break aa;
                        }

                        mgr.createUser(input[2],input[3]);
                    }

                    else if (input[1].equals("GROUP")) {

                        List<Integer> grp_members = new ArrayList<Integer>(input.length - 2);

                        for (int i = 2; i < input.length; i ++) {
                            grp_members.add(Integer.parseInt(input[i]));
                        }

                        mgr.createGroup(grp_members);
                    }

                    // if the word after "CREATE" is neither "USER" nor "GROUP", the command is invalid
                    else {
                        System.out.println("Invalid command");
                        break aa;
                    }
                }

                else if (input[0].equals("JOIN")) {

                    //checking if the command is valid
                    if(input.length != 3) {
                        System.out.println("Invalid command");
                        break aa;
                    }
                    
                    int user_ID = Integer.parseInt(input[1]);
                    int group_ID = Integer.parseInt(input[2]);
                    mgr.joinGroup(user_ID,group_ID);
                }

                else if (input[0].equals("LEAVE")) {

                    //checking if the command is valid
                    if(input.length != 3) {
                        System.out.println("Invalid command");
                        break aa;
                    }
                    
                    int user_ID = Integer.parseInt(input[1]);
                    int group_ID = Integer.parseInt(input[2]);
                    mgr.leaveGroup(user_ID,group_ID);
                }

                else if (input[0].equals("SHOW")) {
                    
                    if (input.length == 2) {

                        if (input[1].equals("ALL")) 
                            mgr.showBalance();

                        else 
                            mgr.showBalance(Integer.parseInt(input[1]));
                    }

                    else if (input.length == 3) {

                        if (input[1].equals("ALL")) {

                            if (input[2].equals("USERS")) {

                                mgr.displayAllUsers();
                            }

                            else if (input[2].equals("GROUPS")) {

                                mgr.displayAllGroups();

                            }

                            else
                                System.out.println("Invalid command");
                        }

                        else 
                            System.out.println("Invalid command");
                    }

                    else
                        System.out.println("Invalid command");
                }

                else if (input[0].equals("PAY")) {

                    //checking if command is valid
                    if (input.length != 4) {
                        System.out.println("Invalid command");
                        break aa;
                    }

                    else
                        mgr.pay(Integer.parseInt(input[1]),Integer.parseInt(input[2]),Float.parseFloat(input[3]));

                }
                
                else if (input[0].equals("EXPENSE")) {

                    if (input[1].equals("GROUP")) {

                        int group_ID = Integer.parseInt(input[2]);

                        int userWhoPaid = Integer.parseInt(input[3]);

                        //checking if userwhopaid exists
                        if (userWhoPaid < 0 || userWhoPaid >= mgr.getNoOfUsers()) {
                            System.out.println("User does not exist");
                            break aa;
                        }

                        if (input[4].equals("EQUAL")) {

                            //checking if the command is valid
                            if (input.length != 6)
                                System.out.println("Invalid command");

                            float total_amt = Float.parseFloat(input[5]);

                            mgr.split(group_ID,userWhoPaid,total_amt);
                        }

                        else if (input[4].equals("EXACT")) {

                            ArrayList<Float> exact_amt = new ArrayList<Float>();

                            for (int i = 5; i < input.length; i ++) {

                                exact_amt.add(Float.parseFloat(input[i]));
                            }

                            mgr.split(group_ID,userWhoPaid,exact_amt);
                        }

                        else if (input[4].equals("PERCENT")) {

                            ArrayList<Float> percentage = new ArrayList<Float>();

                            float total_amt = Integer.parseInt(input[5]);

                            for (int i = 6; i < input.length; i ++) {

                                percentage.add(Float.parseFloat(input[i]));
                            }

                            mgr.split(group_ID,userWhoPaid,total_amt,percentage);
                        }

                        //if split is anything other than "EXACT" "EQUAL" or "PERCENT", command is invalid
                        else {
                            System.out.println("Invalid command");
                            break aa;
                        }
                    }

                    else {
                        int userWhoPaid = Integer.parseInt(input[1]);

                        //checking if user ID is valid
                        if (userWhoPaid < 0 || userWhoPaid >= mgr.getNoOfUsers()){
                            System.out.println("User does not exist");
                            break aa;
                        }

                        int noOfUsers = Integer.parseInt(input[2]);

                        ArrayList<Integer> IDs = new ArrayList<Integer>(noOfUsers);

                        IDs.add(userWhoPaid);

                        for (int i = 0; i < noOfUsers; i ++) {
                            int nextInput = Integer.parseInt(input[2+i]);

                            //checking if user IDs are valid
                            if (nextInput < 0 || nextInput >= mgr.getNoOfUsers()){
                                System.out.println("User does not exist");
                                break aa;
                            }
                            IDs.add(Integer.parseInt(input[3 + i]));
                        }

                        if (input[3 + noOfUsers].equals("EQUAL")) {

                            //checking if command is valid
                            if (input.length != (5 + noOfUsers))
                                System.out.println("Invalid command");

                            float total_amt = Float.parseFloat(input[4 + noOfUsers]);

                            mgr.split(IDs,total_amt);
                        }

                        else if (input[3 + noOfUsers].equals("EXACT")) {

                            ArrayList<Float> exact_amt = new ArrayList<Float>();

                            for (int i = (4 + noOfUsers); i < input.length; i ++) {

                                exact_amt.add(Float.parseFloat(input[i]));
                            }

                            mgr.split(IDs,exact_amt);
                        }

                        else if (input[3 + noOfUsers].equals("PERCENT")) {

                            ArrayList<Float> percentage = new ArrayList<Float>();

                            float total_amt = Integer.parseInt(input[4 + noOfUsers]);

                            for (int i = 5 + noOfUsers; i < input.length; i ++) {

                                percentage.add(Float.parseFloat(input[i]));
                            }

                            mgr.split(IDs,total_amt,percentage);
                        }

                        //if split is anything other than "EXACT" "EQUAL" or "PERCENT", commmand is invalid
                        else {
                            System.out.println("Invalid command");
                            break aa;
                        }
                    }

                }

                else if (input[0].equals("END")) {
                    break;
                }

                //command should only start with "CREATE", "JOIN", "LEAVE", "SHOW", "PAY", "EXPENSE" or "END"
                else 
                    System.out.println("Invalid command");
            }

            catch (Exception e) {
                System.out.println("Invalid command");
            }
        }
    }
}