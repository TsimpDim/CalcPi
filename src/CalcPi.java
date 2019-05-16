public class CalcPi {

    public static void main(String[] args) {

        // Default numSteps
        // numSteps defines our accuracy e.g {numSteps = 1 --> pi = 3.2} or {numSteps = 90000000 --> pi = 3.14159265358991120000}
        long numSteps = 900000000;

        if (args.length != 1) {
            System.out.println("arguments:  <number_of_steps>\nMore steps = more accuracy");
            System.exit(1);
        }

        try {

            numSteps = Long.parseLong(args[0]);

        } catch (NumberFormatException e) {
            System.out.println("argument "+ args[0] +" must be long int");
            System.exit(1);
        }


        // Define number of threads
        int threads = Runtime.getRuntime().availableProcessors(); // We get it automatically
        // We could also do:
        // int threads = 20;
        // and be done with it

        System.out.println("Starting computations");

        // Start timer
        long startTime = System.currentTimeMillis();

        // Create groups
        /*
        * We've got <numSteps> of calculations.
        *
        *     ---------------------------------------------------------------------------
        * e.g |****** .... *   *     *     *     *     .... *        *       *       *  |
        *     |123456 .... 10k 10k+1 10k+2 10k+3 10k+4 .... 89k+997  89k+998 89k+999 90k|
        *     ---------------------------------------------------------------------------
        *
        * And our system has <threads> number of cores. We divide our problem into <threads> pieces
        * and each piece is ran separately, adding it's sub-sum into our SharedSum.
        *
        * */

        // Create them
        ThreadGroup[] groups = new ThreadGroup[threads];
        for(int i = 0; i < threads; i++)
            groups[i] = new ThreadGroup(i, threads, numSteps);

        // Start them
        for(int i = 0; i < threads; i++) {
            System.out.println("Starting group " + i);
            groups[i].start();
        }

        // Join/Wait for them
        for(int i = 0; i < threads; i++)
            try {
                groups[i].join();
            }catch (InterruptedException e){}


        double step = 1.0 / (double) numSteps;
        double pi = SharedSum.sum * step;

        // Stop timer
        long endTime = System.currentTimeMillis();

        // Print results
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("Sequential program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n" , pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
}