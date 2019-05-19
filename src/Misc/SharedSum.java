package Misc;

public class SharedSum {
    private double sum;

    public SharedSum(){
        sum = 0;
    }

    synchronized public void add(double amount){
        sum += amount;
    }

    public double getSum() {
        return sum;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.equals(sum);
    }

    @Override
    public String toString() {
        return String.valueOf(sum);
    }
}