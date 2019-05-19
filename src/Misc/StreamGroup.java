package Misc;

import java.io.*;
import java.net.Socket;

public class StreamGroup {
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Socket socket;

    public StreamGroup(OutputStream out, InputStream in){
        try {
            this.outputStream = new ObjectOutputStream(out);
            this.inputStream = new ObjectInputStream(in);
        } catch (IOException e) {}

        this.socket = socket;
    }

    public StreamGroup(Socket socket){
        this.socket = socket;
    }

    public void setOutputStream(OutputStream outputStream) {
        try {
            this.outputStream = new ObjectOutputStream(outputStream);
        } catch (IOException e) {}
    }

    public void setInputStream(InputStream inputStream) {
        try {
            this.inputStream = new ObjectInputStream(inputStream);
        } catch (IOException e) {}
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public Socket getSocket() {
        return socket;
    }

    public void closeStreams(){
        try {
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {}
    }
}
