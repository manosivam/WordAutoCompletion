package editor.autocompletion.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TrieNode implements Serializable{
	
	private static final long serialVersionUID = -223654114471476158L;
	private char value; 
	private HashMap<Character,TrieNode> children; 
	private boolean bIsEnd;
	private TreeMap<Integer,ArrayList<String>> scoreNameListMap;//to make the search efficient (in sub linear time), we are storing all words (word,score) in every node
	
	public TrieNode(char ch) {
		//assuming only lower case characters for easy processing. 
		value = ch; 
		scoreNameListMap = new TreeMap<Integer,ArrayList<String>>(Collections.reverseOrder());
		
		if(ch == '*') //assuming * meta-char will not be present in any word. 
		{
			children = null;
			bIsEnd = true;
		}
		else
		{
			children = new HashMap<>();
			bIsEnd = false; 
		}
	}

	public HashMap<Character,TrieNode> getChildren() 
	{ 
		return children; 
	}
	public Character getValue()						 
	{ 
		return value;	 
	}
	
	public boolean isEnd()
	{
		return bIsEnd; 
	} 
	
	public void addNameScorePairToNode(String word, Integer score)
	{
		if(scoreNameListMap.containsKey(score))
		{
			ArrayList<String> wordList = scoreNameListMap.get(score);
			wordList.add(word);
			wordList = wordList.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
			scoreNameListMap.put(score,wordList);
		}
		else 
		{
			ArrayList<String> wordList = new ArrayList<String>();
			wordList.add(word);
			scoreNameListMap.put(score,wordList);
		}
	}
	
	public TreeMap<Integer,ArrayList<String>> getNameScorePairSetOfEndNode()
	{
		return this.scoreNameListMap;
	}
	
} 

