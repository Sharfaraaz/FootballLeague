package fixtures; /**
 * Created by user pc on 31/03/2017.
 */



import database.DatabaseConnection;

import java.sql.*;
import java.awt.GridLayout;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.DefaultListModel;
import java.util.HashSet; //Import hashset to use
import java.util.Random; //import random to randomize number

public class GenerateFixtures  extends JFrame {
    //Declaring buttons to use in the Generate Fixtures Form
    private JButton GO;
    private JButton Save;
    private JButton Close;
    private JButton Preview;

    Connection conn;
    Connection conn1;

    public static int Seasons;

    // Declare listbox Jlist
    public JList<String> TeamJList;
    //Declaring arays which will be used to store number and Team Name
    public static int number[] = new int[32];
    public static String TeamName[] = new String[40];

    //Setting dimensions for buttons and listbox
    Dimension d = new Dimension(125, 50);
    Dimension d1 = new Dimension(480, 200);




    public GenerateFixtures() {
        super("Generate Fixtures"); //Name on form


        conn = DatabaseConnection.getConnection();
        conn1 = DatabaseConnection.getConnection();
        JPanel p1 = new JPanel();
        JPanel p5 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p = new JPanel();






//set layout for the various panels
        p2.setLayout(new GridLayout(2, 1, 0, -170));
        p.setLayout(new FlowLayout());
        p5.setLayout(new FlowLayout());
        p1.setLayout(new GridLayout(2, 2, 10, 10));


        p2.add(p5);
        p.add(p2);
        p.add(p1);
        add(p);

        try {
            GetSeason();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JLabel Season = new JLabel("Season" + " "+ Seasons); //Adding Jlabel to show which season's fixtures are being made
        GO = new JButton("Go");
        Save = new JButton("Save");
        Close = new JButton("Close");
        Preview = new JButton("Preview");


        GenerateFixtures.ButtonGO handler = new GenerateFixtures.ButtonGO(); //Triggers action after button go is clicked
        GO.addActionListener(handler);
        DefaultListModel<String> ListModel = new DefaultListModel<>();


        //Adding Teams will appear here in Listbox TeamJlist via ListModel
        ListModel.addElement("Click GO to display the different groups");

        TeamJList = new JList<>(ListModel);

        GenerateFixtures.ButtonClose handle = new GenerateFixtures.ButtonClose(); //Triggers action after button Close is clicked
        Close.addActionListener(handle);

        GenerateFixtures.ButtonSave handles = new GenerateFixtures.ButtonSave(); //Triggers action after button Save is clicked
        Save.addActionListener(handles);

        GenerateFixtures.ButtonPreview handled = new GenerateFixtures.ButtonPreview(); //Triggers action after button Save is clicked
        Preview.addActionListener(handled);


        TeamJList.setVisibleRowCount(10);// Letting 10 Items appear in listbox
        TeamJList.setLayoutOrientation(JList.VERTICAL_WRAP); //Setting layout orientation to vertical wrap

//Setting Fixed cell width and height to adjust the data in the listbox TeamJlist
        TeamJList.setFixedCellHeight(20);
        TeamJList.setFixedCellWidth(1000);
        //Setting Selected field in the listbox to color cyan
        TeamJList.setSelectionBackground(Color.cyan);


//Set the size of buttons to d which is 125 width and 50 height
        //set the size of the listbox to d1 which is 480 width and 200 height
        GO.setPreferredSize(d);
        Save.setPreferredSize(d);
        Close.setPreferredSize(d);
        Preview.setPreferredSize(d);

        TeamJList.setPreferredSize(d1);


        p5.add(Season);      //Add Label Season

        p2.add(TeamJList);  //Add listbox

        //Adding the three buttons on the form

        p1.add(GO);


        p1.add(Close);


        p1.add(Save);

        p1.add(Preview);


        //Disabling some buttons which must not be clicked on before button go is clicked to avoid errors
        Save.setEnabled(false);
        Preview.setEnabled(false);


    }

    private class ButtonGO implements ActionListener { //Action for button Go
        public void actionPerformed(ActionEvent event) {

            TeamJList.setFixedCellWidth(120);

            RefreshList(); //Calling procedure Refresh list


            //Allowing user to click on the buttons Save and Clear to either save the teams and groups in the database
            //or to allow the user to clear the listbox
            Save.setEnabled(true);
            Preview.setEnabled(true);

        }
    }

    private class ButtonPreview implements ActionListener { //Action for button Go
        public void actionPerformed(ActionEvent event) {

            PreviewGroup Group= new PreviewGroup();

            Group.setSize(1024,770);
            Group.setVisible(true);
            Group.setResizable(false);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



        }
    }

    private class ButtonClose implements ActionListener { //Action for button Close
        public void actionPerformed(ActionEvent event) {
            int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to close",
                    "SuperLeague", JOptionPane.YES_NO_OPTION); //Ask if user want to close
            //If User really wants to close the program
            if (reply == JOptionPane.YES_OPTION) {

                System.exit(0); //Exit the form

            }

        }
    }

