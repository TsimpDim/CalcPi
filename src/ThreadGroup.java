public class ThreadGroup extends Thread {

    private int id;
    private long start;
    private long stop;
    private long steps;

    public ThreadGroup(int id, int threads, long steps){
        this.id = id;
        this.start = id * (steps/ threads);
        this.stop = start + (steps/ threads);
        this.steps = steps;
        if (id == threads - 1) this.stop = steps;
    }

    @Override
    public void run(){
        double step = 1.0 / steps;
        double localSum = 0;

        for(long i = start; i < stop; i++){
            double x = (i + 0.5) * step;
            localSum += 4.0 / (1.0 + x * x); // First add sums to our local sum
        }

        // And add our local sums to the shared sum only when calculations are finished
        SharedSum.add(localSum);
    }
}
