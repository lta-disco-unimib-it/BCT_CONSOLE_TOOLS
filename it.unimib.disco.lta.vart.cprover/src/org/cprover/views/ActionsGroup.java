package org.cprover.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;


/**
 * Class: ActionsGroup
 * 
 * Purpose: Group actions applied on views of CProver plugin.
 */

public class ActionsGroup extends Action implements IMenuCreator {
	private Menu menu;
	private Action[] actions;

	public ActionsGroup(String title, Action[] actions) {
		this.setText(title);
		this.setToolTipText(title);
		this.setMenuCreator(this);
		this.actions = actions;
	}

	public void dispose() {
		if (menu != null)
			menu.dispose();
	}

	public Menu getMenu(Control parent) {
		this.dispose();
		
		this.menu = new Menu(parent);
		for (Action a: actions)
			new ActionContributionItem(a).fill(menu, -1);
		return this.menu;
	}

	public Menu getMenu(Menu parent) {
		return null;
	}
}
