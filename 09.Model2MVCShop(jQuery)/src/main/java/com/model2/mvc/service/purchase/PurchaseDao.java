package com.model2.mvc.service.purchase;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;


public interface PurchaseDao {
	public void addPurchase(Purchase purchaseVO) throws Exception;
	
	public Purchase getPurchase(int tranNo) throws Exception;
	
	public Purchase getPurchase2(int ProdNo) throws Exception;
	
	public List<Purchase> getPurchaseList(Search search, String buyer) throws Exception;
	
	//public List<Purchase> getSaleList(Search search) throws Exception;
	
	public void updatePurchase(Purchase purchaseVO) throws Exception;
	
	public void updateTranCode(Purchase purchase) throws Exception;
	
	public int getTotalCount(String buyer) throws Exception ;
}
