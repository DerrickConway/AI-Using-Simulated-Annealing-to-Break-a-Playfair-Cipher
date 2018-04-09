package ie.gmit.sw.ai;

import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Map;


/*
 * Author: Alan Heanue - G00318763
 */


public class SimulatedAnnealing {
	//variables 
	private Key key;
	private Playfair playFair;
	private SecureRandom rand;
	private int transitions;
	private Freq grams;
	private int temp;
	
	
	
	private Map<String, Integer> gramsMap; 
	
	//Constructor
	public SimulatedAnnealing(int temp, int transitions, String cipherText)
	{
		super();
		this.grams = new Freq("4grams.txt");
		this.playFair = new Playfair();
		//this.playFair.settextCip(textCip);
		this.playFair.setCipherText(cipherText);
		this.key = Key.keyInstance();
		this.setTemp(temp);
		this.transitions = transitions;
	}// end sa 
	//gets and sets 
		public Map<String, Integer> getGramsMap() {
			return gramsMap;
		}
		public void setGramsMap(Map<String, Integer> gramsMap) {
			this.gramsMap = gramsMap;
		}
		public int getTemp() {
			return temp;
		}
		public void setTemp(int temp) {
			this.temp = temp;
		}
	
	

	public void annealing(boolean debug) throws Throwable
	{		
		setGramsMap(grams.loadGrams());
		String parent = key.generateKey();
		String decryptedText = playFair.decrypt(parent);
		//score the fitness 
		double parentScore = grams.scoreText(decryptedText);
		double bestScore = parentScore;
		double probability;
		rand = new SecureRandom();
		double startScore = bestScore;
		for(@SuppressWarnings("unused")
		int temp1 = temp; temp > 0; temp--) {
			//Loop for each transition
			for (int index = transitions; index > 0; index--) {
				String child = key.shuffleKey(parent);
				decryptedText = playFair.decrypt(child);
				double childScore = grams.scoreText(decryptedText);
				double delta = childScore - parentScore;
				
				if
				(delta > 0)
				{
					parent = child;
					parentScore = childScore;
					}
				else 
				{
					probability = (Math.exp((delta / temp)));
					
					
					if
					(probability > rand.nextDouble()) {
						parent = child;
						parentScore = childScore;
					}
				}

				if(parentScore > bestScore) {
					//Save bescScore
					bestScore = parentScore;
					if(debug == true) System.out.printf("\nTemp: %d Transition: %d Key: %s Score: %.2f", temp, index, parent, bestScore);
				}
			}

			if(bestScore > (startScore/1.5)){
				if(debug == true) System.out.printf("\n\n Temp: %d Key: %s Score: %.2f\n\n", temp, parent, bestScore);
				if(bestScore > (startScore/1.6)) break;
			}
		}
		
		System.out.println("\n\nKey found: " + parent + "\nDecrypted message: " + playFair.decrypt(parent));
		try (PrintWriter out = new PrintWriter("result.txt"))
		{
		    out.println("Key found: " + parent + "\r\nDecrypted message: " + playFair.decrypt(parent));
		}//try
	}//end annealing
}//end class 
