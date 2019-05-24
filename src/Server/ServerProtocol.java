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
	private int arrived = 0;


	public ServerProtocol(){
		this.steps = Info.getSteps();
		this.threads = Info.getThreads();

		sum = new SharedSum();
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

		}else if(req.getReqType().equals("DLVR_L_SUM")) {
			arrived++;
			sum.add(req.getValue());

			System.out.println("Server: arrived threads ("+arrived+"/"+threads+")");
			if(arrived == threads){
				double step = 1.0 / (double) steps;
				double pi = SharedSum.sum * step;

				System.out.println(pi);
			}

		}


		return res;
	}

}