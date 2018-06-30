package fixtures;

/**
 * Created by user pc on 08/04/2017.
 */
import database.DatabaseConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.*;
import javax.xml.bind.annotation.XmlType;


public class SelectTeam extends JFrame {
    private JButton[] Teams;

    Connection conn;
    private JPanel p1 = new JPanel();
    public int rows;
    public String SelectedTeam ;
    public int Selectedid;
    static String[] TeamParticipate;
    ArrayList<String> strings = new ArrayList<String>();
    HashSet<String> Team = new HashSet<>();


    public SelectTeam() {

        super("Choose 32 teams");
        conn = DatabaseConnection.getConnection();
        add(p1);


        Statement Team = null;
        Statement logo = null;

        String query = "SELECT id, TeamName FROM team";


        try {


            Team = conn.createStatement();
            ResultSet rs = Team.executeQuery(query);
            rs.last();
            rows = rs.getRow();
            System.out.print(rows);

            int Columns;
            Columns = 0;
            Columns = rows / 5;


            p1.setLayout(new GridLayout(Columns, rows));
            Teams = new JButton[rows];
            for (int i = 0; i < rows; i++) {


                Teams[i] = new JButton();
                Teams[i].setPreferredSize(new Dimension(125, 50));

                p1.add(Teams[i]);


                Teams[i].addActionListener(actionListener);


            }
            populatebutton();


        } catch (SQLException e1) {
            System.out.println(e1.getMessage());

        }
    }




    public void populatebutton() throws SQLException {

        Statement stmt = null;
        String query = "select TeamName from team";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                strings.add(rs.getString("TeamName"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }

        for (int i = 0; i < strings.size(); i++) {

            Teams[i].setText(strings.get(i));
            Icon TeamsPicture = new ImageIcon(new ImageIcon(GetNames(Teams[i].getText())).
                    getImage().getScaledInstance(156, 100, Image.SCALE_DEFAULT)); //Set path to icon and re size the picture



            Teams[i].setToolTipText(strings.get(i));
            Teams[i].setIcon(TeamsPicture); //Add icon to button
            Teams[i].setForeground(new Color(255, 255, 255, 0));//set button foreground color and text color the saem
            //to make text appear invisible



        }


    }


    // private class ButtonHandler implements ActionListener{

    // public void actionPerformed(ActionEvent event){

    //System.out.println(ActionEvent.getActionCommand());

    //
    ActionListener actionListener = new ActionListener() { //Defining action listener
        public void actionPerformed(ActionEvent actionEvent) {

            SelectedTeam = actionEvent.getActionCommand();
            //actionEvent.getActionCommand gives the text found on the button
            //assigning Selected id to the name of button





            if (Team.contains(actionEvent.getActionCommand())) {

                Selectedid = Selectedid - 1;
                Team.remove(actionEvent.getActionCommand());
                for (int i=0; i<rows;i++){
                    if (Teams[i].getText()==actionEvent.getActionCommand()){
                        Teams[i].setBackground(null);
                    }
                }

            } else {
                Team.add(actionEvent.getActionCommand());


                for (int i=0; i<rows;i++){
                    if (Teams[i].getText()==actionEvent.getActionCommand()){
                        Teams[i].setBackground(Color.cyan);
                    }
                }
                Selectedid =  Selectedid + 1;


                if ( Selectedid == 32) {
                    int reply = JOptionPane.showConfirmDialog(null, "Are sure about selecting those teams",
                            "SuperLeague", JOptionPane.YES_NO_OPTION); //Ask if user want to close
                    //If User really wants to close the program
                    if (reply == JOptionPane.NO_OPTION) {

                        Selectedid = 0;
                        Team.clear();
                        for (int i=0; i<rows;i++){
                                Teams[i].setBackground(null);

                        }

                    } else {


                        //Converting hashset Team to array teamToPlay
                        String[] teamToPlay = new String[Team.size()];
                        TeamParticipating(Team.toArray(teamToPlay));
                        //Calling procedure TeamParticipating with parameters Array teamToPlay

//Opening Generate Fixtures form
                        GenerateFixtures form1 = new GenerateFixtures();
                        form1.setSize(900, 350);
                        form1.setVisible(true);
                        form1.setResizable(false);


//Closing Select Team form
                        dispose();


                    }
                }


            }
        }
    };


    public void TeamParticipating(String[] Teams) {

        TeamParticipate = Teams;

    }


    public static String GetNames(String Name) { //know which logo to assign to which Jpanel

        String path= "";

        switch (Name) { //switch name
            case "Liverpool": //if name is liverpool, add \\liverpool.png to make liverpool.png appear in Jpanel
                //same steps are repeated for other teams
                path = "Liverpool.png";

                break;
            case "Manchester United":
                path = "Manchester.png";

                break;
            case "Manchester City":
                path = "ManchesterCity.png";

                break;
            case "Chelsea":
                path = "Chelsea.png";

                break;
            case "Arsenal":
                path = "Arsenal.png";

                break;
            case "Leicester":
                path = "Leicester.png";

                break;
            case"Barcelona":
                path = "Barcelona.png";

                break;
            case "Real Madrid":
                path = "RealMadrid.png";

                break;
            case "Athletico Madrid":
                path = "AtleticoMadrid.png";

                break;
            case "Sevilla":
                path = "Sevilla.png";

                break;
            case "Bayern Munich":
                path = "Bayern.png";

                break;
            case "Dortmund":
                path = "Dortmund.png";

                break;
            case "Ac Milan":
                path = "ACMilan.png";

                break;
            case "Inter Milan":
                path = "InterMilan.png";

                break;
            case  "Juventus":
                path = "Juventus.png";

                break;
            case "Napoli":
                path = "Napoli.png";

                break;
            case "As Roma":
                path = "AsRoma.png";

                break;
            case "Fiorentina":
                path = "Fiorentina.png";

                break;
            case "Lazio":
                path = "Lazio.png";

                break;
            case "Torino":
                path = "Torino.png";

                break;
            case "Borussia M.glad":
                path = "M'Gladbach.png";

                break;
            case "Bayer Leverkusen":
                path ="BayerLeverkusen.png";

                break;
            case "Fc Copenhagen":
                path = "FCCopenhagen.png";

                break;
            case "Tottenham":
                path = "Tottenham.png";

                break;
            case "Shaktar Donesk":
                path = "ShakhtarDonetsk.png";

                break;
            case "Villareal":
                path = "Villarreal.png";

                break;
            case "Nice":
                path = "Nice.png";

                break;
            case "Paris St Germain":
                path = "PSG.png";

                break;
            case "Lyon":
                path = "Lyon.png";

                break;
            case  "Marseille":
                path = "Marseille.png";

                break;
            case "Monaco":
                path = "Monaco.png";

                break;
            case "Ajax Amsterdam":
                path = "Ajax.png";


                break;
            case "Anderlecht":
                path = "Anderlecht.png";
                break;
            case "Feyenoord":
                path = "Feyenoord.png";

                break;
            default: path= Name + ".png";


        }


        return path; //return path
    }

    public static void main(String[]args){ //Main

        SelectTeam form1= new SelectTeam();
        form1.setSize(900,900);
        form1.setVisible(true);
        form1.setResizable(false);


    }


}
