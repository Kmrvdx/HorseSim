
package Part1;

/**
 * Write a description of class Horse here.
 * 
 * @author Karim Abdelmoaty 
 * @version v1, 24/04/24
 */


public class Horse
{
    String horseName; 
    private char horseSymbol;
    double distance; 
    boolean fallen; 
    private double horseConfidence; 

    public Horse (){}

    public Horse (char horseSymbol, String horseName, double horseConfidence){
        this.horseSymbol = horseSymbol;
        this.horseName = horseName;
        this.horseConfidence = horseConfidence;
    }

    public boolean fall(){
        fallen = true; 
        return fallen;
    }

    public boolean hasFallen(){
        boolean returnVal = false; 

        if (fallen == true){
            returnVal = true; 
        }

        return returnVal;
    }

    public double getConfidence(){
        return horseConfidence;
    }

    public double setConfidence (double newConfidence){
        horseConfidence = newConfidence; 
        return horseConfidence; 
    }


    public double getDistanceTravelled(){
        return distance;
    }

    public String getName(){
        return horseName;
    }

    public char getSymbol(){
        return horseSymbol;
    }

    public char setSymbol (char newSymbol){
        horseSymbol = newSymbol; 
        return horseSymbol;
    }

    public void goBackToStart(){
        // resets the horse to the start of the race
        System.out.println("blahh");
    }

    public double moveForward(){
        this.distance += 1; 
        return distance; 
    }

    public static void main (String [] args){
        System.out.println("Hi");
    }
    
}

