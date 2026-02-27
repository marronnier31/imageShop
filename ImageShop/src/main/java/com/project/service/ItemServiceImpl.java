package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.domain.Item;
import com.project.mapper.ItemMapper;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemMapper mapper;

	@Override
	public int register(Item item) throws Exception {
		return mapper.create(item);
	}
}
