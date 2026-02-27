package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.Item;
import com.project.mapper.ItemMapper;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemMapper mapper;

	@Override
	@Transactional
	public int register(Item item) throws Exception {
		return mapper.create(item);
	}

	@Override
	public List<Item> list() throws Exception {
		return mapper.list();
	}

	@Override
	public String getPreview(Integer itemId) throws Exception {
		return mapper.getPreview(itemId);
	}

	@Override
	public String getPicture(Integer itemId) throws Exception {
		return mapper.getPicture(itemId);
	}

	@Override
	public Item read(Item item) throws Exception {
		return mapper.read(item);
	}

	@Override
	public int modify(Item item) throws Exception {
		return mapper.update(item);
	}

	@Override
	public int remove(Item item) throws Exception {
		return mapper.delete(item);
	}
}
