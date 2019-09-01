package editor.autocompletion.driver;

import java.util.Scanner;

import editor.autocompletion.AutoCompletion;
import editor.autocompletion.model.ITrie;
import editor.autocompletion.model.Trie;
import editor.serialization.Serialization;
import editor.utils.Logger;

public class Driver {

	public static void main(String[] args) {
		new Driver().start();	
	}
	
	private AutoCompletion autoCompletion = AutoCompletion.getInstance();
	private Logger logger;
	private Scanner inputReader;
	private ITrie trie;
	
	public Driver()
	{
		logger = Logger.getInstance();
		logger.printMessage("....Initializing required components....");
		autoCompletion = AutoCompletion.getInstance();
		trie = Trie.getInstance();
		inputReader = new Scanner(System.in); 
	}
	
	public void start()
	{
		/*trie.insert("rev_tag",20);
		trie.insert("book_revision", 1000);
		trie.printAllWords();
		trie.insert("revs",200);
		trie.insert("comma", 10);
		trie.insert("ammo", 210);
		trie.insert("revss",200);
		trie.insert("__a",1001);
		trie.insert("___",0);
		trie.insert("Tag", 20);
		trie.insert("rev_tag", 299);
		trie.insert("rev_tag_tags",300);*/
		
		do
		{
			readWordsScoresAsInput();
			performSerializationAndDesealization();
			autoSuggestion();
			logger.printMessage(".... want to stop the application? type yes or no ....");
			logger.printMessage(".... no or invalid input - go to feed more words ....");
			logger.printMessage(".... yes - to stop the application ....");
			if(inputReader.hasNext())
			{
				String userInputToStopApplication = inputReader.nextLine();
				if(userInputToStopApplication.toLowerCase().equals("yes"))
				{
					break;
				}
			}
		}while(true);
		
		
		logger.printMessage(".... Stopping application. Thank you, Have a good day! ....");
	}
	
	private void autoSuggestion() {
		
		logger.printMessage("..... Enter words (in new line) to get suggestions ....");
		logger.printMessage(".... type \"done\" to stop getting suggestions ....");
		
		for(;;)
		{
			if(inputReader.hasNext())
			{
				String inputFromUser = inputReader.nextLine();
				if(inputFromUser.toLowerCase().equals("done"))
				{
					break;
				}
				logger.printMessage(".... Getting suggestions for ["+inputFromUser+"]");
				autoCompletion.getSuggestions(inputFromUser);
				logger.printMessage("..........................................");
			}
		}
		logger.printMessage("..........................................................");
		
	}

	private void performSerializationAndDesealization() {
		
		logger.printMessage("....Before serialization printing all words in trie with its score....");
		trie.printAllWords();
		logger.printMessage("....Serialization in progress....");
		
		Serialization serialization = Serialization.getInstance();
		serialization.writeObject(trie);
		
		logger.printMessage("....Serialization done....");
		
		Object obj = serialization.loadObject();
		ITrie trie2 = Trie.getInstance();
		if(obj!=null)
		{
			trie2 = (ITrie) obj;
		}
		logger.printMessage("....After deserialization, printing all words with its score....");
		trie2.printAllWords();
		logger.printMessage("........................................................................");
		
	}

	public void readInputsForSuggestion()
	{
		
	}
	
	public void readWordsScoresAsInput()
	{
		logger.printMessage(".... type word<Space>score as input ...");
		logger.printMessage(".... Example 1: revenue 200 ....");
		logger.printMessage(".... Example 2: yearly_revenue 10 ....");
		logger.printMessage(".... type \"done\" to stop entering words and print all words ....");
		
		for(;;)
		{
			if(inputReader.hasNext())
			{
				String inputFromUser = inputReader.nextLine();
				if(inputFromUser.toLowerCase().equals("done"))
				{
					logger.printMessage("....Printing all words with its score....");
					//trie.printAllWords();
					logger.printMessage("........................................................................");
					return;
				}
				else if(inputFromUser.contains(" "))  
				{
					String[] nameAndScore = inputFromUser.split(" ");
					trie.insert(nameAndScore[0], Integer.parseInt(nameAndScore[1]));	
					//logger.printMessage("inserted into store");
				}
				else 
				{
					logger.printMessage("... Invalid input... please recheck your input and type \"word<space>score\" or \"done\"");
				}
			}
		}
		
		
		
	}

}
