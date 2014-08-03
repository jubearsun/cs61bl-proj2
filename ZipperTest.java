import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;

import org.junit.Test;

public class ZipperTest {
	
	@Test
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
	
	@Test
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
	
	@Test
	public void testFindTOCLength() throws IOException {
		Zipper zip = new Zipper();
		HuffmanEncoding huff = new HuffmanEncoding();
		assertTrue(zip.findTOCLength("sample_files/example_dir.zipper") == 173);
		assertTrue(zip.findTOCLength("sample_files/kaleidoscopezip.zipper") == 20);
	}
	
	@Test
	public void testSingleFile() throws IOException {
		Zipper myZip = new Zipper();
		File myDecompress = new File("decompressed");
		File myCompress = new File("compressed");
		myZip.zip(new File("sample_files/lastquestion.txt"), myCompress.getPath(), "lastquestion.txt");
		myZip.dezip(myCompress.getPath(), myDecompress.getPath());
		File[] file = myDecompress.listFiles();
		assertTrue(file[0].getName().equals("lastquestion.txt"));
		FileCharIterator myOriginal = new FileCharIterator("sample_files/lastquestion.txt");
		FileCharIterator myDecompressed = new FileCharIterator("decompressed/lastquestion.txt");
		while (myOriginal.hasNext()) {
			assertTrue(myOriginal.next().equals(myDecompressed.next()));
		}
		assertTrue(!myDecompressed.hasNext());
		myDecompressed.closeStream();
		myOriginal.closeStream();
		file[0].delete();
		myDecompress.delete();
		myCompress.delete();
	}
	
	@Test
	public void testDirectoryWithSingleFile() throws IOException {
		Zipper myZip = new Zipper();
		File myDecompress = new File("decompressed");
		File myCompress = new File("compressed");
		myZip.zip(new File("sample_files/single_file"), myCompress.getPath(), "single_file");
		myZip.dezip(myCompress.getPath(), myDecompress.getPath());
		File[] directory = myDecompress.listFiles();
		File[] file = directory[0].listFiles();
		assertTrue(directory[0].getName().equals("single_file"));
		assertTrue(file[0].getName().equals("Kaleidoscope.txt"));
		FileCharIterator myOriginal = new FileCharIterator("sample_files/Kaleidoscope.txt");
		FileCharIterator myDecompressed = new FileCharIterator("decompressed/single_file/Kaleidoscope.txt");
		while (myOriginal.hasNext()) {
			assertTrue(myOriginal.next().equals(myDecompressed.next()));
		}
		assertTrue(!myDecompressed.hasNext());
		myDecompressed.closeStream();
		myOriginal.closeStream();
		file[0].delete();
		directory[0].delete();
		myDecompress.delete();
		myCompress.delete();
	}
	
	@Test
	public void testEmptyDirectories() throws IOException {
		Zipper myZip = new Zipper();
		File myDecompress = new File("decompressed");
		File myCompress = new File("compressed");	
		myZip.zip(new File("sample_files/empty_folders"), myCompress.getPath(), "empty_folders");
		myZip.dezip(myCompress.getPath(), myDecompress.getPath());
		File[] directory = myDecompress.listFiles();
		assertTrue(directory[0].getName().equals("empty_folders"));
		File[] file = directory[0].listFiles();
		assertTrue(file[0].getName().equals("empty"));
		assertTrue(file[1].getName().equals("not_empty"));
		File[] moreFiles = file[1].listFiles();
		assertTrue(moreFiles[0].getName().equals("not_empty"));
		File[] evenMoreFiles = moreFiles[0].listFiles();
		assertTrue(evenMoreFiles[0].getName().equals("empty"));
		evenMoreFiles[0].delete();
		moreFiles[0].delete();
		file[0].delete();
		file[1].delete();
		directory[0].delete();
		myDecompress.delete();
		myCompress.delete();
	}
	
	@Test
	public void testMultipleFolders() throws IOException {
		Zipper myZip = new Zipper();
		File myDecompress = new File("decompressed");
		File myCompress = new File("compressed");
		myZip.zip(new File("sample_files/example_dir"), myCompress.getPath(), "example_dir");
		myZip.dezip(myCompress.getPath(), myDecompress.getPath());
		File[] directory = myDecompress.listFiles();
		assertTrue(directory[0].getName().equals("example_dir"));
		File[] directory2 = directory[0].listFiles();
		assertTrue(directory2[0].getName().equals("AModestProposal.txt"));
		assertTrue(directory2[1].getName().equals("empty_dir"));
		assertTrue(directory2[2].getName().equals("images"));
		assertTrue(directory2[3].getName().equals("Kaleidoscope.txt"));
		File[] files = directory2[2].listFiles();
		assertTrue(files[0].getName().equals("HangInThere.jpg"));
		FileCharIterator myOriginal = new FileCharIterator("sample_files/HangInThere.jpg");
		FileCharIterator myDecompressed = new FileCharIterator("decompressed/example_dir/images/HangInThere.jpg");
		while (myOriginal.hasNext()) {
			assertTrue(myOriginal.next().equals(myDecompressed.next()));
		}
		assertTrue(!myDecompressed.hasNext());
		myDecompressed.closeStream();
		myOriginal.closeStream();
		
		FileCharIterator myOriginal2 = new FileCharIterator("sample_files/Kaleidoscope.txt");
		FileCharIterator myDecompressed2 = new FileCharIterator("decompressed/example_dir/Kaleidoscope.txt");
		while (myOriginal2.hasNext()) {
			assertTrue(myOriginal2.next().equals(myDecompressed2.next()));
		}
		assertTrue(!myDecompressed2.hasNext());
		myDecompressed2.closeStream();
		myOriginal2.closeStream();
		FileCharIterator myOriginal3 = new FileCharIterator("sample_files/example_dir/AModestProposal.txt");
		FileCharIterator myDecompressed3 = new FileCharIterator("decompressed/example_dir/AModestProposal.txt");
		while (myOriginal3.hasNext()) {
			assertTrue(myOriginal3.next().equals(myDecompressed3.next()));
		}
		assertTrue(!myDecompressed3.hasNext());
		myDecompressed3.closeStream();
		myOriginal3.closeStream();
		files[0].delete();
		directory2[3].delete();
		directory2[2].delete();
		directory2[1].delete();
		directory2[0].delete();
		directory[0].delete();
		myDecompress.delete();
		myCompress.delete();
	}
}
