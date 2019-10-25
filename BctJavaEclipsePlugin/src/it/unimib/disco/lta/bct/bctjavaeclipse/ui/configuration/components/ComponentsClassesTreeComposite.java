package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components;


import it.unimib.disco.lta.bct.bctjavaeclipse.ui.BctJavaEclipsePlugin;

import java.net.URL;
import java.util.Collection;

import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import util.componentsDeclaration.Component;

public class ComponentsClassesTreeComposite extends Composite {
	private IJavaProject project;
	private Tree tree;
	private Image componentImage = null;
	
	public ComponentsClassesTreeComposite(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new org.eclipse.swt.layout.GridLayout());
		this.setSize(400,200);
	    tree = new Tree(this, SWT.BORDER);
	    tree.setLayoutData(new GridData(GridData.FILL_BOTH));

	    URL url = BctJavaEclipsePlugin.getDefault().getBundle().getEntry("/icons/component.gif");
	        
	    ImageDescriptor image = ImageDescriptor.createFromURL(url);
	    componentImage  = image.createImage();
	}

	public IJavaProject getProject() {
		return project;
	}

	public void setProject(IJavaProject project) {
		this.project = project;
	}
	
	public void showComponents(Collection<Component> components){
		if ( project == null ){
			return;
		}
		tree.removeAll();
		
	    try {
	    	for ( Component component : components ){
	    	    TreeItem componnetItem = new TreeItem(tree, SWT.NULL);
	    	    componnetItem.setText(component.getName());
	    	    
	    		componnetItem.setImage(componentImage);
	    		for ( IJavaElement e : project.getChildren() ){
	    			if ( e instanceof IPackageFragmentRoot ){
	    				//processRoot((IPackageFragmentRoot) e,componnetItem,component);
	    			}
	    		}
	    	}
		} catch (JavaModelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tree.redraw();
		tree.update();
		this.update();
	}

	private void processRoot(IPackageFragmentRoot root, TreeItem componnetItem, Component component) {
		
		TreeItem rootItem = new TreeItem(componnetItem, SWT.NULL);
		rootItem.setText(root.getElementName());
		try {
			for ( IJavaElement e : root.getChildren() ){
				if ( e instanceof IPackageFragment ){
					processPackage((IPackageFragment) e,rootItem,component);
				}
			}
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean processPackage(IPackageFragment packageFragment, TreeItem rootItem,
			Component component) {
		TreeItem packItem = new TreeItem(rootItem, SWT.NULL);
		packItem.setText(packageFragment.getElementName());
		boolean found = false;
		try {
			for ( IJavaElement e : packageFragment.getChildren() ){
				if ( e instanceof IPackageFragment ){
					found |= processPackage((IPackageFragment) e,packItem,component);
				} else if ( e instanceof ICompilationUnit ){
					ICompilationUnit cu = (ICompilationUnit) e; 
					for ( IType t : cu.getAllTypes()){
						String className = t.getTypeQualifiedName();
						String fullName = t.getFullyQualifiedName();
						String packageName= fullName.substring(0, fullName.length()-className.length());
						System.out.println(packageName+" "+className);
						try{
							if ( component.acceptClass(packageName, className) ){
								TreeItem classItem = new TreeItem(packItem, SWT.NULL);
								classItem.setText(className);
								found = true;
							}
						}catch (Exception ex){
							ex.printStackTrace();
						}
					}
				} else if ( e instanceof IClassFile  ){
					//TODO: BynaryType are the children, need to manage them in a permormant way 
					
//					IClassFile f = (IClassFile) e;
//					for ( IJavaElement c : f.getChildren() ){
//						System.out.println(c.getClass().getName());
//					}
				}
			}
			

		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		packItem.setExpanded(found);
		return found;
	}

	public void clean() {
		tree.removeAll();
	}

}