    private class ButtonSave implements ActionListener { //Action for button Save
        public void actionPerformed(ActionEvent event) {

            try {


                for (int i = 0; i < 32; i++) {

                    PreparedStatement pst=conn.prepareStatement(Save(i)); //Call procedure save with number i

                    //Save Season 1 and TeamName[i] in database
                    pst.setInt(1,Seasons);
                    pst.setString(2,TeamName[i]);
                    pst.executeUpdate();


                }
                }catch (SQLException e) {

                JOptionPane.showMessageDialog(GenerateFixtures.this, "Not Successful");
                e.printStackTrace();
                }


            JOptionPane.showMessageDialog(GenerateFixtures.this,String.format(
                    "Teams have been added to groups.", event.getActionCommand()
                    //Message to confirm that data has been saved
            ));


           Groups(); //Calling procedure groups to set and save the various matches

        }
    }

    public static String Save(int Number){
        String Statement= "";


        switch (Number){ //Switch case number to find out in which group must the team be saved

            //if the team is between 0 and 3, it must be saved in group a
            case  0: case 1: case 2: case 3: Statement= "Insert into groupa (Season, TeamName) values(?, ?)";
            break;
            case  4: case 5: case 6: case 7: Statement= "Insert into groupe (Season, TeamName) values(?, ?)";
            break;
            case  8: case 9: case 10: case 11: Statement= "Insert into groupb (Season, TeamName) values(?, ?)";
            break;
            case  12: case 13: case 14: case 15: Statement= "Insert into groupf (Season, TeamName) values(?, ?)";
            break;
            case  16: case 17: case 18: case 19: Statement= "Insert into groupc (Season, TeamName) values(?, ?)";
            break;
            case  20: case 21: case 22: case 23: Statement= "Insert into groupg (Season, TeamName) values(?, ?)";
            break;
            case  24: case 25: case 26: case 27: Statement= "Insert into groupd (Season, TeamName) values(?, ?)";
            break;
            case  28: case 29: case 30: case 31: Statement= "Insert into grouph (Season, TeamName) values(?, ?)";
            break;
        }



       return  Statement; //return statement to save buton to save the teams in the various group table in the database
    }

    public void RefreshList() { //Procedure to add the different groups as wel as the team they contain in the listbox
        GetFixtures(); //Call procedure Get Fixtures

        //Declaring Array selected to use it to highlight the various groups in the listbox
        int[] Selected = new int[8];

        //Initialising variable count
        int count = 0;

        //Remove all previous items in the listbox
        DefaultListModel listModel = (DefaultListModel) TeamJList.getModel();
        listModel.removeAllElements();

        //Using for loop to add items in the Listbox TeamJlist using the listModel
        for (int i = 0; i < 40; i++) {

            if (i == 0) {
                //Add Group A to Listbox
                listModel.addElement("Group A");

                //Adding Number 0 to Array Selected
                Selected[count] = i + count;
                count++;

            }
            if (i == 4) {
                //Add Group E to listbox
                listModel.addElement("Group E");

                //Adding Number 5 to Array Selected
                Selected[count] = i + count;
                count++;

            }
            if (i == 8) {

                //Add Group B to listbox
                listModel.addElement("Group B");

                //Adding Number 10 to Array Selected
                Selected[count] = i + count;
                count++;


            }
            if (i == 12) {

                //Add Group F to listbox
                listModel.addElement("Group F");


                //Adding Number 15 to Array Selected
                Selected[count] = i + count;
                count++;
            }
            if (i == 16) {
                //Add Group C to listbox
                listModel.addElement("Group C");
                //Adding Number 20 to Array Selected
                Selected[count] = i + count;
                count++;

            }
            if (i == 20) {
                //Add Group G to listbox
                listModel.addElement("Group G");
                //Adding Number 25 to Array Selected
                Selected[count] = i + count;
                count++;

            }
            if (i == 24) {
                //Add Group D to listbox
                listModel.addElement("Group D");
                //Adding Number 30 to Array Selected
                Selected[count] = i + count;
                count++;
            }
            if (i == 28) {

                //Add Group H to listbox
                listModel.addElement("Group H");
                //Adding Number 35 to Array Selected
                Selected[count] = i + count;
                count++;
            }

            //Add TeamNames at position i in the array TeamName in the listbox

            listModel.addElement(TeamName[i]);

        }

        //Highlight all items in the array selected;
        // Highlight all the different Groups
        TeamJList.setSelectedIndices(Selected);

    }

