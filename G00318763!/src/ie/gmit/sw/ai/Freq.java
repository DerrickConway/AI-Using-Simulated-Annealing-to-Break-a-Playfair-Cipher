package ie.gmit.sw.ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;



/*
 * Author: Alan Heanue - G00318763
 */


public class Freq {
	
	private String filename;
	private Map<String, Integer> grams;
	
	
    public long num;

	
	
	public Freq(String fileName) {
		this.filename = fileName;
		this.grams = new HashMap<String, Integer>();
	}
	//hash map 
	public Map<String, Integer> loadGrams()  throws Exception {
		long count = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
		String line = "";
		
				while((line = br.readLine()) != null) {
			grams.put(line.split(" ")[0], Integer.parseInt(line.split(" ")[1]));
			//Sum up the total 4grams
			count += Double.parseDouble(line.split(" ")[1]);
		}
		//Save the total number of 4grams
		setNo(count);
		br.close();	
		return this.grams;
	}
	
	//getFitness method is used to determine the fitness of the ciphertext compared to english 
	public double scoreText(String cipherText) {
		double score = 0;
		int frequency = 0;
		
		//loop through the cipher every 4 letters..
		for(int i=0; i< cipherText.length() - 4; i++){
			if(grams.get(cipherText.substring(i, i+4)) != null){
				frequency = grams.get(cipherText.substring(i, i+4));
			}
			
			else	
			{frequency = 1;}
			//log probability
			score += Math.log10((double) frequency/this.getNo());
		}//end for 
		return score;
	}//end class 
	
	//get sets 
	
	public void setNo(long no) {
		this.num = no;
	}
	
	public long getNo() {
		return this.num;
	}
}//class
