package Server;
import java.io.*;
import java.net.*;

import Misc.Reply;
import Misc.Request;

public class Server {
   private static final int PORT = 1234;

   public static void main(String[] arg) throws IOException {

		ServerSocket socketConnection = new ServerSocket(PORT);
		Socket pipe;
//		ObjectInputStream serverInputStream;
//		ObjectOutputStream serverOutputStream;

		System.out.println("Server Waiting");

		ServerProtocol serverProt = new ServerProtocol();
		while(true){
			pipe = socketConnection.accept();
			final ObjectInputStream serverInputStream = new ObjectInputStream(pipe.getInputStream());
			final ObjectOutputStream serverOutputStream = new ObjectOutputStream(pipe.getOutputStream());
			
			Thread t = new Thread(() -> {
				try {
					Request req = (Request) serverInputStream.readObject();
					Reply rep = serverProt.processRequest(req);
					serverOutputStream.writeObject(rep);


					Request req2 = (Request) serverInputStream.readObject();
					Reply rep2 = serverProt.processRequest(req2);
					serverOutputStream.writeObject(rep2);
				} catch (IOException e) {} catch (ClassNotFoundException e) {}
			});
			t.start();



			// Should never be called by the generated clients
//			if(repSum.getCode() == 1)
//				break;

		}

//		serverInputStream.close();
//		serverOutputStream.close();
//		pipe.close();
//		socketConnection.close();
   }

}

