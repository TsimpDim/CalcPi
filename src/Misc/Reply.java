package Misc;
import java.io.*;
import java.util.*;

public class Reply implements Serializable {

	private long start;
	private long stop;

	public Reply(long start, long stop){
		this.start = start;
		this.stop = stop;
	}

	public Reply(){}

	public void setStart(long start) {
		this.start = start;
	}

	public void setStop(long stop) {
		this.stop = stop;
	}

	public long getStart() {
		return start;
	}

	public long getStop() {
		return stop;
	}
}

