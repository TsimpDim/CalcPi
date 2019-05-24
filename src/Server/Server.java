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
		ServerProtocol serverProt = new ServerProtocol();
		System.out.println("Server Waiting");

		while(true){
			pipe = socketConnection.accept();
			final ObjectInputStream serverInputStream = new ObjectInputStream(pipe.getInputStream());
			final ObjectOutputStream serverOutputStream = new ObjectOutputStream(pipe.getOutputStream());

			Thread t = new Thread(() -> {
				try {
					while(true) {
						Request req = (Request) serverInputStream.readObject();
						Reply rep = serverProt.processRequest(req);
						serverOutputStream.writeObject(rep);
					}
				} catch (IOException e) {} catch (ClassNotFoundException e) {}
			});
			t.start();

		}
   }

}

