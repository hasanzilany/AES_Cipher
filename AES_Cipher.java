package aesCrypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AES_Cipher {
	
	//key,initVector Must be 16 bytes
    private static final String key = "AyeshaSiddikaSon";
    private static final String initVector = "HasanMohiuddinZi";
    
    
public static String encrypt(String plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
	Cipher ciphertext = Cipher.getInstance("AES/CBC/PKCS5Padding");
	
	 IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
     SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
     
     ciphertext.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
     return Base64.getEncoder().encodeToString(ciphertext.doFinal(plaintext.getBytes("UTF-8")));
    
}

public static String decrypt(String encryptedtxt) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
   
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
 
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
 
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedtxt)));
        
}



	public static void main(String[] args) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		
		  //Change File repository according to your computer 
		String path = System.getProperty("user.dir");
		  File plaintext = new File(path + "\\plaintext.txt"); 
		  if(!plaintext.exists()) {
			  System.out.println("File does not exist " );
			  return;
		  }
		  String cryptxt = null;
			 DataInputStream enc = new DataInputStream(new FileInputStream(plaintext));
			    int nBytesToRead = enc.available();
			    if(nBytesToRead > 0) {
			        byte[] bytes = new byte[nBytesToRead];
			        enc.read(bytes);
			        cryptxt = new String(bytes);
			        enc.close();
			    }
		  String st = encrypt(cryptxt); 
		//Change File repository according to your liking, currently set to default directory 
		  File myFile = new File(path + "\\crypto.txt");
          
		  FileWriter fileWriter = new FileWriter(myFile);
	      fileWriter.write(st);
			
		  fileWriter.flush();
		  fileWriter.close();;
		  
		  
		  //decode file----------------------------
        
		  String stcryp = decrypt(st); 
		  
		//Change File repository according to your liking, currently set to default directory 
		  String path2 = System.getProperty("user.dir");
		
		  File myFileplain = new File(path2 + "\\cleartext.txt");
          
		  FileWriter fileWriterP = new FileWriter(myFileplain);
	      fileWriterP.write(stcryp);
			
		  fileWriterP.flush();
		  fileWriterP.close();;
		  System.out.println("Encryption and decryption done! Files created!" );
	}


}
