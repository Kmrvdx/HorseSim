//import HORSESIM.Horse; 
import java.util.concurrent.TimeUnit;
import java.lang.Math;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author Karim Abdelmoaty
 * @version v1 24/04/24
 */
public class Race extends Horse 
{
    private int raceLength;

    //private Horse lane1Horse;
    // private Horse lane2Horse;
    // private Horse lane3Horse;
    // private Horse lane4Horse;
    // private Horse lane5Horse;
    // private Horse lane6Horse;
    static int selectedNumberOfHorses;
    private int selectedNumberOfTracks;
    private int numberOfHorses; 
    private int numberOfTracks; 
    private Horse [] laneHorses; 
    //private RacePanel racePanel; // Panel to display the race visually

    private Race raceInstance; // Add a field to hold the race instance


    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */

    public Race(int distance)
    {
        // initialise instance variables
        raceLength = distance;

        //lane1Horse = null;
        // lane2Horse = null;
        // lane3Horse = null;
        // lane4Horse = null;
        // lane5Horse = null;
        // lane6Horse = null;
    }
    
    public void setRaceInstance(Race raceInstance) {
        this.raceInstance = raceInstance;
    }

    public void setNumberOfHorses(int numberOfHorses) {
        this.numberOfHorses = numberOfHorses;
    }

    public void setNumberOfTracks(int numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }


    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        if (laneNumber == 1)
        {
            laneHorses[0] = theHorse;
        }
        else if (laneNumber == 2)
        {
            laneHorses[1]= theHorse;
        }
        else if (laneNumber == 3)
        {
            laneHorses[2]= theHorse;
        }
        else if (laneNumber == 4)
        {
            laneHorses[3] = theHorse;
        }
        else if (laneNumber == 5)
        {
            laneHorses[4] = theHorse;
        }
        else if (laneNumber == 6)
        {
            laneHorses[5] = theHorse;
        }
        else
        {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace(int selectedNumberOfHorses)
    {
        //racePanel.setHorses(lane1Horse, lane2Horse, lane3Horse); // Set the horses for the visual race
        //racePanel.repaint(); // Repaint the panel to update the race display

        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        
        //reset all the lanes (all horses not fallen and back to 0).
        for( int i=0; i<=selectedNumberOfHorses-1; i++){
            laneHorses[i].goBackToStart();
        }
        // lane1Horse.goBackToStart();
        // lane2Horse.goBackToStart();
        // lane3Horse.goBackToStart();
                      
        while (!finished)
        {
            //move each horse
            for( int j=0; j<=selectedNumberOfHorses-1; j++){
                moveHorse(laneHorses[j]);
            }
            // moveHorse(lane1Horse);
            // moveHorse(lane2Horse);
            // moveHorse(lane3Horse);
                        
            //print the race positions
            
            //racePanel.repaint(); // Repaint the panel to update the race display
            printRace(selectedNumberOfTracks);
            
            //if any of the three horses has won the race is finished
            for (int x=0; x<selectedNumberOfHorses; x++){
                if (raceWonBy(laneHorses[x])){
                    finished = true;
                    System.out.println("Well Done "+laneHorses[x].getName()+ "!");
                }
            }

            // if (raceWonBy(lane1Horse)){
            //     finished = true;
            //     System.out.println("Well Done "+lane1Horse.getName()+ "!");
            // }else if (raceWonBy(lane2Horse)){
            //     finished = true;
            //     System.out.println("Well Done "+lane2Horse.getName() + "!");
            // }else if( raceWonBy(lane3Horse)){ 
            //     finished = true;
            //     System.out.println("Well Done "+lane3Horse.getName()+ "!");
            // }
           
            //wait for 100 milliseconds
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
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
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            return (true);
        }
        else
        {
            return false;
        }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace(int selectedNumberOfTracks)
    {
        System.out.print('\u000C');  //clear the terminal window
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();

        for (int j = 0; j<= selectedNumberOfTracks-1; j++){
            printLane(laneHorses[j]);
            System.out.print("   "+ laneHorses[j].getName() + ": Current Confidence: "+laneHorses[j].getConfidence());
            System.out.println(); 
        }
            
        
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
        //frame.setResizable(true);


        JPanel mainPanel = new JPanel(new GridLayout(3, 1)); // Main panel with 3 rows
        mainPanel.setBackground(Color.WHITE);


        JPanel dropdownPanel = new JPanel(new FlowLayout()); // FlowLayout for dropdown
        dropdownPanel.setBackground(Color.WHITE);

        // Create a combo box for selecting the number of horses
        String[] horseOptions = {"2", "3", "4", "5", "6"}; // Number of horse options
        JComboBox<String> horseComboBox = new JComboBox<>(horseOptions);
        horseComboBox.setSelectedIndex(1); // Default selection
        horseComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                selectedNumberOfHorses = Integer.parseInt((String) combo.getSelectedItem());
            }
        });

