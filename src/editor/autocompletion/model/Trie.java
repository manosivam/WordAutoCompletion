package editor.autocompletion.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import editor.utils.Logger;
public class Trie implements Serializable, ITrie{
	
	private static final long serialVersionUID = -3527775164611321180L;
	private TrieNode root;
	private static Trie trie;
	
	private Trie()
	{
		root = new TrieNode('_'); 
	}

	public static Trie getInstance()
	{
		if(trie == null)
		{
			trie = new Trie();
		}
		return trie;
	}
	
	@Override
	public void insert(String word, Integer score)
	{
		//word should not be null.
		if(word == null)
		{
			return;
		}
		
		//trimming the empty spaces. word has to be always space free.
		char charArray[] = word.trim().toLowerCase().toCharArray(); 
		
		TrieNode travelPointer = root;
		
		for(int i=0;i< charArray.length;i++)
		{
			Map<Character, TrieNode> children = travelPointer.getChildren();
			
			char scanningCharacter = charArray[i];
			if(children.containsKey(scanningCharacter))
			{
				//travel further. getChildren.
				travelPointer = children.get(scanningCharacter);
			}
			else
			{
				TrieNode tNode = null;
				if(scanningCharacter == '_')
				{
					tNode = root;
					//already root node will have _ in it. 
				}
				else 
				{
					tNode = new TrieNode(scanningCharacter);
				}
				travelPointer = tNode;
				children.put(scanningCharacter,tNode);
			}
			travelPointer.addNameScorePairToNode(word, score);
		}
		
		char characterToAdd = '*';
		Map<Character,TrieNode> lastChildren = travelPointer.getChildren();
		TrieNode endNode = null;
		if(lastChildren.containsKey('*'))
		{
			endNode = lastChildren.get('*');
		}
		else 
		{
			endNode = new TrieNode(characterToAdd);
			lastChildren.put(characterToAdd, endNode);
		}
		endNode.addNameScorePairToNode(word, score);
		
	}
	
	@Override
	public void printAllWords()
	{
		printAllWords(root);
	}
	
	private void printAllWords(TrieNode node)
	{
		TreeMap<Integer,ArrayList<String>> allWordsWithScoreForTheGivenNode = getAllWords(node);
		Logger logger = Logger.getInstance();
		for(Map.Entry<Integer,ArrayList<String>> nameScorePair : allWordsWithScoreForTheGivenNode.entrySet())
		{
			ArrayList<String> wordList = nameScorePair.getValue();
			for(String word : wordList)
			{
				logger.printMessage("[name score] = ["+nameScorePair.getKey() +" "+word+"]");
			}
		}
		return;
	}
	
	//Get suggestions in O(length of query String) time.
	public ArrayList<String> getSuggestions(String query)
	{
		ArrayList<String> wordSuggestions = new ArrayList<String>();
		TreeMap<Integer,ArrayList<String>> suggestionsWithScoreSorted =  getSuggestionsWithScore(query);
		Logger logger = Logger.getInstance();
		for(Map.Entry<Integer, ArrayList<String>> entry: suggestionsWithScoreSorted.entrySet())
		{
			ArrayList<String> words = entry.getValue();
			ArrayList<String> wordsCommaScoreList = new ArrayList<String>();
			//appending score to the each words delimited with comma.
			for(String word : words)
			{
				String wordCommaScore = word +","+ entry.getKey();
				wordsCommaScoreList.add(wordCommaScore);
			}
			wordSuggestions.addAll(wordsCommaScoreList);
		}
		//logger.printArrayList(wordSuggestions);
		return wordSuggestions;
	}
	
	private TreeMap<Integer,ArrayList<String>> getSuggestionsWithScore(String query)
	{
		TreeMap<Integer,ArrayList<String>> suggestionsWithScoreSorted = new TreeMap<Integer, ArrayList<String>>();
		
		if(query!=null)
		{
			query = query.trim().toLowerCase();
			char[] queryAsCharArray = query.toCharArray();
			
			TrieNode crawler = root; 
			for(int i=0;i<queryAsCharArray.length;i++)
			{
				Map<Character,TrieNode> children =  crawler.getChildren();
				char scanningCharacter = queryAsCharArray[i];
				//TODO: what if the scanningCHaracter is _. think about it. 
				if(children.containsKey(scanningCharacter))
				{
					crawler = children.get(scanningCharacter);
				}
				else
				{
					return suggestionsWithScoreSorted; // empty - no suggestions.
				}
			}
			suggestionsWithScoreSorted = crawler.getNameScorePairSetOfEndNode();
		}
		return suggestionsWithScoreSorted;
	}
	
	private TreeMap<Integer,ArrayList<String>> getAllWords(TrieNode node)
	{
		
		TreeMap<Integer,ArrayList<String>> allWordsWithScore = new TreeMap<>(Collections.reverseOrder());
		if(node!=null)
		{
			Map<Character,TrieNode> immediateChildOfGivenNode = node.getChildren();
			for(Map.Entry<Character, TrieNode> mapIterator : immediateChildOfGivenNode.entrySet())
			{
				TreeMap<Integer, ArrayList<String>>  scoreWordsMap = mapIterator.getValue().getNameScorePairSetOfEndNode();
				for(Map.Entry<Integer, ArrayList<String>> scoreWord : scoreWordsMap.entrySet())
				{
					if(allWordsWithScore.containsKey(scoreWord.getKey()))
					{
						ArrayList<String> words = scoreWord.getValue();
						words.addAll(allWordsWithScore.get(scoreWord.getKey()));
						words = words.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
						allWordsWithScore.put(scoreWord.getKey(), words);
					}
					else 
					{
						allWordsWithScore.put(scoreWord.getKey(),scoreWord.getValue());
					}
				}
				
			}
		}
		return allWordsWithScore;
	}
	
	
	
}
