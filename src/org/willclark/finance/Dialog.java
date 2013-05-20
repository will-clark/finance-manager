package org.willclark.finance;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.willclark.finance.utils.StringUtil;

public class Dialog {

	private static Logger log = Logger.getLogger(Dialog.class);
	
	private static Map<String, Dialog> map = new HashMap<String, Dialog>();
	
	static {
		map.put("default", new Dialog("en"));
		map.put("en", new Dialog("en"));
	}
	
	private Properties properties;
	
	private Dialog(String locale) {
    	try {   		
	    	properties = new Properties();
	    	properties.load(Dialog.class.getResourceAsStream("/dialog/"+locale+".properties"));	    	
    	}
    	catch (IOException e) {
    		log.error("Error loading email.properties file", e);
    	}		
	}
	
	public static Dialog getInstance() {
		return Dialog.getInstance("default");
	}
	
	public static Dialog getInstance(String locale) {
		Dialog dialog = map.get(locale);
		if (dialog == null) {
			throw new IllegalArgumentException("The specified locale: '"+locale+"' is not recogonized at this time");
		}
		return dialog;
	}
	
	public String get(String key) {
		return StringUtil.toString(properties.get(key));
	}

	// if called with key=welcome.email and keyValuePairs={"username","bob"}
	// this method will replace any text in the welcome.email message matching %username with the value bob
	public String getSubVars(String key, String... keyValuePairs) {
		if (keyValuePairs == null || (keyValuePairs.length % 2 != 0)) throw new IllegalArgumentException("Wrong nubmer of arguments: For every key there must be a value to subsitute with");
		String temp = get(key);
		for(int i=0; i < keyValuePairs.length; i=i+2) {
			temp = temp.replaceAll("%"+keyValuePairs[i], keyValuePairs[i+1]);
		}
		return temp;
	}
	
}
