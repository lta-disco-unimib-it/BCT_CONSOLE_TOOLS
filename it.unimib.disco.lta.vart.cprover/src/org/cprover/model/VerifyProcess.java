package org.cprover.model;

import org.cprover.core.CProverPlugin;
import org.cprover.core.MessageFilter;
import org.cprover.core.OutputStreamMonitor;
import org.cprover.core.StreamsProxy;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IProcess;

public class VerifyProcess implements IProcess {

    /**
     * Launch that triggered the debug session.
     */
    protected ILaunch launch;

    /**
     * The system process represented by this <code>IProcess</code>
     */
    private Process fProcess;
    
    /**
     * name as it is displayed in debug view
     */
    private String label;

    /**
     * The streams proxy for this process
     */
    private StreamsProxy fStreamsProxy;

    private OutputStreamMonitor fRealOutputMonitor;
    
    private MessageFilter fMessageFilter;
    
    /**
     * Filtered. 
     */
    private OutputStreamMonitor fFakeOutputMonitor;
    
    /**
     * Boolean determining if this process was already terminated or not.
     */
    private boolean terminated;
    
    public VerifyProcess(ILaunch launch, Process fProcess, String label) {
        this.launch = launch;
        this.fProcess = fProcess;
        this.label = label;

        fStreamsProxy = createStreamsProxy();

        launch.addProcess(this);
    }

    /**
     * Returns the underlying system process associated with this process.
     * 
     * @return system process
     */
    protected Process getSystemProcess() {
        return fProcess;
    }

    /**
     * @see IProcess#getStreamsProxy()
     */
    public StreamsProxy getStreamsProxy() {
        return fStreamsProxy;
    }

    /**
     * Creates and returns the streams proxy associated with this process.
     * 
     * @return streams proxy
     */
    protected StreamsProxy createStreamsProxy() {
        String encoding = getLaunch().getAttribute(DebugPlugin.ATTR_CONSOLE_ENCODING);
        fRealOutputMonitor =  new OutputStreamMonitor(fProcess.getInputStream(), encoding);
        fMessageFilter = new MessageFilter(fRealOutputMonitor, encoding);
        fFakeOutputMonitor =  new OutputStreamMonitor(fMessageFilter.getInputStream(), encoding);
        StreamsProxy streamsProxy = new StreamsProxy(getSystemProcess(), encoding, fFakeOutputMonitor, fRealOutputMonitor);
        fMessageFilter.startMonitoring();
		return streamsProxy;
    }
    
    public void close() {
    	fMessageFilter.close();
    	fStreamsProxy.close();
    }

    public String getAttribute(String key) {
//        System.out.println(key);
        // TODO Auto-generated method stub
        return null;
    }

    public int getExitValue() throws DebugException {
        return fProcess.exitValue();
    }

    public String getLabel() {
        return label;
    }

    public ILaunch getLaunch() {
        return launch;
    }

    public void setAttribute(String key, String value) {
        // TODO Auto-generated method stub

    }

    public Object getAdapter(Class adapter) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean canTerminate() {
        return true;
    }

    public boolean isTerminated() {
        return terminated;
//        try {
//            fProcess.exitValue();
//            return true;
//        }
//        catch(IllegalThreadStateException e) {
//            return false;
//        }
    }

    public void terminate() throws DebugException {
        try {
            if(fProcess != null){
                fProcess.destroy();
            }
        } catch (RuntimeException e) {
            CProverPlugin.log(e);
        }
        fProcess = null;
        terminated = true;
    }

}
