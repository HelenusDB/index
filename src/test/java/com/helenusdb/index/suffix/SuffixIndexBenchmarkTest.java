package com.helenusdb.index.suffix;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.helenusdb.index.Corpus;
import com.helenusdb.index.suffix.SuffixIndex;

class SuffixIndexBenchmarkTest
{
	private static final int SEARCHES = 10000;

	@Test
	void test()
	{
		System.out.println("\nSuffix Index Benchmark:");
		SuffixIndex<String> index = timeIndexing();
		assertNotNull(index);
		timeSearch(index);
	}

	private SuffixIndex<String> timeIndexing()
	{
		SuffixIndex<String> index = new SuffixIndex<>();
		long start = System.currentTimeMillis();

		for (String description : Corpus.DESCRIPTIONS)
		{
			index.insert(description, description);
		}

		long end = System.currentTimeMillis();
		long totalTimeMillis = end - start;
		double avgTimeMicros = (totalTimeMillis / (double) Corpus.DESCRIPTIONS.length) * 1000.0;
		System.out.println(String.format("Indexing of %d phrases took %dms (%.3f microseconds per phrase)",
			Corpus.DESCRIPTIONS.length, totalTimeMillis, avgTimeMicros));
		return index;
	}

	private void timeSearch(SuffixIndex<String> index)
	{
		String[] phrases = { "waxing kit", "eco-friendly", "gaming keyboard", "water bottle", "wireless charging pad",
			"usb-c", "anti-bacterial", "lip balm", "earbuds" };

		long start = System.nanoTime();

		for (int i = 0; i < SEARCHES; i++)
		{
			index.search(phrases[i % phrases.length]);
		}

		long end = System.nanoTime();
		long totalTimeMillis = (end - start) / 1000000;
		double avgTimeMicros = totalTimeMillis / (double) SEARCHES * 1000.0;
		System.out.println(String.format("%d Searches took %dms (%.3f microseconds per search)",
			SEARCHES, totalTimeMillis, avgTimeMicros));
	}
}
