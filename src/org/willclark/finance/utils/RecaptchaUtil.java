package org.willclark.finance.utils;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

public class RecaptchaUtil {

	private static Logger log = Logger.getLogger(RecaptchaUtil.class);
	
	private static String publicKey;
	private static String privateKey;
		
	static {
    	try {   		
	    	Properties properties = new Properties();
	    	properties.load(RecaptchaUtil.class.getResourceAsStream("/recaptcha.properties"));	    	
	    	publicKey = properties.getProperty("recaptcha.public");
	    	privateKey = properties.getProperty("recaptcha.private");	    	
    	}
    	catch (IOException e) {
    		log.error("Error loading recaptcha.properties file", e);
    	}
	}
	
	public static String generateWidget() {
		return ReCaptchaFactory.newReCaptcha(publicKey, privateKey, false).createRecaptchaHtml(null, null);
	}
	
	public static boolean hasValidSolution(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey(privateKey);

        String challenge = request.getParameter("recaptcha_challenge_field");
        String uresponse = request.getParameter("recaptcha_response_field");
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);
        
        return reCaptchaResponse.isValid();
	}
	
}
