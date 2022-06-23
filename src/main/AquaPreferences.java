package main;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Locale;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.UIManager;



public class AquaPreferences {
	
	private Preferences prefs;

	public AquaPreferences(AquaEditorApplication app) {
		prefs=Preferences.userNodeForPackage(app.getClass());
	}
	
	public Locale getLocale() {
		String language = prefs.get(LANGUAGE_KEY, LANGUAGE);
		String country = prefs.get(COUNTRY_KEY, COUNTRY);
		String variant = prefs.get(VARIANT_KEY, VARIANT);
		return new Locale(language,country,variant);
	}
	
	public void setLocale(Locale locale) {
		prefs.put(LANGUAGE_KEY, locale.getLanguage());
		prefs.put(COUNTRY_KEY, locale.getCountry());
		prefs.put(VARIANT_KEY, locale.getVariant());
	}
	
	public int getWindowState() {
		return prefs.getInt(WINDOW_STATE_KEY,DEFAULT_WINDOW_STATE);
	}

	public void setWindowState(int state) {
		prefs.putInt(WINDOW_STATE_KEY, state);
	}
	
	public Dimension getWindowSize() {
		int w = prefs.getInt(WINDOW_WIDTH_KEY,DEFAULT_WINDOW_WIDTH);
		int h = prefs.getInt(WINDOW_HEIGHT_KEY,DEFAULT_WINDOW_HEIGHT);
		return new Dimension(w,h);
	}

	public void setWindowSize(Dimension size) {
		prefs.putInt(WINDOW_WIDTH_KEY,size.width);
		prefs.putInt(WINDOW_HEIGHT_KEY,size.height);
	}

	public Point getWindowPoint() {
		int x = prefs.getInt(WINDOW_X_KEY,DEFAULT_WINDOW_X);
		int y = prefs.getInt(WINDOW_Y_KEY,DEFAULT_WINDOW_Y);
		return new Point(x,y);
	}

	public void setWindowPoint(Point location) {
		prefs.putInt(WINDOW_X_KEY,location.x);
		prefs.putInt(WINDOW_Y_KEY,location.y);
	}

	public String getLafClassName() {
		return prefs.get(LAF_KEY, DEFAULT_LAF);
	}
	
	public void setLafClassName(String laf) {
		prefs.put(LAF_KEY, laf);
	}
	
	public static final String LANGUAGE = Locale.getDefault().getLanguage();
	public static final String COUNTRY = Locale.getDefault().getCountry();
	public static final String VARIANT = Locale.getDefault().getVariant();
	public static final int DEFAULT_WINDOW_STATE = JFrame.NORMAL;
	public static final GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	public static final Rectangle r = gc.getBounds();
	public static final int DEFAULT_WINDOW_WIDTH = 700;
	public static final int DEFAULT_WINDOW_HEIGHT = 600;
	public static final int DEFAULT_WINDOW_X = r.x + r.width/2 - DEFAULT_WINDOW_WIDTH/2;
	public static final int DEFAULT_WINDOW_Y = r.y + r.height/2 - DEFAULT_WINDOW_HEIGHT/2;
	public static final String DEFAULT_LAF = UIManager.getSystemLookAndFeelClassName();
	public static final String LANGUAGE_KEY = "L";
	public static final String COUNTRY_KEY = "C";
	public static final String VARIANT_KEY = "V";
	public static final String WINDOW_STATE_KEY = "S";
	public static final String WINDOW_WIDTH_KEY = "W";
	public static final String WINDOW_HEIGHT_KEY = "H";
	public static final String WINDOW_X_KEY = "X";
	public static final String WINDOW_Y_KEY = "Y";
	public static final String LAF_KEY = "LAF";
	
}
