package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseDao;

@Service("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao{
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public PurchaseDaoImpl() {
		System.out.println(this.getClass());
	}
	
	public void addPurchase(Purchase purchase) throws Exception{
		sqlSession.insert("PurchaseMapper.addPurchase", purchase);
	} 
	
	public Purchase getPurchase(int tranNo) throws Exception{
		return sqlSession.selectOne("PurchaseMapper.getPurchase", tranNo);
	}
	

	public Purchase getPurchase2(int ProdNo) throws Exception{
		return sqlSession.selectOne("PurchaseMapper.getPurchase", ProdNo);
	}
	
	public List<Purchase> getPurchaseList(Search search, String buyer) throws Exception {
		
		System.out.println("search:::"+search);
		System.out.println("buyer:::"+buyer);
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		map.put("search", search);
		map.put("buyerId", buyer);
		
		List<Purchase> list = sqlSession.selectList("PurchaseMapper.getPurchaseList", map); 
	
		System.out.println("list size:::"+list.size());
		
		
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setBuyer((User)sqlSession.selectOne("UserMapper.getUser", list.get(i).getBuyer().getUserId()));
			list.get(i).setPurchaseProd((Product)sqlSession.selectOne("ProductMapper.getProduct", list.get(i).getPurchaseProd().getProdNo()));
		}
		

	return list;
	}
	
	/*
	public List<Purchase> getPurchaseList(Search search) throws Exception{
		return sqlSession.selectList("PurchaseMapper.getPurchaseList", search);
	}
	
	public List<Purchase> getSaleList(Search search) throws Exception{
		return sqlSession.selectList("PurchaseMapper.getSaleList", search);
	}
	*/
	public void updatePurchase(Purchase purchase) throws Exception{
		sqlSession.selectList("PurchaseMapper.updatePurchase", purchase);
	}
	
	public void updateTranCode(Purchase purchase) throws Exception{
		sqlSession.selectList("PurchaseMapper.updateTranCode", purchase);
	}
	
	public int getTotalCount(String buyer) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", buyer);
	}
	
}
