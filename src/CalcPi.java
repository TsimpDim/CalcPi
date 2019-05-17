public class CalcPi {

    public static void main(String[] args) {

        // Default numSteps
        // numSteps defines our accuracy e.g {numSteps = 1 --> pi = 3.2} or {numSteps = 90000000 --> pi = 3.14159265358991120000}
        long numSteps = 2000000000;

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
        //int threads = Runtime.getRuntime().availableProcessors(); // We get it automatically
        // We could also do:
         int threads = 50;
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
        * and each piece is ran separately, adding it's sub-sum into our final sum.
        *
        * */

        // Create them
        double[] sums = new double[threads];
        ThreadGroup[] groups = new ThreadGroup[threads];
        for(int i = 0; i < threads; i++)
            groups[i] = new ThreadGroup(i, threads, numSteps, sums);

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


        // Sum up the results
        /*
        * Assume the indices [0][1][2][3][4][5][6][7] where each index holds a value.
        * What we want is to reduce the indices in the following way:
        *
        *   [0][1][2][3][4][5][6][7]
        *    | /   | /   | /   | /
        *   [0]   [2]   [4]   [6]
        *    |    /      |    /
        *    |   /       |   /
        *    |  /        |  /
        *   [0]         [4]
        *    |          /
        *    |         /
        *    |        /
        *    |       /
        *    |      /
        *    |     /
        *    |    /
        *   [0]
        *
        * or with an odd number of indices:
        *
        *   [0][1][2]
        *    | /   |
        *   [0]    |
        *    |    /
        *    |   /
        *    |  /
        *   [0]
        *
        *
        * Thus, index 0 will be the one to hold the final sum.
        *
        * To achieve this we declare three main axioms:
        *   1. On each pair one will absorb the sum of the other ("master-slave")
        *   2. We will hold the position of the last "master" index
        *   3. If a value of an index has been absorbed by another index, then its value becomes -1
        *
        * As such, our algorithm is as follows:
        *   1. For each level (we have log2(n) levels where n is the number of threads)
        *       2. Reset the last master position
        *       3. For every thread
        *           4. If the index's value hasn't been absorbed
        *               5.1. If there's no master : assign the current index
        *               5.2. Ιf there IS a master : absorb the sum of the current index, "empty it out" and reset the master
        *
        * */


        for(int i = 0; i < Math.log(threads)/Math.log(2.0); i++){ // log10(n)/log10(2) = log2(n)
            int lastValidPos = -1;

            for(int j = 0; j < threads; j++) { // Perhaps this could be optimised to include only valid threads/indices
                if (sums[j] != -1) {
                    if (lastValidPos == -1) {
                        lastValidPos = j;
                    }
                    else {
                        sums[lastValidPos] += sums[j];
                        sums[j] = -1;
                        lastValidPos = -1;
                    }
                }
            }
        }

        double step = 1.0 / (double) numSteps; // ?
        double pi = sums[0] * step;

        // Stop timer
        long endTime = System.currentTimeMillis();

        // For debugging purposes
        //for(double d: sums)
        //    System.out.println(d * step);

        // Print results
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("Sequential program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n" , pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
}