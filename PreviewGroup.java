package fixtures; /**
 * Created by user pc on 04/04/2017.
 */

/**
 * Created by user pc on 17/02/2017.
 */
import sun.audio.*;


import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;


public class PreviewGroup extends JFrame {

    //To make the champions league official theme play when the preview form is loaded
    public static void music() { //Procedure music
        AudioStream ChampionsLeague; //Define audio stream


        try {
            InputStream test = new FileInputStream("cl.wav");
            ChampionsLeague = new AudioStream(test);
            AudioPlayer.player.start(ChampionsLeague);


        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }


    }


    private Image bgImage;
    private Icon Teams11;



    public PreviewGroup() {
        super("SuperLeague");

        music(); //Calling procedure music to play the champion's league hyme


        try {
            bgImage = ImageIO.read(new File("Champions-League.jpg"));


        } catch (IOException e) {
            e.printStackTrace();
        }



        JLabel[] Team = new JLabel[]{ //Array of label to store the team names


                //Array of labels for Group A
                new JLabel(GenerateFixtures.sendGroup(0)),
                new JLabel(GenerateFixtures.sendGroup(1)),
                new JLabel(GenerateFixtures.sendGroup(2)),
                new JLabel(GenerateFixtures.sendGroup(3)),

                //Array of labels for Group B
                new JLabel(GenerateFixtures.sendGroup(8)),
                new JLabel(GenerateFixtures.sendGroup(9)),
                new JLabel(GenerateFixtures.sendGroup(10)),
                new JLabel(GenerateFixtures.sendGroup(11)),

                //Array of labels for Group C
                new JLabel(GenerateFixtures.sendGroup(16)),
                new JLabel(GenerateFixtures.sendGroup(17)),
                new JLabel(GenerateFixtures.sendGroup(18)),
                new JLabel(GenerateFixtures.sendGroup(19)),

                //Array of labels for Group D
                new JLabel(GenerateFixtures.sendGroup(24)),
                new JLabel(GenerateFixtures.sendGroup(25)),
                new JLabel(GenerateFixtures.sendGroup(26)),
                new JLabel(GenerateFixtures.sendGroup(27)),

                //Array of labels for Group E
                new JLabel(GenerateFixtures.sendGroup(4)),
                new JLabel(GenerateFixtures.sendGroup(5)),
                new JLabel(GenerateFixtures.sendGroup(6)),
                new JLabel(GenerateFixtures.sendGroup(7)),

                //Array of labels for Group F
                new JLabel(GenerateFixtures.sendGroup(12)),
                new JLabel(GenerateFixtures.sendGroup(13)),
                new JLabel(GenerateFixtures.sendGroup(14)),
                new JLabel(GenerateFixtures.sendGroup(15)),

                //Array of labels for Group G
                new JLabel(GenerateFixtures.sendGroup(20)),
                new JLabel(GenerateFixtures.sendGroup(21)),
                new JLabel(GenerateFixtures.sendGroup(22)),
                new JLabel(GenerateFixtures.sendGroup(23)),

                //Array of labels for Group H
                new JLabel(GenerateFixtures.sendGroup(28)),
                new JLabel(GenerateFixtures.sendGroup(29)),
                new JLabel(GenerateFixtures.sendGroup(30)),
                new JLabel(GenerateFixtures.sendGroup(31)),


        };


        JPanel p1 = new JPanel() { //Create JPanel
            @Override
            protected void paintComponent(Graphics g) { //Procedure to add background picture

                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, null);
            }
        };

        add(p1);
        p1.setLayout(null);


        //Defining font to be used by labels
        Font myFont = new Font("Serif", Font.PLAIN, 18);
        for (int i = 0; i < 32; i++) { //Adding labels to panel, Setting the color and font

            Teams11 = new ImageIcon(new ImageIcon(SelectTeam.GetNames
                    (Team[i].getText())).getImage().getScaledInstance(20, 25, Image.SCALE_DEFAULT));

            Teams11.getIconHeight();



            p1.add(Team[i]); //Add label Team(i) in the panel p1
            Team[i].setIcon(Teams11);
            Team[i].setForeground(Color.white); //set color of label white
            Team[i].setFont(myFont); //set font of label

        }


        //set position of Group A
        Team[0].setBounds(125, 50, 200, 30);


        Team[1].setBounds(125, 75, 200, 30);


        Team[2].setBounds(125, 100, 200, 30);


        Team[3].setBounds(125, 125, 200, 30);


        //set position of Group B
        Team[4].setBounds(125, 230, 200, 30);


        Team[5].setBounds(125, 255, 200, 30);


        Team[6].setBounds(125, 280, 200, 30);


        Team[7].setBounds(125, 305, 200, 30);



        //set position of Group C
        Team[8].setBounds(125, 425, 200, 30);

        Team[9].setBounds(125, 450, 200, 30);


        Team[10].setBounds(125, 475, 200, 30);


        Team[11].setBounds(125, 500, 200, 30);

        //set position of Group D
        Team[12].setBounds(125, 610, 200, 30);


        Team[13].setBounds(125, 635, 200, 30);


        Team[14].setBounds(125, 660, 200, 30);


        Team[15].setBounds(125, 685, 200, 30);


        //set position of Group E
        Team[16].setBounds(800, 50, 200, 30);


        Team[17].setBounds(800, 75, 200, 30);


        Team[18].setBounds(800, 100, 200, 30);


        Team[19].setBounds(800, 125, 200, 30);


        //set position of Group F
        Team[20].setBounds(800, 230, 200, 30);


        Team[21].setBounds(800, 255, 200, 30);


        Team[22].setBounds(800, 280, 200, 30);


        Team[23].setBounds(800, 305, 200, 30);


        //set position of Group G
        Team[24].setBounds(800, 425, 200, 30);


        Team[25].setBounds(800, 450, 200, 30);


        Team[26].setBounds(800, 475, 200, 30);


        Team[27].setBounds(800, 500, 200, 30);


        //set position of Group H
        Team[28].setBounds(800, 610, 200, 30);


        Team[29].setBounds(800, 635, 200, 30);


        Team[30].setBounds(800, 660, 200, 30);


        Team[31].setBounds(800, 685, 200, 30);

    }




}






