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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.IoModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.IoExpression;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import modelsViolations.BctIOModelViolation;
import modelsViolations.BctIOModelViolation.Position;
import modelsViolations.BctModelViolation;

public class ModelsCleaner {

	public void cleanupModels(MonitoringConfiguration monitoringConfiguration,
			List<BctModelViolation> falseViolations) {
		
		HashMap<String, IoModel> models = extractModelsToCleanUp(monitoringConfiguration,falseViolations);
		
		Resource resource = removeInvalidExpressionsFromModels(
				monitoringConfiguration, falseViolations, models);
		
		persistModels(models, resource);
		
	}

	private void persistModels(HashMap<String, IoModel> models,
			Resource resource) {
		for ( IoModel model : models.values()){
			try {
				resource.saveEntity(model);
			} catch (MapperException e) {
				e.printStackTrace();
			}
		}
	}

	private Resource removeInvalidExpressionsFromModels(
			MonitoringConfiguration monitoringConfiguration,
			List<BctModelViolation> falseViolations,
			HashMap<String, IoModel> models) {
		Resource resource = MonitoringConfigurationRegistry.getInstance().getResource(monitoringConfiguration);
		
		for ( BctModelViolation viol : falseViolations ){
			if ( viol instanceof BctIOModelViolation ){
				BctIOModelViolation ioViol = (BctIOModelViolation) viol;
				
				String violation = ioViol.getViolation();
				
				String method = ioViol.getViolatedMethod();
				
				try {
					
					IoModel iomodel = models.get(method);
					
					IoExpression e = new IoExpression(violation);
					boolean removed;
					if ( ioViol.getPosition() == Position.ENTER ){
						System.out.println("CONTAINS "+iomodel.containsExpressionEnter(e));
						removed = iomodel.removeExpressionEnter(e);
					} else {
						System.out.println("CONTAINS "+iomodel.containsExpressionExit(e));
						removed = iomodel.removeExpressionExit(e);
					}
					
					System.out.println("REMOVED "+removed);
				} catch (LoaderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return resource;
	}

	private HashMap<String, IoModel> extractModelsToCleanUp(
			MonitoringConfiguration monitoringConfiguration,
			List<BctModelViolation> falseViolations) {
		
		HashMap<String,IoModel> models = new HashMap<String,IoModel>();
		Resource resource = MonitoringConfigurationRegistry.getInstance().getResource(monitoringConfiguration);

		
		for ( BctModelViolation viol : falseViolations ){
			if ( viol instanceof BctIOModelViolation ){
				BctIOModelViolation ioViol = (BctIOModelViolation) viol;
				String model = ioViol.getViolatedModel();
				String violation = ioViol.getViolation();
				System.out.println("Model "+model+" ,\\n Violation "+violation);
				
				
				String method = ioViol.getViolatedMethod();
				
				try {
					
					if ( ! models.containsKey(method) ){
						Method bctMethod = resource.getFinderFactory().getMethodHandler().getMethod(method);
						IoModel iomodel = resource.getFinderFactory().getIoModelsHandler().getIoModel(bctMethod);
						models.put(method, iomodel);
					}

				} catch (MapperException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return models;
	}

}
