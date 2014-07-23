import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class HuffmanEncoding {

	private ArrayList<String> myChars;
	private ArrayList<Integer> myWeights;
	private ArrayList<Frequency> myFreq;
	
	public static void main(String[] args) {  // the main method right now is set up to test sorting items, just get rid of stuff if you want to do other testing
		// TODO Auto-generated method stub
		String myFile = args[0];	
		HuffmanEncoding encode = new HuffmanEncoding();
		encode.characterCount(myFile);
		for (Frequency element: encode.myFreq) {
			System.out.println(element.myString + " has a frequency of " + element.myWeight);
		}
		System.out.println("--------------BREAK------------");
		encode.sortFreq();
		for (Frequency element: encode.myFreq) {
			System.out.println(element.myString + " has a frequency of " + element.myWeight);
		}
	}
	
	public void characterCount(String input) { 
		FileCharIterator inputIter = new FileCharIterator(input);
		myFreq = new ArrayList<Frequency>();
		myChars = new ArrayList<String>();			// contains each unique byte,
		myWeights = new ArrayList<Integer>();		// contains the frequency of each unique byte at the corresponding index
		while (inputIter.hasNext()) {				// ^ there's probably a better way to do this
			String current = inputIter.next();
			if (myChars.contains(current)) {
				int holdIndex = myChars.indexOf(current);
				int holdValue = myWeights.get(myChars.indexOf(current));
				myWeights.remove(myChars.indexOf(current));
				myWeights.add(holdIndex, holdValue + 1);
			} else {
				myChars.add(current);
				myWeights.add(1);
			}
		}
		for (int i = 0; i < myWeights.size(); i++) { // uses myChar and myWeights to make an array of Frequency objects
			myFreq.add(new Frequency(myWeights.get(i), myChars.get(i)));
		}
	}
	
	public void sortFreq() { // sorts a set of frequency objects by weight
		Collections.sort(myFreq);
	}
	
	public class Frequency implements Comparable<Frequency>{ // Frequency object stores a weight, and the byte as a String
		
		private int myWeight;
		private String myString;
		
		Frequency(int weight, String symbol) {
			myWeight = weight;
			myString = symbol; 
		}
		
		public int compareTo(Frequency o) { // special compareTo method for comparing Frequency objects
			return (myWeight - o.myWeight);
		}	
	}
	
	public class HuffmanNode {
		private int weight;
		private String element;
		private HuffmanNode myLeft;
		private HuffmanNode myRight;
	
		HuffmanNode (String character, int frequency) { // a Leaf
			element = character;
			weight = frequency;
			myLeft = null;
			myRight = null;
		}
		
		HuffmanNode (int frequency, HuffmanNode left, HuffmanNode right) { // an Internal Node
			element = null;
			weight = frequency;
			myLeft = left;
			myRight = right;
		}
		
		
//			this seems like the general idea for constructing a huffman tree, it's also iterative (I think recursive might give us system out of memory error)
//			https://www.siggraph.org/education/materials/HyperGraph/video/mpeg/mpegfaq/huffman_tutorial.html
//			I made this class last night, I'm not sure exactly what it should look like? Feel free to change shit.
//			If you can think of an easier/cleaner way to implement the "finding characters/frequencies" lemme know, but I tried
//			doing a TreeMap earlier and ran into trouble, so far having a Frequency class seems to work and it's decently fast 
//			
//			on another note, at this point the ArrayList myFreq should have a list of Frequency objects, in order of increasing frequency, so 
//			I think the method described in that link should work fine?
		
			
		public int weight() {
			return weight;
		}
		
		public boolean hasElement() {
			return (element == null);
		}
	}
}
