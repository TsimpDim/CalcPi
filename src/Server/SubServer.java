package Server;

import Misc.Reply;
import Misc.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SubServer extends Thread{
    private Socket pipe;
    private ServerProtocol serverProt;

    public SubServer(Socket pipe, ServerProtocol serverProt){
        this.pipe = pipe;
        this.serverProt = serverProt;
    }

    @Override
    public void run(){

        ObjectInputStream serverInputStream = null;
        ObjectOutputStream serverOutputStream = null;
        try {
            serverOutputStream = new ObjectOutputStream(pipe.getOutputStream());
            serverInputStream = new ObjectInputStream(pipe.getInputStream());
        } catch (IOException e) {}

        while(true) {
            try {
                Request req = (Request) serverInputStream.readObject();
                Reply rep = serverProt.processRequest(req);
                serverOutputStream.writeObject(rep);
            } catch (ClassNotFoundException e) {} catch (IOException e) {}
        }
    }
}
