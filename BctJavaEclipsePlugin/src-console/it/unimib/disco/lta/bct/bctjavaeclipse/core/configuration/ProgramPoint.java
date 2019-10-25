package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

public class ProgramPoint {

	private String absolutePath;
	public String getAbsolutePath() {
		return absolutePath;
	}

	public int getLineNumber() {
		return lineNumber;
	}


	private int lineNumber;

	
	public void setAbsolutePath(String pathRelativeToProject) {
		this.absolutePath = pathRelativeToProject;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}


	public ProgramPoint(){
		
	}
	
	public ProgramPoint(String absolutePath, int lineNumber) {
		this.absolutePath = absolutePath;
		this.lineNumber = lineNumber;
	}
	
	
}
