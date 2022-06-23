package view;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JPanel;

import main.Constraints;
import main.ErrorMessage;
import main.Resources;
import model.DimensionenList;
import model.Szenarien;
import model.Szenario;

import dao.AquaDaoException;


/**
 * Hauptpanel des Szenario-Editors
 * @author Konstantin Karzanov
 * 28.08.2008
 */
public class PanelSzenario extends JPanel implements ActionListener {

	private static final long serialVersionUID = 9081299795592612955L;
	private PanelSzenarioCombo szenPanel = null;
	private PanelSzenarioCombo aquaPanel = null;
	private PanelSzenarioSpinners tempPanel = null;
	private PanelSzenarioSpinners phPanel = null;
	private PanelSzenarioSpinners lichtPanel = null;
	private PanelSzenarioList fzPanel = null;
	private PanelSzenarioLebewesen lwPanel = null;
	private PanelDimensionen dimPanel = null;
	private int selectedSzenario = -1;
	private Szenarien szenModel = null;
	private DimensionenList aquaModel = null;

	public PanelSzenario() {
		super(new BorderLayout());
		add(getMainNorthPanel(), BorderLayout.NORTH);
		add(getMainCenterPanel(), BorderLayout.CENTER);
		setModels();
	}

	private JPanel getMainNorthPanel() {
		JPanel panel = new JPanel(new GridBagLayout());

		tempPanel = new PanelSzenarioSpinners("Szen.tempp_border","Szen.tempp_minLabel","Szen.tempp_maxLabel");
		phPanel = new PanelSzenarioSpinners("Szen.php_border","Szen.php_minLabel","Szen.php_maxLabel");
		lichtPanel = new PanelSzenarioSpinners("Szen.lichtp_border","Szen.lichtp_einLabel","Szen.lichtp_ausLabel");
		fzPanel = new PanelSzenarioList();

		panel.add(getSzenarioPanel(), new Constraints(0,0,6,1).setWeight(1,0).setAnchor(Constraints.NORTHWEST).setFill(Constraints.HORIZONTAL).setInsets(5, 5, 0, 5));
		panel.add(getAquariumPanel(), new Constraints(0,1,6,1).setWeight(1,0).setAnchor(Constraints.WEST).setFill(Constraints.HORIZONTAL));
		panel.add(tempPanel, new Constraints(0,2,2,2).setWeight(1,0).setAnchor(Constraints.WEST).setFill(Constraints.HORIZONTAL));
		panel.add(phPanel, new Constraints(2,2,2,2).setWeight(1,0).setAnchor(Constraints.WEST).setFill(Constraints.HORIZONTAL));
		panel.add(lichtPanel, new Constraints(4,2,2,2).setWeight(1,0).setAnchor(Constraints.WEST).setFill(Constraints.HORIZONTAL));
		panel.add(fzPanel, new Constraints(6,1,4,3).setWeight(1,0).setAnchor(Constraints.WEST).setFill(Constraints.BOTH));

		return panel;
	}
	
	private JPanel getMainCenterPanel() {
		lwPanel = new PanelSzenarioLebewesen();
		return lwPanel;
	}

	private JPanel getSzenarioPanel() {
		szenPanel = new PanelSzenarioCombo();

		szenPanel.setComboBox(true,"szenchange",this);
		szenPanel.addButton("Szen.newButton","szennew",this);
		szenPanel.addButton("Szen.saveButton","szensave",this);
		szenPanel.addButton("Szen.deleteButton","szendel",this);

		return szenPanel;
	}
	
	private JPanel getAquariumPanel() {
		aquaPanel = new PanelSzenarioCombo("Szen.aqp_border");

		aquaPanel.setComboBox(false,"aquachange",this);
		aquaPanel.addButton("Szen.aqp_newButton","aquanew",this);
		aquaPanel.addButton("Szen.aqp_editButton","aquaedit",this);
		aquaPanel.addButton("Szen.aqp_deleteButton","aquadel",this);

		return aquaPanel;
	}

	private void setModels() {
		try {
			szenModel = new Szenarien();
			szenPanel.setModel(szenModel);
			aquaModel = new DimensionenList();
			aquaPanel.setModel(aquaModel);
			lwPanel.setModels();

			if(szenModel.getSize()>0) {
				setSpinnerModels();
				setValues();
			}
			else
				setDefaults();
		} catch (AquaDaoException e) {
			ErrorMessage.show(ErrorMessage.CONNECTION);
			this.setVisible(false);
		}
	}
	
