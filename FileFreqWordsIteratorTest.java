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
	public void constructor() throws IOException {
		myFile = folder.newFile("temp");
		myFile.deleteOnExit();
		StringBuilder bin = new StringBuilder();
		String string = "it was the best of times, it was the worst of times";
		for (Byte b : string.getBytes()) {
			bin.append(String.format("%8s",
                Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
		}
		FileOutputHelper.writeBinStrToFile(bin.toString(), folder.getRoot() + "/temp");
		FileFreqWordsIterator iterator = new FileFreqWordsIterator(folder.getRoot() + "/temp", 4);
		
		assertEquals(iterator.getSorted().size(), 4);
		assertEquals(iterator.getSorted().get(0), "it");
		assertEquals(iterator.getSorted().get(1), "was");
		assertEquals(iterator.getSorted().get(2), "of"); // order doesn't matter here as long as it's in order in the next method
		assertEquals(iterator.getSorted().get(3), "the");
		
		myFile = folder.newFile("booboo");
		myFile.deleteOnExit();
		StringBuilder bin2 = new StringBuilder();
		String string2 = "     ok";
		for (Byte b : string2.getBytes()) {
			bin2.append(String.format("%8s",
                Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
		}
		FileOutputHelper.writeBinStrToFile(bin2.toString(), folder.getRoot() + "/booboo");
		FileFreqWordsIterator f = new FileFreqWordsIterator(folder.getRoot() + "/booboo", 4);
		
		assertEquals(f.getSorted().size(), 1);
		assertEquals(f.getSorted().get(0), "ok");
		
		myFile = folder.newFile("yum");
		myFile.deleteOnExit();
		StringBuilder bin3 = new StringBuilder();
		String string3 = "     ";
		for (Byte b : string3.getBytes()) {
			bin3.append(String.format("%8s",
                Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
		}
		FileOutputHelper.writeBinStrToFile(bin3.toString(), folder.getRoot() + "/yum");
		FileFreqWordsIterator i = new FileFreqWordsIterator(folder.getRoot() + "/yum", 4);
		
		assertEquals(i.getSorted().size(), 0);
	}
	
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

	@Test
	public void next() throws IOException {
		myFile = folder.newFile("temporary");
		myFile.deleteOnExit();
		StringBuilder binary = new StringBuilder();
		String s = "it was the best of times, it was the worst of times";
		for (Byte b : s.getBytes()) {
			binary.append(String.format("%8s",
                Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
		}
		FileOutputHelper.writeBinStrToFile(binary.toString(), folder.getRoot() + "/temporary");
		FileFreqWordsIterator freq = new FileFreqWordsIterator(folder.getRoot() + "/temporary", 4);
		assertEquals("0110100101110100", freq.next()); // it
		assertEquals("00100000", freq.next());
		assertEquals("011101110110000101110011", freq.next()); // was
		assertEquals("00100000", freq.next());
		assertEquals("011101000110100001100101", freq.next()); // the
		assertEquals("00100000", freq.next());
		assertEquals("01100010", freq.next()); // b
		assertEquals("01100101", freq.next()); // e
		assertEquals("01110011", freq.next()); // s
		assertEquals("01110100", freq.next()); // t
		assertEquals("00100000", freq.next());
		assertEquals("0110111101100110", freq.next()); // of
		assertEquals("00100000", freq.next());
		assertEquals("01110100", freq.next()); //t
		assertEquals("01101001", freq.next()); //i
		assertEquals("01101101", freq.next()); //m
		assertEquals("01100101", freq.next()); //e
		assertEquals("01110011", freq.next()); //s
		assertEquals("00101100", freq.next()); //,
		assertEquals("00100000", freq.next());
		assertEquals("0110100101110100", freq.next()); // it
		assertEquals("00100000", freq.next());
		assertEquals("011101110110000101110011", freq.next()); // was
		assertEquals("00100000", freq.next());
		assertEquals("011101000110100001100101", freq.next()); // the
		assertEquals("00100000", freq.next());
		assertEquals("01110111", freq.next()); //w
		assertEquals("01101111", freq.next()); //o
		assertEquals("01110010", freq.next()); //r
		assertEquals("01110011", freq.next()); //s
		assertEquals("01110100", freq.next()); //t
		assertEquals("00100000", freq.next());
		assertEquals("0110111101100110", freq.next()); // of
		assertEquals("00100000", freq.next());
		assertEquals("01110100", freq.next()); //t
		assertEquals("01101001", freq.next()); //i
		assertEquals("01101101", freq.next()); //m
		assertEquals("01100101", freq.next()); //e
		assertEquals("01110011", freq.next()); //s
		
		
		myFile = folder.newFile("temporary2");
		myFile.deleteOnExit();
		StringBuilder binary2 = new StringBuilder();
		String sh = "it was the best of times, it was the worst of times";
		for (Byte b : sh.getBytes()) {
			binary2.append(String.format("%8s",
                Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
		}
		FileOutputHelper.writeBinStrToFile(binary2.toString(), folder.getRoot() + "/temporary2");
		FileFreqWordsIterator it = new FileFreqWordsIterator(folder.getRoot() + "/temporary2", 3);
		
		assertEquals("0110100101110100", it.next()); //it
		assertEquals("00100000", it.next());
		assertEquals("011101110110000101110011", it.next()); // was
		assertEquals("00100000", it.next());
		assertEquals("01110100", it.next()); // t
		assertEquals("01101000", it.next()); // h
		assertEquals("01100101", it.next()); // e
		assertEquals("00100000", it.next());
		assertEquals("01100010", it.next()); // b
		assertEquals("01100101", it.next()); // e
		assertEquals("01110011", it.next()); // s
		assertEquals("01110100", it.next()); // t
		assertEquals("00100000", it.next());
		assertEquals("0110111101100110", it.next()); //of
		assertEquals("00100000", it.next());
		assertEquals("01110100", it.next()); //t
		assertEquals("01101001", it.next()); //i
		assertEquals("01101101", it.next()); //m
		assertEquals("01100101", it.next()); //e
		assertEquals("01110011", it.next()); //s
		assertEquals("00101100", it.next()); //,
		assertEquals("00100000", it.next());
		assertEquals("0110100101110100", it.next()); //it
		assertEquals("00100000", it.next());
		assertEquals("011101110110000101110011", it.next()); // was
		assertEquals("00100000", it.next());
		assertEquals("01110100", it.next()); // t
		assertEquals("01101000", it.next()); // h
		assertEquals("01100101", it.next()); // e	
		assertEquals("00100000", it.next());
		assertEquals("01110111", it.next()); //w
		assertEquals("01101111", it.next()); //o
		assertEquals("01110010", it.next()); //r
		assertEquals("01110011", it.next()); //s
		assertEquals("01110100", it.next()); //t
		assertEquals("00100000", it.next());
		assertEquals("0110111101100110", it.next()); //of
		assertEquals("00100000", it.next());
		assertEquals("01110100", it.next()); //t
		assertEquals("01101001", it.next()); //i
		assertEquals("01101101", it.next()); //m
		assertEquals("01100101", it.next()); //e
		assertEquals("01110011", it.next()); //s
		
		myFile = folder.newFile("temporary3");
		myFile.deleteOnExit();
		StringBuilder binary3 = new StringBuilder();
		String shu = "     ok";
		for (Byte b : shu.getBytes()) {
			binary3.append(String.format("%8s",
                Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
		}
		FileOutputHelper.writeBinStrToFile(binary3.toString(), folder.getRoot() + "/temporary3");
		FileFreqWordsIterator spaces = new FileFreqWordsIterator(folder.getRoot() + "/temporary3", 1);
		
		assertEquals("00100000", spaces.next()); // space
		assertEquals("00100000", spaces.next()); // space
		assertEquals("00100000", spaces.next()); // space
		assertEquals("00100000", spaces.next()); // space
		assertEquals("00100000", spaces.next()); // space
		assertEquals("0110111101101011", spaces.next()); // ok
		
		myFile = folder.newFile("temporary4");
		myFile.deleteOnExit();
		StringBuilder binary4 = new StringBuilder();
		String shut = "you are you eat";
		for (Byte b : shut.getBytes()) {
			binary4.append(String.format("%8s",
                Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
		}
		FileOutputHelper.writeBinStrToFile(binary4.toString(), folder.getRoot() + "/temporary4");
		FileFreqWordsIterator beg = new FileFreqWordsIterator(folder.getRoot() + "/temporary4", 1);
		
		assertEquals("011110010110111101110101", beg.next()); // you
		assertEquals("00100000", beg.next());
		assertEquals("01100001", beg.next()); //a
		assertEquals("01110010", beg.next()); //r
		assertEquals("01100101", beg.next()); //e
		assertEquals("00100000", beg.next());
		assertEquals("011110010110111101110101", beg.next()); // you
		assertEquals("00100000", beg.next());
		assertEquals("01100101", beg.next()); //e
		assertEquals("01100001", beg.next()); //a
		assertEquals("01110100", beg.next()); //t
		
		myFile = folder.newFile("temporary5");
		myFile.deleteOnExit();
		StringBuilder binary5 = new StringBuilder();
		String shutu = "just a kitten a kitten";
		for (Byte b : shutu.getBytes()) {
			binary5.append(String.format("%8s",
                Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
		}
		FileOutputHelper.writeBinStrToFile(binary5.toString(), folder.getRoot() + "/temporary5");
		FileFreqWordsIterator end = new FileFreqWordsIterator(folder.getRoot() + "/temporary5", 1);
		
		assertEquals("01101010", end.next()); // j
		assertEquals("01110101", end.next()); // u
		assertEquals("01110011", end.next()); // s
		assertEquals("01110100", end.next()); // t
		assertEquals("00100000", end.next());
		assertEquals("01100001", end.next()); // a
		assertEquals("00100000", end.next());
		assertEquals("011010110110100101110100011101000110010101101110", end.next()); // kitten
		assertEquals("00100000", end.next());
		assertEquals("01100001", end.next()); // a
		assertEquals("00100000", end.next());
		assertEquals("011010110110100101110100011101000110010101101110", end.next()); // kitten
	}

}
