import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;


public class FileFreqWordsIterator implements Iterator<String>{
	protected FileInputStream input;
	protected BufferedReader reader;
    private String inputFileName;
    private int nextChar;
    private HashMap<String, Integer> freqWords = new HashMap<String, Integer>(); //store words and their frequencies in a hashmap

    public FileFreqWordsIterator(String inputFileName, int numberOfWords) {
        try {
            input = new FileInputStream(inputFileName);
            
            //New stuff starts here
            
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
            String text;
            while ((text = reader.readLine()) != null) {
            	String[] words = text.split("\n|\\s"); //not sure about the format of delimiters. This is supposed to split the text by spaces and the newline character
            	for (String word : words) {
            		if (freqWords.containsKey(word)) {
            			freqWords.put(word, freqWords.get(word) + 1);
            		} else {
            			freqWords.put(word, 1);
            		}
            	}


            }
            
            //need to construct a list of the words sorted by how often they appear.
            //look into .entrySet() from the HashMap class and SortedSet/TreeSet. In particular, look at the constructor for TreeSet.
            //one constructor takes in a collection and sorts them by comparator: http://docs.oracle.com/javase/7/docs/api/java/util/TreeSet.html
            //probably have to override a compareTo method or create a comparator or something
            
            //another way to do it is to write a class similar to the Frequency class we used and write a compareTo method that would determine 
            //whether one word is "greater" than another by looking at its frequency
            
            
            //end
            
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
        return nextChar != -1;

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
            Byte b = (byte) this.nextChar;
            String toRtn = String.format("%8s",
                    Integer.toBinaryString(b & 0xFF)).replace(' ', '0'); //need to do this for each word too
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
	
    @Override
    public void remove() {
        throw new UnsupportedOperationException(
                "FileFreqWordsIterator does not delete from files.");
    }
}
