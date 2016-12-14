import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;


public class B1 {
	
	String publicIdentity;
	BigInteger p,q, M;
	ArrayList<byte[]> unenryptedBits;
	BigInteger biNegative, bi0, bi1, bi2, bi3, bi4, bi5, bi6, bi7, bi8, bi9;
	Random random;

	public B1(String publicIdentity, String p, String q, String[] unencryptedBits){
		this.publicIdentity = publicIdentity;
		this.p = new BigInteger(p, 16);
		this.q = new BigInteger(q, 16);
		this.unenryptedBits = new ArrayList();
		for(int i = 0; i < unencryptedBits.length; i++){
			this.unenryptedBits.add(hexToByte(unencryptedBits[i]));
		}
		M = this.p.multiply(this.q);
		biNegative = BigInteger.valueOf(-1);
	    bi0 = BigInteger.valueOf(0);
	    bi1 = BigInteger.valueOf(1);
	    bi2 = BigInteger.valueOf(2);
	    bi3 = BigInteger.valueOf(3);
	    bi4 = BigInteger.valueOf(4);
	    bi5 = BigInteger.valueOf(5);
	    bi6 = BigInteger.valueOf(6);
	    bi7 = BigInteger.valueOf(7);
	    bi8 = BigInteger.valueOf(8);
	    bi9 = BigInteger.valueOf(9);
	    this.random = new Random();
	}
	
	public String byteToHex(byte[] b){
		return DatatypeConverter.printHexBinary(b);
	}
	
	public byte[] hexToByte(String s){
		return DatatypeConverter.parseHexBinary(s);
	}
	
	public byte[] combineByteArrays(byte[] b1, byte[] b2){
		byte[] bytesCombined = new byte[b1.length+b2.length];
		System.arraycopy(b1, 0, bytesCombined, 0, b1.length);
		System.arraycopy(b2, 0, bytesCombined, b1.length, b2.length);
		System.out.println(Arrays.toString(bytesCombined));
		return bytesCombined;
	}
	
	public byte[] SHA1(byte[] arr){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md.digest(arr);
	}
	
	//TAKEN FROM: https://cryptocode.wordpress.com/2008/08/16/jacobi-symbol/
	public BigInteger jacobi(BigInteger a, BigInteger n) {
	    BigInteger ans = BigInteger.valueOf(1337);
	    if (a.compareTo(bi0) == 0)
	        ans = (n.compareTo(bi1) == 0) ? bi1 : bi0;
	    else if (a.compareTo(bi2) == 0) {
	        switch ( n.mod(bi8).intValue() ) {
	            case 1:
	            case 7:
	                    ans.equals(1);
	                    break;
	            case 3:
	            case 5:
	                    ans.equals(-1);
	                    break;
	        }
	    }
	    else if ( a.compareTo(n) >= 0 )
	        ans = jacobi(a.mod(n), n);
	    else if ( a.mod(bi2).compareTo(bi0) == 0 )
	        ans = (jacobi(bi2,n)).multiply(jacobi(a.divide(bi2), n));
	    else
	        ans = ( a.mod(bi4).compareTo(bi3) == 0 && n.mod(bi4).compareTo(bi3) == 0 ) ? jacobi(n,a).multiply(biNegative) : jacobi(n,a);
	    return ans;
	}
	
	public int jacobi2(BigInteger a, BigInteger n) {
		int ans = 0;
		if (bi0.equals(a))
			ans = (bi1.equals(n)) ? 1 : 0;
		else if (bi2.equals(a)) {
			BigInteger mod = n.mod(bi8);
			if (bi1.equals(mod) || bi7.equals(mod))
				ans = 1;
			else if (bi3.equals(mod) || bi5.equals(mod))
				ans = -1;
		} else if (a.compareTo(n) >= 0)
			ans = jacobi2(a.mod(n), n);
		else if (bi0.equals(a.mod(bi2)))
			ans = jacobi2(bi2, n) * jacobi2(a.divide(bi2), n);
		else
			ans = (bi3.equals(a.mod(bi4)) && bi3.equals(n.mod(bi4))) ? -jacobi2(n, a) : jacobi2(n, a);
		return ans;
	}
		
	public void run(){
		BigInteger jakobiSymbol = bi0;
		byte[] hashedID = SHA1(publicIdentity.getBytes());
		String dot = ".";
//		while(jakobiSymbol.compareTo(bi1) != 0){
//			hashedID = SHA1(hashedID);
//			jakobiSymbol = BigInteger.valueOf( jacobi2( (new BigInteger(hashedID) ), bi0) );
//			System.out.print(dot);
//		}
		while(jacobi2(new BigInteger(byteToHex(hashedID), 16),M) != 1){
			System.out.print(dot);
			hashedID = SHA1(hashedID);
		}
		System.out.println();
		BigInteger a = new BigInteger(hashedID);
		BigInteger rUp = M.add(bi5).subtract(p).subtract(q).divide(bi8);
		BigInteger r = a.modPow(rUp, M);
		System.out.println("r = "+r.toString(16));
		
		ArrayList<byte[]> bitOutputs = new ArrayList();
		String res = "";
		for(int i = 0; i < unenryptedBits.size(); i++){
			hashedID = SHA1(unenryptedBits.get(i));
			while(jacobi2(new BigInteger(byteToHex(hashedID), 16),M) != 1){
				
				hashedID = SHA1(hashedID);
			}
			bitOutputs.add(hashedID);
		}
		
		
//		BigInteger x = bi1;
//		if(random.nextInt(2) == 0){
//			x = biNegative;
//		}
//		BigInteger t = BigInteger.valueOf(random.nextInt(Integer.MAX_VALUE)).mod(M);
//		BigInteger s = t.add(a.divide(t)).mod(M);
	}
	
	//SOURCE: https://www.ime.usp.br/~rt/cranalysis/IBECCocks.pdf
	public static void main(String[] args){
		String bitStr01 = "2f2aa07cfb07c64be95586cfc394ebf26c2f383f90ce1d494dde9b2a3728ae9b";
		String bitStr02 = "63ed324439c0f6b823d4828982a1bbe5c34e66d985f55792028acd2e207daa4f";
		String bitStr03 = "85bb7964196bf6cce070a5e8f30bc822018a7ad70690b97814374c54fddf8e4b";
		String bitStr04 = "30fbcc37643cc433d42581f784e3a0648c91c9f46b5671b71f8cc65d2737da5c";
		String bitStr05 = "5a732f73fb288d2c90f537a831c18250af720071b6a7fac88a5de32b0df67c53";
		String bitStr06 = "504d6be8542e546dfbd78a7ac470fab7f35ea98f2aff42c890f6146ae4fe11d6";
		String bitStr07 = "10956aff2a90c54001e85be12cb2fa07c0029c608a51c4c804300b70a47c33bf";
		String bitStr08 = "461aa66ef153649d69b9e2c699418a7f8f75af3f3172dbc175311d57aeb0fd12";
		String[] bits = {bitStr01, bitStr02, bitStr03, bitStr04, bitStr05, bitStr06, bitStr07, bitStr08};
		B1 b1 = new B1("walterwhite@crypto.sec", "9240633d434a8b71a013b5b00513323f", "f870cfcd47e6d5a0598fc1eb7e999d1b", bits);
		b1.run();
	}
}
