import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class HuffmanEncoding {

	private ArrayList<Frequency> myFreq;
	private TreeMap<String, StringBuilder> myTreeMap;
	private TreeMap<String, String> myDecodeMap;

	public static void main(String[] args) throws IOException {
		if (args[0].equals("encode")) {
			HuffmanEncoding encode = new HuffmanEncoding();
			encode.encode(args[1], args[2], 0);
		}
		else if (args[0].equals("decode")) {
			String myFile = args[1];	
			HuffmanEncoding decode = new HuffmanEncoding();
			decode.decode(myFile, args[2], 0);
		}
		else if (args[0].equals("encode2")) {
			HuffmanEncoding encode2 = new HuffmanEncoding();
			encode2.encode(args[1], args[2], Integer.parseInt(args[3]));
		} else {
			System.out.println("invalid command");
		}
	}
	
	public ArrayList<Frequency> getFreq() {
		return myFreq;
	}

	public void encode(String myFile, String myName, int numberOfWords) throws IOException {
		characterCount(myFile, numberOfWords);
		sortFreq();
		HuffmanTree myTree = generateHuffmanTree();
		generateCodeMap(myTree);
		encodeSequence(myFile, myName, numberOfWords);
	}

	public void decode(String input, String output, int index) {
		ArrayList<String> keysValues = new ArrayList<String>();
		String current = new String();
		char prev = 0;
		FileCharIterator decode = new FileCharIterator(input);
		while (index != 0) {
			decode.next();
			index--;
		}
		while (decode.hasNext()) {
			char a = (char) Integer.parseInt(decode.next(), 2);
			if (a == ('\n') && prev == ('\n')) {
				break;
			}
			if ( a != (',' ) && a != ('\n')) {
				current += a;
			}
			if (a == (',') || a == ('\n')) {
				keysValues.add(current);
				current = "";
			}
			prev = a;
		}
		myDecodeMap = new TreeMap<String, String>();
		for (int i = 0; i < keysValues.size(); i += 2) {
			myDecodeMap.put(keysValues.get(i+1), keysValues.get(i));
		}
		FileOutputHelper myOutput = new FileOutputHelper();
		while (decode.hasNext()) {
			current += decode.next();
			int i = 0;
			int distance = 1;
			while (distance <= current.length()) {
				if (myDecodeMap.containsKey(current.substring(i, distance))) {
					if (myDecodeMap.get(current.substring(i, distance)).equals("EOF")) {
						break;
					}
					FileOutputHelper.writeBinStrToFile(myDecodeMap.get(current.substring(i, distance)), output);
					current = current.substring(distance);
					i = 0;
					distance = 1;
				} else {
					distance++;
				}
			}
		}
		decode.closeStream();
	}

	public void characterCount(String input, int numberOfWords) { 
		FileFreqWordsIterator inputIter = new FileFreqWordsIterator(input, numberOfWords);
		myFreq = new ArrayList<Frequency>();
		outerloop:
		while (inputIter.hasNext()) {
			String current = inputIter.next();
			for (Frequency element: myFreq) {
				if (element.getString().equals(current)) {
					element.setWeight(element.getWeight() + 1);
					continue outerloop;
				}	
			}
			myFreq.add(new Frequency(1, current));	
		}
		myFreq.add(new Frequency(1, "EOF"));
		inputIter.closeStream();
	}

	public void sortFreq() { 
		Collections.sort(myFreq);
	}

	public HuffmanTree generateHuffmanTree() {
		return new HuffmanTree(myFreq);
	}

	public void generateCodeMap(HuffmanTree myTree) {
		myTreeMap = new TreeMap<String, StringBuilder>();
		if (myTree.myRoot == null) {
			return;
		} else {
			StringBuilder myString1 = new StringBuilder();
			myTree.codeMapHelper(myTree.myRoot, myString1, myTree.myRoot);
			}
		}

	public void encodeSequence(String sequence, String name, int numberOfWords) throws IOException {
		FileFreqWordsIterator sequenceIter = new FileFreqWordsIterator(sequence, numberOfWords);
		StringBuilder encoded = new StringBuilder();
		while (sequenceIter.hasNext()) {
			String binString = sequenceIter.next();
			String code = myTreeMap.get(binString).toString();
			encoded.append(code);
		}
		encoded.append(myTreeMap.get("EOF"));
		if (encoded.length() % 8 != 0) {
			while ((encoded.length() % 8) != 0) {
				encoded.append(0);
			}
		}
		FileWriter f = new FileWriter(name, true);
		BufferedWriter b = new BufferedWriter(f);
		b.write(codemapString());
		b.close();
		FileOutputHelper.writeBinStrToFile(encoded.toString(), name);
		sequenceIter.closeStream();
	}

	public String codemapString() {
		StringBuilder codemap = new StringBuilder();
		for (Map.Entry<String, StringBuilder> entry : myTreeMap.entrySet()) {
			String key = entry.getKey();
			StringBuilder value = entry.getValue();
			codemap.append(key + "," + value + "\n");
		}
		codemap.append("\n");
		return codemap.toString();
	}

	public class Frequency implements Comparable<Frequency> { 

		private int myWeight;
		private String myString;

		Frequency(int weight, String symbol) {
			myWeight = weight;
			myString = symbol; 
		}

		public int compareTo(Frequency o) { 
			return (myWeight - o.myWeight);
		}

		public void setWeight(int weight) {
			myWeight = weight;
		}

		public int getWeight() {
			return myWeight;
		}

		public String getString() {
			return myString;
		}
	}

	public class HuffmanTree {

		private HuffmanNode myRoot;

		public HuffmanTree() {
			myRoot = null;
		}

		public HuffmanTree(HuffmanNode node) {
			myRoot = node;
		}

		public void codeMapHelper(HuffmanNode myNode, StringBuilder myString, HuffmanNode start) {
			if (myNode.hasElement() && !myNode.found) {
				myTreeMap.put(myNode.myElement, myString);
				myNode.found = true;
				StringBuilder newString = new StringBuilder("");
				codeMapHelper(start, newString, start);
			}
			if (myNode.myLeft != null && !myNode.myLeft.found) {
				StringBuilder temp = new StringBuilder(myString);
				StringBuilder newString = new StringBuilder(myString.append(0));
				codeMapHelper(myNode.myLeft, newString, start);
				if (myNode.myRight != null && !myNode.myRight.found) {
					StringBuilder newString1 = new StringBuilder(temp.append(1));
					codeMapHelper(myNode.myRight, newString1, start);
				}
			} 
		}

		public HuffmanTree(ArrayList<Frequency> freq){
			ArrayList<HuffmanNode> treeList = new ArrayList<HuffmanNode>();
			for (Frequency f: freq) {
				HuffmanNode t = (new HuffmanNode(f.myString, f.myWeight));
				treeList.add(t);
			}
			if (treeList.size() == 1) {
				myRoot = treeList.get(0);
			} else {
				while (treeList.size() > 1) {
					HuffmanNode temp1 = treeList.get(0);
					HuffmanNode temp2 = treeList.get(1);
					treeList.remove(0);
					treeList.remove(0);
					HuffmanNode fused = new HuffmanNode(temp1.myWeight + temp2.myWeight, temp1, temp2);
					treeList.add(fused);
					Collections.sort(treeList);
				}
				myRoot = treeList.get(0);
			}
		}

		public class HuffmanNode implements Comparable<HuffmanNode> { 
			private int myWeight;
			private String myElement;
			private HuffmanNode myLeft;
			private HuffmanNode myRight;
			private boolean found = false;

			public HuffmanNode (String character, int frequency) {
				myElement = character;
				myWeight = frequency;
				myLeft = null;
				myRight = null;
			}

			public HuffmanNode (int frequency, HuffmanNode left, HuffmanNode right) {
				myElement = null;
				myWeight = frequency;
				myLeft = left;
				myRight = right;
			}

			public String toString() {
				return ("(" + myElement + ", " + myWeight + ")" );
			}

			public boolean hasElement() {
				return !(myElement == null);
			}

			public int compareTo(HuffmanNode arg0) {
 				if (myWeight - arg0.myWeight == 0) {
					return -1;
				}
				return myWeight - arg0.myWeight;
			}
		}
	}
}
