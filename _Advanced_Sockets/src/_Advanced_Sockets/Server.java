package _Advanced_Sockets;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public class Server {
	
	public Server() {
		ServerSocket listener = new ServerSocket(1234);
		
		while(!finished) {
			Socket client = listener.accept(); // wait for connection
			
			InputStream in = client.getInputStream();
			OutputStream out = client.getOutputStream();
			
			// read a byte
			byte someByte = (byte)in.read();
			
			// read a newline or carriage-return-delimited string
			BufferedReader bin =
					new BufferedReader( new InputStreamReader (in) );
			String someString = bin.readLine();
			
			//write a byte
			out.write(43);
			
			// say goodbye
			PrintWriter pout = new PrintWriter(out, true);
			pout.println("GoodBye!");
			
			// read a serialized Java object
			ObjectInputStream oin = new ObjectInputStream(in);
			Date date = (Date)oin.readObject();
			
			client.close();
			
		}
		
		listener.close();
	}
	
	

}
