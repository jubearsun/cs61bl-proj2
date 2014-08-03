proj2
=====

Julia Sun cs61bl-av
Michael Zhang cs61bl-at
Ben Marimon cs61bl-be
Huu Pham cs61bl-bf

HuffmanEncodingTest.java
1. characterCount test tested that given a file, the frequencies of each binary string representation of a character in the file had the correct weight, or frequency.  Since we had added all the characters and their weights to an ArrayList of frequencies we simply checked that each index of the ArrayList contained the correct frequency for the file.

FileFreqWordsIteratorTest.java
1. Our constructor test tests the size of the sorted, an ArrayList we wrote in FileFreqWordsIterator that contains the n most frequent words that appear in the file.  So if n = 4, the size of sorted should also be 4.  We also tested that each index of the ArrayList contained a String object that corresponded to the n most frequent words.
2. Our hasnext test tests if, while iterating with FileFreqWordsIterator through a file, there is something still to be returned.  We tested in the middle of iterating that hasnext is true and at the end that hasnext is false.  
3. Our next test tests that the iterator returns the correct binary string representation of the next character or word to be returned.  I used convertbinary.com to find the binary string representation of words/characters.  It also tested that the next character/word to be returned was in the correct order as they were in the file.  We tested if the frequent word(s) was (were) at the beginning, end, and middle, or both of the file.

ZipperTest.java

testMakeDirectory():
1. We have a makeDirectory that uses the java’s file to make directories within directories. This test make sure that when a directory is made the name input is the same as the file’s name. As well as if there is a child in that directory(created using the makeDirectory() method), then child’s get parent file method should be able to get the directory, as well as the directory name. In addition the test also tests that the child of the directory can also have its own children, and it’s children should be able to call get parent file twice to get the original directory. It also make sure that the function allow the directory to have multiple children.

testDeleteExistingFile():
2.	this test the method to delete directory. At first we try to delete a single file, and make sure it doesn’t exists after we delete it. Then we made a file with another file as its child. The child is then deleted, and check to ensure that it no longer exists, and we also check to make sure that the parent file doesn’t get deleted even if the child folder is deleted. We then add a new child to the parent file, and delete the parent file. Lastly is that we check that the parent file is deleted, as well as it’s children.

testFindTOCLength():
3.	this test to make sure that findTOCLength returns the correct length of the table of contents. We ran the methods on several zipper files, and manually counting the characters of the table of contents(including newline characters) we then check that the function returns a number that is a correct representation of how many characters are in the table of contents.

4.	
testSingleFile()
tests that the zippercan compress and then decompress a single file with no other directories involved except for the output one

5.
testDirectoryWithSingleFile()
tests that the zipper can compress and then decompress a single file inside a single directory, with no other directories nested inside the original one

6.
testEmptyDirectories()
tests that the zipper can compress and then decompress a series of directories with no actual files

7,
testMultpleFolders()
tests that the zipper can compress and then decompress a directory that contains multiple other directories and multiple files

