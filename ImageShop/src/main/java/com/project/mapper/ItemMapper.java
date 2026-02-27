package com.project.mapper;

import java.util.List;

import com.project.domain.Item;

public interface ItemMapper {
	public int create(Item item) throws Exception;

	public List<Item> list() throws Exception;

	public Item read(Item item) throws Exception;

	public int update(Item item) throws Exception;

	public int delete(Item item) throws Exception;

	public String getPreview(Integer itemId) throws Exception;

	public String getPicture(Integer itemId) throws Exception;

}
