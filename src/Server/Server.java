package Server;
import java.io.*;
import java.net.*;

import Misc.Reply;
import Misc.Request;

public class Server {
   private static final int PORT = 1234;

   public static void main(String[] arg) throws IOException, ClassNotFoundException {

		ServerSocket socketConnection = new ServerSocket(PORT);
		Socket pipe;
		ObjectInputStream serverInputStream;
		ObjectOutputStream serverOutputStream;

		System.out.println("Server Waiting");

		ServerProtocol serverProt = new ServerProtocol();
		while(true){
			pipe = socketConnection.accept();
			serverInputStream = new ObjectInputStream(pipe.getInputStream());
			serverOutputStream = new ObjectOutputStream(pipe.getOutputStream());
			
			
			Request req = (Request) serverInputStream.readObject();
			Reply rep = serverProt.processRequest(req);
			serverOutputStream.writeObject(rep);

			Request reqSum = (Request) serverInputStream.readObject();
			Reply repSum = serverProt.processRequest(reqSum);
			serverOutputStream.writeObject(rep);

			// Should never be called by the generated clients
			if(repSum.getCode() == 1)
				break;

		}

		serverInputStream.close();
		serverOutputStream.close();
		pipe.close();
		socketConnection.close();
   }

}