        dropdownPanel.add(new JLabel("Select number of horses:")); // Add label
        dropdownPanel.add(horseComboBox); // Add combo box



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

        // Panel for button
        JPanel buttonPanel = new JPanel(new FlowLayout()); // FlowLayout for button
        buttonPanel.setBackground(Color.WHITE);

        //racePanel = new RacePanel();
        //mainPanel.add(racePanel, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Simulation");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedNumberOfTracks = slider.getValue();
                // Save the selected number of horses and speed
                System.out.println("Number of horses: " + selectedNumberOfHorses);
                System.out.println("Number of tracks: " + selectedNumberOfTracks);

                // Set the number of horses and tracks for the race instance
                raceInstance.setNumberOfHorses(selectedNumberOfHorses);
                raceInstance.setNumberOfTracks(selectedNumberOfTracks);

                // Start the race
                raceInstance.startRace();
            }
        });
        

        mainPanel.add(new JLabel()); // Empty row for spacing
        mainPanel.add(dropdownPanel); // Add dropdown panel
        mainPanel.add(sliderPanel); // Add slider panel
        buttonPanel.add(startButton);
        mainPanel.add(buttonPanel);

        frame.add(mainPanel); // Add main panel to the frame



        //System.out.println("GUI Loaded");
        frame.setVisible(true);

    }


    public static void main(String [] args){
        Race r1 = new Race(15);
        Horse YIN = new Horse('\u2656', "YIN", 0.5);
        Horse YANG = new Horse('\u2657', "YANG", 0.5);
        Horse VIO = new Horse('\u2658', "VIO", 0.5);
        Horse POTTY = new Horse('\u2659', "POTTY", 0.5);
        Horse DAMIEN = new Horse('\u2655', "DAMIEN", 0.5);
        Horse BRO = new Horse('\u2654', "BRO", 0.5);
        //r1.addHorse(YIN,1); 
        for (int i =0; i<=selectedNumberOfHorses; i++){
            r1.addHorse(YIN,1); 
            r1.addHorse(YANG, 2);
            r1.addHorse(VIO, 3);
            r1.addHorse(POTTY, 4);
            r1.addHorse(DAMIEN, 5);
            r1.addHorse(BRO, 6);
        }
        
        r1.startRace(selectedNumberOfHorses);
        //r1.setRaceInstance(r1); // Set the race instance
        //r1.raceGUI();
        
        
    }

    // private class RacePanel extends JPanel {
    //     private Horse horse1;
    //     private Horse horse2;
    //     private Horse horse3;

    //     public void setHorses(Horse horse1, Horse horse2, Horse horse3) {
    //         this.horse1 = horse1;
    //         this.horse2 = horse2;
    //         this.horse3 = horse3;
    //     }

    //     @Override
    //     protected void paintComponent(Graphics g) {
    //         super.paintComponent(g);

    //         // Draw the horses
    //         if (horse1 != null) {
    //             g.drawString("" + horse1.getSymbol(), 50, 50);
    //         }
    //         if (horse2 != null) {
    //             g.drawString("" + horse2.getSymbol(), 50, 100);
    //         }
    //         if (horse3 != null) {
    //             g.drawString("" + horse3.getSymbol(), 50, 150);
    //         }
    //     }
    // }
}

// Race.main(null);