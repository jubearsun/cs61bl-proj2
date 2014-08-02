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
    private HashMap<String, Integer> freqWords = new HashMap<String, Integer>(); //store words and their frequencies in a hashmap
    private Set<Map.Entry<String, Integer>> entrySet;
    private TreeSet<Entry> freqWordsSorted;
    private int numOfWords;
    private int index = 0;
    private ArrayList<String> sorted = new ArrayList<String>();
    
    private String nextWord;
    private ArrayList<String> wordsRead = new ArrayList<String>();
    private int internalIndex = 0;

    
    public static void main(String[] args) {
    	FileFreqWordsIterator freqwords = new FileFreqWordsIterator(args[0], Integer.parseInt(args[1]));

    	//for (String en : freqwords.wordsRead) {
    		//System.out.println("OK " + en);
    	//}
    	
    	
    	
    	
    	while (freqwords.hasNext()) {
    		System.out.println(freqwords.next());
    	}
    	




    }
    
    public class myComp implements Comparator{

		@Override
		public int compare(Object o1, Object o2) {
			Entry obj1 = (Entry) o1;
			Entry obj2 = (Entry) o2;
			
			int freqComp = ((Comparable) obj2.getValue()).compareTo((Comparable) obj1.getValue());
			if (freqComp == 0) {
				return freqComp-1;
			}
			return freqComp;
		}
		
		@Override
		public boolean equals(Object o1) {
			Entry obj = (Entry) o1;
			return (((Entry) this).getKey().equals(obj.getKey()));
		}

	
    }

    public FileFreqWordsIterator(String inputFileName, int numberOfWords) {
    	numOfWords = numberOfWords;
    	
        try {
            input = new FileInputStream(inputFileName);
            
            //New stuff starts here
            
            
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
            String text;
            while ((text = reader.readLine()) != null) {
            	String[] words = text.split("\n|\\s+");
            	for (String word : words) {
            		wordsRead.add(word);
            		if (freqWords.containsKey(word)) {
            			freqWords.put(word, freqWords.get(word) + 1);
            		} else {
            			freqWords.put(word, 1);
            		}
            	}

            }
            reader.close();
               
            entrySet = freqWords.entrySet(); // this is the unsorted set
            freqWordsSorted = new TreeSet<Entry>(new myComp()); // this is the empty sorted list
            
            for (Entry pair : entrySet) {
            	freqWordsSorted.add((Entry) pair);
            }
            Iterator<Entry> iterator = freqWordsSorted.iterator();
            while (sorted.size() < numOfWords && iterator.hasNext()) {
            	sorted.add((String) iterator.next().getKey());
            }
            
            
            nextWord = wordsRead.get(index);
            index++;
            
            
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
    

	@Override
	public boolean hasNext() {
        return (nextChar != -1);

	}

	@Override
	public String next() {
		//idea is to first iterate through the word list sorted by frequency and then iterate through the characters. I would just 
		//surround this in a big if-else statement (have some sort of index and if that index is less than the size of the sorted
		//word list then set toRtn to the i-th element of that sorted word list otherwise do everything below

		//but I'm not sure if that's what they're asking for
		//haven't constructed sorted list yet
		
		

		
		
		if (this.nextChar == -1) {
            return "";
        } else {
        	if (this.nextChar == 32 || this.nextChar == 10) {
        		Byte b = (byte) this.nextChar;
	            String toRtn = String.format("%8s",
	                    Integer.toBinaryString(b & 0xFF)).replace(' ', '0'); //need to do this for each word too
	            int c = Integer.parseInt(toRtn, 2);
	            toRtn = new Character((char) c).toString();
	            
	            try {
                    this.nextChar = this.input.read();
                    
                } catch (IOException e) {
                    System.err.printf(
                            "IOException while reading in from file %s\n",
                            this.inputFileName);
                }
	            nextWord = wordsRead.get(index);
            	index++;
	            return toRtn;
        	}
            if (sorted.contains(nextWord)) {
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
            	
            	return toRtn;
            	
            } else {
            	String toRtn = null;
            	if (internalIndex == nextWord.length()) {
            		internalIndex = 0;
            	}
            	if (internalIndex < nextWord.length()) {
		            Byte b = (byte) this.nextChar;
		            toRtn = String.format("%8s",
		                    Integer.toBinaryString(b & 0xFF)).replace(' ', '0'); //need to do this for each word too
		            int c = Integer.parseInt(toRtn, 2);
		            toRtn = new Character((char) c).toString();
		            try {
		                this.nextChar = this.input.read();
		            } catch (IOException e) {
		                System.err.printf(
		                        "IOException while reading in from file %s\n",
		                        this.inputFileName);
		            }
		            internalIndex++;
		            
		            
		            
		            return toRtn;

		        } 

				return toRtn ;
            }
        }
	}

    @Override
    public void remove() {
        throw new UnsupportedOperationException(
                "FileFreqWordsIterator does not delete from files.");
    }



    
}
