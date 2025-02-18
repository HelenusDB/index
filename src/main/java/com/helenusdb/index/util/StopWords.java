package com.helenusdb.index.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * A utility class for filtering out stop words from text. This is useful for text analysis and search indexing.
 * 
 * @author Todd Fredrich
 * @see <a href="https://en.wikipedia.org/wiki/Stop_words">Stop Words</a>
 */
public class StopWords
{
	private static final String FILTER_REGEX = "[^a-zA-Z0-9 ]";

	/**
	 * A minimal list of stop words for use in general use cases for names, descriptions, etc.
	 */
	public static final String[] MINIMAL = { "a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in",
		"into", "is", "it", "no", "not", "of", "on", "or", "such", "that", "the", "their", "then", "there", "these",
		"they", "this", "to", "was", "will", "with" };

	/**
	 * A list of pronouns used in the Porter2 stemmer.
	 * 
	 * @see https://snowballstem.org/algorithms/english/stop.txt
	 */
	public static final String[] SNOWBALL_PRONOUNS = { "i", "me", "my", "myself", "we", "us", "our", "ours", "ourselves", "you",
		"your", "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it",
		"its", "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this",
		"that", "these", "those" };

	/**
	 * A list of verb forms used in the Porter2 stemmer.
	 * 
	 * @see https://snowballstem.org/algorithms/english/stop.txt
	 */
	public static final String[] SNOWBALL_VERB_FORMS = { "am", "is", "are", "was", "were", "be", "been", "being", "have", "has",
		"had", "having", "do", "does", "did", "doing", "would", "should", "could", "ought" };

	/**
	 * A list of other common stop words used in the Porter2 stemmer.
	 * 
	 * @see https://snowballstem.org/algorithms/english/stop.txt
	 */
	public static final String[] SNOWBALL_OTHER = { "and", "but", "if", "or", "because", "as", "until", "while", "of", "at",
		"by", "for", "with", "about", "against", "between", "into", "through", "during", "before", "after", "above",
		"below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again", "further", "then",
		"once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each", "few", "more", "most",
		"other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very" };

	/**
	 * A list of stop words used in the Porter2 stemmer.
	 * 
	 * @see https://snowballstem.org/algorithms/english/stop.txt
	 */
	public static final String[] SNOWBALL_ALL = Stream.of(SNOWBALL_PRONOUNS, SNOWBALL_VERB_FORMS, SNOWBALL_OTHER)
		.flatMap(Stream::of).toArray(String[]::new);

	/**
	 * A list of stop words commonly used in the InnoDB storage engine.
	 * 
	 * @see https://dev.mysql.com/doc/refman/9.1/en/information-schema-innodb-ft-default-stopword-table.html
	 */
	public static final String[] INNODB = { "a", "about", "an", "are", "as", "at", "be", "by", "com", "de", "en", "for",
		"from", "how", "i", "in", "is", "it", "la", "of", "on", "or", "that", "the", "this", "to", "was", "what",
		"when", "where", "who", "will", "with", "und", "the", "www" };

	/**
	 * A more extensive list of stop words in the English language as specified by
	 * https://www.textfixer.com/tutorials/common-english-words.txt
	 */
	public static final String[] ENGLISH = { "a", "able", "about", "across", "after", "all", "almost", "also", "am",
		"among", "an", "and", "any", "are", "as", "at", "be", "because", "been", "but", "by", "can", "cannot", "could",
		"dear", "did", "do", "does", "either", "else", "ever", "every", "for", "from", "get", "got", "had", "has",
		"have", "he", "her", "hers", "him", "his", "how", "however", "i", "if", "in", "into", "is", "it", "its", "just",
		"least", "let", "like", "likely", "may", "me", "might", "most", "must", "my", "neither", "no", "nor", "not",
		"of", "off", "often", "on", "only", "or", "other", "our", "own", "rather", "said", "say", "says", "she",
		"should", "since", "so", "some", "than", "that", "the", "their", "them", "then", "there", "these", "they",
		"this", "tis", "to", "too", "twas", "us", "wants", "was", "we", "were", "what", "when", "where", "which",
		"while", "who", "whom", "why", "will", "with", "would", "yet", "you", "your" };

