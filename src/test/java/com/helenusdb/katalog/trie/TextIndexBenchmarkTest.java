package com.helenusdb.katalog.trie;

import org.junit.jupiter.api.Test;

class TextIndexBenchmarkTest
{
	private static final int SEARCHES = 10000;
	private static final String[] DESCRIPTIONS = { "Wireless Bluetooth earbuds with noise cancellation",
		"Stainless steel water bottle with vacuum insulation", "Portable power bank with 10000mAh capacity",
		"Adjustable laptop stand with ergonomic design", "Smart LED bulb with color-changing features",
		"Fitness tracker with heart rate monitoring", "USB-C charging cable, durable and fast-charging",
		"Eco-friendly reusable grocery bags, set of 5", "Portable camping hammock with straps included",
		"Non-stick frying pan with scratch-resistant coating", "Wireless keyboard and mouse combo, ultra-slim",
		"Memory foam pillow for neck support and comfort", "Electric toothbrush with rechargeable battery",
		"Compact travel umbrella, windproof and lightweight", "Anti-theft backpack with USB charging port",
		"Collapsible silicone food storage containers", "Indoor air purifier with HEPA filter technology",
		"Waterproof phone case with touch screen compatibility", "Magnetic car phone mount, adjustable and secure",
		"Digital kitchen scale with high-precision sensors", "Silicone baking mats, reusable and non-stick",
		"Adjustable resistance bands for full-body workouts", "LED ring light for photography and video streaming",
		"Noise-canceling over-ear headphones with mic", "Desk organizer with compartments for stationery",
		"Electric kettle with fast-boil technology", "Double-walled insulated travel mug with lid",
		"Pet grooming brush for shedding and detangling", "Compact portable projector with HD resolution",
		"Rechargeable book light with flexible neck", "Multi-tool pocket knife with 12 functions",
		"Wireless doorbell with long-range signal", "USB-powered mini desk fan, quiet and efficient",
		"Stainless steel reusable straws, set of 4", "Magnetic screen door with durable mesh material",
		"Wooden cutting board with juice groove", "Smart home security camera with motion detection",
		"Portable hand warmer with rechargeable battery", "Ergonomic office chair with lumbar support",
		"Compact spice rack with adjustable tiers", "Ultra-soft microfiber bath towels, set of 3",
		"Solar-powered outdoor garden lights, pack of 6", "Kitchen knife set with stainless steel blades",
		"Foldable yoga mat with non-slip surface", "Lightweight travel backpack with multiple pockets",
		"Adjustable phone stand for desks and counters", "Electric wine opener with rechargeable base",
		"Digital alarm clock with sunrise simulation", "Reusable silicone sandwich bags, set of 3",
		"Smart thermostat with Wi-Fi compatibility", "Wireless earbuds with touch controls",
		"Washable baby onesie in soft cotton", "Bluetooth speaker for outdoor use",
		"Travel-sized moisturizer with SPF 30", "Smartwatch with heart rate monitor",
		"Durable camping knife with multiple tools", "Rechargeable power bank for gadgets",
		"Reusable silicone food storage bags", "Slim-fit women's blazer in navy",
		"Waterproof hiking boots for all terrains", "High-efficiency air purifier with CADR rating",
		"Compact digital scale for kitchen use", "Natural laundry detergent without dyes",
		"Adjustable resistance exercise bands set", "Plantable seed paper with designs",
		"LED grow light for indoor gardens", "Bluetooth headphones with noise cancellation",
		"Memory foam travel pillow for comfort", "Aluminum camping cookware set",
		"Vegan protein powder with monk fruit sweetener", "Anti-slip yoga mat for home practice",
		"Dual-port USB charger with LED indicator", "Disposable razor in hypoallergenic design",
		"Bright starfish candle for relaxation", "Water-saving toilet tank kit with timer",
		"Quick-release bicycle lock in stainless steel", "Dust-proof camera lens cleaning brush",
		"Waterproof silicone phone case with kickstand", "Stand-up paddleboard for beginners",
		"Dual USB car charger with LED lights", "Stainless steel water bottle with straw",
		"Organic Greek yogurt in 16oz jar", "Color-changing silicone ice pack for cold packs",
		"High-visibility reflective jacket for safety", "One-touch electric kettle with timer",
		"Full-body massage gun with Bluetooth", "Eco-friendly reusable beeswax wraps for food storage",
		"DIY plant starter kit with pots and soil", "Water-proof cell phone case in clear",
		"360-degree mirror mount for RVs and boats", "Triple-pouch diaper backpack with pockets",
		"Reusable water bottle in glass with lid", "Magazine holder for living room or office",
		"Quilted travel blanket with carrying handle", "Packet of 50 basil seeds for home gardening",
		"Adhesive-backed wall art in abstract designs", "Carbon fiber telescopic fire extinguisher for cars",
		"24-pack of eco-friendly cotton buds with recyclable sticks", "Soft rubber bath mat with non-slip backing",
		"Knee-high compression socks for travel", "Quick-release bike lock in silver",
		"Bright LED reading lamp with adjustable arm", "Anti-bacterial hand sanitizer spray",
		"Reusable coffee cup with lid and sleeve", "Waterproof smartphone screen protector",
		"Durable hiking backpack for day trips", "Slim-fit belt in black leather",
		"Bluetooth headphones with noise cancellation", "Portable solar charger for phone and tablet",
		"Travel-sized carry-on cosmetic bag", "Wireless mouse with ergonomic design",
		"Aluminum water bottle in stainless steel", "Solar-powered charger for outdoor use",
		"Dry erase board with whiteboard marker set", "Water-resistant phone case in clear",
		"High-efficiency LED light bulb in A19 size", "Adhesive wall art in minimalist frames",
		"Natural wood cutting board with knife groove", "Dual-port car charger with USB ports",
		"Reusable silicone ice cube tray in 12-cavity", "Portable speaker for backyard parties",
		"Waterproof sun umbrella with straps", "Sewing kit with 12 needles and thread",
		"Keychain mirror in shiny finish", "Travel pillow for airplane trips",
		"Reusable water bottle with silicone cap", "Universal power strip with 4 outlets",
		"Natural beeswax candle in white", "Dual USB charger for phone and tablet",
		"Desk organizer with 4 compartments", "Waterproof backpack for outdoor activities",
		"DIY plant potting soil mix in 5-pound bag", "Scented soy candles in lavender",
		"Adjustable standing desk with power outlet", "Portable speaker in waterproof design",
		"Durable fishing reel in black finish", "Ultra-soft microfiber cleaning cloth",
		"Waterproof phone case with magnetic closure", "Travel-sized nail file set in compact design",
		"Anti-slip yoga mat for home practice", "Natural beauty face mask with aloe vera",
		"Waterproof storage container in clear", "Water-resistant phone case with camera hole",
		"Dry erase board marker set of 10 colors", "Reusable food containers in 4 pack",
		"Universal car mirror for visibility check", "Waterproof ski goggle with lens cleaning spray",
		"Solar-powered phone charger in portable design", "Full-coverage face mask with moisturizing formula",
		"Water bottle in sleek glass design", "Solar-powered cell phone charger for hiking",
		"Anti-bacterial hand soap with pump", "Rechargeable water purifier in travel size",
		"Durable camping lantern with long-life LED", "Anti-slip yoga mat in 72-inch length",
		"Wireless earbuds with touch controls", "Waterproof phone case for iPhone in clear",
		"Reusable food wrap with plant-based material", "Portable solar charger for trekking trips",
		"Durable fishing rod in aluminum design", "Scented soy candles with lemon essential oil",
		"Waterproof backpack for beach use", "Anti-bacterial hand sanitizer in spray bottle",
		"Solar-powered phone charger with USB port", "Waterproof phone case for Samsung Galaxy",
		"Travel-sized hair brush in soft bristles", "Anti-bacterial hand wash with lavender scent",
		"Durable fishing rod holder for boats", "Scented soy candles in jasmine aroma",
		"Waterproof phone case with shock absorption", "Travel-sized body lotion in 4 oz bottle",
		"Durable fishing line in 100 yards spool", "Anti-bacterial hand soap with moisturizing formula",
		"Solar-powered phone charger in clear design", "Travel-sized makeup remover wipe pack",
		"Durable fishing rod with extended handle", "Scented soy candles in vanilla aroma",
		"Waterproof phone case for Android", "Travel-sized hair spray with hold",
		"Anti-bacterial hand sanitizer in pump bottle", "Durable fishing rod with high-strength line",
		"Scented soy candles in orange aroma", "Waterproof phone case for Apple devices",
		"Travel-sized lip balm with SPF", "Durable fishing rod in graphite material",
		"Scented soy candles with eucalyptus scent", "Waterproof phone case for smartphones",
		"Travel-sized hair gel with hold", "Anti-bacterial hand soap in clear bottle",
		"Durable fishing rod in aluminum design", "Scented soy candles in grapefruit scent",
		"Waterproof phone case for iPhones", "Travel-sized perfume in 1 oz bottle",
		"Durable fishing rod with swivel line", "Scented soy candles with jasmine scent",
		"Waterproof phone case for general use", "Waterproof phone case in clear", "Travel-sized lip balm with SPF",
		"Solar-powered charger for phones", "Anti-bacterial hand sanitizer spray",
		"Bright LED camping lantern with handle", "Durable fishing rod with extendable handle",
		"Waterproof phone case for Androids", "Travel-sized sunscreen in clear tube",
		"Solar-powered charger with USB ports", "Durable fishing reel with high-tension line",
		"Waterproof phone case in clear plastic", "Solar-powered charger for outdoor use",
		"Travel-sized deodorant with roll-on applicator", "Durable fishing rod in lightweight design",
		"Waterproof phone case for smartphones", "Travel-sized hair conditioner with vitamin E",
		"Solar-powered charger in portable design", "Durable fishing rod with high-quality spool",
		"Waterproof phone case for Samsung devices", "Solar-powered charger for iPhones" };

	@Test
	void test()
	{
		TextIndex<String> index = timeIndexing();
		timeSearch(index);
	}

	private TextIndex<String> timeIndexing()
	{
		TextIndex<String> index = new TextIndex<>();
		long start = System.currentTimeMillis();

		for (String description : DESCRIPTIONS)
		{
			index.insert(description, description);
		}

		long end = System.currentTimeMillis();
		long totalTimeMillis = end - start;
		double avgTimeMicros = (totalTimeMillis / (double) DESCRIPTIONS.length) * 1000.0;
		System.out.println(String.format("Indexing of %d phrases took %dms (%.3f microseconds per phrase)",
			DESCRIPTIONS.length, totalTimeMillis, avgTimeMicros));
		return index;
	}

	private void timeSearch(TextIndex<String> index)
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
