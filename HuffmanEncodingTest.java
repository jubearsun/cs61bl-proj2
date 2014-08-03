import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

// for all the binary strings I used this tool to look at the characters/words they corresponded to 
// http://www.convertbinary.com/

public class HuffmanEncodingTest {
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	public File tempfile;
	
	@Test
	public void testGenerateCodeMap() {
		HuffmanEncoding h = new HuffmanEncoding();
		HuffmanEncoding.Frequency f1 = h.new Frequency(1, "a");
		HuffmanEncoding.Frequency f2 = h.new Frequency(2, "b");
		HuffmanEncoding.Frequency f3 = h.new Frequency(3, "c");
		ArrayList<HuffmanEncoding.Frequency> freq = new ArrayList<HuffmanEncoding.Frequency>();
		freq.add(f1);
		freq.add(f2);
		freq.add(f3);
		HuffmanEncoding.HuffmanTree tree = h.new HuffmanTree(freq);
		h.generateCodeMap(tree);
		Iterator<Entry<String, StringBuilder>> i = h.getTreeMap().entrySet().iterator();
		assertEquals(i.next().getKey(), tree.getRoot().getLeft().getLeft().getElement());
		assertEquals(i.next().getKey(), tree.getRoot().getLeft().getRight().getElement());
		assertEquals(i.next().getKey(), tree.getRoot().getRight().getElement());
		
		
		HuffmanEncoding h2 = new HuffmanEncoding();

		HuffmanEncoding huffypuffy = new HuffmanEncoding();
		HuffmanEncoding.Frequency f = huffypuffy.new Frequency(1, "one");
		HuffmanEncoding.Frequency ff = huffypuffy.new Frequency(1, "oone");
		HuffmanEncoding.Frequency fff = huffypuffy.new Frequency(2, "two");
		HuffmanEncoding.Frequency ffff = huffypuffy.new Frequency(3, "three");
		ArrayList<HuffmanEncoding.Frequency> freq2 = new ArrayList<HuffmanEncoding.Frequency>(); 
		freq2.add(f);
		freq2.add(ff);
		freq2.add(fff);
		freq2.add(ffff);
		HuffmanEncoding.HuffmanTree tree2 = h2.new HuffmanTree(freq2);
		h2.generateCodeMap(tree2);
		Iterator<Entry<String, StringBuilder>> i2 = h2.getTreeMap().entrySet().iterator();
		System.out.println(h2.getTreeMap().entrySet());
		assertEquals(i2.next().getKey(), tree2.getRoot().getRight().getLeft().getLeft().getElement());
		assertEquals(i2.next().getKey(), tree2.getRoot().getRight().getLeft().getRight().getElement());
		assertEquals(i2.next().getKey(), tree2.getRoot().getLeft().getElement());
		assertEquals(i2.next().getKey(), tree2.getRoot().getRight().getRight().getElement());


	}
	
	@Test
	public void testHuffmanTreeConstructor() {
		HuffmanEncoding h = new HuffmanEncoding();
		HuffmanEncoding.Frequency f1 = h.new Frequency(1, "a");
		HuffmanEncoding.Frequency f2 = h.new Frequency(2, "b");
		HuffmanEncoding.Frequency f3 = h.new Frequency(3, "c");
		ArrayList<HuffmanEncoding.Frequency> freq = new ArrayList<HuffmanEncoding.Frequency>();
		freq.add(f1);
		freq.add(f2);
		freq.add(f3);
		HuffmanEncoding.HuffmanTree tree = h.new HuffmanTree(freq);
		assertEquals(tree.getRoot().toString(), "(null, 6)");
		assertEquals(tree.getRoot().getLeft().toString(), "(null, 3)");
		assertEquals(tree.getRoot().getLeft().getLeft().toString(), "(a, 1)");
		assertEquals(tree.getRoot().getLeft().getRight().toString(), "(b, 2)");
		assertEquals(tree.getRoot().getRight().toString(), "(c, 3)");
		
		HuffmanEncoding huffypuffy = new HuffmanEncoding();
		HuffmanEncoding.Frequency f = huffypuffy.new Frequency(1, "one");
		HuffmanEncoding.Frequency ff = huffypuffy.new Frequency(1, "oone");
		HuffmanEncoding.Frequency fff = huffypuffy.new Frequency(2, "two");
		HuffmanEncoding.Frequency ffff = huffypuffy.new Frequency(3, "three");
		ArrayList<HuffmanEncoding.Frequency> freq2 = new ArrayList<HuffmanEncoding.Frequency>(); 
		freq2.add(f);
		freq2.add(ff);
		freq2.add(fff);
		freq2.add(ffff);
		HuffmanEncoding.HuffmanTree tree2 = h.new HuffmanTree(freq2);

		assertEquals(tree2.getRoot().toString(), "(null, 7)");
		assertEquals(tree2.getRoot().getLeft().toString(), "(three, 3)");
		assertEquals(tree2.getRoot().getRight().toString(), "(null, 4)");
		assertEquals(tree2.getRoot().getRight().getLeft().toString(), "(null, 2)");
		assertEquals(tree2.getRoot().getRight().getRight().toString(), "(two, 2)");
	}

