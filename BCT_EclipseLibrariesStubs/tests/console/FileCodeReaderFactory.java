/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
