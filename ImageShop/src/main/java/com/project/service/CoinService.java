package com.project.service;

import java.util.List;

import com.project.domain.ChargeCoin;

public interface CoinService {

	public int charge(ChargeCoin chargeCoin) throws Exception;

	public List<ChargeCoin> list(int userNo) throws Exception;

}
