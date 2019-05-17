package Client;

import Misc.Info;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ClientGen{

    // Number of clients according to our threads
    public static void main(String args[]){

        int threads = Info.getThreads();
        long steps = Info.getSteps();

        Client[] clients = new Client[threads];
        for(int i = 0; i < threads; i++)
            clients[i] = new Client(i, steps);

        shuffleArray(clients);
        System.out.println("Firing up clients");
        for(Client c : clients) {
            System.out.println(c.getClientId());
            c.start();
        }
    }

    // https://stackoverflow.com/a/1520212
    static void shuffleArray(Client[] ar){
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Client a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}

