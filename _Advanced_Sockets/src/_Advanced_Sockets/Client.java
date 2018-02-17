package _Advanced_Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	public static void main (String[] args) {
		
		
		
		try {
			Socket socket = new Socket(InetAddress.getLocalHost(), 1234);
			InputStream in = server.getInputStream();
			OutputStream out = server.getOutputStream();
			
			// write a byte
			out.write(42);
			
			// write a new line or carriage return delimited string
			PrintWriter pout = new PrintWriter(out, true);
			pout.println("Hello");
			
			// read a byte
			byte back = (byte)in.read();
			
			// read a new line or carriage return delimited string
			BufferedReader bin = 
					new BufferedReader(new InputStreamReader( in ) );
			String response = bin.readLine();
			
			// send serialized java object
			ObjectOutputStream oout = 
					new ObjectOutputStream( out );
			oout.writeObject( new java.util.Date() );
			oout.flush();
			
			server.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
