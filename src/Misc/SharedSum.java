package Misc;

public class SharedSum {
    static public double sum;

    public SharedSum(){
        sum = 0;
    }

    synchronized static public void add(double amount){
        sum += amount;
    }
}