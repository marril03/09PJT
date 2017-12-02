package com.model2.mvc.web.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;



//==> 회원관리 Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductController(){
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
	
	
	//@RequestMapping("/addProductView.do")
	@RequestMapping( value="addProduct", method=RequestMethod.GET )
	public String addProduct() throws Exception {
		System.out.println("/product/addProdcut : GET");
		return "redirect:/product/addProductView.jsp";
	}
	
	//@RequestMapping("/addProduct.do")
	@RequestMapping( value="addProduct", method=RequestMethod.POST )
	public String addProduct(@ModelAttribute("product") Product product ,
							 @RequestParam("file") MultipartFile file,
							 Model model,
							 HttpServletRequest req
							 ) throws Exception {

		System.out.println("/product/addProdcut : POST");
		String temDir = "C:\\Users\\301-3\\git\\09PJT\\09.Model2MVCShop(jQuery)\\WebContent\\images\\uploadFiles\\";
		File f = new File(temDir+file.getOriginalFilename());
		product.setFileName(file.getOriginalFilename());
		file.transferTo(f);
		
		productService.addProduct(product);
		model.addAttribute("product", product);	
		
		return "forward:/product/addProduct.jsp";
	}
	
	/*
	@RequestMapping( value="addProduct", method=RequestMethod.POST )
	public String addProduct(@ModelAttribute("product") Product product , Model model,HttpServletRequest req ) throws Exception {

		System.out.println("/product/addProdcut : POST");
		//Business Logic
		if(FileUpload.isMultipartContent(req)) {
			String temDir = "C:\\Users\\301-3\\git\\07PJT\\07.Model2MVCShop(URI,pattern)\\WebContent\\images\\uploadFiles\\";
			
			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			fileUpload.setSizeMax(1024*1024*10);
			fileUpload.setSizeThreshold(1024*100);
			
			System.out.println("fileUpload.getSizeMax():::"+fileUpload.getSizeMax());
			System.out.println("req.getContentLength():::"+req.getContentLength());
			
			if(req.getContentLength() < fileUpload.getSizeMax()) {
				
				StringTokenizer token = null;
				List fileItemList = fileUpload.parseRequest(req);
				int Size = fileItemList.size();
				
				for(int i = 0; i < Size; i++) {
					FileItem fileItem = (FileItem)fileItemList.get(i);
				
					if(fileItem.isFormField()) {
						if(fileItem.getFieldName().equals("manuDate")) {
							token = new StringTokenizer(fileItem.getString("euc-kr"),"-");
							String manuDate = token.nextToken()+token.nextToken()+token.nextToken();
							product.setManuDate(manuDate);
							System.out.println("manuDate::::"+manuDate);
						}
						else if(fileItem.getFieldName().equals("prodName"))
							product.setProdName(fileItem.getString("euc-kr"));
						else if(fileItem.getFieldName().equals("prodDetail"))
							product.setProdDetail(fileItem.getString("euc-kr"));
						else if(fileItem.getFieldName().equals("price"))
							product.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
		
					}else {
						if(fileItem.getSize() > 0) {
							int idx = fileItem.getName().lastIndexOf("\\");
						
							if(idx==-1) {
								idx = fileItem.getName().lastIndexOf("/");
							}
							String fileName=fileItem.getName().substring(idx+1);
							product.setFileName(fileName);
							try {
								File uploadedFile = new File(temDir,fileName);
								fileItem.write(uploadedFile);
							}catch(IOException e) {
								System.out.println(e);
							}
						}else {
							product.setFileName("../../images/empty.GIF");
						}
					}
				}
				System.out.println("product::::"+product.getFileName());
				productService.addProduct(product);
				model.addAttribute("product", product);
			}else {
				int overSize = (req.getContentLength()/1000000);
				System.out.println("<script>alert('파일의 크기는 1MB까지 입니다. 올리신 파일 용량은 "+overSize+"MB 입니다.');");
				System.out.println("history.back(); </script>");
			}
		}else {
			System.out.println("인코딩 타입이 multipart/form-data가 아닙니다..");
		}
		return "forward:/product/addProduct.jsp";
	}
	*/
	
	
	//@RequestMapping("/getProduct.do")
	@RequestMapping( value="getProduct", method=RequestMethod.POST )
	public String getProduct( @RequestParam("prodNo") int prodNo , Model model ) throws Exception {
		
		System.out.println("/product/getProduct : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		
		return "forward:/product/getProduct.jsp";
	}	
	
	//@RequestMapping("/updateProduct.do")
	@RequestMapping( value="updateProduct", method=RequestMethod.POST )
	public String updateProduct( @ModelAttribute("product") Product product, Model model) throws Exception{

		System.out.println("/product/updateProduct : POST");
		//Business Logic
		productService.updateProduct(product);
		String menu="ok";
		
		return "redirect:/product/getProduct?prodNo="+product.getProdNo()+"&menu="+menu;
	}
	
	//@RequestMapping("/updateProductView.do")
	@RequestMapping( value="updateProduct", method=RequestMethod.GET )
	public String updateProduct( @RequestParam("prodNo") int prodNo, Model model) throws Exception{

		System.out.println("/product/updateProductView : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product",product);

		return "forward:/product/updateProduct.jsp";
	}
		
	@RequestMapping(value="listProduct")
	public String listProduct( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/product/listProduct");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
}