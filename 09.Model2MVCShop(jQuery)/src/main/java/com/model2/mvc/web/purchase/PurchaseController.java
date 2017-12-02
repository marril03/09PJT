package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	
	public PurchaseController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
		
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping(value="/purchase/addPurchase",method=RequestMethod.GET)
	public String addPurchase(@RequestParam("prodNo") int prodNo,Model model,HttpSession session) throws Exception {
		System.out.println("/purchase/addPurchaseView : GET");
		User user = (User)session.getAttribute("user");
		Product product = productService.getProduct(prodNo);
		
		model.addAttribute("product", product);
		model.addAttribute("user", user);
		
		System.out.println("user:::"+user);
		System.out.println("product:::"+product);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
	
	@RequestMapping(value="/purchase/addPurchase",method=RequestMethod.POST)
	public String addPurchase(@ModelAttribute("pruchase") Purchase purchase,
							  @RequestParam("prodNo") int prodNo,
							  HttpSession session,
							  Model model ) throws Exception {

		System.out.println("/purchase/addPurchase : POST");
		User user = (User)session.getAttribute("user");
		Product product = productService.getProduct(prodNo);

		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		purchase.setTranCode("0");
		
		//Business Logic
		System.out.println("purchase:::"+purchase);
		System.out.println("product:::"+product);
		System.out.println("user:::"+user);
		
		model.addAttribute("purchase", purchase);
		purchaseService.addPurchase(purchase);
		
		return "forward:/purchase/addPurchase.jsp";
	}
	
	@RequestMapping(value="/purchase/listPurchase")
	public String listPurchase( @ModelAttribute("search") Search search , 
								Model model , 
								HttpSession session,
								HttpServletRequest request
								) throws Exception{
		
		System.out.println("/purchase/listPurchase : POST");
		
		if(search.getCurrentPage() == 0 ){
			search.setCurrentPage(1);
		}
		System.out.println("search:::"+search);
		search.setPageSize(pageSize);
		User user = (User)session.getAttribute("user");
		
		// Business logic 수행
		Map<String , Object> map = purchaseService.getPurchaseList(search, user.getUserId());
		System.out.println("search2:::"+search);
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		
		System.out.println("resultPage:::::"+resultPage);
		
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/purchase/listPurchase.jsp";
	}
	
	@RequestMapping(value="/purchase/getPurchase",method=RequestMethod.GET)
	public String getProduct( @RequestParam("tranNo") int tranNo , Model model ) throws Exception {
		
		System.out.println("/purchase/getPurchase : GET");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		// Model 과 View 연결
		model.addAttribute("purchase", purchase);
		return "forward:/purchase/getPurchase.jsp";
	}
	
	@RequestMapping(value="/purchase/updatePurchase",method=RequestMethod.GET)
	public String updatePurchase(@RequestParam("tranNo") int tranNo , Model model ) throws Exception {
		
		System.out.println("/purchase/getPurchase : GET");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		// Model 과 View 연결
		model.addAttribute("purchase", purchase);
		return "forward:/purchase/updatePurchaseView.jsp";
	}
	
	@RequestMapping(value="/purchase/updatePurchase",method=RequestMethod.POST)
	public String updatePurchase(@ModelAttribute("purchase") Purchase purchase,
								 @RequestParam("tranNo") int tranNo, 
								 Model model ) throws Exception {
		
		System.out.println("/purchase/getPurchase : POST");
		//Business Logic
		
		purchaseService.updatePurchase(purchase);
		purchase=purchaseService.getPurchase(tranNo);
		model.addAttribute("purchase", purchase);

		return "forward:/purchase/updatePurchase.jsp";
	}
	
	@RequestMapping(value="/purchase/updateTranCode",method=RequestMethod.GET)
	public String updateTranCode(@RequestParam("tranNo") int tranNo,
								 @RequestParam("TranCode") String TranCode, 
								 Model model ) throws Exception {
		
		System.out.println("/purchase/updateTranCode : POST");
		//Business Logic
		System.out.println("tranNo::"+tranNo+"   proTrnaCode::"+TranCode);
		Purchase purchase = purchaseService.getPurchase(tranNo);
		purchase.setTranCode(TranCode);
		
		purchaseService.updateTranCode(purchase);
		
		model.addAttribute("purchase", purchase);

		return "forward:/purchase/listPurchase";
	}
	
	@RequestMapping(value="/purchase/updateTranCodeByProd",method=RequestMethod.GET)
	public String updateTranCodeByProd(@RequestParam("prodNo") int prodNo,
									   @RequestParam("proTranCode") String proTranCode, 
								 	   Model model ) throws Exception {
		
		System.out.println("/updateTranCodeByProd : POST");
		//Business Logic
		System.out.println("prodNo::"+prodNo+"   proTrnaCode::"+proTranCode);
		Purchase purchase = purchaseService.getPurchase2(prodNo);
		purchase.setTranCode(proTranCode);
		purchaseService.updateTranCode(purchase);
		
		model.addAttribute("purchase", purchase);

		return "forward:/product/listProduct?menu=manage";
	}
}
