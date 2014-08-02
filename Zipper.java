import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Zipper {

	private ArrayList<File> myFiles;
	private ArrayList<Integer> fileSizes;
	private TreeMap<String, String> myLoc;
	private String myStart;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		if (args[0].equals("zipper")) {
			Zipper zipper = new Zipper();
			zipper.zip(new File(args[1]), args[2], new File(args[1]).getName());
		}
		else if (args[0].equals("unzipper")) {
			Zipper zipper = new Zipper();
			zipper.deZipper(args[1], args[2]);
		} else {
			System.out.println("invalid command");
		}
	}
	
	public Zipper() {
		myFiles = new ArrayList<File>();
		fileSizes = new ArrayList<Integer>();
		myLoc = new TreeMap<String, String>();
	}
	
	public void zip(File myFile, String myName, String myStart) throws IOException {
		findFiles(myFile);
		compressFiles(myName, myStart);
	}
	
	public void deZipper(String input, String output) {
		char prev = 0;
		FileCharIterator decode = new FileCharIterator(input);
		File outputFile = new File(output);
		File parentFile;
		if (outputFile.exists()) {
			deleteExistingFile(outputFile);
			outputFile = new File(output);
		}
		outputFile.mkdir();
		parentFile = outputFile;
		String current = "";
		String fileName = "";
		HashMap<String, File> madeFiles = new HashMap<String, File>();
		int tOCLength = findTOCLength(input);
		while (decode.hasNext()) {
			char a = (char) Integer.parseInt(decode.next(), 2);
			if (prev == ('\n') && a == ('\n')) {
				break;
			}
			if (a != (',') && a != ('\n') && a != ('/')) {
				current += a;
			}
			else if (a == ('/')) {
				if (!madeFiles.containsKey(current)) {
					parentFile = makeDirectory(parentFile, current);
					madeFiles.put(current, parentFile);
				} else {
					parentFile = madeFiles.get(current);
				}
				current = "";
			}
			else if (a == (',')) {
				fileName = current;
				current = "";
			}
			else if (a == ('\n')) {
				if (current.equals("-1")) {
					if (!madeFiles.containsKey(fileName)) {
						parentFile = makeDirectory(parentFile, fileName);
						madeFiles.put(fileName, parentFile);
					}
				} else {
					HuffmanEncoding helper = new HuffmanEncoding();
					int index = Integer.parseInt(current);
					File newFile = new File(parentFile, fileName);
					helper.makeDecodeMap(input, newFile.toString(), index + tOCLength);
				}
				parentFile = outputFile;
				current = "";
			}
			prev = a;
		}
	}
	
	public File makeDirectory(File parent, String name) {
		File rtnFile = new File(parent, name);
		rtnFile.mkdir();
		return rtnFile;
	}
	
	public int findTOCLength(String input) {
		FileCharIterator decode = new FileCharIterator(input);
		HuffmanEncoding decodeZipper = new HuffmanEncoding();
		boolean tableOfContents = true;
		char prev = 0;
		int i = 0;
		while (tableOfContents) {
			char a = (char) Integer.parseInt(decode.next(), 2);
			i++;
			if (a == ('\n') && prev == ('\n')) {
				tableOfContents = false;
			}
			prev = a;
		}
		return i;
	}

	public void deleteExistingFile(File fileToBeDeleted) {
		File[] childrenFile = fileToBeDeleted.listFiles();
		for (int i = 0; i < childrenFile.length; i++) {
			if (childrenFile[i].isDirectory()) {
				deleteExistingFile(childrenFile[i]);
			} else {
				childrenFile[i].delete();
			}
		}
		fileToBeDeleted.delete();
	}

	public String generateTOC() {
		StringBuilder contents = new StringBuilder();
		for (Entry<String, String> entry : myLoc.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			contents.append(key + "," + value + "\n");
		}
		contents.append('\n');
		return contents.toString();
	}
	
	public void findFiles(File myFile) throws IOException {
		if (!myFile.isDirectory()) {
			System.out.println("Found a file");
			System.out.println(myFile.toString());
			myFiles.add(myFile);
		} else {
			myFiles.add(myFile);
			File[] temp = myFile.listFiles();
			for (File element: temp) {
				findFiles(element);
			} 
		}
	}
	 
	public void compressFiles(String myOutput, String myStart) throws IOException {
		int index = 0;
		File tempFile = new File("temporary");
		tempFile.deleteOnExit();
		for (File myFile: myFiles) {		
			File temp = myFile;
			StringBuilder myName = new StringBuilder(temp.getName());
			while (!temp.getName().equals(myStart)) {
				myName.insert(0, "/");
				temp = new File(temp.getParent());
				myName.insert(0, temp.getName());
			}
			if (myFile.isFile()) {
				HuffmanEncoding encode = new HuffmanEncoding();
				encode.encode(myFile.getPath(), "temporary");
				myLoc.put(myName.toString(), "" + index);	
				FileOutputHelper.writeBinStrToFile("00001010", "temporary");
				index = (int) tempFile.length();
			} else {
				myLoc.put(myName.toString(), "-1");
			}
		}
		FileWriter fw = new FileWriter(myOutput, true);
		fw.write(this.generateTOC());
		fw.close();
		FileCharIterator myIter = new FileCharIterator("temporary");
		while (myIter.hasNext()) {
			FileOutputHelper.writeBinStrToFile(myIter.next(), myOutput);
		}
	}
}
