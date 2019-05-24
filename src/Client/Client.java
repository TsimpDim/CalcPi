package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


import Misc.Info;
import Misc.Reply;
import Misc.Request;

public class Client extends Thread{

	private int id = 0;
	private long steps;
	private long start;
	private long stop;
	private double sum;

   
	public Client(int id, long steps){
		this.id = id;
		this.steps = steps;
	}
   
	@Override
	public void run(){
	   try{
			Socket socketConnection = new Socket(Info.getHOST(), Info.getPORT());
			ObjectOutputStream clientOutputStream = new ObjectOutputStream(socketConnection.getOutputStream());
			ObjectInputStream clientInputStream = new ObjectInputStream(socketConnection.getInputStream());

			// Send request to get range
			System.out.println("Client["+id+"] requesting range");
			Request reqRange = new Request(id, "REQ_RANGE");
			clientOutputStream.writeObject(reqRange);
			Reply repRange = (Reply)clientInputStream.readObject();

			System.out.println("Client["+id+"] acquired range. Calculating sum");
			start = repRange.getStart();
			stop = repRange.getStop();

			// Calculate sum
			double step = 1.0 / steps;
			double localSum = 0;

			for(long i = start; i < stop; i++){
			   double x = (i + 0.5) * step;
			   localSum += 4.0 / (1.0 + x * x); // First add sums to our local sum
			}

			// Send sum
			System.out.println("Client["+id+"] sending local sum ("+localSum+")");
			Request reqSum = new Request(id, "DLVR_L_SUM", localSum);
			clientOutputStream.writeObject(reqSum);

		    System.out.println("Client["+id+"] exiting");

			clientOutputStream.close();
			clientInputStream.close();
			socketConnection.close();

		// :(
	   } catch (IOException e) {
			e.printStackTrace();
	   } catch (ClassNotFoundException e) {
			e.printStackTrace();
	   }
	}


	public int getClientId() {
		return id;
	}
}
