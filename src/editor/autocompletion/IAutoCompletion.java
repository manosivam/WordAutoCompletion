package editor.autocompletion;

import java.util.ArrayList;
import java.util.List;

public interface IAutoCompletion {

	public List<String> getSuggestions(String query);
	
}
