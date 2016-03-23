import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class iotsys {
 
	public static void main(String[] args) throws Exception{
	System.out.println("\nPresent Status: \n" + callURL("http://localhost:8080/VirtualDevices/virtualBrightnessActuator/value"));
/*  
* This Section of the program sends PUT request and updats the value of  
* Virtual Brightness Acutator to the value entered by the user ranging 
* from value 0 to 100 both inclusive.
*/
	Scanner in = new Scanner(System.in);
	System.out.println("enter the value:");
	int value = in.nextInt();

	URL url = new URL("http://localhost:8080/VirtualDevices/virtualBrightnessActuator/value");
	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	connection.setRequestMethod("PUT");
	connection.setDoOutput(true);
	connection.setRequestProperty("Content-Type", "application/json");
	connection.setRequestProperty("Accept", "application/json");
	OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
	osw.write(String.format("{tag: \"int\", val: \"%1$d\"}", value));
	osw.flush();
	osw.close();
	System.err.println(connection.getResponseCode());

}
 
public static String callURL(String myURL) {
		/* This section of the program retrives the current status of 
		*	virtual Brightness Acutator and it Shows the Status as Output 
		*/
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(myURL);
			urlConn = url.openConnection();
		if (urlConn != null)
			urlConn.setReadTimeout(60 * 1000);
		if (urlConn != null && urlConn.getInputStream() != null) {
			in = new InputStreamReader(urlConn.getInputStream(),
					Charset.defaultCharset());
			BufferedReader bufferedReader = new BufferedReader(in);
			if (bufferedReader != null) {
				int cp;
				while ((cp = bufferedReader.read()) != -1) {
					sb.append((char) cp);
				}
				bufferedReader.close();
			}
		}
		in.close();
		} catch (Exception e) {
			throw new RuntimeException("Exception while calling URL:"+ myURL, e);
		}
		 
			return sb.toString();
		}
}