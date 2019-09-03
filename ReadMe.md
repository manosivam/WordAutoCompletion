## Editor: Auto-completion feature. 

### Use case: 

We will get as input a long list of (name, score) pairs. We want to create a program that takes a
list of these name score pairs and then suggests the top 10 names (by score) that match a
query string given by a user. We will consider a name to be a match for a query if:
1. The query is a prefix of the name
  - Ex. The query “rev” would match the name “revenue”
2. The query is a prefix of a part of the name following an underscore
  - Ex. The query “rev” would match the name “yearly_revenue” because even
    though it is not a prefix of the whole name, it is a prefix of the substring “revenue”
    following an underscore. Note that other substring matches that are not after an
    underscore do NOT count as matches
	
#### Serialization

In addition to creating the query serving program, we would like to be able to serialize any
underlying data structure this program uses. We should be able to save this structure to disk
and then load it back later without rebuilding it from the original list of name score pairs.

Time Complexity: *** O(length of query) ***

Interfaces: 
- [IAutoCompletion](https://github.com/manosivam/WordAutoCompletion/blob/master/src/editor/autocompletion/IAutoCompletion.java) (editor.autocompletion.IAutoCompletion.java)
	``` 
	List<String> getSuggestions(String query); 
	```
- [ITrie](https://github.com/manosivam/WordAutoCompletion/blob/master/src/editor/autocompletion/model/ITrie.java) (editor.autocompletion.model.ITrie.java)
	```
		void insert(String word, Integer score);
		void printAllWords();
		ArrayList<String> getSuggestions(String query);
	```
	
Classes involved: 
- [AutoCompletion](https://github.com/manosivam/WordAutoCompletion/blob/master/src/editor/autocompletion/AutoCompletion.java) (editor.autocompletion.AutoCompletion.java)
	- Uses Trie Datastructure and get suggestions for the given query string(if any) and limits it to top 10.
	- Singleton class in the editor.
- [Serialization](https://github.com/manosivam/WordAutoCompletion/blob/master/src/editor/serialization/Serialization.java) (editor.serialization.Serialization.java)
	- to serialize and deserialize to/from file or any storage.
	- Singleton class in the editor.
- [Logger](https://github.com/manosivam/WordAutoCompletion/tree/master/src/editor/utils) (editor.utils.Logger.java)
	- Singleton class to log messages.
- [TrieNode](https://github.com/manosivam/WordAutoCompletion/blob/master/src/editor/autocompletion/model/TrieNode.java) (editor.autocompletion.model.TrieNode.java)
	- Structure of a TrieNode is (only data members)
		```
		private char value; 
		private HashMap<Character,TrieNode> children; 
		private boolean bIsEnd;
		private TreeMap<Integer,ArrayList<String>> scoreNameListMap;//to make the search efficient *** (Time complexity: O(length of query)) ***, we are storing all words (word,score) in every node
		```
- [Trie](https://github.com/manosivam/WordAutoCompletion/blob/master/src/editor/autocompletion/model/Trie.java) (editor.autocompletion.model.Trie.java)(Datastructure to hold words)
	- Singleton class for a autocompletion. 
	- Structure is (only data members)
		```
		private TrieNode root; ** //root node will be have character _. For any word with underscore, the TrieNode of the last character before underscore will point to root TrieNode. **
		```
- [Constants](https://github.com/manosivam/WordAutoCompletion/blob/master/src/editor/autocompletion/utils/Constants.java) (editor.autocompletion.utils.Constants.java)

## How to execute the program? 
	
	1. Run the application
	2. It will prompt to enter word<space>score to create the trie data structure.
	3. type done to start quering for autocompletion. 
	4. Type in the query string. 
	5. AutoCompletion algorithm will suggest the top 10 (by score) words. 

- [Main class](https://github.com/manosivam/WordAutoCompletion/blob/master/src/editor/autocompletion/driver/Driver.java)	
	
### Note: please refer output.txt for sample output of the program. 
[output file](https://github.com/manosivam/WordAutoCompletion/blob/master/bin/output.txt)

