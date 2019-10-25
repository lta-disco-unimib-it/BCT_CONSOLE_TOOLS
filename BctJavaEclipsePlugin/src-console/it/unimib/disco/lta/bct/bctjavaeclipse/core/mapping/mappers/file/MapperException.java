package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file;

import conf.BctSettingsException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;

public class MapperException extends Exception {

	public MapperException(String string) {
		super(string);
	}
	
	public MapperException(String msg,Throwable t) {
		super(msg,t);
	}

	public MapperException(Exception e) {
		super(e);
	}


}
