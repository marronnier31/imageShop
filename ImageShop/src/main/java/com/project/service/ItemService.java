package com.project.service;

import java.util.List;

import com.project.domain.Item;

public interface ItemService {

	public int register(Item item) throws Exception;

	public List<Item> list() throws Exception;

	public String getPreview(Integer itemId) throws Exception;

	public String getPicture(Integer itemId) throws Exception;

	public Item read(Item item) throws Exception;

	public int modify(Item item) throws Exception;

	public int remove(Item item) throws Exception;

}
