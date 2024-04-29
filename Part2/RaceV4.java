package Part2;

import Part1.Horse; 
import java.util.concurrent.TimeUnit;
import java.lang.Math;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;




/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author Karim Abdelmoaty
 * @version v1 24/04/24
 */
public class RaceV4 extends Horse 
{
    private int raceLength;
    private static int selectedNumberOfHorses;
    private int selectedNumberOfTracks;
    private int trackLength; 
    private static int lengthRace; 
    private Horse [] laneHorses; 




    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */

    public RaceV4(int distance, int selectedNumberOfHorses)
    {
        raceLength = distance;
        laneHorses = new Horse[selectedNumberOfHorses];

        for (int i = 0; i < selectedNumberOfHorses; i++) {
            laneHorses[i] = new Horse(); 
        }

    }
    


    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */

    public void addHorse(Horse theHorse, int laneNumber)
    {
        laneHorses[laneNumber - 1] = theHorse; 
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     * @return 
     */

    public void startRace()
    {
        boolean finished = false;
        
        //reset all the lanes (all horses not fallen and back to 0).
        for (int i=0; i<=selectedNumberOfHorses-1; i++){
            laneHorses[i].goBackToStart();
        }
                      
        while (!finished) {
            for (Horse horse : laneHorses) {
                moveHorse(horse);
            }
            printRace(trackLength);
            if (raceWonBy()) {
                finished = true;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }

    }

    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */

    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */

    private boolean raceWonBy()
    {
        for (Horse horse : laneHorses) {
            if (horse.getDistanceTravelled() >= trackLength) {
                System.out.println("Well Done " + horse.getName() + "!");
                return true;
            }
        }
        return false; 
        
    }
    
    /***
     * Print the race on the terminal
     */
    
    private void printRace(int trackLength)
    {
        System.out.print('\u000C');  //clear the terminal window
        
        multiplePrint('=',trackLength+3); //top edge of track
        System.out.println();

        for (int j = 0; j <= selectedNumberOfHorses-1; j++){
            printLane(laneHorses[j]);
            System.out.print("   "+ laneHorses[j].getName() + ": Current Confidence: "+laneHorses[j].getConfidence());
            System.out.println(); 
        }
        
        System.out.println();

        multiplePrint('=',trackLength+3); //bottom edge of track
        System.out.println();    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */

    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        double spacesBefore = theHorse.getDistanceTravelled();
        double spacesAfter = trackLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print('\u2322');
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');

    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */

    private void multiplePrint(char aChar, double times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }

    public void raceGUI(){

        JFrame frame = new JFrame("Horse Simulation Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setResizable(true);



        ///////////////// Main Panel ////////////////////////


        JPanel mainPanel = new JPanel(new BorderLayout()); // Main panel 
        mainPanel.setBackground(Color.WHITE);



        ///////////////// Panel for dropDown menu ////////////////////////


        JPanel dropdownPanel = new JPanel(new FlowLayout()); // FlowLayout for dropdown
        dropdownPanel.setBackground(Color.WHITE);
    


        // Create a combo box for selecting the number of horses
        String[] horseOptions = {"2", "3", "4", "5", "6"}; // Number of horse options

        JComboBox<String> horseComboBox = new JComboBox<>(horseOptions);
        horseComboBox.setSelectedIndex(1); // Default selection at "3"

        horseComboBox.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                selectedNumberOfHorses = Integer.parseInt((String) horseComboBox.getSelectedItem());
                laneHorses = new Horse[selectedNumberOfHorses];
                System.out.println("Number of horses: " + selectedNumberOfHorses);

                for (int i = 0; i < selectedNumberOfHorses; i++) {
                    laneHorses[i] = new Horse(); 
                }
            }
        });

        dropdownPanel.add(new JLabel("Select number of horses:")); // Add label
        dropdownPanel.add(horseComboBox); // Add combo box




        ///////////////// Panel for Track slider ////////////////////////



        JPanel trackPanel = new JPanel(new FlowLayout()); // Flow layout for slider
        trackPanel.setBackground(Color.WHITE);

        JLabel trackLabel = new JLabel("Select Length of Track:"); // Create label for the slider
        trackLabel.setHorizontalAlignment(JLabel.CENTER); // Center align the label

        JSlider trackSlider = new JSlider();
        trackSlider.setMinimum(5);
        trackSlider.setMaximum(35);
        trackSlider.setValue(10);
        trackSlider.setMajorTickSpacing(1);
        trackSlider.setPaintTicks(true); // Set to true to paint ticks
        trackSlider.setPaintLabels(true); // Set to true to paint labels
        trackSlider.setSnapToTicks(true); // Snap to the nearest tick value

        trackSlider.addChangeListener(new ChangeListener() 
        {
            public void stateChanged(ChangeEvent e) 
            {
                trackLength = trackSlider.getValue(); // Convert slider value to double (0.1 - 0.9)
                printRace(trackLength); 
                lengthRace = trackLength; 
                //laneHorses[horseIndex].setConfidence(selectedConfidence); // Update the current horse's confidence level
                System.out.println("Track Length: " + trackLength);

                // Horse horse = new Horse(getSymbolForHorse(horseIndex), "Horse " + (horseIndex + 1), selectedConfidence);
                // addHorse(horse, horseIndex + 1);

            }
        });
        // Set preferred width
        trackSlider.setPreferredSize(new Dimension(700, trackSlider.getPreferredSize().height)); 
        trackPanel.add(trackLabel);
        trackPanel.add(trackSlider);

        // trackLength = trackSlider.getValue();
        // lengthRace = trackLength; 
        // System.out.println(trackLength);
        //System.out.println("Number of horses: " + selectedNumberOfHorses);
        // System.out.println("Number of tracks: " + selectedNumberOfTracks);





        /////////// Panel for button //////////////



        //////////////button 2 panel////////////

        JPanel button2Panel = new JPanel(new FlowLayout()); // FlowLayout for button
        button2Panel.setBackground(Color.WHITE);


        JButton GUIButton = new JButton("Confidence Level Selector");
        GUIButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                raceGUI2();   
            }
        });
        
        button2Panel.add(GUIButton);

        mainPanel.add(dropdownPanel, BorderLayout.NORTH); // Add dropdown panel
        mainPanel.add(trackPanel, BorderLayout.CENTER); // Add slider panel
        //mainPanel.add(buttonPanel, BorderLayout.SOUTH); // Add button panel
        mainPanel.add(button2Panel, BorderLayout.SOUTH); 
 
        frame.add(mainPanel); // Add main panel to the frame
 
 
 
        //System.out.println("GUI Loaded");
        frame.setVisible(true);
 
    }



    public void raceGUI2(){

        JFrame frame = new JFrame("Horse Simulation Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setResizable(true);




        ///////// Panel for horse confidence level ///////////
 



        JPanel confidencePanel = new JPanel(new GridLayout(selectedNumberOfHorses, 1)); 
        confidencePanel.setBackground(Color.BLACK);

        // Create an array to hold confidence sliders for each horse
        JSlider[] confidenceSliders = new JSlider[selectedNumberOfHorses];
         

        for (int i = 0; i < selectedNumberOfHorses; i++) 
        {
            JPanel horseConfidencePanel = new JPanel(new FlowLayout()); 
            horseConfidencePanel.setBackground(Color.WHITE);

            JLabel horseLabel = new JLabel("Horse " + (i + 1) + " Confidence Level:");
            horseLabel.setHorizontalAlignment(JLabel.CENTER); 

            // Create a slider for selecting confidence level for the current horse
            //JSlider confidenceSlider = new JSlider(JSlider.HORIZONTAL, 1, 9, 5); // Values from 0.1 to 0.9 with step 0.1
            JSlider confidenceSlider = new JSlider();
            confidenceSlider.setMinimum(1);
            confidenceSlider.setMaximum(9);
            confidenceSlider.setValue(5);
            confidenceSlider.setMajorTickSpacing(1);
            confidenceSlider.setPaintTicks(true); 
            confidenceSlider.setPaintLabels(true); 
            confidenceSlider.setSnapToTicks(true); // Snap to the nearest tick value

            final int horseIndex = i; // Required for using inside inner class

            // Add ChangeListener to the confidence slider
            confidenceSlider.addChangeListener(new ChangeListener() 
            {
                public void stateChanged(ChangeEvent e) 
                {
                    double selectedConfidence = confidenceSlider.getValue() / 10.0; // Convert slider value to double (0.1 - 0.9)
                    laneHorses[horseIndex].setConfidence(selectedConfidence); // Update the current horse's confidence level
                    System.out.println("Confidence Level: " + selectedConfidence);

                    Horse horse = new Horse(getSymbolForHorse(horseIndex), "Horse " + (horseIndex + 1), selectedConfidence);
                    addHorse(horse, horseIndex + 1);

                }
            });
            
            horseConfidencePanel.add(horseLabel);
            horseConfidencePanel.add(confidenceSlider);

            confidencePanel.add(horseConfidencePanel); // Add horse confidence panel to the main panel

            // Store the slider for later reference
            confidenceSliders[i] = confidenceSlider;
            
        }

        //startRace();

        ///////// Panel for start button ///////////


        JPanel buttonPanel = new JPanel(new FlowLayout()); // FlowLayout for button
        buttonPanel.setBackground(Color.WHITE);


        JButton startButton = new JButton("Start Race");
        startButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                startRace();   
            }
        });

        buttonPanel.add(startButton);



        ///////// Panel for back button ///////////




        JPanel backPanel = new JPanel(new FlowLayout()); // FlowLayout for button
        backPanel.setBackground(Color.WHITE);


        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                raceGUI();   
            }
        });

        backPanel.add(backButton);
    
        frame.add(confidencePanel, BorderLayout.CENTER); // Add confidence panel to the main panel
        frame.add(buttonPanel, BorderLayout.SOUTH); 
        frame.add(backPanel, BorderLayout.WEST); 

        //frame.add(mainPanel); // Add main panel to the frame

        //System.out.println("GUI Loaded");
        frame.setVisible(true);

    }

    private char getSymbolForHorse(int index) {
        return (char) ('\u265E' + index); // Return symbols for horses '♞', '♟', '♚', etc.
    }


    public static void main(String [] args){
        RaceV4 r1 = new RaceV4(lengthRace, selectedNumberOfHorses);
        for (int i = 0; i < selectedNumberOfHorses; i++) {
            r1.addHorse(new Horse(), i + 1); // You might need to adjust this based on your Horse class constructor
        }
        r1.raceGUI();
        //r1.startRace();
        
        
    }

}




