import java.math.BigInteger;


public class C1 {
public static void main(String args[]){
	
	//Useful sites//
	/*
	http://lapo.it/asn1js
	https://conv.darkbyte.ru/
	http://www.rapidtables.com/convert/number/hex-to-decimal.htm
	http://www.miniwebtool.com/remove-spaces/
	http://www.miniwebtool.com/remove-line-breaks/
	http://elearning.eit.lth.se/moodle/pluginfile.php/11774/mod_resource/content/7/home-assignment-5.pdf
	*/
		
/*
	-----BEGIN RSA PRIVATE KEY-----
	MIICXQIBAAKBgQDGlcensoredcensoredcensoredcensored1TUxhnjkCbowxZc
	7PIpI1E2Po6aIgCBd9+6i0NUIfYm8vR6kqiqLz8k8o4LYoBkq/9Jx7pgV2Jqhr4u
	wvlaQQUzi9c4qPKXp+QGoUu9f1zp8ORIMpeJmF7uA20DC93uba07qdC6twIDAQAB
	AoGBAIovDuYnGiiQS6K27L4EY8e/5sbqAwdlTOVlWsfz+ai3DLNiFPSbbT1Wx9G4
	4b06X6O258SD1suZ/g/ICnmnxxe5ua3a5+iiDIwGYmBDcNfq5gMq/d+1/UJF/Bb4
	A1nuH2iUg6gRTPEpbg2+RYwquyWenFbqfHMgXqbHVGmOXj7hAkEA8rChKjs5zVmd
	j9Gk53psry4CtuxRc39NrHuLqat9Iu0MA51Sgv4c+8dgo75DVAnT5PoLBhHJJAVa
	e+rUMC4kfwJBANF7jcKzJ2UuPmL6JpbWcyirybjMIm2eCxR5U1bYlNYT+A49oOFS
	Eg5woswgCyH9gDPk2Zwpq3qud9HD7Rn0bckCQQDHgwdrRXc2ZybN1eZAWffBaAzZ
	PpuTXKOJWaOuX4mnTcLjsdDkWW2QWw8Kbd7B1rZ49kpbugFmeHQzjRDVbwmXAkBm
	T3nFBcrP1+4QWSxPrx0/V+eFoe2OrAmtTjQtzkmi5M3Z5q+UXIkFFG3uVBgb2bur
	nLHLW26s1Fkg0hgS/RZBAkAFnE+7QvRCW4+v3OsIkN63f+GIjHfCuv8L15RpBLlf
	XXQyOmmu8YekTu5vbFHtSAiLyuW1yCe
	-----END RSA PRIVATE KEY-----
 */	
/*
	RSAPrivateKey ::= SEQUENCE {
		version Version,
		modulus INTEGER, -- n
		publicExponent INTEGER, -- e
		privateExponent INTEGER, -- d
		prime1 INTEGER, -- p
		prime2 INTEGER, -- q
		exponent1 INTEGER, -- d mod (p-1)
		exponent2 INTEGER, -- d mod (q-1)
		coefficient INTEGER, -- (inverse of q) mod p
		otherPrimeInfos OtherPrimeInfos OPTIONAL
	}
*/

//	SOURCE: http://lapo.it/asn1js/
//	Censored part = MODULUS
//
//	How to recover modulus: http://stackoverflow.com/questions/5747013/how-to-factor-rsa-modulus-given-the-public-and-private-exponent
//	N = p*q
//	p = prime1 = field 5 = HEX 024100D17B8DC2B327652E3E62FA2696D67328ABC9B8CC226D9E0B14795356D894D613F80E3DA0E152120E70A2CC200B21FD8033E4D99C29AB7AAE77D1C3ED19F46DC9
//	q = prime2 = field 6 = HEX 024100C783076B4577366726CDD5E64059F7C1680CD93E9B935CA38959A3AE5F89A74DC2E3B1D0E4596D905B0F0A6DDEC1D6B678F64A5BBA01667874338D10D56F0997

//	p (dec) = 1980505096443354445224925611252476104412870551515842512767281401556308983401306838192254943467318671111134420582998775670770339401551546738449797490299546922441
//	q (dec) = 1980504574230191093822074091571876301864210060150881719777308603730167993946182011176381808201062734312312353774182268005589509235752455149547125424083925141911
//	N = 5148499d6717ee0db88ed73528b7a22e06fce681acae57985270853a8208cd33e0bca89e45ae34e3ea1888228a74ecc0eacfcac426fd8b5b1f06e51cbb32a140b0adbd8958bbd922756096d1e220381f5af613830f32119174d727ce4cee05f71e4ffb76f1d4fd676eb2581ea8c923cbd572a3ae9dd4d671f7aec4add79ce18a7be2fd28f
//
//	https://conv.darkbyte.ru/
//	encrypted message (64)= Qe7+h9OPQ7PN9CmF0ZOmD32fwpJotrUL67zxdRvhBn2U3fDtoz4iUGRXNOxwUXdJ2Cmz7zjS0DE8ST5dozBysByz/u1H//iAN+QeGlFVaS1Ee5a/TZilrTCbGPWxfNY4vRXHP6CB82QxhMjQ7/x90/+JLrhdAO99lvmdNetGZjY=
//	encrypted message (16) = 41eefe87d38f43b3cdf42985d193a60f7d9fc29268b6b50bebbcf1751be1067d94ddf0eda33e2250645734ec70517749d829b3ef38d2d0313c493e5da33072b01cb3feed47fff88037e41e1a5155692d447b96bf4d98a5ad309b18f5b17cd638bd15c73fa081f3643184c8d0effc7dd3ff892eb85d00ef7d96f99d35eb466636

	String pHex = "00f2b0a12a3b39cd599d8fd1a4e77a6caf2e02b6ec51737f4dac7b8ba9ab7d22ed0c039d5282fe1cfbc760a3be435409d3e4fa0b0611c924055a7bead4302e247f";
	String qHex = "00d17b8dc2b327652e3e62fa2696d67328abc9b8cc226d9e0b14795356d894d613f80e3da0e152120e70a2cc200b21fd8033e4d99c29ab7aae77d1c3ed19f46dc9";
	BigInteger p = new BigInteger(pHex, 16);
	BigInteger q = new BigInteger(qHex, 16);
	BigInteger N = p.multiply(q);
	System.out.println("N: "+N.toString(16));
	System.out.println("N length: "+N.toString(16).length());
	System.out.println("N length hex pairs: "+N.toString(16).length()/2);
	
	System.out.println("Now to insert the correct N in our RSA private key...");
	String censoredHex = "3082025D02010002818100C695C7A7B28ADE75C7A7B28ADE75C7A7B28ADE75C7A7B28ADE7754D4C619E39026E8C3165CECF2292351363E8E9A22008177DFBA8B435421F626F2F47A92A8AA2F3F24F28E0B628064ABFF49C7BA6057626A86BE2EC2F95A4105338BD738A8F297A7E406A14BBD7F5CE9F0E448329789985EEE036D030BDDEE6DAD3BA9D0BAB70203010001028181008A2F0EE6271A28904BA2B6ECBE0463C7BFE6C6EA0307654CE5655AC7F3F9A8B70CB36214F49B6D3D56C7D1B8E1BD3A5FA3B6E7C483D6CB99FE0FC80A79A7C717B9B9ADDAE7E8A20C8C0662604370D7EAE6032AFDDFB5FD4245FC16F80359EE1F689483A8114CF1296E0DBE458C2ABB259E9C56EA7C73205EA6C754698E5E3EE1024100F2B0A12A3B39CD599D8FD1A4E77A6CAF2E02B6EC51737F4DAC7B8BA9AB7D22ED0C039D5282FE1CFBC760A3BE435409D3E4FA0B0611C924055A7BEAD4302E247F024100D17B8DC2B327652E3E62FA2696D67328ABC9B8CC226D9E0B14795356D894D613F80E3DA0E152120E70A2CC200B21FD8033E4D99C29AB7AAE77D1C3ED19F46DC9024100C783076B4577366726CDD5E64059F7C1680CD93E9B935CA38959A3AE5F89A74DC2E3B1D0E4596D905B0F0A6DDEC1D6B678F64A5BBA01667874338D10D56F09970240664F79C505CACFD7EE10592C4FAF1D3F57E785A1ED8EAC09AD4E342DCE49A2E4CDD9E6AF945C8905146DEE54181BD9BBAB9CB1CB5B6EACD45920D21812FD16410240059C4FBB42F4425B8FAFDCEB0890DEB77FE1888C77C2BAFF0BD7946904B95F5D74323A69AEF187A44EEE6F6C51ED48088BCAE5B5C82792B0D98A6245FA130F7D";
	
	//it is offset by 14 hex digits, according to ASN.1 JavaScript decoder
	//the following INTEGER is offset by 139*2=278 hex digits
	//
	char[] privateKeyHexChars = censoredHex.toCharArray();
	String NString = N.toString(16);
	if(NString.length()/2 < 129){
		NString = "00"+NString;
	}
	System.out.println("New N: "+NString);
	int newNLength = NString.length()/2;
	System.out.println("New N length hex: "+newNLength);
	char[] NChars = N.toString(16).toCharArray();
	System.out.println("NChars length: "+NChars.length);
	int forlength = 278-1-14-(3*2);
	System.out.println("For loop length: "+forlength);
	int NOffset = 7*2;
	int NHeader = 3*2;
	for(int i = 0; i<NChars.length; i++){
		int temp = i+NOffset+NHeader;
		System.out.println("Placed in: (char)"+temp+" (hex)"+(temp/2));
		privateKeyHexChars[i+2+NOffset+NHeader] = NChars[i];
	}
	String privateKeyHexRestored = new String(privateKeyHexChars);
	
	System.out.println("Key o: "+censoredHex);
	System.out.println("Key n: "+privateKeyHexRestored);
	
	//DECRYPTION
	//C:\OpenSSL-Win32\bin\openssl.exe
	
	//enc -in C:\OpenSSL-Win32\bin\C1msg.txt -out C:\OpenSSL-Win32\bin\C1msgBinary.txt -d -a
	//rsautl -decrypt -in C:\OpenSSL-Win32\bin\C1msg.b64 -out C:\OpenSSL-Win32\bin\C1Out.txt -inkey C:\OpenSSL-Win32\bin\C1key.pem
	//rsa 
	//base64 -d C:\OpenSSL-Win32\bin\blob.b64 > blob
	//certutil -encode inputFileName encodedOutputFileName
	
	/*
	certutil -decode C:\OpenSSL-Win32\bin\C1msg.txt C:\OpenSSL-Win32\bin\C1msg.b64
	rsautl -decrypt -in C:\OpenSSL-Win32\bin\C1msg.b64 -out C:\OpenSSL-Win32\bin\C1Out.txt -inkey C:\OpenSSL-Win32\bin\C1key.pem
	*/
	//cms -decrypt -in C:\OpenSSL-Win32\bin\C1msg.txt  -recip C:\OpenSSL-Win32\bin\C1key.pem -inkey C:\OpenSSL-Win32\bin\C1key.pem -out C:\OpenSSL-Win32\bin\C1DecryptOut.txt
	
	String cryptMsg16 = "41eefe87d38f43b3cdf42985d193a60f7d9fc29268b6b50bebbcf1751be1067d94ddf0eda33e2250645734ec70517749d829b3ef38d2d0313c493e5da33072b01cb3feed47fff88037e41e1a5155692d447b96bf4d98a5ad309b18f5b17cd638bd15c73fa081f3643184c8d0effc7dd3ff892eb85d00ef7d96f99d35eb466636";
	BigInteger cryptMsg = new BigInteger(cryptMsg16, 16);
}
}
