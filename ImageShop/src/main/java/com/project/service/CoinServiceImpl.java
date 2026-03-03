package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.ChargeCoin;
import com.project.mapper.CoinMapper;

@Service
public class CoinServiceImpl implements CoinService{
	@Autowired
	private CoinMapper mapper;

	@Override
	@Transactional
	public int charge(ChargeCoin chargeCoin) throws Exception {
		int count = mapper.create(chargeCoin);
		if(count !=0) return mapper.charge(chargeCoin);
		return 0;
	}

	@Override
	public List<ChargeCoin> list(int userNo) throws Exception {
		return mapper.list(userNo);
	}
}
