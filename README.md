Julia Sun cs61bl-av Michael Zhang cs61bl-at Ben Marimon cs61bl-be Huu Pham cs61bl-bf

CONTRIBUTIONS

We split things up between Julia + Michael and Ben + Huu.

Ben + Huu: 
1. Set up the Frequency class, characterCount and codeMap
2. Decode
3. Part 3
4. Tests for part 3 
5. tests for encode/decode, runtimes

Julia + Michael:
1. Set up HuffmanTree/Node
2. Encode
3. Part 2
4. Tests for part 2
5. tests for helper methods for HuffmanEncoding

HuffmanEncodingTest.java 1. characterCount test tested that given a file, the frequencies of each binary string representation of a character in the file had the correct weight, or frequency. Since we had added all the characters and their weights to an ArrayList of frequencies we simply checked that each index of the ArrayList contained the correct frequency for the file. 2. generateCodeMap test tested that our codemaps were correct, by manually adding frequencies to an ArrayList that we use to construct a HuffmanTree. We then checked that the Huffman tree's structure matched the code mapping by manually creating the tree ourselves and tracking the left and right values of the Huffman tree and ensuring that each entry belongs where it is. 3. HuffmanTreeConstructor test tested the constructor for the HuffmanTree, one with different frequencies and one for different words that had the same frequency.

testEncodeAndDecode() It's difficult to test if two compressed files are equal because differing implementations of encode can result in different file structures. In order to test encode, we encoded two of the sample files and then decoded them, verifying that both worked by iterating over the decoded files to make sure they were the same as the originals.

FileFreqWordsIteratorTest.java 1. Our constructor test tests the size of the sorted, an ArrayList we wrote in FileFreqWordsIterator that contains the n most frequent words that appear in the file. So if n = 4, the size of sorted should also be 4. We also tested that each index of the ArrayList contained a String object that corresponded to the n most frequent words. 2. Our hasnext test tests if, while iterating with FileFreqWordsIterator through a file, there is something still to be returned. We tested in the middle of iterating that hasnext is true and at the end that hasnext is false.
3. Our next test tests that the iterator returns the correct binary string representation of the next character or word to be returned. I used convertbinary.com to find the binary string representation of words/characters. It also tested that the next character/word to be returned was in the correct order as they were in the file. We tested if the frequent word(s) was (were) at the beginning, end, and middle, or both of the file.  We also had one test that ran a while loop, and if the string to be returned was greater than eight characters (a full word, instead of a single character), we add to a counter. At the end the counter must equal the number of words to keep track of multiplied by the number of times those words appear in the string.

ZipperTest.java

testMakeDirectory(): 1. We have a makeDirectory that uses the java’s file to make directories within directories. This test make sure that when a directory is made the name input is the same as the file’s name. As well as if there is a child in that directory(created using the makeDirectory() method), then child’s get parent file method should be able to get the directory, as well as the directory name. In addition the test also tests that the child of the directory can also have its own children, and it’s children should be able to call get parent file twice to get the original directory. It also make sure that the function allow the directory to have multiple children.

testDeleteExistingFile(): 2. this test the method to delete directory. At first we try to delete a single file, and make sure it doesn’t exists after we delete it. Then we made a file with another file as its child. The child is then deleted, and check to ensure that it no longer exists, and we also check to make sure that the parent file doesn’t get deleted even if the child folder is deleted. We then add a new child to the parent file, and delete the parent file. Lastly is that we check that the parent file is deleted, as well as it’s children.

testFindTOCLength(): 3. this test to make sure that findTOCLength returns the correct length of the table of contents. We ran the methods on several zipper files, and manually counting the characters of the table of contents(including newline characters) we then check that the function returns a number that is a correct representation of how many characters are in the table of contents.

testSingleFile() 4. tests that the zippercan compress and then decompress a single file with no other directories involved except for the output one

testDirectoryWithSingleFile() 5. tests that the zipper can compress and then decompress a single file inside a single directory, with no other directories nested inside the original one

testEmptyDirectories() 6. tests that the zipper can compress and then decompress a series of directories with no actual files

testMultpleFolders() 7. tests that the zipper can compress and then decompress a directory that contains multiple other directories and multiple files

Runtimes

Encode, O(n log n)

The first part of encode involves counting the frequency of characters and creating frequency objects. This requires iterating over the entire input file, or O(n) time where n is the number of characters in the input file. Next, encode sort's these frequency objects using Comparable and Collections.sort, which after some research, should take O(k log k) time where k are the number of elements to sort. In the worst case, k = n (every character in the input is unique), so O(n log n). Constructing the huffman tree next should take O(n log n) time as well, making n calls to the put method which takes O(log n). Generating the codemap requires visiting all n nodes in the huffman tree, or n recusive calls (linear time). Finally, making the actual compressed file iterates over the original file, taking O(n) time as before. Overall then, our encode method should be O(n log n) time.

Decode, O(n log n)

The first part of decode involves iterating over the input file and deciphering the codemap header. This should take O(1) time as the headers are all relatively the same length. The method then adds each line of the codemap to a TreeMap, which should take O(log n) time. Next, decode iterates over the rest of the file and checks tries to find strings of bits in the TreeMap, which should take O(n log n) time.
