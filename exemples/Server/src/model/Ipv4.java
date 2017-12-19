package model;

import java.net.InetAddress;

import model.exceptions.IllegalIpException;

/**
 * @author Yann D'ISANTO
 * and some modifications by Jimmy Vogel
 * 
 * This class represent and ipv4 address.
 * **/
public class Ipv4{
 
    private byte[] rawIp;
 
    //Constructor
    public Ipv4(InetAddress inet) throws IllegalIpException{
    	byte[] rawIp = inet.getAddress();
        if(rawIp.length != 4) {
        	System.out.println(rawIp.length);
            throw new IllegalIpException();
        }
        this.rawIp = rawIp;
    }
 
    //Constructor
    public Ipv4(String ip) throws IllegalArgumentException{
        if(!isIpv4String(ip)) {
            throw new IllegalArgumentException(ip + " is an invalid IP string");
        }
        rawIp = new byte[4];
        String[] fields = ip.split("\\.");
        for(int i = 0; i < 4; i ++) {
            rawIp[i] = (byte) Integer.parseInt(fields[i]);
        }
    }
 
    /**
     * Check if the chain given is an ip address.(ipv4)
     * 
     * @param ip a chain of characters
     * @return true if the chain is an ip address.
     */
    static public final Boolean isIpv4String(String ip) {
        Boolean b = java.util.regex.Pattern.
                matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}", ip);
        if(b) {
            String[] fields = ip.split("\\.");
            for(String field : fields) {
                int value = Integer.parseInt(field);
                if((value < 0) || (value > 255)) {
                    b = false;
                    break;
                }
            }
        }
        return b;
    }
 
    /**
     * Change the ip from an array of byte in this class to a string format.
     * 
     * @return an ip address in String format.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        int i;
        for(i = 0; i < 4; i++) {
            byte b = this.rawIp[i];
            sb.append(String.valueOf((b < 0) ? (256 + b) : b));
            sb.append((i != 3) ? "." : "");
        }
        return(sb.toString());
    }
 
    public void setRawIp(InetAddress inet) throws IllegalIpException {
    	byte[] raw = inet.getAddress();
        if(raw.length != 4) {
            throw new IllegalIpException();
        }
        this.rawIp = raw;
    }
}