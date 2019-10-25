package it.unimib.disco.lta.bct.bctjavaeclipse.ui.fsa.editors;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorLauncher;

public class FSAEditorLauncher implements IEditorLauncher {

	public void open(IPath file) {
		
		
			final String fsafile = ResourcesPlugin.getWorkspace().getRoot().getFile(file).getRawLocation().toFile().getAbsolutePath();
			
					
				
					//EnvironmentFrame f = FrameFactory.createFrame(FiniteStateAutomaton.readSerializedFSA(ResourcesPlugin.getPlugin().getWorkspace().getRoot().getFile(file).getRawLocation().toFile().getAbsolutePath()));
					try {
					//Process process = Runtime.getRuntime().exec("java -cp "+DefaultOptionsManager.getBctJarFile()+" tools.ShowFSA "+fsafile);
					Process process = Runtime.getRuntime().exec("doShowFSA "+fsafile);
					final InputStream es =  process.getErrorStream();
					final InputStream is = process.getInputStream();
					final int bufSize = 256;
					Thread ir = new Thread(){
						public void run(){
							byte buf[] = new byte[bufSize];
							int readed;
							try {
								while ( ( readed = is.read(buf, 0, bufSize) ) > 0 ){


									System.out.write(buf,0,readed);

								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					};

					Thread er = new Thread(){
						public void run(){
							byte buf[] = new byte[bufSize];
							int readed;
							try {
								while ( ( readed = es.read(buf, 0, bufSize) ) > 0 ){


									System.err.write(buf,0,readed);

								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};

					ir.start();
					er.start();


					process.waitFor();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
//						} catch (DefaultOptionsManagerException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}

			
			

		
	

}
