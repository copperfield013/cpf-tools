package cn.sowell.cpftools.main.controller.iris;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/iris")
public class IrisController {
	
	@RequestMapping("/main")
	public String main(){
		return "/iris/main.jsp";
	}
	
	
	@RequestMapping("/demo")
	public String demo(){
		return "/iris/demo.jsp";
	}
	
	@RequestMapping("/iris")
	public String iris(){
		return "/iris/iris.jsp";
	}
	
}
