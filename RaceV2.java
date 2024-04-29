//import HORSESIM.Horse; 
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
public class RaceV2 extends Horse 
{
    private int raceLength;

    //private Horse lane1Horse;
    // private Horse lane2Horse;
    // private Horse lane3Horse;
    // private Horse lane4Horse;
    // private Horse lane5Horse;
    // private Horse lane6Horse;
    private static int selectedNumberOfHorses;
    private int selectedNumberOfTracks;
    // private int numberOfHorses; 
    // private int numberOfTracks; 
    private Horse [] laneHorses; 
    //private RacePanel racePanel; // Panel to display the race visually

    //private RaceV2 raceInstance; // Add a field to hold the race instance


    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */

    public RaceV2(int distance, int selectedNumberOfHorses)
    {
        // initialise instance variables
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
     */
    public void startRace()
    {

        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        
        //reset all the lanes (all horses not fallen and back to 0).
        // for( int i=0; i<=selectedNumberOfHorses-1; i++){
        //     laneHorses[i].goBackToStart();
        // }
        // lane1Horse.goBackToStart();
        // lane2Horse.goBackToStart();
        // lane3Horse.goBackToStart();
                      
        while (!finished) {
            for (Horse horse : laneHorses) {
                moveHorse(horse);
            }
            printRace();
            if (raceWonBy()) {
                finished = true;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
            if (horse.getDistanceTravelled() >= raceLength) {
                System.out.println("Well Done " + horse.getName() + "!");
                return true;
            }
        }
        return false; 
        // if (theHorse.getDistanceTravelled() == raceLength)
        // {
        //     return (true);
        // }
        // else
        // {
        //     return false;
        // }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        System.out.print('\u000C');  //clear the terminal window
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();

        for (Horse horse : laneHorses) {
            printLane(horse);
            System.out.print("   " + horse.getName() + ": Current Confidence: " + horse.getConfidence());
            System.out.println();
        }
        System.out.println();

        // for (int j = 0; j<= selectedNumberOfTracks-1; j++){
        //     printLane(laneHorses[j]);
        //     System.out.print("   "+ laneHorses[j].getName() + ": Current Confidence: "+laneHorses[j].getConfidence());
        //     System.out.println(); 
        // }
            
        
            // printLane(lane1Horse);
            // System.out.print("   "+ lane1Horse.getName() + ": Current Confidence: "+lane1Horse.getConfidence());
            // System.out.println();
            
            // printLane(lane2Horse);
            // System.out.print("   " + lane2Horse.getName() + ": Current Confidence: "+lane2Horse.getConfidence());
            // System.out.println();
            
            // printLane(lane3Horse);
            // System.out.print("   " + lane3Horse.getName() + ": Current Confidence: "+lane3Horse.getConfidence());
            // System.out.println();
        
        multiplePrint('=',raceLength+3); //bottom edge of track
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
        double spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
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
        frame.setSize(800, 600);
        frame.setResizable(true);



        ///////////////// Main Panel ////////////////////////


        JPanel mainPanel = new JPanel(new GridLayout(3, 2)); // Main panel with 3 rows
        mainPanel.setBackground(Color.WHITE);



        ///////////////// Panel for dropDown menu ////////////////////////


        JPanel dropdownPanel = new JPanel(new FlowLayout()); // FlowLayout for dropdown
        dropdownPanel.setBackground(Color.WHITE);

        // Create a combo box for selecting the number of horses
        String[] horseOptions = {"2", "3", "4", "5", "6"}; // Number of horse options
        JComboBox<String> horseComboBox = new JComboBox<>(horseOptions);
        horseComboBox.setSelectedIndex(1); // Default selection
        horseComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedNumberOfHorses = Integer.parseInt((String) horseComboBox.getSelectedItem());
                laneHorses = new Horse[selectedNumberOfHorses];

                for (int i = 0; i < selectedNumberOfHorses; i++) {
                    laneHorses[i] = new Horse(); 
                }
            }
        });

        dropdownPanel.add(new JLabel("Select number of horses:")); // Add label
        dropdownPanel.add(horseComboBox); // Add combo box




        ///////////////// Panel for slider ////////////////////////



        JPanel sliderPanel = new JPanel(new FlowLayout()); // Flow layout for slider
        sliderPanel.setBackground(Color.WHITE);

        JLabel sliderLabel = new JLabel("Select number of tracks:"); // Create label for the slider
        sliderLabel.setHorizontalAlignment(JLabel.CENTER); // Center align the label

