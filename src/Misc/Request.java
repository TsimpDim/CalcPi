package Misc;
import java.io.*;

public class Request implements Serializable {


	private int id;
	private String type;
	private double value;

	public Request(int id, String type) {
		this.id = id;
		this.type = type;
	}

	public Request(int id, String type, double value) {
		this.id = id;
		this.type = type;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public String getReqType() {
		return type;
	}

	public double getValue() {
		return value;
	}
}

