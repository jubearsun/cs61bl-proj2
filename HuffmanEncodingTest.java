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

// for all the binary strings I used this dictionary to look at the letters they corresponded to 
// http://www.convertbinary.com/alphabet.php
// I also used convertbinary.com for quick checks


public class HuffmanEncodingTest {
	
	@Test
	public void characterCount() throws IOException {
		tempfile = folder.newFile("tempfile");
		BufferedWriter out = new BufferedWriter(new FileWriter(tempfile));
		out.write("happy birthday");
		out.close();
		HuffmanEncoding hufflepuff = new HuffmanEncoding();
		hufflepuff.characterCount(folder.getRoot() + "/tempfile");
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
	}

}