        JSlider slider = new JSlider();
        slider.setMinimum(2);
        slider.setMaximum(6);
        slider.setValue(3);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true); // Set to true to paint ticks
        slider.setPaintLabels(true); // Set to true to paint labels
        slider.setSnapToTicks(true); // Snap to the nearest tick value

        // Set preferred width
        slider.setPreferredSize(new Dimension(300, slider.getPreferredSize().height)); 
        sliderPanel.add(sliderLabel);
        sliderPanel.add(slider);




        /////////// Panel for button //////////////




        JPanel buttonPanel = new JPanel(new FlowLayout()); // FlowLayout for button
        buttonPanel.setBackground(Color.WHITE);


        JButton startButton = new JButton("Start Simulation");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedNumberOfTracks = slider.getValue();
                // Save the selected number of horses and speed
                System.out.println("Number of horses: " + selectedNumberOfHorses);
                System.out.println("Number of tracks: " + selectedNumberOfTracks);
                for (int i = 0; i < laneHorses.length; i++) {
                    Horse horse = new Horse(getSymbolForHorse(i), "Horse " + (i + 1), 0.5);
                    addHorse(horse, i + 1);
                }
            }
        });
        
        buttonPanel.add(startButton);




        ///////// Panel for horse confidence level ///////////



        
        JPanel confidencePanel = new JPanel(new GridLayout(selectedNumberOfHorses, 1)); 
        confidencePanel.setBackground(Color.WHITE);

        // Create an array to hold confidence sliders for each horse
        JSlider[] confidenceSliders = new JSlider[selectedNumberOfHorses];

        for (int i = 0; i < selectedNumberOfHorses; i++) {
            JPanel horseConfidencePanel = new JPanel(new FlowLayout()); 
            horseConfidencePanel.setBackground(Color.WHITE);

            JLabel horseLabel = new JLabel("Horse " + (i + 1) + " Confidence Level:");
            horseLabel.setHorizontalAlignment(JLabel.CENTER); 

            // Create a slider for selecting confidence level for the current horse
            JSlider confidenceSlider = new JSlider(JSlider.HORIZONTAL, 1, 9, 5); // Values from 0.1 to 0.9 with step 0.1
            confidenceSlider.setMajorTickSpacing(1);
            confidenceSlider.setPaintTicks(true); 
            confidenceSlider.setPaintLabels(true); 

            final int horseIndex = i; // Required for using inside inner class

            // Add ChangeListener to the confidence slider
            confidenceSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    double selectedConfidence = confidenceSlider.getValue() / 10.0; // Convert slider value to double (0.1 - 0.9)
                    laneHorses[horseIndex].setConfidence(selectedConfidence); // Update the current horse's confidence level
                    System.out.println("Confidence Level: " + selectedConfidence);

                }
            });

            horseConfidencePanel.add(horseLabel);
            horseConfidencePanel.add(confidenceSlider);

            confidencePanel.add(horseConfidencePanel); // Add horse confidence panel to the main panel

            // Store the slider for later reference
            confidenceSliders[i] = confidenceSlider;
        }

        mainPanel.add(new JLabel()); // Empty row for spacing
        mainPanel.add(dropdownPanel); // Add dropdown panel
        mainPanel.add(sliderPanel); // Add slider panel
        buttonPanel.add(startButton);
        mainPanel.add(buttonPanel);
        mainPanel.add(confidencePanel); // Add confidence panel to the main panel

        frame.add(mainPanel); // Add main panel to the frame



        //System.out.println("GUI Loaded");
        frame.setVisible(true);

    }

    private char getSymbolForHorse(int index) {
        return (char) ('\u265E' + index); // Return symbols for horses '♞', '♟', '♚', etc.
    }


    public static void main(String [] args){
        RaceV2 r1 = new RaceV2(15, selectedNumberOfHorses);
        // Horse YIN = new Horse('\u2656', "YIN", 0.5);
        // Horse YANG = new Horse('\u2657', "YANG", 0.5);
        // Horse VIO = new Horse('\u2658', "VIO", 0.5);
        // Horse POTTY = new Horse('\u2659', "POTTY", 0.5);
        // Horse DAMIEN = new Horse('\u2655', "DAMIEN", 0.5);
        // Horse BRO = new Horse('\u2654', "BRO", 0.5);
        // //r1.addHorse(YIN,1); 
        // for (int i =0; i<=selectedNumberOfHorses; i++){
        //     r1.addHorse(YIN,1); 
        //     r1.addHorse(YANG, 2);
        //     r1.addHorse(VIO, 3);
        //     r1.addHorse(POTTY, 4);
        //     r1.addHorse(DAMIEN, 5);
        //     r1.addHorse(BRO, 6);
        // }
        
        // r1.startRace(selectedNumberOfHorses);
        //r1.setRaceInstance(r1); // Set the race instance
        for (int i = 0; i < selectedNumberOfHorses; i++) {
            r1.addHorse(new Horse(), i + 1); // You might need to adjust this based on your Horse class constructor
        }
        r1.raceGUI();
        
        
    }

}


