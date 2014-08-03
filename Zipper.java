import java.io.*;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Zipper {

	private ArrayList<File> myFiles;
	private TreeMap<String, String> myLoc;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		if (args[0].equals("zipper")) {
			Zipper zipper = new Zipper();
			zipper.zip(new File(args[1]), args[2], new File(args[1]).getName());
		}
		else if (args[0].equals("unzipper")) {
			Zipper zipper = new Zipper();
			zipper.dezip(args[1], args[2]);
		} else {
			System.out.println("invalid command");
		}
	}
	
	public Zipper() {
		myFiles = new ArrayList<File>();
		myLoc = new TreeMap<String, String>();
	}
	
	public void zip(File myFile, String myName, String myStart) throws IOException {
		findFiles(myFile);
		compressFiles(myName, myStart);
	}
	
	public void dezip(String input, String output) {
		char prev = 0;
		FileCharIterator decode = new FileCharIterator(input);
		File outputFile = new File(output);
		File parentFile;
		deleteExistingFile(outputFile);
		outputFile = new File(output);
		outputFile.mkdir();
		parentFile = outputFile;
		String current = "";
		String fileName = "";
		int tocLength = findTOCLength(input);
		while (decode.hasNext()) {
			char a = (char) Integer.parseInt(decode.next(), 2);
			if (prev == ('\n') && a == ('\n')) {
				break;
			}
			if (a != (',') && a != ('\n') && a != ('/')) {
				current += a;
			}
			else if (a == ('/')) {
				parentFile = makeDirectory(parentFile, current);
				current = "";
			}
			else if (a == (',')) {
				fileName = current;
				current = "";
			}
			else if (a == ('\n')) {
				if (current.equals("-1")) {
					parentFile = makeDirectory(parentFile, fileName);
				} else {
					HuffmanEncoding helper = new HuffmanEncoding();
					int index = Integer.parseInt(current);
					File newFile = new File(parentFile, fileName);
					helper.decode(input, newFile.toString(), index + tocLength);
				}
				parentFile = outputFile;
				current = "";
			}
			prev = a;
		}
		decode.closeStream();
	}
	
	public File makeDirectory(File parent, String name) {
		File rtnFile;
		if (parent != null) {
			rtnFile = new File(parent, name);
		} else {
			rtnFile = new File(name);
		}
		rtnFile.mkdir();
		return rtnFile;
	}
	
	public int findTOCLength(String input) {
		FileCharIterator decode = new FileCharIterator(input);
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
		decode.closeStream();
		return i;
	}

	public void deleteExistingFile(File fileToBeDeleted) {
		if (fileToBeDeleted.exists()) {
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
				encode.encode(myFile.getPath(), "temporary", 0);
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
		if (tempFile.exists()) {
			FileCharIterator myIter = new FileCharIterator("temporary");
			while (myIter.hasNext()) {
				FileOutputHelper.writeBinStrToFile(myIter.next(), myOutput);
			}
			myIter.closeStream();
		}
	}
}
