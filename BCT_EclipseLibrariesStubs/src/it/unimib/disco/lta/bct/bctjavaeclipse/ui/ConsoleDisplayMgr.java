package it.unimib.disco.lta.bct.bctjavaeclipse.ui;

public class ConsoleDisplayMgr {
	
	private static final ConsoleDisplayMgr fDefault = new ConsoleDisplayMgr();

	private ConsoleDisplayMgr(){} 
	
	public void printfInfo(String str){
		System.out.println();
	}

	public static ConsoleDisplayMgr getDefault() {
		return fDefault;
	}	
	
	public void printError(String msg){
		if( msg == null ) return;
		System.err.println(msg);
	}
	
	public void printInfo(String msg) {		
		if( msg == null ) return;
		
		System.out.println(msg);
			
	}
	
	public void clear() {	
		
	}
	
}
