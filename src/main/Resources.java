package main;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Resources {
	
	public static final String PROPERTIES_FILE = "resources.aquagui";
	private static ResourceBundle rb = null;

	public static void setResourceBundle(Locale locale) {
		try {
			rb = ResourceBundle.getBundle(PROPERTIES_FILE, locale);
		} catch (MissingResourceException mre) {
			mre.printStackTrace();
		}
	}
	
	public static String getString(String key) {
		try { //if(rb!=null && rb.containsKey(key))
			return rb.getString(key);
		} catch (NullPointerException npe) {
		} catch (MissingResourceException mre) {
			mre.printStackTrace();
		}
		return null;
	}

	public static char getMnemonic(String key) {
		try { //if(rb!=null && rb.containsKey(key))
			return rb.getString(key).charAt(0);
		} catch (NullPointerException npe) {
		} catch (MissingResourceException mre) {
			mre.printStackTrace();
		}
		return 0;
	}
}
