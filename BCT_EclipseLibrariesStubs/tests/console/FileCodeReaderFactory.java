package console;

import java.io.File;
import java.io.IOException;

import org.eclipse.cdt.core.dom.ICodeReaderFactory;
import org.eclipse.cdt.core.index.IIndexFileLocation;
import org.eclipse.cdt.core.parser.CodeReader;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.ICodeReaderCache;
import org.eclipse.cdt.internal.core.dom.AbstractCodeReaderFactory;
import org.eclipse.cdt.internal.core.parser.scanner.InternalFileContent;
import org.eclipse.cdt.internal.core.parser.scanner.InternalFileContentProvider;
import org.eclipse.core.runtime.CoreException;

public class FileCodeReaderFactory extends InternalFileContentProvider {
	
	
	public CodeReader createCodeReaderForInclusion(String path) {
		try {
			if (!new File(path).isFile())
				return null;
			return new CodeReader(path);
		} catch (IOException e) {
			return null;
		}
	}

	public CodeReader createCodeReaderForTranslationUnit(String path) {
		try {
			if (!new File(path).isFile())
				return null;
			return new CodeReader(path);
		} catch (IOException e) {
			return null;
		}
	}

	public ICodeReaderCache getCodeReaderCache() {
		return null;
	}

	public int getUniqueIdentifier() {
		return 0;
	}

//	@Override
//	public CodeReader createCodeReaderForInclusion(IIndexFileLocation ifl, String astPath) throws CoreException, IOException {
//		return createCodeReaderForInclusion(astPath);
//	}
	
	private static FileCodeReaderFactory instance;

	


	@Override
	public InternalFileContent getContentForInclusion(String path) {
		return (InternalFileContent) FileContent.createForExternalFileLocation(path);
	}

	public static FileCodeReaderFactory getInstance() {
		if (instance == null)
			instance = new FileCodeReaderFactory();
		return instance;
	}

	@Override
	public InternalFileContent getContentForInclusion(IIndexFileLocation ifl, String astPath) {
		// not used as a delegate
		return null;
	}




}
