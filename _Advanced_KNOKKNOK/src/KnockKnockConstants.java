
/**
 * Class full of static constants used throughout the package.
 * Allows for changes in one area to avoid hassle of changing in each 
 * separate class.
 * 
 * @author Humberto Sainz
 *
 */
public final class KnockKnockConstants 
{
	protected static int PORT = 4444;	// default port number
	protected static String HOST = "localhost"; // default hostname
	protected static int TIMEOUT = 2000; // time out for serverSocket.accept() method. 
	protected static int MAX_CLIENTS = 16;	// restrict number of threads created (new KKMST for each client)
}
