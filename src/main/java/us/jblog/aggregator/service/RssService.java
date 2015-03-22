package us.jblog.aggregator.service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.hibernate.validator.internal.util.privilegedactions.Unmarshal;
import org.springframework.stereotype.Service;

import us.jblog.aggregator.entity.Item;
import us.jblog.aggregator.exception.RssException;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import us.jblog.aggregator.rss.ObjectFactory;
import us.jblog.aggregator.rss.TRss;
import us.jblog.aggregator.rss.TRssChannel;
import us.jblog.aggregator.rss.TRssItem;

/**
 * This class performs transformation of xml source to java objects using JAXB.
 * TestCase can be created using eclipse. Right click on this class
 * on navigator and create other > testcase.
 * Create a new dependency for junit 4.* in pom.xml
 * @author lokesh
 *
 */
@Service
public class RssService {
	
	public List<Item> getItems(File fileSource) throws RssException {
		return getItems(new StreamSource(fileSource));
	}
	
	public List<Item> getItems(String url) throws RssException {
		return getItems(new StreamSource(url));
	}

	/**
	 * method made private in order to not allow just anyone to invoke this method.
	 * Hence created overloaded versions of this method as public.
	 * 
	 * @param source
	 * @return
	 * @throws RssException
	 */
	private List<Item> getItems(Source source) throws RssException {
		List<Item> listItems = new ArrayList<Item>();

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			//create an Unmarshaller, which is used to convert xml to java objects.
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			//unmarshaller can unmarshal the content in many ways, from
			//file, or from url, from inputstream etc. It can also consume a Source, which can be 
			//a file or a URL.
			//since trss is the root element of the rss file, hence the arguement is TRss object class.
			JAXBElement<TRss> element = unmarshaller.unmarshal(source, TRss.class);//to convert xml to objects
			TRss rss = element.getValue();

			List<TRssChannel> channels = rss.getChannel();
			for(TRssChannel channel: channels){
				List<TRssItem> items = channel.getItem();
				for(TRssItem rssItem: items){
					Item item = new Item();
					item.setTitle(rssItem.getTitle());
					item.setDescription(rssItem.getDescription());
					item.setLink(rssItem.getLink());
					java.util.Date pubishedDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(rssItem.getPubDate());
					item.setPublishedDate(pubishedDate);
					listItems.add(item);
				}
			}
		} catch (JAXBException e) {
			throw new RssException(e);
		}catch (ParseException e) {
			throw new RssException(e);
		}
		return listItems;

	}
}
