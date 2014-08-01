import java.io.*;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Zipper {

	private ArrayList<File> myFiles;
	private ArrayList<Integer> fileSizes;
	private TreeMap<String, String> myLoc;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		if (args[0].equals("zipper")) {
			Zipper zipper = new Zipper();
			zipper.zip(new File(args[1]), args[2]);
		} else {
			System.out.println("invalid command");
		}	
	}
	
	public Zipper() {
		myFiles = new ArrayList<File>();
		fileSizes = new ArrayList<Integer>();
		myLoc = new TreeMap<String, String>();
	}
	
	public void zip(File myFile, String myName) throws IOException {
		findFiles(myFile);
		compressFiles(myName);
	}

	public String generateContents() {
		StringBuilder contents = new StringBuilder();
		for (Entry<String, String> entry : myLoc.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			contents.append(key + "," + value + "\n");
		}
		return contents.toString();
	}
	
	public void findFiles(File myFile) throws IOException {
		if (!myFile.isDirectory()) {
			System.out.println("Found a file");
			System.out.println(myFile.toString());
			myFiles.add(myFile);
		} else {
			myLoc.put(myFile.getPath(), "-1");
			File[] temp = myFile.listFiles();
			for (File element: temp) {
				findFiles(element);
			}
		}
	}
	
	public void compressFiles(String myOutput) throws IOException {
		HuffmanEncoding encode = new HuffmanEncoding();
		FileWriter f = new FileWriter(myOutput, true);
		BufferedWriter b = new BufferedWriter(f);
		File tempFile = new File("temporary");
		tempFile.deleteOnExit();
		for (File myFile: myFiles) {
			encode.encode(myFile.getPath(), "temporary");
			if (fileSizes.size() == 0) {
				myLoc.put(myFile.getPath(), "0");
				fileSizes.add((int) myFile.length());
			} else {
				fileSizes.add((int) myFile.length() + fileSizes.get(fileSizes.size() - 1));
				myLoc.put(myFile.getPath(), "" + fileSizes.get(fileSizes.size() - 1));
			}
			FileOutputHelper.writeBinStrToFile("0000101000001010", "temporary");
		}
		b.write(this.generateContents());
		b.write("\n");
		FileInputStream fis = new FileInputStream("temporary");
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));		
		String aLine;
		while ((aLine = in.readLine()) != null) {
			b.write(aLine);
			b.newLine();
			if (aLine == "") {
				b.newLine();
			}
		}
		in.close();
		b.close();
	}
}
