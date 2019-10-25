package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;

import java.util.ArrayList;
import java.util.List;

public class MethodCallItem {
	private MethodCallPoint methodCall;
	private MethodCallPoint methodCallExit;
	
	public MethodCallPoint getMethodCallExit() {
		return methodCallExit;
	}

	public void setMethodCallExit(MethodCallPoint methodCallExit) {
		this.methodCallExit = methodCallExit;
	}

	private List<MethodCallItem> children;

	public MethodCallItem(MethodCallPoint methodCallPoint) {
		methodCall = methodCallPoint;
		children = new ArrayList<MethodCallItem>();
	}

	public MethodCallPoint getMethodCall() {
		return methodCall;
	}

	public void setMethodCall(MethodCallPoint methodCall) {
		this.methodCall = methodCall;
	}

	public List<MethodCallItem> getChildren() {
		return children;
	}

	public void addChild(MethodCallItem methodCall) {
		children.add(methodCall);
	}


}
