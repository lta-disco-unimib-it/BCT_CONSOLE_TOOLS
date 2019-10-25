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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.graphics.Image;

public class BctMonitoringConfigurationTreeData {

	public enum ContentType {
		IO_MODELS, FSA_MODELS, RAW_IO_TRACES, NORMALIZED_IO_TRACES, RAW_INTERACTION_TRACES, NORMALIZED_INTERACTION_TRACES
	}

	private URI uri;
	private String txtToDisplay;
	private Object parent;
	private Image image;
	private ContentType contentType;

	/*
	 * leaf field indicates if this tree entry is a leaf entry (i.e. it doesn't have children) or not. openable field
	 * indicates if this tree entry can be opened in a editor.
	 */
	private boolean leaf, openable;

	public BctMonitoringConfigurationTreeData(String txtToDisplay, Object parent) {
		this.txtToDisplay = txtToDisplay;
		this.parent = parent;
		image = null;
		leaf = false;
	}

	public String getTxtToDisplay() {
		return txtToDisplay;
	}

	public Object getParent() {
		return parent;
	}

	public Image getImage() {
		return image;
	}

	public URI getURI() {
		return uri;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public void setURI(URI uri) {
		this.uri = uri;
	}

	public void setOpenable(boolean openable) {
		this.openable = openable;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public boolean isOpenable() {
		return openable;
	}

	public MonitoringConfiguration getMonitoringConfiguration() {
		IFile monitoringConfigurationFile = getMonitoringConfigurationFile(this);
		return MonitoringConfigurationRegistry.getInstance().getMonitoringConfiguration(monitoringConfigurationFile);
	}

	private IFile getMonitoringConfigurationFile(Object treeData) {
		if (treeData instanceof IFile)
			return (IFile) treeData;
		else if (treeData instanceof BctMonitoringConfigurationTreeData) {
			BctMonitoringConfigurationTreeData data = (BctMonitoringConfigurationTreeData) treeData;
			return getMonitoringConfigurationFile(data.getParent());
		}
		return null;
	}
}
