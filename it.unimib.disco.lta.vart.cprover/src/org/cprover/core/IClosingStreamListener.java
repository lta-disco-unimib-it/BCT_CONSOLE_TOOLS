package org.cprover.core;

import org.eclipse.debug.core.IStreamListener;

/**
 * @author Gérard Basler
 */
public interface IClosingStreamListener extends IStreamListener {
    /**
     * Notifies the listener that the stream has been closed.
     * @param outputStreamMonitor 
     */
    public void streamClosed(OutputStreamMonitor outputStreamMonitor);
}
