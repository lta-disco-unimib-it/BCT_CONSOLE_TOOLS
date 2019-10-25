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

import java.io.Serializable;

import it.unimib.disco.lta.ava.engine.AVAResult;
import it.unimib.disco.lta.ava.engine.AvaClusteredResult;

public class AvaAnalysisResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String failureId;
	private AVAResult avaResult;
	
	public void setFailureId(String failureId) {
		this.failureId = failureId;
	}

	private AvaClusteredResult avaClusteredResult;

	public AvaAnalysisResult(){
		
	}
	
	public AVAResult getAvaResult() {
		return avaResult;
	}
	
	public AvaClusteredResult getAvaClusteredResult() {
		return avaClusteredResult;
	}

	public String getFailureId() {
		return failureId;
	}

	public AvaAnalysisResult(String fid) {
		this.failureId = fid;
	}

	public void setAvaResult(AVAResult avaResult) {
		this.avaResult = avaResult; 
		this.avaClusteredResult = new AvaClusteredResult(avaResult); //this is useful for optimization, the result is created in the background thread
	}

}
