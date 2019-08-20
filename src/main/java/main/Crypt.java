package main;

import java.util.Random;

/**
 * Used to encrypt and decrypt a password
 * 
 * @author devon
 *
 */

class Crypt {

	private Crypt() {
		super();
	}

	static String decryptWord(String word) {
		return decrypt(word);
	}

	static String encryptWord(String word) {
		return encrypt(word);
	}

	private static String junk(int i) {
		String str = new String();
		Random set = new Random();
		for (int j = 0; j < i; j++) {
			str += (char) (33 + set.nextInt(100));
		}
		return str;
	}

	private static String encrypt(String word) {
		String str = new String();
		str += junk(4);
		for (int i = 0; i < 2; i++) {
			str += (char) ((((int) word.charAt(i)) * 2) - 42);
		}
		str += junk(6);
		for (int i = 2; i < (word.length() - 2); i++) {
			str += (char) ((((int) word.charAt(i)) * 2) - 34);
		}
		str += junk(2);
		for (int i = (word.length() - 2); i < word.length(); i++) {
			str += (char) ((((int) word.charAt(i)) * 2) - 56);
		}
		str += junk(7);
		return str;
	}

	private static String decrypt(String word) {
		String str = new String();
		for (int i = 4; i < 6; i++) {
			str += (char) (((int) word.charAt(i) + 42) / 2);
		}
		for (int i = 12; i < (word.length() - 11); i++) {
			str += (char) (((int) word.charAt(i) + 34) / 2);
		}
		for (int i = (word.length() - 9); i < (word.length() - 7); i++) {
			str += (char) (((int) word.charAt(i) + 56) / 2);
		}
		return str;

	}

	public static void main(String[] args) {
		String[] unenc = { "SngprFV04A@", "qRiMJzqfTaHh!", "xaHDihEd$", "GL3ckD2E!", "yF5hOQavg@", "dPZN6MfOkYhd@",
				"fXgpE2B!", "ZkEiY2vun1!", "8YB1VvCN8I!", "svHjnlK3ka6!" };
		String [] decryp = { "CV_,|²Adr%\"q¬¾Âj?>FDvJHB+31sH>","C!y#¸z-w4|w5°xrÒÀª? n*s?zHXq!Bk","?ZPXÆ?~q?J<Vnf°®hiG?zX@yM5f"
				,"@tLbdnXEzyL&D¤´fB0#R_YgzbGv","B:K{Èb`mLVhEH®|? Ê{f?H|q=?;I/",".0?4?vr3b'G??zJxª|´?®|Y?HUR?].Zz"
				,"N(4b¢?pR#2=?¬¾hB,L~?S9AM_","/y9M?¬?3y^c~h°?BÊÈºuZ*?b'/'L{","wx/F?2QO,B0b@?ÊdzN}kZ*nap1V~","??,=¼Â+ix{r'n²º¶tD´ ?e45tm&LKJ"};
		for(int i = 0; i < decryp.length; i++) {
			
			System.out.println( decrypt(decryp[i]) == unenc[i]);
		}
	}

}
