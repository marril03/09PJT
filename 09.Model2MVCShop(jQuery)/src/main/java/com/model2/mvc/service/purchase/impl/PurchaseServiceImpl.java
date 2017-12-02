package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService{
	
	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDao;
	
	public void setPurchaseDao(PurchaseDao purchaseDao) {
		this.purchaseDao = purchaseDao;
	}
	
	public PurchaseServiceImpl() {
		System.out.println(this.getClass());
	}

	public void addPurchase(Purchase purchase) throws Exception{
		purchaseDao.addPurchase(purchase);
	} 
	
	public Purchase getPurchase(int tranNo) throws Exception{
		return purchaseDao.getPurchase(tranNo);
	}
	

	public Purchase getPurchase2(int ProdNo) throws Exception{
		return purchaseDao.getPurchase2(ProdNo);
	}
	
	
	public Map<String,Object> getPurchaseList(Search search, String buyer) throws Exception{
		
		
		System.out.println("list:::::::들어가기전");	
		int totalCount = purchaseDao.getTotalCount(buyer);
		System.out.println("totalCount:::::"+totalCount);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("buyerId", buyer);
		map.put("list", purchaseDao.getPurchaseList(search, buyer));
		map.put("totalCount", purchaseDao.getTotalCount(buyer));
		
		return map;
	}
	
	/*
	public Map<String,Object> getSaleList(Search search) throws Exception{
		return purchaseDao.getSaleList(search);
	}
	*/
	
	public void updatePurchase(Purchase purchase) throws Exception{
		purchaseDao.updatePurchase(purchase);
	}
	
	public void updateTranCode(Purchase purchase) throws Exception{
		purchaseDao.updateTranCode(purchase);
	}
}
