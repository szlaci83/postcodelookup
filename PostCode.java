package network;

import java.util.Arrays;
import java.math.*;

public class PostCode {
private double longitude;
private double latitude;
private String [] addresses;

public double getLongitude() {
	return longitude;
}
public void setLongitude(double lattitude) {
	this.longitude = lattitude;
}
public double getLatitude() {
	return latitude;
}
public void setLatitude(double altitude) {
	this.latitude = altitude;
}
public String[] getAddresses() {
	return addresses;
}
public void setAddresses(String[] addresses) {
	this.addresses = addresses;
}

//constructor to create an object from the result of JSON query
public PostCode(String inputLine){
	this.latitude = Double.parseDouble(inputLine.substring(inputLine.indexOf(":")+1, inputLine.indexOf(",")));
   	this.longitude = Double.parseDouble(inputLine.substring(inputLine.indexOf("Longitude\":")+11, inputLine.indexOf(",\"Addresses")));
   	String addressesLine = inputLine.substring(inputLine.indexOf("[")+2,inputLine.length()-3);
    
    //split the addresses into a String array by ","
    String [] addresses = addressesLine.split("\",\"");
    //cut the ,,,,,city,county off from the each addresses
    for (int i=0; i<addresses.length; i++){
      addresses[i] = addresses[i].substring(0,addresses[i].indexOf(","));
    }
    this.addresses=addresses;
}

/**constructor creating object with Lattitude, longitude, list of address values*/
public PostCode(double latitude, double longitude, String[] addresses ){
	this.latitude = latitude;
	this.longitude = longitude;
	this.addresses = addresses;		
}

public double getDistance(PostCode dest){
	//return (Math.sqrt(Math.abs(this.getLatitude() - dest.getLatitude())+ Math.abs(this.getLongitude() - dest.getLongitude())));

	//using the Equirectangular approximation: 
	//(http://www.movable-type.co.uk/scripts/latlong.html)
	final int R = 6371; // Radious of the earth in km
	
	Double latDistance = Math.toRadians(dest.getLatitude()-this.getLatitude());
    Double lonDistance = Math.toRadians(dest.getLongitude()-this.getLongitude());
    
    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + 
               Math.cos(Math.toRadians(this.getLatitude())) * Math.cos(Math.toRadians(dest.getLatitude())) * 
               Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    return R * c; // returns km
}

//TODO implement google API lookup for distance


@Override
public String toString() {
	return "PostCode [longitude=" + longitude + ", latitude=" + latitude + ", addresses=" + Arrays.toString(addresses)
			+ "]";
}
}
