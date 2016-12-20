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
	ArrayList<BigInteger> unenryptedBits;
	BigInteger biNegative, bi0, bi1, bi2, bi3, bi4, bi5, bi6, bi7, bi8, bi9;
	Random random;
	
	static BigInteger zero = BigInteger . ZERO ;
	static BigInteger one = BigInteger .ONE;
	static BigInteger two = new BigInteger ("2");
	static BigInteger three = new BigInteger ("3");
	static BigInteger four = new BigInteger ("4");
	static BigInteger five = new BigInteger ("5");
	static BigInteger eight = new BigInteger ("8");

	public B1(String publicIdentity, String p, String q, String[] unencryptedBits){
		this.publicIdentity = publicIdentity;
		this.p = new BigInteger(p, 16);
		this.q = new BigInteger(q, 16);
		this.unenryptedBits = new ArrayList();
		for(int i = 0; i < unencryptedBits.length; i++){
			this.unenryptedBits.add(new BigInteger(unencryptedBits[i], 16));
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
	
	public int jacobi(BigInteger n, BigInteger m) {
		int j = 1;
		int t;
		BigInteger tmp;
		n = n.mod(m);
		while (!n.equals(zero)) {
			t = 0;
			while (!n.and(one).equals(one)) {
				n = n.divide(two);
				t++;
			}
			BigInteger mmod8 = m.mod(eight);
			if (((t & 0x01) == 1)
					&& (mmod8.equals(three) || mmod8.equals(five))) {
				j = -j;
			}
			if (n.mod(four).equals(three) && m.mod(four).equals(three)) {
				j = -j;
			}
			tmp = n;
			n = m.mod(n);
			m = tmp;
		}
		if (m.equals(one)) {
			return j;
		}
		return 0;
	}

		
	public void run(){
		BigInteger jakobiSymbol = bi0;
		byte[] hashedID = SHA1(publicIdentity.getBytes());
		String dot = ".";
		while(jacobi(new BigInteger(byteToHex(hashedID), 16),M) != 1){
			System.out.print(dot);
			hashedID = SHA1(hashedID);
		}
		System.out.println();
		BigInteger a = new BigInteger(hashedID);
		BigInteger rUp = M.add(bi5).subtract(p).subtract(q).divide(bi8);
		BigInteger r = a.modPow(rUp, M);
		System.out.println("r = "+r.toString(16));
		
		String msg = "";
		for(int i = 0; i < unenryptedBits.size(); i++){
			BigInteger s = unenryptedBits.get(i);
			BigInteger t = (s.add(r.multiply(bi2)));
			int temp = jacobi(t,M);
			if(temp == -1){
				msg = msg + "0";
			}
			else{
				msg = msg + "1";
			}
		}
		int decMsg = Integer.parseInt(msg, 2);
		System.out.println("msg: "+msg);
		System.out.println("msg (decimal): " + decMsg);
	}
	
	//SOURCE: https://www.ime.usp.br/~rt/cranalysis/IBECCocks.pdf
	public static void main(String[] args){
		String bitStr01 = "771bb448bfd68e320193b25d6a01018dd139ae4ee9fa8f5df319e50b44f0418b";
		String bitStr02 = "7fbe5f33122084221aeec53a8175ca2997250687f7518e528130a3ea4f3d6948";
		String bitStr03 = "0df4136273b4f77ed66c91ed2c8351612cf5b8de22d8d29531972d1eca6b4b68";
		String bitStr04 = "068c9a0281859bb62a66de4c75264d234c34fa8ee10114abd86b2ed70363efa8";
		String bitStr05 = "078f4cbfee88121422e60915154ec0e7e91af1e13ae6ade40c1f389287a50c3c";
		String bitStr06 = "2e6777640cd02dd0b26e1846c142aaa32cdb3bbbe8ec9d080ce74469ee11408b";
		String bitStr07 = "4acae1f2dadf8c5e2ef67349816c5aa441f1af527c5834ee619a783e524c0ac1";
		String bitStr08 = "3f4553a2e7b171c86a750d96d341cb89e82e1fd2018f1cd66e3e4482e7873012";
		String[] bits = {bitStr01, bitStr02, bitStr03, bitStr04, bitStr05, bitStr06, bitStr07, bitStr08};
		B1 b1 = new B1("mallory@crypto.sec", "9240633d434a8b71a013b5b00513323f", "f870cfcd47e6d5a0598fc1eb7e999d1b", bits);
		b1.run();
	}
}
