package com.criptografia.es;
import java.security.Key;
import java.security.Provider;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class DemoCrypto {
	public static String ENCRYPT_KEY="clave-simetrica-no-revelar-nunca";
	/*	Lo metemos en properties o en DDBB mejor.
	 * 	AES admite solo claves del 16,24,32,128,256 bytes
	 * 	tal y como está, es de 32 bytes. ENCRYPT_KEY (No clave personal).
	 */
	
	private static void dameProveedoresSeguridad() {
		Provider[] provs = Security.getProviders();
		for (Provider p: provs) {
			System.out.println(p.getName());
		}
	}
	
	private static String encripto(String text) throws Exception {
		Key aesKey = new SecretKeySpec(ENCRYPT_KEY.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, aesKey);
		
		System.out.println("Proveedor actual: "+cipher.getProvider()+"\n");
		byte[] encriptado = cipher.doFinal(text.getBytes());
		return Base64.encodeBase64String(encriptado);
	}
	
	private static String decripto(String encripte) throws Exception {
		byte[] encriptadosBytes=Base64.decodeBase64(encripte.replace("\n", ""));
		Key aesKey = new SecretKeySpec(ENCRYPT_KEY.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, aesKey);
		
		String decripte = new String(cipher.doFinal(encriptadosBytes));
		return decripte;
	}
	
	public static void main(String[] args) throws Exception {
		dameProveedoresSeguridad();
		String micripto=encripto("veamos que tal va el tema espero que bien");
		System.out.println("encriptado: "+micripto);
		
		String midecripto=decripto(micripto);
		System.out.println("decriptado: "+midecripto);

	}

}
