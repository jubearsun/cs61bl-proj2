import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import junit.framework.TestCase;


public class ZipperTest extends TestCase {
	
	public void testMakeDirectory() {
		Zipper zip = new Zipper();
		File file = zip.makeDirectory(null, "file");
		assertTrue(file.getName().equals("file"));
		assertTrue(file.getParentFile() == null);
		File fileChild = zip.makeDirectory(file, "fileChild");
		assertTrue(file.listFiles().length == 1);
		assertTrue(fileChild.getParentFile().getName().equals("file"));
		File fileChildChild = zip.makeDirectory(fileChild, "fileChildChild");
		assertTrue(fileChildChild.getParentFile().getName().equals("fileChild"));
		assertTrue(fileChildChild.getParentFile().getParentFile().getName().equals("file"));
		assertTrue(file.listFiles().length == 1);
		File fileChildChild1 = zip.makeDirectory(fileChild, "fileChildChild1");
		assertTrue(fileChild.listFiles().length == 2);
		assertTrue(fileChild.listFiles()[0].getName().equals("fileChildChild"));
		assertTrue(fileChild.listFiles()[1].getName().equals("fileChildChild1"));
		fileChildChild1.deleteOnExit();
		fileChildChild.deleteOnExit();
		fileChild.deleteOnExit();
		file.deleteOnExit();
	}
	
	public void testDeleteExistingFile() {
		Zipper zip = new Zipper();
		File file = new File("file");
		assertTrue(file.exists() == true);
		zip.deleteExistingFile(file);
		assertTrue(file.exists() == false);
		File file1 = zip.makeDirectory(null, "file1");
		assertTrue(file1.exists() == true);
		File file1Child = zip.makeDirectory(file1, "file1Child");
		assertTrue(file1Child.exists() == true);
		zip.deleteExistingFile(file1Child);
		assertTrue(file1Child.exists() == false);
		File file1NewChild = zip.makeDirectory(file1, "file1NewChild");
		assertTrue(file1NewChild.exists() == true);
		zip.deleteExistingFile(file1);
		assertTrue(file1.exists() == false);
		assertTrue(file1NewChild.exists() == false);
	}
	
	public void testFindTOCLength() throws IOException {
		Zipper zip = new Zipper();
		HuffmanEncoding huff = new HuffmanEncoding();
		assertTrue(zip.findTOCLength("sample_files/example_dir.zipper") == 173);
		assertTrue(zip.findTOCLength("sample_files/kaleidoscopezip.zipper") == 20);
	}
	
}
