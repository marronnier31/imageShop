package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.domain.Item;
import com.project.domain.Member;
import com.project.domain.PayCoin;
import com.project.domain.UserItem;
import com.project.mapper.CoinMapper;
import com.project.mapper.UserItemMapper;

@Service
public class UserItemServiceImpl implements UserItemService {
	@Autowired
	private UserItemMapper mapper;

	@Autowired
	private CoinMapper coinMapper;

	@Override
	public int register(Member member, Item item) throws Exception {
		// 장바구니등록(구입한 사용자 정보, 구입한 물건 정보)
		UserItem userItem = new UserItem();
		userItem.setUserNo(member.getUserNo());
		userItem.setItemId(item.getItemId());
		// 구입한 물건에 대한 코인 지급
		PayCoin payCoin = new PayCoin();
		payCoin.setUserNo(member.getUserNo());
		payCoin.setItemId(item.getItemId());
		payCoin.setAmount(item.getPrice());
		coinMapper.pay(payCoin); // 코인 지급
		coinMapper.createPayHistory(payCoin); // 구매 내역 등록
		// 장바구니 생성
		return mapper.create(member, item);
	}

	@Override
	public List<UserItem> list(Member member) throws Exception {
		return mapper.list(member);
	}

	@Override
	public UserItem read(UserItem userItem) throws Exception {
		return mapper.read(userItem);
	}
}
