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

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class BctMonitoringConfigurationLabelProvider extends LabelProvider {

	@Override
	public Image getImage(Object element) {
		if (element instanceof BctMonitoringConfigurationTreeData) {		
			return ((BctMonitoringConfigurationTreeData) element).getImage();
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof BctMonitoringConfigurationTreeData) {
			return ((BctMonitoringConfigurationTreeData) element).getTxtToDisplay();
		}
		return null;
	}
}
