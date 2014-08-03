import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
	public void characterCount() throws IOException {
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

}
