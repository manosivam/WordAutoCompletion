package editor.autocompletion;

import java.util.List;
import java.util.stream.Collectors;

import editor.autocompletion.model.ITrie;
import editor.autocompletion.model.Trie;
import editor.autocompletion.utils.Constants;
import editor.utils.Logger;

public class AutoCompletion implements IAutoCompletion{

	private static AutoCompletion autoCompletion;
	private int suggestionLimit = Constants.DEFAULT_AUTO_SUGGESTION_LIMIT;
	
	private AutoCompletion() {};
	
	public static AutoCompletion getInstance()
	{
		if(autoCompletion == null)
		{
			autoCompletion = new AutoCompletion();
		}
		return autoCompletion;
	}
	
	@Override
	public List<String> getSuggestions(String query) {
		
		ITrie trie = Trie.getInstance();
		List<String> topSuggestions = trie.getSuggestions(query);
		topSuggestions = topSuggestions.stream().limit(suggestionLimit).collect(Collectors.toList());
		Logger.getInstance().printArrayList(topSuggestions);
		return topSuggestions;
	}
	
}