	@Test
	public void testCharacterCount() throws IOException {
		tempfile = folder.newFile("tempfile");
		tempfile.deleteOnExit();
		BufferedWriter out = new BufferedWriter(new FileWriter(tempfile));
		out.write("happy birthday");
		out.close();
		HuffmanEncoding hufflepuff = new HuffmanEncoding();
		hufflepuff.characterCount(folder.getRoot() + "/tempfile", 0);
		assertEquals("01101000", (hufflepuff.getFreq().get(0).getString()));
		assertEquals(2, (hufflepuff.getFreq().get(0).getWeight()));
		assertEquals("01100001", (hufflepuff.getFreq().get(1).getString()));
		assertEquals(2, (hufflepuff.getFreq().get(1).getWeight()));
		assertEquals("01110000", (hufflepuff.getFreq().get(2).getString()));
		assertEquals(2, (hufflepuff.getFreq().get(2).getWeight()));
		assertEquals("01111001", (hufflepuff.getFreq().get(3).getString()));
		assertEquals(2, (hufflepuff.getFreq().get(3).getWeight()));
		assertEquals("00100000", (hufflepuff.getFreq().get(4).getString()));
		assertEquals(1, (hufflepuff.getFreq().get(4).getWeight()));
		assertEquals("01100010", (hufflepuff.getFreq().get(5).getString()));
		assertEquals(1, (hufflepuff.getFreq().get(5).getWeight()));
		assertEquals("01101001", (hufflepuff.getFreq().get(6).getString()));
		assertEquals(1, (hufflepuff.getFreq().get(6).getWeight()));
		assertEquals("01110010", (hufflepuff.getFreq().get(7).getString()));
		assertEquals(1, (hufflepuff.getFreq().get(7).getWeight()));
		assertEquals("01110100", (hufflepuff.getFreq().get(8).getString()));
		assertEquals(1, (hufflepuff.getFreq().get(8).getWeight()));
		assertEquals("01100100", (hufflepuff.getFreq().get(9).getString()));
		assertEquals(1, (hufflepuff.getFreq().get(9).getWeight()));
		assertEquals("EOF", (hufflepuff.getFreq().get(10).getString()));
		
		tempfile = folder.newFile("tempfile2");
		tempfile.deleteOnExit();
		BufferedWriter characters = new BufferedWriter(new FileWriter(tempfile));
		characters.write(",.:'/!");
		characters.close();
		HuffmanEncoding gib = new HuffmanEncoding();
		gib.characterCount(folder.getRoot() + "/tempfile2", 0);
		assertEquals("00101100", gib.getFreq().get(0).getString());
		assertEquals("00101110", gib.getFreq().get(1).getString());
		assertEquals("00111010", gib.getFreq().get(2).getString());
		assertEquals("00100111", gib.getFreq().get(3).getString());
		assertEquals("00101111", gib.getFreq().get(4).getString());
		assertEquals("00100001", gib.getFreq().get(5).getString());
	}
	
	@Test
	public void testEncodeAndDecode1() throws IOException {
		HuffmanEncoding huff = new HuffmanEncoding();		
		File lastQuestionEncoded = new File("sample_files/lastquestionEncoded.huffman");
		huff.encode("sample_files/lastquestion.txt", "sample_files/lastquestionEncoded.txt.huffman", 0);
		File lastQuestionDecoded = new File("sample_files/lastquestionDecoded.txt");
		huff.decode("sample_files/lastquestionEncoded.txt.huffman", "sample_files/lastquestionDecoded.txt", 0);
		FileCharIterator decodedLQFile = new FileCharIterator("sample_files/lastquestion.txt");
		FileCharIterator checkLQdecoded = new FileCharIterator("sample_files/lastquestionDecoded.txt");
		while (checkLQdecoded.hasNext()) {
			assertTrue(checkLQdecoded.next().equals(decodedLQFile.next()));
		}
		assertTrue(!decodedLQFile.hasNext());
		decodedLQFile.closeStream();
		checkLQdecoded.closeStream();
		lastQuestionEncoded.delete();
		lastQuestionDecoded.delete();
	}
	
	@Test
	public void testEncodeAndDecode2() throws IOException {
		HuffmanEncoding huff = new HuffmanEncoding();		
		File KaleidoscopeEncoded = new File("sample_files/KaleidoscopeEncoded.huffman");
		huff.encode("sample_files/Kaleidoscope.txt", "sample_files/KaleidoscopeEncoded.txt.huffman", 0);
		File KaleidoscopeDecoded = new File("sample_files/KaleidoscopeDecoded.txt");
		huff.decode("sample_files/KaleidoscopeEncoded.txt.huffman", "sample_files/KaleidoscopeDecoded.txt", 0);
		FileCharIterator decodedLQFile = new FileCharIterator("sample_files/KaleidoscopeDecoded.txt");
		FileCharIterator checkLQdecoded = new FileCharIterator("sample_files/KaleidoscopeDecoded.txt");
		while (checkLQdecoded.hasNext()) {
			assertTrue(checkLQdecoded.next().equals(decodedLQFile.next()));
		}
		assertTrue(!decodedLQFile.hasNext());
		decodedLQFile.closeStream();
		checkLQdecoded.closeStream();
		KaleidoscopeEncoded.delete();
		KaleidoscopeDecoded.delete();
	}
	
}
