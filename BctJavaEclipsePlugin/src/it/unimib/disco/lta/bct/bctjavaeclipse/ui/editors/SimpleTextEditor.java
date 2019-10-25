package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors;

import org.eclipse.ui.editors.text.TextEditor;

public class SimpleTextEditor extends TextEditor {

	private ColorManager colorManager;

	public SimpleTextEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());
	}
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
