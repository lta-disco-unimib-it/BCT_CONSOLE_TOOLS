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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class BctConsoleView extends ViewPart {

	private Text text;
	private MyOutputStream out;

	public static class MyOutputStream extends OutputStream{
		
		private Text text;

		MyOutputStream(Text text){
			this.text = text;
		}
		
		@Override
		public void write(int b) throws IOException {
			if (text.isDisposed()) return;
			text.append(String.valueOf((char) b));
		}
		
	}
	
	public BctConsoleView() {
		// TODO Auto-generated constructor stub
	}

	public OutputStream getOutputStream(){
		return out;
	}
	
	@Override
	public void createPartControl(Composite parent) {
		System.out.println("Created");
		text = new Text(parent, SWT.READ_ONLY | SWT.MULTI);
		out = new MyOutputStream(text);

		try {
			out.write("Bct View".getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("out is "+out);
//		final PrintStream oldOut = System.out;
//		System.setOut(new PrintStream(out));
//		text.addDisposeListener(new DisposeListener() {
//			public void widgetDisposed(DisposeEvent e) {
//				System.setOut(oldOut);
//			}
//		});
	}

	@Override
	public void setFocus() {
		text.setFocus();
	}

	public void flush() throws IOException {
		out.flush();
	}

	public void write(byte[] b, int off, int len) throws IOException {
		out.write(b, off, len);
	}



	public void write(int b) throws IOException {
		out.write(b);
	}

}