	/**
	 * A more extensive list of stop words in the English language as specified by Fox, Christopher (1989-09-01). "A
	 * stop list for general text". ACM SIGIR Forum. 24 (1/2): 19–21.
	 * 
	 * @see https://doi.org/10.1145/378881.378888
	 */
	public static final String[] GENERAL_TEXT = { "a", "about", "above", "across", "after", "again", "against", "all",
		"almost", "alone", "along", "already", "also", "although", "always", "among", "an", "and", "another", "any",
		"anybody", "anyone", "anything", "anywhere", "are", "area", "areas", "around", "as", "ask", "asked", "asking",
		"asks", "at", "away", "b", "back", "backed", "backing", "backs", "be", "because", "become", "becomes", "became",
		"been", "before", "began", "behind", "being", "beings", "best", "better", "between", "big", "both", "but", "by",
		"c", "came", "can", "cannot", "case", "cases", "certain", "certainly", "clear", "clearly", "come", "could", "d",
		"did", "differ", "different", "differently", "do", "does", "done", "down", "downed", "downing", "downs",
		"during", "e", "each", "early", "either", "end", "ended", "ending", "ends", "enough", "even", "evenly", "ever",
		"every", "everybody", "everyone", "everything", "everywhere", "f", "face", "faces", "fact", "facts", "far",
		"felt", "few", "find", "finds", "first", "for", "four", "from", "full", "fully", "further", "furthered",
		"furthering", "furthers", "g", "gave", "general", "generally", "get", "gets", "give", "given", "gives", "go",
		"going", "good", "goods", "got", "great", "greater", "greatest", "group", "grouped", "grouping", "groups", "h",
		"had", "has", "have", "having", "he", "her", "herself", "here", "high", "higher", "highest", "him", "himself",
		"his", "how", "i", "if", "important", "in", "interest", "interested", "interesting", "interests", "into", "is",
		"it", "its", "itself", "j", "just", "k", "keep", "keeps", "kind", "knew", "know", "known", "knows", "l",
		"large", "largely", "last", "later", "latest", "least", "less", "let", "lets", "like", "likely", "long",
		"longer", "longest", "m", "made", "make", "making", "man", "many", "may", "me", "member", "members", "men",
		"might", "more", "most", "mostly", "mr", "mrs", "must", "my", "myself", "n", "necessary", "need", "needed",
		"needing", "needs", "never", "new", "newer", "newest", "next", "no", "non", "not", "nobody", "noone", "nothing",
		"now", "nowhere", "number", "numbers", "o", "of", "off", "often", "old", "older", "oldest", "on", "once", "one",
		"only", "open", "opened", "opening", "opens", "or", "order", "ordered", "ordering", "orders", "other", "others",
		"our", "out", "over", "p", "part", "parted", "parting", "parts", "per", "perhaps", "place", "places", "point",
		"pointed", "pointing", "points", "possible", "present", "presented", "presenting", "presents", "problem",
		"problems", "put", "puts", "q", "quite", "r", "rather", "really", "right", "room", "rooms", "s", "said", "same",
		"saw", "say", "says", "second", "seconds", "see", "sees", "seem", "seemed", "seeming", "seems", "several",
		"shall", "she", "should", "show", "showed", "showing", "shows", "side", "sides", "since", "small", "smaller",
		"smallest", "so", "some", "somebody", "someone", "something", "somewhere", "state", "states", "still", "such",
		"sure", "t", "take", "taken", "than", "that", "the", "their", "them", "then", "there", "therefore", "these",
		"they", "thing", "things", "think", "thinks", "this", "those", "though", "thought", "thoughts", "three",
		"through", "thus", "to", "today", "together", "too", "took", "toward", "turn", "turned", "turning", "turns",
		"two", "u", "under", "until", "up", "upon", "us", "use", "uses", "used", "v", "very", "w", "want", "wanted",
		"wanting", "wants", "was", "way", "ways", "we", "well", "wells", "went", "were", "what", "when", "where",
		"whether", "which", "while", "who", "whole", "whose", "why", "will", "with", "within", "without", "work",
		"worked", "working", "works", "would", "x", "y", "year", "years", "yet", "you", "young", "younger", "youngest",
		"your", "yours", "z" };

	private Set<String> wordSet;

	/**
	 * Factory method to create a StopWords instance with the Snowball stop words list from the Porter2 stemmer.
	 * 
	 * @return a new StopWords instance.
	 * @see <a href="https://snowballstem.org/algorithms/english/stop.txt">Snowball Stop Words</a>
	 */
	public static StopWords snowball()
	{
		return new StopWords(SNOWBALL_ALL);
	}

	/**
	 * Factory method to create a StopWords instance with the minimal stop words list.
	 * 
	 * @return a new StopWords instance.
	 */
	public static StopWords minimal()
	{
		return new StopWords(MINIMAL);
	}

	/**
	 * Factory method to create a StopWords instance with the InnoDB stop words list.
	 * 
	 * @return a new StopWords instance.
	 */
	public static StopWords innoDb()
	{
		return new StopWords(INNODB);
	}

	/**
	 * Factory method to create a StopWords instance with the English stop words list.
	 * 
	 * @return a new StopWords instance.
	 */
	public static StopWords english()
	{
		return new StopWords(ENGLISH);
	}

	/**
	 * Factory method to create a StopWords instance with the general text stop words list.
	 * 
	 * @return a new StopWords instance.
	 */
	public static StopWords generalText()
	{
		return new StopWords(GENERAL_TEXT);
	}

	/**
	 * Default constructor.
	 */
	public StopWords()
	{
		this(MINIMAL);
	}

	/**
	 * Constructor that initializes the stop words list.
	 * 
	 * @param words the stop words list.
	 */
	public StopWords(String[] words)
	{
		super();
		set(words);
	}

	/**
	 * Adds a word to the stop words list.
	 * 
	 * @param word the word to add.
	 */
	public StopWords add(String word)
	{
		wordSet.add(word);
		return this;
	}

	/**
	 * Adds multiple words to the stop words list.
	 * 
	 * @param words the words to add.
	 */
	public StopWords addAll(String[] words)
	{
		wordSet.addAll(Arrays.asList(words));
		return this;
	}

	/**
	 * Initializes the stop words list.
	 * 
	 * @param words the new stop words list.
	 */
	public void set(String[] words)
	{
		this.wordSet = new HashSet<>(Arrays.asList(words));
	}

	/**
	 * Returns a copy of the internal stop words list.
	 * 
	 * @return the stop words list.
	 */
	public String[] get()
	{
		return wordSet.toArray(new String[0]);
	}

	/**
	 * Tokenizes the input text, removes punctuation, converts to lowercase, and filters out stop words
	 * based on the internal stop words list.
	 *
	 * @param text the input text
	 * @return a list of filtered tokens
	 */
	public List<String> filter(String text)
	{
		if (text == null || text.isBlank())
		{
			return Collections.emptyList();
		}

		// Convert to lowercase and remove punctuation
		String normalizedText = text.toLowerCase().replaceAll(FILTER_REGEX, "");
		// Split the text into words
		String[] words = normalizedText.split("\\s+");
		// Filter out stop words
		return Arrays.stream(words).filter(word -> !wordSet.contains(word)).toList();
	}
}
