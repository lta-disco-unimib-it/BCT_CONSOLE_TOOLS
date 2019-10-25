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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis;

import org.eclipse.core.runtime.IProgressMonitor;

public class WaitProgressMonitor implements IProgressMonitor {

	private boolean done;

	@Override
	public void beginTask(String name, int totalWork) {
		// TODO Auto-generated method stub

	}

	@Override
	public void done() {
		// TODO Auto-generated method stub
		done = true;
	}

	@Override
	public void internalWorked(double work) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCanceled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCanceled(boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTaskName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void subTask(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void worked(int work) {
		// TODO Auto-generated method stub

	}

	public synchronized void waitFor() {

		try {
			while ( ! done ){
				this.wait(500);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
