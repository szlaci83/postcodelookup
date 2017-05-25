/**
 * Simple program to test the getAddress.io API for postcode lookup.
 * Uses free key to connect to the service and gets a JSON object as 
 * a response. Uses String methods to cut off the not required parts and
 * split the addresses into a String array. Later this array can be used 
 * as a dropdown list to select the address.
 */

package network;
import java.net.*;
import java.io.*;

public class PostCodeLookUp {
	
	static PostCode toPostCode(String postcode) throws Exception{
				//first and last bit of the query 	
				String firstBit="https://api.getAddress.io/v2/uk/";
				String lastBit="?api-key=UijSMe-yj02cpYPz_W5hOA2880";
				
				//create the new URL object using first and last bit of the 
				//query inserting the Postcode into the right place
				 URL url = new URL(firstBit+postcode+lastBit);	
				 URLConnection yc = url.openConnection();
				 
				 //read one line from the result 
			     BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			     String inputLine = in.readLine();
			     in.close();
			     
			     double latitude = Double.parseDouble(inputLine.substring(inputLine.indexOf(":")+1, inputLine.indexOf(",")));
			     double longitude = Double.parseDouble(inputLine.substring(inputLine.indexOf("Longitude\":")+11, inputLine.indexOf(",\"Addresses")));
			     String addressesLine = inputLine.substring(inputLine.indexOf("[")+2,inputLine.length()-3);
			     
			     //split the addresses into a String array by ","
			     String [] addresses = addressesLine.split("\",\"");
			     //cut the ,,,,,city,county off from the each addresses
			     for (int i=0; i<addresses.length; i++){
			       addresses[i] = addresses[i].substring(0,addresses[i].indexOf(","));
			     }
			     
			     //create PosctCode object
			     return new PostCode(latitude, longitude, addresses );
	}
	
	//throws exception: bad practice exception not handeled it in the 
	//throws it back to the calling program (JVM and Op system)
	public static void main(String[] args) throws Exception{	
	     PostCode from, to;
	     from = toPostCode("LU28AU");
	     to = toPostCode("HP26AA");
	     
	     //print out members
	     System.out.println(from);
	     System.out.println(to);
	     System.out.println(from.getDistance(to));
	}
}
