import java.io.*;
import java.net.*;
import java.util.*;

public class QuoteClient
{
    public static void main(String[] args) throws IOException
    {

        if (args.length != 1)
        {
             System.out.println("Usage: java QuoteClient <hostname>");
             return;
        }

	// get a datagram socket
        DatagramSocket socket = new DatagramSocket();

	// send request
	byte[] buf = new byte[256];
	InetAddress address = InetAddress.getByName(args[0]);
	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
	socket.send(packet);

	// get response
	packet = new DatagramPacket(buf, buf.length);
	socket.receive(packet);

	// display response
	String received = new String(packet.getData(), 0);
	System.out.println("Quote of the Moment: " + received);

	socket.close();
    }
}

++++++++++++++++++++++++++++++++++++++++++

import java.io.*;

public class QuoteServer
{
    public static void main(String[] args) throws IOException
    {
        new QuoteServerThread().start();
    }
}


++++++++++++++++++++++++++++++++++++++++

import java.io.*;
import java.net.*;
import java.util.*;

public class QuoteServerThread extends Thread {

    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean moreQuotes = true;

    public QuoteServerThread() throws IOException {
	this("QuoteServerThread");
    }

    public QuoteServerThread(String name) throws IOException {
        super(name);
        socket = new DatagramSocket(4445);

        try {
            in = new BufferedReader(new FileReader("one-liners.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("Could not open quote file. Serving time instead.");
        }
    }

    public void run() {

        while (moreQuotes) {
            try {
                byte[] buf = new byte[256];

                    // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                    // figure out response
                String dString = null;
                if (in == null)
                    dString = new Date().toString();
                else
                    dString = getNextQuote();
                buf = dString.getBytes();

		    // send the response to the client at "address" and "port"
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
		moreQuotes = false;
            }
        }
        socket.close();
    }

    protected String getNextQuote() {
        String returnValue = null;
        try {
            if ((returnValue = in.readLine()) == null) {
                in.close();
		moreQuotes = false;
                returnValue = "No more quotes. Goodbye.";
            }
        } catch (IOException e) {
            returnValue = "IOException occurred in server.";
        }
        return returnValue;
    }
}


