package com.hooni.encrypt;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;




public class AesIO extends Aes
{
	public AesIO()
	{
		super();
	}
	
	/** 
	 * Encrypt a text using AES encryption in Counter mode of operation
	 *
	 * Unicode multi-byte character safe
	 *
	 * @param {String} plaintext Source text to be encrypted
	 * @param {String} password  The password to use to generate a key
	 * @param {Number} nBits     Number of bits to be used in the key (128, 192, or 256)
	 * @throws UnsupportedEncodingException 
	 * @returns {string}         Encrypted text
	 */
	
	public String encrypt(String plaintext, String password, int nBits) throws Exception
	{
		 int blockSize = 16;  // block size fixed at 16 bytes / 128 bits (Nb=4) for AES
		  if (!(nBits==128 || nBits==192 || nBits==256)) return "";  // standard allows 128/192/256 bit keys
		  
		  Charset cset = Charset.forName("UTF-8");

		  StringBuffer ptext = new StringBuffer(plaintext);
		  StringBuffer pword = new StringBuffer(password);
		  
		  
		  int nBytes = nBits/8;
		  int[] pwBytes = new int[nBytes];
		  int psize = pword.length();
		  
		  
		  for (int j=0; j<nBytes; j++)
		  { 
			  if (j < psize)
				  pwBytes[j] = pword.charAt(j);
			  else
				  pwBytes[j] = 0;
		  }
		  int[] i_key = cipher(pwBytes, keyExpansion(pwBytes));
		  int[] key = new int[i_key.length + nBytes-16];
		  key = Arrays.copyOf(i_key, key.length);
		  System.arraycopy(i_key, 0, key, i_key.length, nBytes-16);
		  
			 		  
		  int[] counterBlock = new int[blockSize];
		  long nonce = (new Date()).getTime();
		  long nonceMs = nonce%1000;
		  long nonceSec = (long) Math.floor(nonce/1000);
		  long nonceRnd = (long) Math.floor(Math.random() * 0xffff);
		  
		  for (int i=0; i<2; i++) counterBlock[i] = (int) ((nonceMs >>> i*8) & 0xff);
		  for (int i=0; i<2; i++) counterBlock[i+2] = (int) ((nonceRnd >>> i*8) & 0xff);
		  for (int i=0; i<4; i++) counterBlock[i+4] = (int) ((nonceSec >>> i*8) & 0xff);
		  
		  StringBuffer binput = new StringBuffer();
		  for (int i=0; i<8; i++)
		  {
			  binput.append((char)counterBlock[i]);
		  }
		  
		  String ctrTxt = binput.toString();
		  
		  
		  int[][] keySchedule = keyExpansion(key);
		  
		  int blockCount = (int) Math.ceil((double)ptext.length()/(double)blockSize);
		  
		  
		  StringBuffer cinput = new StringBuffer();
		  
		  for (int b=0; b<blockCount; b++)
		  {
			  for (int c=0; c<4; c++) counterBlock[15-c] = (b >>> c*8) & 0xff;
			  for (int c=0; c<4; c++) counterBlock[15-c-4] = (int) (b/0x100000000L >>> c*8);
			  
			 			  
			  
			  int [] cipherCntr = cipher(counterBlock, keySchedule);
			  
			  int blockLength = b<blockCount-1 ? blockSize : (ptext.length()-1)%blockSize+1;
			  
			  for (int i=0; i<blockLength; i++) {  // -- xor plaintext with ciphered counter char-by-char --
				  char ccc = (char)((char)cipherCntr[i] ^ ptext.charAt(b*blockSize+i));
			      cinput.append(ccc);
			  }
		  }
		  String cipherTxt= cinput.toString();
		  
		  cipherTxt = ctrTxt + cipherTxt;
		  System.out.println("Before base64.encode: " + cipherTxt);
		  return Base64.encode(cipherTxt);
	}
	/** 
	 * Decrypt a text encrypted by AES in counter mode of operation
	 *
	 * @param {String} ciphertext Source text to be encrypted
	 * @param {String} password   The password to use to generate a key
	 * @param {Number} nBits      Number of bits to be used in the key (128, 192, or 256)
	 * @throws Exception 
	 * @returns {String}          Decrypted text
	 */
	public String decrypt(String cipherText, String password, int nBits) throws Exception
	{
		int blockSize = 16;
		
		
		if (!(nBits==128 || nBits == 192 || nBits == 256))
		{
			System.err.println("standard allows 128/192/256 bit keys");
			return "";
		}
		
		Charset cset = Charset.forName("UTF-8");
		String ctext = Base64.decode(cipherText);
		 StringBuffer pword = new StringBuffer(password);
		 
		int nBytes = nBits/8;
		  int[] pwBytes = new int[nBytes];
		  int psize = pword.length();
		  
		  
		  for (int j=0; j<nBytes; j++)
		  { 
			  if (j < psize)
				  pwBytes[j] = pword.charAt(j);
			  else
				  pwBytes[j] = 0;
		  }
		  int[] i_key = cipher(pwBytes, keyExpansion(pwBytes));
		  int[] key = new int[i_key.length + nBytes-16];
		  key = Arrays.copyOf(i_key, key.length);
		  System.arraycopy(i_key, 0, key, i_key.length, nBytes-16);
		
		
		
		int[] counterBlock =  new int[blockSize];
		String ctrTxt = ctext.substring(0,8);
		
		
		for (int i=0; i<8; i++)
		{
			counterBlock[i] = ctrTxt.charAt(i);
			
		}
		
		int[][] keySchedule = keyExpansion(key);
		
		int nBlocks = (int) Math.ceil((double)(ctext.length()-8) / (double)blockSize);
		
		  //int blockCount = (int) Math.ceil((double)ptext.length()/(double)blockSize);
		
		
		StringBuffer[] ct = new StringBuffer[nBlocks];
		for (int b = 0;  b<nBlocks; b++)
		{
			if (8+b*blockSize+blockSize > ctext.length())
				ct [b]= new StringBuffer(ctext.substring(8+b*blockSize));
			else
				ct [b]= new StringBuffer(ctext.substring(8+b*blockSize, 8+b*blockSize+blockSize));
		}
		 
		StringBuffer cinput = new StringBuffer();
		
		
		for (int b=0; b<nBlocks; b++)
		{
			
			for (int c=0; c<4; c++) counterBlock[15-c] = (b >>> c*8) & 0xff;
			for (int c=0; c<4; c++) counterBlock[15-c-4] = (int) ((long)b/0x100000000L >>> (long)c*8);
			  
			
			
			
			int[] cipherCntr = cipher(counterBlock, keySchedule);
			
			
			for (int i=0; i<ct[b].length(); i++)
			{
			      // -- xor plaintxt with ciphered counter byte-by-byte --
				char ccc = (char)((char)cipherCntr[i] ^ ct[b].charAt(i));
			    cinput.append(ccc);
			     
			 }
			
		}
		
				
		return cinput.toString();
	}
	
	public static final byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
	}
}
