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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
/**
 * this is the Console for plug-in
 * open a console in plugin runtime
 * @author Terragni Valerio
 *
 */
public class ConsoleDisplayMgr
{
	private static ConsoleDisplayMgr fDefault = new ConsoleDisplayMgr("BCT-CONSOLE");
	private String fTitle = null;

	private MessageConsoleStream oStream;
	private MessageConsoleStream eStream;
	private MessageConsole fMessageConsole;
	
	public static final int MSG_INFORMATION = 1;
	public static final int MSG_ERROR = 2;
	public static final int MSG_WARNING = 3;
		
	public static class ConsoleOutputStream extends OutputStream{

		private MessageConsoleStream messageConsoleStream;

		public ConsoleOutputStream(MessageConsoleStream messageConsoleStream) {
			this.messageConsoleStream = messageConsoleStream;
		}

		@Override
		public void write(int b) throws IOException {
			messageConsoleStream.write(b);
		}
		
	}
	
	private ConsoleDisplayMgr(String messageTitle)
	{		
		fTitle = messageTitle;
		oStream = getNewMessageConsoleStream(MSG_INFORMATION);
		eStream = getNewMessageConsoleStream(MSG_ERROR);
	}
	
	public static ConsoleDisplayMgr getDefault() {
		return fDefault;
	}	
	
	public void printError(String msg){
		if( msg == null ) return;
		eStream.print(msg);
	}
	
	public void printInfo(String msg)
	{		
		if( msg == null ) return;
		
		oStream.print(msg);
			
	}
	
	public void clear()
	{		
		IDocument document = getMessageConsole().getDocument();
		if (document != null) {
			document.set("");
		}			
	}	
		
	public boolean displayConsoleView()
	{
		try
		{
			IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			if( activeWorkbenchWindow != null )
			{
				IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
				if( activePage != null )
					activePage.showView(IConsoleConstants.ID_CONSOLE_VIEW, null, IWorkbenchPage.VIEW_VISIBLE);
			}
			
		} catch (PartInitException partEx) {			
			return false;
		}
		
		return true;
	}

	
	private MessageConsoleStream getNewMessageConsoleStream(int msgKind)
	{		
		int swtColorId = SWT.COLOR_DARK_GREEN;
		
		switch (msgKind)
		{
			case MSG_INFORMATION:
				swtColorId = SWT.COLOR_DARK_GREEN;				
				break;
			case MSG_ERROR:
				swtColorId = SWT.COLOR_DARK_MAGENTA;
				break;
			case MSG_WARNING:
				swtColorId = SWT.COLOR_DARK_BLUE;
				break;
			default:				
		}	
		
		MessageConsoleStream stream = getMessageConsole().newMessageStream();
		
		
		return stream;
		
	}
	
	private MessageConsole getMessageConsole()
	{
		if( fMessageConsole == null )
			createMessageConsoleStream(fTitle);	
		
		return fMessageConsole;
	}
		
	private void createMessageConsoleStream(String title)
	{
		fMessageConsole = new MessageConsole(title, null); 
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{ fMessageConsole });
	}

	public OutputStream getOutputStream() {
		return oStream;
	}


	public OutputStream getErrorStream() {
		return eStream;
	}
}