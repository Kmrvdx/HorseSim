import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class raceGUI extends RaceV3{

    public raceGUI(){}

    JFrame frame = new JFrame("Horse Simulation Settings");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setResizable(true);



    ///////////////// Main Panel ////////////////////////


    JPanel mainPanel = new JPanel(new GridLayout(3, 2)); // Main panel with 3 rows and 2 columns
    mainPanel.setBackground(Color.BLACK);



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
    startButton.addActionListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e) 
        {
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



    
    JPanel confidencePanel = new JPanel(new GridLayout(selectedNumberOfHorses, )); 
    confidencePanel.setBackground(Color.WHITE);

    // Create an array to hold confidence sliders for each horse
    JSlider[] confidenceSliders = new JSlider[selectedNumberOfHorses];

    for (int i = 0; i < selectedNumberOfHorses; i++) {
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

    private char getSymbolForHorse(int index) {
        return (char) ('\u265E' + index); // Return symbols for horses '♞', '♟', '♚', etc.
    }

}


