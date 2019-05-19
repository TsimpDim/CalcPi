package Server;

import Misc.Info;
import Misc.Reply;
import Misc.Request;
import Misc.SharedSum;

import java.io.*;

public class ServerProtocol implements Serializable {
	

	private SharedSum sum;
	private long steps;
	private int threads;
	private SharedSum arrived;


	public ServerProtocol(){
		this.steps = Info.getSteps();
		this.threads = Info.getThreads();
		this.arrived = new SharedSum();
		this.sum = new SharedSum();
	}

	public Reply processRequest(Request req) {
		Reply res = new Reply();

		if(req.getReqType().equals("REQ_RANGE")) {

			System.out.println("Server: calculating range for Client["+req.getId()+"]");

			// Calculate start & stop
			long start = req.getId() * (steps/ threads);
			long stop = start + (steps/ threads);
			if (req.getId() == threads - 1) stop = steps;

			// Prepare answer
			res.setStart(start);
			res.setStop(stop);
			res.setCode(0);

		}else if(req.getReqType().equals("DLVR_L_SUM")) {
			arrived.add(1);
			if(req.getValue() < 0)
				res.setCode(1);
			else {
				sum.add(req.getValue());
				res.setCode(0);
			}

			System.out.println("Server: arrived threads ("+(int)arrived.getSum()+"/"+threads+")");
			if(arrived.equals((double) threads)){
				double step = 1.0 / (double) steps;
				double pi = sum.getSum() * step;

				System.out.println("Server: All threads arrived. Pi = " + pi);
				res.setCode(1);
			}

		}


		return res;
	}

}