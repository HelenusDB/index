package com.helenusdb.index;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class SimpleIterationBenchmarkTest
{
	private static final int SEARCHES = 10000;

	@Test
	void test()
	{
		System.out.println("\nSimple Iteration Benchmark:");
		timeIterativeSearch(Corpus.DESCRIPTIONS);
	}

	private List<String> timeSorting()
	{
		List<String> corpus = Arrays.asList(Corpus.DESCRIPTIONS);
		long start = System.currentTimeMillis();
		Collections.sort(corpus);
		long end = System.currentTimeMillis();
		long totalTimeMillis = end - start;
		double avgTimeMicros = (totalTimeMillis / (double) Corpus.DESCRIPTIONS.length) * 1000.0;
		System.out.println(String.format("Sorting of %d phrases took %dms (%.3f microseconds per phrase)",
			Corpus.DESCRIPTIONS.length, totalTimeMillis, avgTimeMicros));
		return corpus;
	}

	private void timeIterativeSearch(String[] corpus)
	{
		String[] phrases = { "waxing kit", "eco-friendly", "gaming keyboard", "water bottle", "wireless charging pad",
				"usb-c", "anti-bacterial", "lip balm", "earbuds" };

			long start = System.nanoTime();

			for (int i = 0; i < SEARCHES; i++)
			{
				List<String> results = new ArrayList<>();

				for (String corpusPhrase : corpus)
                {
                    for (String phrase : phrases)
                    {
                        if (corpusPhrase.contains(phrase))
                        {
                            results.add(corpusPhrase);
                        }
                    }
                }

				assert results.isEmpty() == false;
			}

			long end = System.nanoTime();
			long totalTimeMillis = (end - start) / 1000000;
			double avgTimeMicros = totalTimeMillis / (double) SEARCHES * 1000.0;
			System.out.println(String.format("%d Searches took %dms (%.3f microseconds per search)",
				SEARCHES, totalTimeMillis, avgTimeMicros));
	}
}
