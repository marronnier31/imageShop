package com.project.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Item {
	private Integer itemId; 
	private String itemName; 
	private Integer price; 
	private String description; 
	//파일을 받을 때는 반드시 multipartFile로 받아야한다.
	private MultipartFile picture; 
	private String pictureUrl; 
	//파일을 받을 때는 반드시 multipartFile로 받아야한다.
	private MultipartFile preview; 
	private String previewUrl;
}
