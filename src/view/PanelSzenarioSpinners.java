package view;

import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

import main.ErrorMessage;
import main.Resources;


/**
 * Panel mit zwei Spinners
 * @author Konstantin Karzanov
 * 28.08.2008
 */
public class PanelSzenarioSpinners extends JPanel {

	private static final long serialVersionUID = 5460207612321317629L;
	private JSpinner firstSpinner = new JSpinner();
	private JSpinner secondSpinner = new JSpinner();

	public PanelSzenarioSpinners(String keyTitle, String keyLabel1, String keyLabel2) {
		super(new GridLayout(2,2,5,5));
		setBorder(BorderFactory.createTitledBorder(Resources.getString(keyTitle)));
		add(new JLabel(Resources.getString(keyLabel1),JLabel.RIGHT));
		add(firstSpinner);
		add(new JLabel(Resources.getString(keyLabel2),JLabel.RIGHT));
		add(secondSpinner);
	}

	/**
	 * Nummer-Model einstellen
	 * @param value1
	 * @param value2
	 * @param minimum
	 * @param maximum
	 * @param stepSize
	 */
	public void setNumberModel(double value1, double value2, double minimum, double maximum, double stepSize) {
		firstSpinner.setModel(new SpinnerNumberModel(value1,minimum,maximum,stepSize));
		secondSpinner.setModel(new SpinnerNumberModel(value2,minimum,maximum,stepSize));
	}
	
	/**
	 * Datum-Model einstellen
	 * @param date1
	 * @param date2
	 * @param pattern
	 * @param calendarField
	 */
	public void setDateModel(String date1, String date2, String pattern, int calendarField) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			firstSpinner.setModel(new SpinnerDateModel(sdf.parse(date1),null,null,calendarField));
			secondSpinner.setModel(new SpinnerDateModel(sdf.parse(date2),null,null,calendarField));
			firstSpinner.setEditor(new JSpinner.DateEditor(firstSpinner,pattern));
			secondSpinner.setEditor(new JSpinner.DateEditor(secondSpinner,pattern));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void setFirstValue(Object value) {
		firstSpinner.setValue(value);
	}

	public void setSecondValue(Object value) {
		secondSpinner.setValue(value);
	}

	public double getFirstNumber() {
		try {
			return Double.parseDouble(firstSpinner.getValue().toString());
		} catch (NumberFormatException nfe) {
			ErrorMessage.show(nfe.getMessage().substring(17), ErrorMessage.NOT_VALID);
			return 0;
		}
	}
	
	public double getSecondNumber() {
		try {
			return Double.parseDouble(secondSpinner.getValue().toString());
		} catch (NumberFormatException nfe) {
			ErrorMessage.show(nfe.getMessage().substring(17), ErrorMessage.NOT_VALID);
			return 0;
		}
	}

	public Date getFirstDate() {
		return (Date)firstSpinner.getValue();
	}

	public Date getSecondDate() {
		return (Date)secondSpinner.getValue();
	}

	/**
	 * Überprüfen ob Minimum Zahl ist kleiner als Maximum Zahl
	 * @return boolean
	 */
	public boolean checkNumbers() {
		if (getFirstNumber()<getSecondNumber())
			return true;
		else {
			ErrorMessage.show(ErrorMessage.MINIMUM_MAXIMUM);
			return false;
		}
			
	}

}
