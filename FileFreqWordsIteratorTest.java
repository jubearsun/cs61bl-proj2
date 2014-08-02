import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


public class FileFreqWordsIteratorTest {
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	public File myFile;

	@Test
	public void hasNext() throws IOException {
		myFile = folder.newFile("temporary");
		myFile.deleteOnExit();
		
		StringBuilder binary = new StringBuilder();
		String s = "it was the best of times,";
		for (Byte b : s.getBytes()) {
			binary.append(String.format("%8s",
                Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
		}
		FileOutputHelper.writeBinStrToFile(binary.toString(), folder.getRoot() + "/temporary");
		FileFreqWordsIterator freq = new FileFreqWordsIterator(folder.getRoot() + "/temporary", 4);
		freq.next();
		freq.next();
		freq.next();
		freq.next();
		freq.next();
		freq.next();
		assertTrue(freq.hasNext());
		freq.next();
		freq.next();
		freq.next();
		freq.next();
		freq.next();
		freq.next();
		freq.next();
		freq.next();
		assertTrue(!freq.hasNext());
		
		myFile = folder.newFile("temporary2");
		myFile.deleteOnExit();
		
		StringBuilder binary2 = new StringBuilder();
		String s2 = "it ";
		for (Byte b : s2.getBytes()) {
			binary2.append(String.format("%8s",
                Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
		}
		FileOutputHelper.writeBinStrToFile(binary2.toString(), folder.getRoot() + "/temporary2");
		FileFreqWordsIterator freq2 = new FileFreqWordsIterator(folder.getRoot() + "/temporary2", 0);
		
		freq2.next();
		freq2.next();
		assertTrue(freq2.hasNext());
		freq2.next();
		assertTrue(!freq2.hasNext());

		
		myFile = folder.newFile("temporary3");
		myFile.deleteOnExit();
		
		StringBuilder binary3 = new StringBuilder();
		String s3 = "   ";
		for (Byte b : s3.getBytes()) {
			binary3.append(String.format("%8s",
                Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
		}
		FileOutputHelper.writeBinStrToFile(binary3.toString(), folder.getRoot() + "/temporary3");
		FileFreqWordsIterator freq3 = new FileFreqWordsIterator(folder.getRoot() + "/temporary3", 0);
		
		freq3.next();
		freq3.next();
		assertTrue(freq3.hasNext());
		freq3.next();
		assertTrue(!freq3.hasNext());
	}
	
	
	

}
