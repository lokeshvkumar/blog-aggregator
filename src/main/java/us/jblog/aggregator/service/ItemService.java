package us.jblog.aggregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import us.jblog.aggregator.entity.*;
import us.jblog.aggregator.repository.ItemRepository;

import java.util.*;

/**
 * This class lists all items in database. This will be invoked from 
 * IndexController on initial load.
 * @author lokesh
 *
 */
@Service
public class ItemService {
	
	
	@Autowired
	ItemRepository itemRepository;
	
	public List<Item> getItems(){
		return itemRepository.findAll(new PageRequest(0,5, Direction.DESC, "publishedDate"))
				.getContent();
	}
}
