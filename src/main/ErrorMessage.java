package main;

import javax.swing.JOptionPane;

public class ErrorMessage {
	
	public static final int ERROR = 0;
	public static final int CONNECTION = 1;
	public static final int NOT_VALID = 2;
	public static final int DIMENSION_IN_USE = 3;
	public static final int NO_FEED = 4;
	public static final int MINIMUM_MAXIMUM = 5;
	
	public static void show(int errorTyp) {
		show(getString(errorTyp));
	}
	
	public static void show(String value, int errorTyp) {
		show(value+" "+getString(errorTyp));
	}

	public static void show(int errorTyp, String value) {
		show(getString(errorTyp)+" "+value);
	}

	public static void show(Object message) {
		JOptionPane.showMessageDialog(null, message, 
				getString(ERROR), JOptionPane.ERROR_MESSAGE);
	}

	private static String getString(int typ) {
		if(typ==0)
			return Resources.getString("Error.title");
		else if(typ==1)
			return Resources.getString("Error.conn");
		else if(typ==2)
			return Resources.getString("Error.edit_wrong");
		else if(typ==3)
			return Resources.getString("Error.dim_in_use");
		else if(typ==4)
			return Resources.getString("Error.list_null");
		else if(typ==5)
			return Resources.getString("Error.min_max");
		else
			return null;
	}

}
