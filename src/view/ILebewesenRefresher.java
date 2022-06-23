package view;

import model.Lebewesen;

public interface ILebewesenRefresher {
	
	public void LebenwesenSelected( Lebewesen curSel, PanelSzenarioTrees parent );
	public void LebenwesenDeselected( Lebewesen curSel, PanelSzenarioTrees parent );
	public void refreshBothSides();
	public void deleteButtonPressed(PanelSzenarioTrees parent);
	public void addButtonPressed(PanelSzenarioTrees parent);

}
