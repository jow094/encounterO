package com.encounterO.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * JSFunction : 자바스크립트를 이용하여 동작할 내용들을 객체화
 * 				alert , history.back , location.href 등등
 */



public class JSFunction {

	public static void alert(HttpServletResponse response,String msg){
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out = response.getWriter();
			out.println("<script>");
			out.println("alert('"+msg+"');");
			out.println("</script>");
			out.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	// alert + back 동작
	public static void alertAndBack(HttpServletResponse response,String msg){
		try {
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out = response.getWriter();
			out.println("<script>");
			out.println("alert('"+msg+"');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	// alert + location 동작
	
	public static void alertAndLocation(HttpServletResponse response,String msg,String location) {
		
			try {
				response.setContentType("text/html; charset=UTF-8");
				
				PrintWriter out = response.getWriter();
				out = response.getWriter();
				String code = "";
				 code += "<script>";
				 code += "alert(' "+msg+" ');";
				 code += "location.href='"+location+"';";
				 code += "</script>";
				out.print(code);
				out.close();
				
			} catch (Exception e) {
				e.printStackTrace();	
			}
		
		
	}
	
}
