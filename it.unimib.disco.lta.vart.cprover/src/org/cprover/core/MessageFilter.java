package org.cprover.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.eclipse.debug.core.model.IStreamMonitor;

public class MessageFilter {

	PipedInputStream is;
	
	private XmlMessageFilter filter;
	
    /**
     * The thread which reads from the stream
     */
    private Thread fThread;
    
	public MessageFilter(OutputStreamMonitor fOutputStreamMonitor, String encoding) {
	    try {
	    	final PipedOutputStream fFilterOutput = new PipedOutputStream();
	    	this.is = new PipedInputStream();
	        
	        filter = new XmlMessageFilter(new PipedInputStream(fFilterOutput), new PipedOutputStream(is));
	        fOutputStreamMonitor.addListener(new IClosingStreamListener() {
	        	
        		public void streamClosed(OutputStreamMonitor outputStreamMonitor) {
	        		try {
	        			fFilterOutput.close();
	        		} catch (IOException e) {
	        			e.printStackTrace();
	        		}
	        	}
	        	
	        	public void streamAppended(String text, IStreamMonitor monitor) {
	        		try {
	        			fFilterOutput.write(text.getBytes(), 0, text.length());
	        		} catch (IOException e) {
	        			e.printStackTrace();
	        		}
	        	}
	        });
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
    public void startMonitoring() {
        if (fThread == null) {
            fThread= new Thread(new Runnable() {
                public void run() {
                    filter.parse();
                }
            }, "MessageFilter"); 
            fThread.setDaemon(true);
            fThread.setPriority(Thread.MIN_PRIORITY);
            fThread.start();
        }
    }
    
    /**
     * Causes the monitor to close all
     * communications between it and the
     * underlying stream by waiting for the thread to terminate.
     */
    public void close() {
        if (fThread != null) {
            Thread thread= fThread;
            fThread= null;
            try {
                thread.join();
            } catch (InterruptedException ie) {
            }
        }
    }
    
	public InputStream getInputStream() {
		return is;
	}
}
