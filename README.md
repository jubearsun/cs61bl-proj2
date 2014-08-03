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
