package editor.autocompletion.model;

import java.util.ArrayList;

public interface ITrie {

	void insert(String word, Integer score);
	void printAllWords();
	ArrayList<String> getSuggestions(String query);
}