package com.joberstein.numbers.servlet;

import java.io.IOException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joberstein.numbers.NumberConstants;
import com.joberstein.numbers.NumberPrinter;

public class NumberValidator extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	        
	        NumberPrinter np = new NumberPrinter();
	        String integerInput = request.getParameter("integer");
	    	String decimalInput = request.getParameter("decimal");
	    	String integer = np.isValidInput(integerInput, NumberPrinter.INTEGER_DIGIT_LIMIT);
	    	String decimal = np.isValidInput(decimalInput, NumberPrinter.DECIMAL_DIGIT_LIMIT);
	    	
	    	if (integer.isEmpty() && decimal.isEmpty()) {
	    		request.setAttribute("error", "Please enter at least either an integer or decimal value.");
	    		request.getRequestDispatcher("index.jsp").forward(request, response);
	    	}
	    	else {
	    		String inputAttr, 
	    		integerAttr, 
    			decimalAttr;
	    		
	    		if (integer.equals("NaN") || decimal.equals("NaN")) {
		        	inputAttr = integerInput + "." + decimalInput;
		        	integerAttr = integer;
		    		decimalAttr = "." + decimal;
		    		request.setAttribute("error", "ERROR: <br>Your number cannot be converted"
		    				+ " to English because it is invalid.");
	    		}
	    		else {
		    		inputAttr = np.formatInteger(integer) + "." + np.formatDecimal(decimal);
		    		integerAttr = np.formatInteger(integer);
		    		decimalAttr = "." + np.formatDecimal(decimal);
					request.setAttribute("result", 
							formResult(np.buildInteger(integer), np.buildDecimal(decimal)));
	    		}
	    		
	    		request.setAttribute("input", inputAttr);
	    		request.setAttribute("integer", integerAttr);
	    		request.setAttribute("decimal", decimalAttr);
	    		request.getRequestDispatcher("main.jsp").forward(request, response);
	    	}
	}
    
	private String formResult(String integerResult, String decimalResult) {
		String result = "";
		
		if (decimalResult.isEmpty()) {
			result = integerResult;
		}
		else if (integerResult.isEmpty() || integerResult.equals("Zero")) {
			result = decimalResult;
		}
		else {
			result = integerResult + ", and <br><br>" + decimalResult;
		}
		
		return result;
	}
    
    @Override
	public void destroy() {
        //add code to release any resource
    }

}
