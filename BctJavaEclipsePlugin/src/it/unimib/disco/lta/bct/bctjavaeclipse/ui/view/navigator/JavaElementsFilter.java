package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import util.componentsDeclaration.SystemElement;

public class JavaElementsFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		SystemElement component = null;
		TreePath treePath = null;

		if (parentElement instanceof TreePath) {
			treePath = (TreePath) parentElement;
			component = getParentComponent(treePath);

			if (component != null) { // we are filtering children elements of ComponentTreeData
				if (element instanceof IPackageFragment) { // filtering packages
					IPackageFragment packageFragment = (IPackageFragment) element;
					return component.acceptPackage(packageFragment.getElementName());
				} else if (element instanceof ICompilationUnit) { // filtering classes
					ICompilationUnit javaFile = (ICompilationUnit) element;
					String packageName = getParentPackage(treePath).getElementName();
					return component.acceptClass(packageName, parseJavaFileName(javaFile.getElementName()));
				} else if (element instanceof IMethod) { // filtering methods
					IMethod method = (IMethod) element;
					String packageName = getParentPackage(treePath).getElementName();
					String className = parseJavaFileName(getParentClass(treePath).getElementName());
					return component.acceptMethod(packageName, className, method.getElementName());
				}
			}
		}
		return true;
	}

	private String parseJavaFileName(String fileName) {
		return fileName.subSequence(0, fileName.lastIndexOf('.')).toString();
	}

	private SystemElement getParentComponent(TreePath path) {
		for (int i = path.getSegmentCount() - 1; i >= 0; i--) {
			Object segment = path.getSegment(i);
			if (segment instanceof ComponentTreeData) {
				return ((ComponentTreeData) segment).getComponent();
			}
		}
		return null;
	}

	private IPackageFragment getParentPackage(TreePath path) {
		for (int i = path.getSegmentCount() - 1; i >= 0; i--) {
			Object segment = path.getSegment(i);
			if (segment instanceof IPackageFragment) {
				return (IPackageFragment) segment;
			}
		}
		return null;
	}

	private ICompilationUnit getParentClass(TreePath path) {
		for (int i = path.getSegmentCount() - 1; i >= 0; i--) {
			Object segment = path.getSegment(i);
			if (segment instanceof ICompilationUnit) {
				return (ICompilationUnit) segment;
			}
		}
		return null;
	}
}
