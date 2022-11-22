

package aesCrypto;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5_Hash {

    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private static final String OUTPUT_FORMAT = "%-20s:%s";

    private static byte[] checksum(String filePath) {

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }

        try (InputStream is = new FileInputStream(filePath);
             DigestInputStream dis = new DigestInputStream(is, md)) {
            while (dis.read() != -1) ; //empty loop to clear the data
            md = dis.getMessageDigest();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return md.digest();

    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
    	
    	String path2 = System.getProperty("user.dir");
    	
        String file = path2+"\\sample.dat";
        String cryptxt = null;
		 DataInputStream enc = new DataInputStream(new FileInputStream(file));
		    int nBytesToRead = enc.available();
		    if(nBytesToRead > 0) {
		        byte[] bytes = new byte[nBytesToRead];
		        enc.read(bytes);
		        cryptxt = new String(bytes);
		        enc.close();
		    }
		    System.out.println("Input = "+cryptxt);
        System.out.println(String.format(OUTPUT_FORMAT, "Input (file) ", file));
        String stcryp = bytesToHex(checksum(file));
        System.out.println(String.format(OUTPUT_FORMAT, "MD5 (checksum hex) ", bytesToHex(checksum(file))));
        File myFileplain = new File(path2 + "\\hvalue.txt");
        
		  FileWriter fileWriterP = new FileWriter(myFileplain);
	      fileWriterP.write(stcryp);
			
		  fileWriterP.flush();
		  fileWriterP.close();;

    }

}