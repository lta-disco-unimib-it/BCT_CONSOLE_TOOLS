package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointRaw;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;

public class FileProgramPointRaw extends ProgramPointRaw {

	private long beginOffset;

	public FileProgramPointRaw(String id, Method method, Type type, long beginOffset) {
		super(id, method, type);
		this.beginOffset =  beginOffset;
	}

	public long getBeginOffset() {
		return beginOffset;
	}

}
