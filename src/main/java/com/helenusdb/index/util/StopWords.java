package com.helenusdb.index.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A utility class for filtering out stop words from text. This is useful for
 * text analysis and search indexing.
 * 
 * @author Todd Fredrich
 * @see <a href="https://en.wikipedia.org/wiki/Stop_words">Stop Words</a>
 */
public class StopWords {
	private static final String FILTER_REGEX = "[^a-zA-Z0-9 ]";

	/**
	 * A minimal list of stop words.
	 */
	public static final String[] MINIMAL = { "a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in",
			"into", "is", "it", "no", "not", "of", "on", "or", "such", "that", "the", "their", "then", "there", "these",
			"they", "this", "to", "was", "will", "with" };

	/**
	 * A more extensive list of stop words in the English language as specified by
	 */
	public static final String[] ENGLISH = { "a", "about", "above", "across", "after", "again", "against", "all",
			"almost", "alone", "along", "already", "also", "although", "always", "among", "an", "and", "another", "any",
			"anybody", "anyone", "anything", "anywhere", "are", "area", "areas", "around", "as", "ask", "asked",
			"asking", "asks", "at", "away", "b", "back", "backed", "backing", "backs", "be", "because", "become",
			"becomes", "became", "been", "before", "began", "behind", "being", "beings", "best", "better", "between",
			"big", "both", "but", "by", "c", "came", "can", "cannot", "case", "cases", "certain", "certainly", "clear",
			"clearly", "come", "could", "d", "did", "differ", "different", "differently", "do", "does", "done", "down",
			"downed", "downing", "downs", "during", "e", "each", "early", "either", "end", "ended", "ending", "ends",
			"enough", "even", "evenly", "ever", "every", "everybody", "everyone", "everything", "everywhere", "f",
			"face", "faces", "fact", "facts", "far", "felt", "few", "find", "finds", "first", "for", "four", "from",
			"full", "fully", "further", "furthered", "furthering", "furthers", "g", "gave", "general", "generally",
			"get", "gets", "give", "given", "gives", "go", "going", "good", "goods", "got", "great", "greater",
			"greatest", "group", "grouped", "grouping", "groups", "h", "had", "has", "have", "having", "he", "her",
			"herself", "here", "high", "higher", "highest", "him", "himself", "his", "how", "i", "if", "important",
			"in", "interest", "interested", "interesting", "interests", "into", "is", "it", "its", "itself", "j",
			"just", "k", "keep", "keeps", "kind", "knew", "know", "known", "knows", "l", "large", "largely", "last",
			"later", "latest", "least", "less", "let", "lets", "like", "likely", "long", "longer", "longest", "m",
			"made", "make", "making", "man", "many", "may", "me", "member", "members", "men", "might", "more", "most",
			"mostly", "mr", "mrs", "must", "my", "myself", "n", "necessary", "need", "needed", "needing", "needs",
			"never", "new", "newer", "newest", "next", "no", "non", "not", "nobody", "noone", "nothing", "now",
			"nowhere", "number", "numbers", "o", "of", "off", "often", "old", "older", "oldest", "on", "once", "one",
			"only", "open", "opened", "opening", "opens", "or", "order", "ordered", "ordering", "orders", "other",
			"others", "our", "out", "over", "p", "part", "parted", "parting", "parts", "per", "perhaps", "place",
			"places", "point", "pointed", "pointing", "points", "possible", "present", "presented", "presenting",
			"presents", "problem", "problems", "put", "puts", "q", "quite", "r", "rather", "really", "right", "room",
			"rooms", "s", "said", "same", "saw", "say", "says", "second", "seconds", "see", "sees", "seem", "seemed",
			"seeming", "seems", "several", "shall", "she", "should", "show", "showed", "showing", "shows", "side",
			"sides", "since", "small", "smaller", "smallest", "so", "some", "somebody", "someone", "something",
			"somewhere", "state", "states", "still", "such", "sure", "t", "take", "taken", "than", "that", "the",
			"their", "them", "then", "there", "therefore", "these", "they", "thing", "things", "think", "thinks",
			"this", "those", "though", "thought", "thoughts", "three", "through", "thus", "to", "today", "together",
			"too", "took", "toward", "turn", "turned", "turning", "turns", "two", "u", "under", "until", "up", "upon",
			"us", "use", "uses", "used", "v", "very", "w", "want", "wanted", "wanting", "wants", "was", "way", "ways",
			"we", "well", "wells", "went", "were", "what", "when", "where", "whether", "which", "while", "who", "whole",
			"whose", "why", "will", "with", "within", "without", "work", "worked", "working", "works", "would", "x",
			"y", "year", "years", "yet", "you", "young", "younger", "youngest", "your", "yours", "z" };

	private Set<String> wordSet;

	public static StopWords minimal() {
		return new StopWords(MINIMAL);
	}

	public static StopWords english() {
		return new StopWords(ENGLISH);
	}

	/**
	 * Default constructor.
	 */
	public StopWords() {
		this(MINIMAL);
	}

	/**
	 * Constructor that initializes the stop words list.
	 * 
	 * @param words the stop words list.
	 */
	public StopWords(String[] words) {
		super();
		set(words);
	}

	/**
	 * Adds a word to the stop words list.
	 * 
	 * @param word the word to add.
	 */
	public StopWords add(String word) {
		wordSet.add(word);
		return this;
	}

	/**
	 * Sets the stop words list.
	 * 
	 * @param words the new stop words list.
	 */
	public void set(String[] words) {
		this.wordSet = new HashSet<>(Arrays.asList(words));
	}

	public String[] get() {
		return wordSet.toArray(new String[0]);
	}

	/**
	 * Tokenizes the input text, removes punctuation, converts to lowercase, and
	 * filters out stop words.
	 *
	 * @param text the input text
	 * @return a list of filtered tokens
	 */
	public List<String> filter(String text) {
		if (text == null || text.isBlank()) {
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