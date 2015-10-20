package us.jblog.aggregator.service;

import static org.junit.Assert.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import us.jblog.aggregator.entity.Item;

public class RssServiceTest {

	private RssService rssService;
	
	@Before
	public void setUp() throws Exception {
		rssService = new RssService();
	}
/*
	@Test
	public void testGetItemsFile() {
		List<Item> items = rssService.getItems(new File("test-rss/java-vids.xml"));
		Item item = items.get(0);
		assertEquals(10, items.size());
		assertEquals("Mismatch!!!","How to solve Source not found error during debug in Eclipse", item.getTitle());
		//22 06 2014 16:35:49
		assertEquals("22 06 2014 16:35:49", new SimpleDateFormat("dd MM yyyy HH:mm:ss").format(item.getPublishedDate()));
	}
*/
}
