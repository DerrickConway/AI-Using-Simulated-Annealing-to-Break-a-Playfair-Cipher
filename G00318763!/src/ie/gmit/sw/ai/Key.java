package ie.gmit.sw.ai;

import java.security.SecureRandom;


/*
 * Author: Alan Heanue - G00318763
 */


public class Key {
	private static Key instance;
	
	public Key() {
	}
	
	public static Key keyInstance() {
		return (instance == null) ? new Key() : instance;
	}

	public String generateKey() {
		StringBuilder newCipherKey = new StringBuilder();
		String cipherKey = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
		newCipherKey.append((cipherKey.length() < 25) ? new FileWorker(null).removeRecurringChars(cipherKey + "ABCDEFGHIKLMNOPQRSTUVWXYZ") : cipherKey);
		for (int i = 0; i < cipherKey.length(); i++) {	
			for (int j = newCipherKey.length() - 1; j > 0; j--) {
				if (cipherKey.charAt(i) == newCipherKey.charAt(j)) {
					if (i < j) {
						newCipherKey.deleteCharAt(j);
					}//end if
				}//end if cipher
			}//end for
		}

		//return key
		return newCipherKey.toString();
	}
	// shuffle method 
		public String shuffleKey(String originalKey) {
		SecureRandom r = new SecureRandom();
		//Random 
		int x = r.nextInt(100);
		
		if(x >= 0 && x < 2) {
			return swapRows(originalKey, r.nextInt(4), r.nextInt(4));
		} else if ( x >= 2 && x < 4) {
			return swapCols(originalKey, r.nextInt(4), r.nextInt(4));
		} else if ( x >= 4 && x < 6) {
			return flipRows(originalKey);
		} else if ( x >= 6 && x < 8) {
			return flipCols(originalKey);
		} else if ( x >= 8 && x < 10) {
			return new StringBuffer(originalKey).reverse().toString();
		} else {
			int a = r.nextInt(originalKey.length()-1);
			int b = r.nextInt(originalKey.length()-1);
			b = (a == b) ? (b == originalKey.length()-1) ? b - 1 : b + 1 : r.nextInt(originalKey.length()-1);
			char[] res = originalKey.toCharArray();
			char tmp = res[a];
			res[a] = res[b];
			res[b] = tmp;
			return new String(res);
		}
	}
	
	// flip rows
	private String flipRows(String key) {
		String[] rows = new String[5];
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < 5; i++) {
			rows[i] = key.substring(i*5, i*5 + 5);
			rows[i] = new StringBuffer(rows[i]).reverse().toString();
			sb.append(rows[i]);
		}
		return sb.toString();
	}
	private String permute(String key, int a, int b, boolean rw) {
			char[] newKey = key.toCharArray();
			if(rw) {
				a *= 5;
				b *= 5;
			} 
			for(int i = 0; i < key.length() / 5 ; i++) {
				int index = (rw) ? i : i*5;
				char tmp =  newKey[(index + a)];
				newKey[(index + a)] = newKey[(index + b)];
				newKey[(index + b)] = tmp;				
			}
			return new String(newKey);
	}
	
	//Method to flip columns
	private String flipCols(String key) {
		char[] cols = key.toCharArray();
		int length = key.length() - (key.length()/5);
		
		for(int i = 0; i < key.length() / 5; i++) {
			for(int j = 0; j < key.length() / 5; j++) {
				char tmp = key.charAt(i*5 + j);
				cols[(i*5) + j] =  key.charAt(length + j);
				cols[length + j] =  tmp;
			}
			length -= 5;
		}
		return new String(cols);
	}
	
	//Method to swap rows
	private String swapRows(String key, int r1, int r2) {	
		return (r1 == r2) ? swapRows(key, new SecureRandom().nextInt(4), new SecureRandom().nextInt(4)) :  permute(key, r1, r2, true);
	}
	
	//Method to swap columns
	private String swapCols(String key, int c1, int c2) {
		return (c1 == c2) ? swapCols(key, new SecureRandom().nextInt(4), new SecureRandom().nextInt(4)) : permute(key, c1, c2, false);
	}
	

}
//class 
class FileWorker {
	
	private String filename;
	public FileWorker(String fileName) {
		this.filename = fileName;
	}
	public String removeRecurringChars(String l) {
		char[] line = l.toUpperCase().toCharArray();
		
		for(int i = 0; i < line.length; i++) {
			if(i != line.length - 1) 
				line[i+1] = (line[i] == line[i+1]) ? 'J' : line[i+1];
		}
		return new String(line);
	}
//gets and sets 
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