	private void setSpinnerModels() {
		tempPanel.setNumberModel(22.0,28.0,15.0,35.0,0.5);
		phPanel.setNumberModel(5.0,8.0,0.0,10.0,0.5);
		lichtPanel.setDateModel("05:00","21:00","HH:mm",Calendar.HOUR_OF_DAY);
		fzPanel.setSpinnerModel();
	}

	private void setValues() throws AquaDaoException {
		selectedSzenario = szenPanel.getSelectedIndex();
		Szenario s = (Szenario)szenModel.getElementAt(selectedSzenario);
		aquaPanel.setSelectedIndex(aquaModel.findId(s.getDim_id()));
		tempPanel.setFirstValue(s.getTempMin());
		tempPanel.setSecondValue(s.getTempMax());
		phPanel.setFirstValue(s.getPhMin());
		phPanel.setSecondValue(s.getPhMax());
		lichtPanel.setFirstValue(s.getLichtEin());
		lichtPanel.setSecondValue(s.getLichtAus());
		fzPanel.setListModel(s.getId());
		lwPanel.setValues(s.getId());
	}

	private void setDefaults() throws AquaDaoException {
		szenPanel.setSelectedItem(Resources.getString("Szen.default_name"));
		selectedSzenario = -1;
		if(aquaModel.getSize()==0)
			aquaModel.create();
		fzPanel.setListModel(0);
		setSpinnerModels();
		lwPanel.setDefaults();
	}

	private boolean showDimensionenDialog(Object model) {
		if(dimPanel==null)
			dimPanel=new PanelDimensionen();
		return (dimPanel.showDialog(this,model));
	}
	
	private void saveSzenario() throws AquaDaoException {
		String name=szenPanel.getSelectedItem().toString();
		if(name.length()>0 &&
				tempPanel.checkNumbers() && 
				phPanel.checkNumbers() && 
				fzPanel.checkList()) {
			int szen_id = szenModel.save(
					selectedSzenario, name,
					aquaModel.getId(aquaPanel.getSelectedIndex()),
					tempPanel.getFirstNumber(), tempPanel.getSecondNumber(),
					phPanel.getFirstNumber(), phPanel.getSecondNumber(),
					lichtPanel.getFirstDate(), lichtPanel.getSecondDate());
			fzPanel.saveList(szen_id);
			if (selectedSzenario==-1)
				selectedSzenario=szenModel.getSize()-1;
			szenPanel.setSelectedIndex(selectedSzenario);
		}
	}
	
	private void deleteSzenario() throws AquaDaoException {
		int szen_id = szenModel.delete(szenPanel.getSelectedIndex());
		fzPanel.deleteList(szen_id);
		if(szenModel.getSize()==0)
			setDefaults();
	}
	
	private void createAquarium() throws AquaDaoException {
		Object object = aquaModel.create();
		if(showDimensionenDialog(object))
			aquaModel.save(object);
		else
			aquaModel.removeElement(object);
	}

	private void editAquarium() throws AquaDaoException {
		Object object = aquaPanel.getSelectedItem();
		if(showDimensionenDialog(object))
			aquaModel.save(object);
	}
	
	private void deleteAquarium() throws AquaDaoException {
		int index = aquaPanel.getSelectedIndex();
		String name = szenModel.isInUse(aquaModel.getId(index));
		if(name==null)
			aquaModel.delete(index);
		else
			ErrorMessage.show(ErrorMessage.DIMENSION_IN_USE, name);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		try {
			if (cmd.equals("szenchange") && szenPanel.getSelectedIndex()!=-1)
				setValues();
			else if (cmd.equals("szennew"))
				setDefaults();
			else if (cmd.equals("szensave"))
				saveSzenario();
			else if (cmd.equals("szendel") && szenPanel.getSelectedIndex()!=-1)
				deleteSzenario();
			else if (cmd.equals("aquanew"))
				createAquarium();
			else if (cmd.equals("aquaedit"))
				editAquarium();
			else if (cmd.equals("aquadel") && aquaPanel.getSelectedIndex()!=-1)
				deleteAquarium();
		} catch (AquaDaoException ade) {
			ErrorMessage.show(ErrorMessage.CONNECTION);
		}
	}

}