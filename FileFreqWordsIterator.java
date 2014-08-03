import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;



public class FileFreqWordsIterator implements Iterator<String>{
	protected FileInputStream input;
	protected BufferedReader reader;
	protected BufferedReader wordReader;

    private String inputFileName;
    private int nextChar;
    private HashMap<String, Integer> freqWords = new HashMap<String, Integer>();
    private Set<Map.Entry<String, Integer>> entrySet;
    private TreeSet<Entry> freqWordsSorted;
    private int numOfWords;
    private int index = 0;
    private ArrayList<String> sorted = new ArrayList<String>();
    
    private String nextWord;
    private ArrayList<String> wordsRead = new ArrayList<String>();
    
    public ArrayList<String> getSorted() {
		return sorted;
	}

    

    
    public class myComp implements Comparator{

		public int compare(Object o1, Object o2) {
			Entry obj1 = (Entry) o1;
			Entry obj2 = (Entry) o2;
			
			int freqComp = ((Comparable) obj2.getValue()).compareTo((Comparable) obj1.getValue());
			if (freqComp == 0) {
				return freqComp-1;
			}
			return freqComp;
		}
		
		public boolean equals(Object o1) {
			Entry obj = (Entry) o1;
			return (((Entry) this).getKey().equals(obj.getKey()));
		}

	
    }

    public FileFreqWordsIterator(String inputFileName, int numberOfWords) {
    	numOfWords = numberOfWords;
    	
        try {
            input = new FileInputStream(inputFileName);
           
            
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
            String text;
            while ((text = reader.readLine()) != null) {
            	String[] words = text.split("\n|\\s+");
            	for (String word : words) {
            		wordsRead.add(word);
            		if (word.length() >= 2) {
	            		if (freqWords.containsKey(word)) {
	            			freqWords.put(word, freqWords.get(word) + 1);
	            		} else {
	            			freqWords.put(word, 1);
	            		}
            		}
            	}

            }
            reader.close();
               
            entrySet = freqWords.entrySet(); 
            freqWordsSorted = new TreeSet<Entry>(new myComp()); 
            
            for (Entry pair : entrySet) {
            	freqWordsSorted.add((Entry) pair);
            }
            Iterator<Entry> iterator = freqWordsSorted.iterator();
            while (sorted.size() < numOfWords && iterator.hasNext()) {
            	sorted.add((String) iterator.next().getKey());
            }
            if (wordsRead.size() != 0) {
            	nextWord = wordsRead.get(index);
            	index++;
            }
            nextChar = input.read();
            this.inputFileName = inputFileName;
        } catch (FileNotFoundException e) {
            System.err.printf("No such file: %s\n", inputFileName);
            System.exit(1);
        } catch (IOException e) {
            System.err.printf("IOException while reading from file %s\n",
                    inputFileName);
            System.exit(1);
        }
    }
    

	public boolean hasNext() {
        return (nextChar != -1);
	}

	public String next() {		
		if (this.nextChar == -1) {
            return "";
        } else {
        	if (this.nextChar == 32 || this.nextChar == 10) {
        		Byte b = (byte) this.nextChar;
	            String toRtn = String.format("%8s",
	                    Integer.toBinaryString(b & 0xFF)).replace(' ', '0');	            
	            try {
                    this.nextChar = this.input.read();
                    
                } catch (IOException e) {
                    System.err.printf(
                            "IOException while reading in from file %s\n",
                            this.inputFileName);
                }
	            if (index != wordsRead.size()) {
	            	nextWord = wordsRead.get(index);
	            	index++; 
	            	}
	            return toRtn;
        	} else if (sorted.contains(nextWord)) {
            	for (int i = 0; i < nextWord.length(); i++) {
                    try {
                        this.nextChar = this.input.read();
                    } catch (IOException e) {
                        System.err.printf(
                                "IOException while reading in from file %s\n",
                                this.inputFileName);
                    }
            	}
            	String toRtn = nextWord;
            	byte[] bb = toRtn.getBytes();
            	StringBuilder binary = new StringBuilder();
            	for (Byte b : bb) {
            		binary.append(String.format("%8s",
    		                Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
            	}
            	return binary.toString();
            	
            } else {
		        Byte b = (byte) this.nextChar;
		        String toRtn = String.format("%8s",
		                Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
		        try {
		        	this.nextChar = this.input.read();
	            } catch (IOException e) {
		            System.err.printf(
		                    "IOException while reading in from file %s\n",
		                    this.inputFileName);
		        }
				return toRtn;
            }
        }
	}

    public void remove() {
        throw new UnsupportedOperationException(
                "FileFreqWordsIterator does not delete from files.");
    }



    
}