    public void GetFixtures() { //Procedure Get Fixtures
        GetRandom(32, 31); //Call procedure get random number


        for (int i = 0; i < 32; i++) {
            //Adding TeamNames in array TeamName
            TeamName[i] = (GetNames(number[i]));

        }
    }


    public static final Random Number = new Random();



//Static function to send the name of the teams in each groups to the preview group
    public static String sendGroup(int TeamNumber){

        String Team= (TeamName[TeamNumber]);

        return( Team);
    }




    public static void GetRandom(int n, int maxRange) {


        HashSet<Integer> Team = new HashSet<Integer>(); //Creating hashset Team

        for (int i = 0; i < n; i++) {

            int newRandom;

            do {
                newRandom = Number.nextInt(maxRange + 1) + 1;
            } while (Team.contains(newRandom));

            Team.add(newRandom); //Add number to hashset
            number[i] = newRandom; //Add Number new random at position i
        }


    }

    public static String GetNames(int Team) {
        String TeamName ;

       TeamName= SelectTeam.TeamParticipate[Team -1];

        return TeamName;
    }

    public void Groups() {

        //calling the procedure set match with different values for Number ,group and matchid
        //matchid will give id of the first match of the group
        //for example for group B, matchId 7 will be the id of the first match
        Setmatch(0, 4,1); //to get match from Group A
        Setmatch(8, 12,7); //to get match from Group B
        Setmatch(16, 20,13); //to get match from Group C
        Setmatch(24, 28,19); //to get match from Group D
        Setmatch(4,8,25); //to get match from Group E
        Setmatch(12,16,31); //to get match from Group F
        Setmatch(20,24,37);//to get match from Group G
        Setmatch(28,32,43);//to get match from Group H
    }

    public void Setmatch(int Number, int group, int MatchId) { //Procedure setmatch

        int x; //first value of team
        x = Number;

// loop to set 6 matches for the 8 different groups
        // group= last value of team present in same group
        for (int i = Number; i < group; i++) {
            for (int n = 0; n < 3; n++) {
                int a;
                a = i;
                if (i == x) { //get the matches for the first team against the next three teams



                    SaveMatch(TeamName[a],MatchId+2*n);
                    SaveMatch(TeamName[a+n+1],MatchId+2*n);
                    //TeamName[a]= 1st team in each group
                    //Team[a+n+1]= [Number of first team in group + position of team in the group + 1]
                    //Example if a= 0, TeamName[a] will give first team in group A
                    // TeamName[0+0+1] will give second team in Group A; n= 0 as it is the first time executing the loop
                    //Team[0+1+1] will give third team
                    //Team[0+2+1] will give the fourth team
                    //2*n is used to store the 1stmatch, the 3rd match and the 5th match in the database
                    //Calling procedure save match with TeamName[a] and MatchID+2*n to save the
                    // firstteam of the group with MatchId 1

                }

                if (i == Number + 1) {
                    //if i= the second team of the group
                    //for example group A: Number = 0 so if i = 1
                    // since the match oposing the first team and the second team of the group is already stored above;
                    //this if statement will cater only for the match opposing the second team vs the third and fourth team


                    if (n == 1) { //get the match of the second team vs the third team of the group
                        a++;

                        //matchId+ 5 to get the 6th match of the groups
                        SaveMatch(TeamName[i],MatchId+5);
                        SaveMatch(TeamName[a],MatchId+5);

                    }

                    if (n == 2) { //get the match of the second team vs the fourth team of the group
                        a = a + 2; //Increment a by two because previous value of a is lost during the loop

                        //matchId+ 1 to get the 4th match of the groups
                        SaveMatch(TeamName[i],MatchId+3);
                        SaveMatch(TeamName[a],MatchId+3);
                    }

                }
                if (i == Number + 2) { //if i== the third team of group

                    if (n == 2) { //get the match of the third team against the fourth team of the group
                        a = Number + 3; //to get last value of array TeamName


                        //matchId+ 1 to get the 2nd match of the groups
                        SaveMatch(TeamName[i],MatchId+1);
                        SaveMatch(TeamName[a],MatchId+1);


                    }

                }

            }
        }
    }


    public void GetSeason() throws SQLException {

        Statement stmt = null;
        String query = "SELECT * FROM `tournament` ORDER by Season DESC Limit 1";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                Seasons=(rs.getInt("Season"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    public void SaveMatch(String Team, int Num ){


        try {

                PreparedStatement pst=conn.prepareStatement("Insert into fixtures (id, TeamName,Season) values(?, ?,?)");
                pst.setInt(1,(Num));
                pst.setString(2,Team);
                pst.setInt(3,Seasons);

                pst.executeUpdate();


        }catch (SQLException e) {

            JOptionPane.showMessageDialog(GenerateFixtures.this, "Not Successful");
            e.printStackTrace();
        }

    }
}




