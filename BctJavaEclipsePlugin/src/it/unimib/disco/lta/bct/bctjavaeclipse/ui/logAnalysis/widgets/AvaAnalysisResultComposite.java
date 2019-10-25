package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import it.unimib.disco.lta.ava.alignment.AlignmentResult;
import it.unimib.disco.lta.ava.anomaliesInterpretation.AnomalyInterpretation;
import it.unimib.disco.lta.ava.anomaliesInterpretation.BasicAnomalyInterpretation;
import it.unimib.disco.lta.ava.anomaliesInterpretation.CompositeAnomalyInterpretation;
import it.unimib.disco.lta.ava.anomaliesInterpretation.InterComponentAnomalyInterpretation;
import it.unimib.disco.lta.ava.engine.AVAResult;
import it.unimib.disco.lta.ava.engine.AvaClusteredResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.AvaAnalysisResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

/**
 * A violation analysis composite show the following information
 * 
 * processes/actions/tests reported in the log file
 * 
 * 
 * @author Fabrizio Pastore
 *
 */
public class AvaAnalysisResultComposite extends Composite implements BCTObservable {
	
	
	private static class TreeData{
		private String id;
		private Object data;

		public String getId() {
			return id;
		}

		public Object getData() {
			return data;
		}

		public TreeData ( String id, Object data ){
			this.id = id;
			this.data = data;
		}
		
		
	}
	
	private BCTObservaleIncapsulated observable;

	

	private Tree tree;

	private boolean groupSimilarInterpretations = true;

	private AvaAnalysisResult avaAnalysisResult;



	private IPostSelectionProvider selectionProvider;



	private Tree componentsPropertiesTree;



	private SashForm sash;



	private HashMap<TreeItem, AnomalyInterpretation> treeData;



	private HashMap<TreeItem, AnomalyInterpretation> componentPropertiesTreeData;



	private List<AnomalyInterpretation> treeRoots;



	private List<AnomalyInterpretation> componentPropertiesTreeRoots;



	private Tree detailedComponentsPropertiesTree;



	private HashMap<TreeItem, AnomalyInterpretation> detailedComponentPropertiesTreeData;



	private List<AnomalyInterpretation> detailedComponentPropertiesTreeRoots;



	private Button switchViewButton;
	
	public AvaAnalysisResultComposite( Composite parent, int swt) {
		super(parent,swt);
		observable = new BCTObservaleIncapsulated(this);
		
		sash = new SashForm(this, SWT.VERTICAL);
		sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		createSimilarGroupedTree();
		createComponentsPropertiesTree();
		createDetailedComponentsPropertiesTree();
		sash.setMaximizedControl(tree);
		createButtonsBar(this);
		
		GridLayout layout = new GridLayout();
		layout.numColumns =  1;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		this.setLayout(layout);
	}
	
	private void createDetailedComponentsPropertiesTree() {
		detailedComponentsPropertiesTree = new Tree(sash, SWT.VIRTUAL | SWT.BORDER);
        
		TreeColumn treeColumn = new TreeColumn(detailedComponentsPropertiesTree, SWT.NONE);
		treeColumn.setText("Score");
		treeColumn.setWidth(150);
		
		treeColumn = new TreeColumn(detailedComponentsPropertiesTree, SWT.NONE);
		treeColumn.setText("Type");
		treeColumn.setWidth(550);
		
		treeColumn = new TreeColumn(detailedComponentsPropertiesTree, SWT.NONE);
		treeColumn.setText("Method");
		treeColumn.setWidth(250);
		
		
		
		
		detailedComponentsPropertiesTree.setHeaderVisible(true);
	}

	private void createComponentsPropertiesTree() {
		componentsPropertiesTree = new Tree(sash, SWT.VIRTUAL | SWT.BORDER);
        
		TreeColumn treeColumn = new TreeColumn(componentsPropertiesTree, SWT.NONE);
		treeColumn.setText("Score");
		treeColumn.setWidth(150);
		
		treeColumn = new TreeColumn(componentsPropertiesTree, SWT.NONE);
		treeColumn.setText("Type");
		treeColumn.setWidth(150);
		
		treeColumn = new TreeColumn(componentsPropertiesTree, SWT.NONE);
		treeColumn.setText("Method");
		treeColumn.setWidth(150);
		
		treeColumn = new TreeColumn(componentsPropertiesTree, SWT.NONE);
		treeColumn.setText("Violations");
		treeColumn.setWidth(150);
        
		treeColumn = new TreeColumn(componentsPropertiesTree, SWT.NONE);
		treeColumn.setText("Id");
		treeColumn.setWidth(20);
		
		
		
		treeColumn = new TreeColumn(componentsPropertiesTree, SWT.NONE);
		treeColumn.setText("Deleted");
		treeColumn.setWidth(100);
		
		treeColumn = new TreeColumn(componentsPropertiesTree, SWT.NONE);
		treeColumn.setText("Inserted");
		treeColumn.setWidth(100);
		
		treeColumn = new TreeColumn(componentsPropertiesTree, SWT.NONE);
		treeColumn.setText("Replaced");
		treeColumn.setWidth(100);
		
		treeColumn = new TreeColumn(componentsPropertiesTree, SWT.NONE);
		treeColumn.setText("Replacing");
		treeColumn.setWidth(100);
		
		componentsPropertiesTree.setHeaderVisible(true);
	}

	private void createSimilarGroupedTree(){
		tree = new Tree(sash, SWT.VIRTUAL | SWT.BORDER);
        
		TreeColumn treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setText("Score");
		treeColumn.setWidth(150);
		
		
		treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setText("Type");
		treeColumn.setWidth(150);
		
		treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setText("Method");
		treeColumn.setWidth(150);
		
		treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setText("Violations");
		treeColumn.setWidth(100);
        
		treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setText("Id");
		treeColumn.setWidth(20);
		
		
		
		treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setText("Deleted");
		treeColumn.setWidth(100);
		
		treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setText("Inserted");
		treeColumn.setWidth(100);
		
		treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setText("Replaced");
		treeColumn.setWidth(100);
		
		tree.setHeaderVisible(true);
		

		//selectionProvider.setSelectionProviderDelegate(new Sel);
	}
	
	private void createButtonsBar(final Composite parent) {
		switchViewButton = new Button(parent, SWT.CHECK);
		switchViewButton.setText("Group similar interpretations");
		switchViewButton.setSelection(true);
		
		switchViewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				groupSimilarInterpretations = ! groupSimilarInterpretations;
				
				groupSimilarInterpretations( groupSimilarInterpretations );
			}
		});
	}
	
	public void groupSimilarInterpretations( boolean group ){
		groupSimilarInterpretations = group;
		if (groupSimilarInterpretations) { // we have to switch to table view
			selectItems(detailedComponentsPropertiesTree,tree,detailedComponentPropertiesTreeData,treeData);
			sash.setMaximizedControl(tree);
			
		} else {
			
			selectItems(tree,detailedComponentsPropertiesTree,treeData,detailedComponentPropertiesTreeData);
			sash.setMaximizedControl(detailedComponentsPropertiesTree);
			//detailedComponentsPropertiesTree.showSelection();
			//Select the already selected elements
			
		}
	}
	
	private void selectItems(Tree currentlyActiveTree, Tree nextActiveTree, HashMap<TreeItem, AnomalyInterpretation> currentlyActiveTreeData, HashMap<TreeItem, AnomalyInterpretation> nextActiveTreeData ){
		TreeItem[] selection = currentlyActiveTree.getSelection();
		if ( selection.length > 0 ){
			
			HashSet<AnomalyInterpretation> data = new HashSet<AnomalyInterpretation>();
			
			//add to data, the anomalyInterpretations associated to each item
			for ( TreeItem selected :selection ){
				
				//find the interpretation associated to this item or the closest among its parents
				AnomalyInterpretation interpretation = currentlyActiveTreeData.get(selected);
				
				while ( interpretation == null ){
					selected = selected.getParentItem();
					if ( selected == null ){
						break;
					}
					interpretation = currentlyActiveTreeData.get(selected);
				}
				
				if ( interpretation != null ){
					data.add(interpretation);
				}
			}
			
			ArrayList<TreeItem> toSelect = new ArrayList<TreeItem>();
			
			LinkedList<TreeItem> items = new LinkedList<TreeItem>();
			for ( TreeItem item : nextActiveTree.getItems() ){
				items.add(item);
			}
			
			while ( ! items.isEmpty() ){
				TreeItem item = items.pop();
				
				//Add children
				for ( TreeItem citem : item.getItems() ){
					items.add(citem);
				}
				
				
				if ( data.contains(nextActiveTreeData.get(item))){
					toSelect.add(item);
					nextActiveTree.showItem(item);
				}
				
				if ( toSelect.size() >= selection.length ){
					break;
				}
			}
			nextActiveTree.setSelection(toSelect.toArray(new TreeItem[toSelect.size()]));
			
			nextActiveTree.showSelection();
		}
	}
	
	

	public void load(AvaAnalysisResult analysisResult) {
		this.avaAnalysisResult = analysisResult;
		
		
		if ( analysisResult == null ){
			return;
		}
		loadGroupedTree();
		loadDetailedPropertiesTree();
		
	}
	
	protected void ungroupSimilarInterpretations() {
		switchViewButton.setSelection(false);
		groupSimilarInterpretations( false );
	}
	
	public void loadGroupedTree( ){
		AvaClusteredResult cr = avaAnalysisResult.getAvaClusteredResult();
		if ( cr == null ){
			return;
		}
		treeRoots = cr.getRootInterpretations();
		
		
		
//		tree.addListener(SWT.SetData, new Listener() {
//		      public void handleEvent(Event event) {
//		        TreeItem item = (TreeItem) event.item;
//		        TreeItem parentItem = item.getParentItem();
//		        String text = null;
//		        if (parentItem == null) {
//		          text = "node " + tree.indexOf(item);
//		        } else {
//		          text = parentItem.getText() + " - " + parentItem.indexOf(item);
//		        }
//		        item.setText(text);
//		        item.setItemCount(10);
//		      }
//		    });
		    
		
		treeData = new HashMap<TreeItem,AnomalyInterpretation>();
		
		tree.addMouseListener(new MouseListener() {
			
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseDoubleClick(MouseEvent arg0) {
				ungroupSimilarInterpretations();
			}

			
		});
		
		tree.addListener(SWT.SetData, new Listener() {


			public void handleEvent(Event event) {
				TreeItem item = (TreeItem)event.item;
				TreeItem parentItem = item.getParentItem();

				if ( treeRoots.size() == 0 ){
					return;
				}
				
				if (parentItem == null) {
					AnomalyInterpretation interpretation = treeRoots.get(event.index);
					//item.setText("AA");
					item.setText(getTextForInterpretation(interpretation));
					List<AnomalyInterpretation> similar = avaAnalysisResult.getAvaClusteredResult().getSimilarInterpretations(interpretation);
					treeData.put(item,interpretation);
					item.setData(similar);
					item.setItemCount(similar.size());
				} else {
					List<AnomalyInterpretation> interpretations = (List<AnomalyInterpretation>) parentItem.getData();
					if ( interpretations == null ){
						return;
					}
					AnomalyInterpretation interpretation = interpretations.get(event.index);
					treeData.put(item,interpretation);
					item.setText(getTextForInterpretation(interpretation));
					item.setItemCount(0);
				}
			}
		});
		
		tree.setItemCount(treeRoots.size());
	}
	
	
	public void loadDetailedPropertiesTree( ){
		detailedComponentPropertiesTreeData = new HashMap<TreeItem,AnomalyInterpretation>();
		AVAResult ar = avaAnalysisResult.getAvaResult();
		if ( ar == null ){
			return;
		}
		detailedComponentPropertiesTreeRoots = ar.getAllAnomaliesInterpretationsAccepted();
		
		detailedComponentsPropertiesTree.addListener(SWT.SetData, new Listener() {


			public void handleEvent(Event event) {
				System.out.println("AAA");
				TreeItem item = (TreeItem)event.item;
				TreeItem parentItem = item.getParentItem();

				if ( detailedComponentPropertiesTreeRoots.size() == 0 ){
					return;
				}
				
				

				if (parentItem == null) {
					AnomalyInterpretation interpretation = detailedComponentPropertiesTreeRoots.get(event.index);
					addMainInterpretationInfo(item,interpretation);
					return;
				}
				
				Object parentData = parentItem.getData();
				
				if ( parentData == null ){
					AnomalyInterpretation pintepretation = detailedComponentPropertiesTreeData.get(parentItem);
					if ( pintepretation instanceof BasicAnomalyInterpretation ){

						BasicAnomalyInterpretation interpretation = (BasicAnomalyInterpretation) pintepretation; 
						if ( event.index == 0 ){
							;

							List<String> replaced = ((BasicAnomalyInterpretation)interpretation).getDeletedSymbols();
							item.setData(replaced);
							item.setText("Deleted ("+replaced.size()+")");
							item.setItemCount(replaced.size());

						} else if ( event.index == 1 ){
							

							List<String> replaced = ((BasicAnomalyInterpretation)interpretation).getInsertedSymbols();
							item.setData(replaced);
							item.setText("Inserted ("+replaced.size()+")");
							item.setItemCount(replaced.size());

						} else if ( event.index == 2 ){


							List<String> replaced = ((BasicAnomalyInterpretation)interpretation).getReplacedSymbols();
							item.setData(replaced);
							item.setText("Replaced ("+replaced.size()+")");
							item.setItemCount(replaced.size());


						} else if ( event.index == 3 ){
							

							List<String> replaced = ((BasicAnomalyInterpretation)interpretation).getReplacingSymbols();
							item.setData(replaced);
							item.setText("Replacing ("+replaced.size()+")");
							item.setItemCount(replaced.size());
						} else if ( event.index == 4 ){
							

							AlignmentResult alignmentResult = ((BasicAnomalyInterpretation)interpretation).getAlignmentResult();
							List<String> observed = alignmentResult.getFirstSequenceAligned();
							List<String> expected = alignmentResult.getSecondSequenceAligned();
							ArrayList<String[]> data = new ArrayList<String[]>();
							for ( int i = 0; i < observed.size(); ++i ){
								data.add(new String[]{expected.get(i),observed.get(i)});
							}
							item.setData(data);
							item.setText(new String[]{"Alignment ("+data.size()+")", "Expected", "Observed"});
							item.setItemCount(data.size());

						}
					} else if ( pintepretation instanceof CompositeAnomalyInterpretation ){
						CompositeAnomalyInterpretation interpretation = (CompositeAnomalyInterpretation) pintepretation;
						if ( event.index == 0 ){
							item.setText("Components");

							List<AnomalyInterpretation> data = interpretation.getInterpretations();
							item.setData(data);
							item.setItemCount(data.size());

						}
					} else if ( pintepretation instanceof InterComponentAnomalyInterpretation ){
						InterComponentAnomalyInterpretation interpretation = (InterComponentAnomalyInterpretation) pintepretation;
						if ( event.index == 0 ){
							item.setText("Components");

							List<AnomalyInterpretation> data = interpretation.getInterpretations();
							item.setData(data);
							item.setItemCount(data.size());

						}
					}
					
					
				} else {
					Object itemData = ((List)parentData).get(event.index);
					if ( itemData instanceof String ){
						item.setText(new String[]{"",(String)itemData});
						item.setItemCount(0);
					} else if ( itemData instanceof String[] ){
						item.setText(new String[]{"",((String[])itemData)[0],((String[])itemData)[1]});
						item.setItemCount(0);
					} else if ( itemData instanceof AnomalyInterpretation ){
						addMainInterpretationInfo(item, (AnomalyInterpretation) itemData);
					} else {
						item.setItemCount(0);
					}
					
				}
					
				
			}

			private void addMainInterpretationInfo(TreeItem item, AnomalyInterpretation interpretation) {
				detailedComponentPropertiesTreeData.put(item, interpretation);
				item.setText(getInterpretationTitle(interpretation));
				item.setItemCount(5);
			}

			private String[] getInterpretationTitle(
					AnomalyInterpretation interpretation) {
				String componentIds = getComponentsIdsExtended(interpretation).toString();
				String id = String.valueOf(interpretation.getId());
				String type = interpretation.getAnomalyType().name();
				String score = String.valueOf(interpretation.getPatternScore());
				String deleted = "";
				String inserted = "";
				String replaced = "";
				String replacing = "";
				String component = "";
				
				if ( interpretation instanceof BasicAnomalyInterpretation ){
					BasicAnomalyInterpretation interpr = (BasicAnomalyInterpretation) interpretation; 
					deleted = interpr.getDeletedSymbols().toString();
					inserted = interpr.getInsertedSymbols().toString();
					replaced = interpr.getReplacedSymbols().toString();
					replacing = interpr.getReplacingSymbols().toString();
					component = interpr.getViolation().getComponentName();
				} else if ( interpretation instanceof CompositeAnomalyInterpretation ){
					CompositeAnomalyInterpretation interpr = (CompositeAnomalyInterpretation) interpretation; 
					
				}
				
				return new String[]{
						score,
						type,
						componentIds
				};
			}

			
		});
		
		detailedComponentsPropertiesTree.setItemCount(detailedComponentPropertiesTreeRoots.size());	
	}
	
	
	public void loadPropertiesTree( ){
		componentPropertiesTreeData = new HashMap<TreeItem,AnomalyInterpretation>();
		
		componentPropertiesTreeRoots = avaAnalysisResult.getAvaResult().getAllAnomaliesInterpretationsAccepted();
		
		componentsPropertiesTree.addListener(SWT.SetData, new Listener() {


			public void handleEvent(Event event) {
				TreeItem item = (TreeItem)event.item;
				TreeItem parentItem = item.getParentItem();

				if ( componentPropertiesTreeRoots.size() == 0 ){
					return;
				}
				Object object;
				AnomalyInterpretation interpretation = null;
				
				if (parentItem == null) {
					object = componentPropertiesTreeRoots.get(event.index);
					
				} else {
					List interpretations = (List) parentItem.getData();
					if ( interpretations == null ){
						return;
					}
					object = interpretations.get(event.index);
				}
				
				
					if ( object instanceof AnomalyInterpretation ){
						interpretation = (AnomalyInterpretation) object;
						componentPropertiesTreeData.put(item, interpretation);
						item.setText(getTextForInterpretation(interpretation));
						List children = getComponents(interpretation);
						System.out.println(children);
						if ( children != null ){
							
							item.setData( children  );
							item.setItemCount(children.size());
						}
					} else {
						item.setText(getTextForAlignment(event.index, (List<String>) object));
						item.setItemCount(0);
					}
					
					
			}

			
		});
		
		
		
		//SPAN MULTI COLUMN
		
	    componentsPropertiesTree.setLinesVisible(true);
	    
	    
	    Listener paintListener = new Listener() {
	      public void handleEvent(Event event) {
	    	  if ( event.item != null ){
	    		  if ( event.item.getData() != null ){
	    			  return;
	    		  }
	    	  }
	    	 
	    	TreeItem item = (TreeItem) event.item;
	    	
	    	//System.out.println("TEXT "+item.getText(1));
	    	String string = item.getText(1);
	    	GC gc = new GC(componentsPropertiesTree);
		    final Point extent = gc.stringExtent(string);
		    gc.dispose();
		    
	        switch (event.type) {
	        
	        case SWT.MeasureItem: {
	          if (event.index >= 1 ) {
	            event.width = extent.x;
	            event.height = Math.max(event.height, extent.y + 2);
	          }
	          break;
	        }
	        case SWT.PaintItem: {
	          if (event.index >= 1 ) {
	            int offset = 0;
	            if (event.index >= 2) {
	            	for ( int i = 1; i < event.index; i++){
	            		TreeColumn column1 = componentsPropertiesTree.getColumn(i);
	            		offset += column1.getWidth();
	            	}
	            }
	            
	            
	            int y = event.y + (event.height - extent.y) / 2;
	            //if ( event.index == 6 ){
	            	event.gc.drawString(string, event.x - offset, y);
	            //}
	          }
	          break;
	        }
	        }
	      }
	    };
	    
	    componentsPropertiesTree.addListener(SWT.MeasureItem, paintListener);
	    componentsPropertiesTree.addListener(SWT.PaintItem, paintListener);
		
		
		componentsPropertiesTree.setItemCount(componentPropertiesTreeRoots.size());
		
		changed();
//
//
//		});
//		
//		
//		
//		tree.setItemCount(roots.size());
		
	}
	
	private String[] getTextForAlignment(int index, List<String> expectedObserved) {
		if ( index == 0 ){
			return new String[]{"Expected",expectedObserved.toString()};
		} else {
			return new String[]{"Observed",expectedObserved.toString()};
		}
	}

	private List getComponents(AnomalyInterpretation interpretation) {
		if ( interpretation == null ){
			return null;
		}
		if ( interpretation instanceof CompositeAnomalyInterpretation ){
			CompositeAnomalyInterpretation interpr = (CompositeAnomalyInterpretation) interpretation;
			return interpr.getInterpretations();
		} else if ( interpretation instanceof InterComponentAnomalyInterpretation ){
			InterComponentAnomalyInterpretation interpr = (InterComponentAnomalyInterpretation) interpretation;
			return interpr.getInterpretations();
		} else if ( interpretation instanceof BasicAnomalyInterpretation ){
			BasicAnomalyInterpretation interpr = (BasicAnomalyInterpretation) interpretation;
			AlignmentResult alignment = interpr.getAlignmentResult();
			
			ArrayList<List<String>> res = new ArrayList<List<String>>();
			
			res.add(alignment.getSecondSequenceAligned());
			res.add(alignment.getFirstSequenceAligned());
			return res;
		}
		return null;
	}
	
	private String[] getTextForInterpretation(
			AnomalyInterpretation interpretation) {
		String componentIds = getComponentsIdsExtended(interpretation).toString();
		String id = String.valueOf(interpretation.getId());
		String type = interpretation.getAnomalyType().name();
		String score = String.valueOf(interpretation.getPatternScore());
		String deleted = "";
		String inserted = "";
		String replaced = "";
		String replacing = "";
		String component = "";
		
		if ( interpretation instanceof BasicAnomalyInterpretation ){
			BasicAnomalyInterpretation interpr = (BasicAnomalyInterpretation) interpretation; 
			deleted = interpr.getDeletedSymbols().toString();
			inserted = interpr.getInsertedSymbols().toString();
			replaced = interpr.getReplacedSymbols().toString();
			replacing = interpr.getReplacingSymbols().toString();
			component = interpr.getViolation().getComponentName();
		} else if ( interpretation instanceof CompositeAnomalyInterpretation ){
			CompositeAnomalyInterpretation interpr = (CompositeAnomalyInterpretation) interpretation; 
			
		}
		
		return new String[]{
				score,
				type,
				component,
				componentIds,
				id,
				
				deleted,
				inserted,
				replaced,
				replacing
		};
	}
	
	private String getComponentIdsString (AnomalyInterpretation interpretation) {
		StringBuffer keyBuffer = new StringBuffer();
		boolean first = true;
		for ( Integer id : getComponentsIds(interpretation) ){
			if ( ! first ) {
				keyBuffer.append("-");
			} else {
				first = false;
			}
			keyBuffer.append(String.valueOf(id));
			
		}
		
		return keyBuffer.toString();
	}
	
	private List<String> getComponentsIdsExtended(
			AnomalyInterpretation interpretation) {
		ArrayList<String> ids = new ArrayList<String>();
		if ( interpretation instanceof BasicAnomalyInterpretation ){
			ids.add(((BasicAnomalyInterpretation)interpretation).getViolation().getComponentName()+"("+((BasicAnomalyInterpretation)interpretation).getViolation().getStartViolationLine()+")");
		} else if ( interpretation instanceof CompositeAnomalyInterpretation ){
			for ( AnomalyInterpretation componentInterpreatation : ((CompositeAnomalyInterpretation)interpretation).getInterpretations() ) {
				ids.addAll(getComponentsIdsExtended(componentInterpreatation));
			}	
		}else if ( interpretation instanceof InterComponentAnomalyInterpretation ){
			for ( AnomalyInterpretation componentInterpreatation : ((InterComponentAnomalyInterpretation)interpretation).getInterpretations() ) {
				ids.addAll(getComponentsIdsExtended(componentInterpreatation));
			}
		}
		
		Collections.sort(ids);
		
		return ids;
	}

	private List<Integer> getComponentsIds(
			AnomalyInterpretation interpretation) {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		if ( interpretation instanceof BasicAnomalyInterpretation ){
			ids.add(((BasicAnomalyInterpretation)interpretation).getViolation().getStartViolationLine());
		} else if ( interpretation instanceof CompositeAnomalyInterpretation ){
			for ( AnomalyInterpretation componentInterpreatation : ((CompositeAnomalyInterpretation)interpretation).getInterpretations() ) {
				ids.addAll(getComponentsIds(componentInterpreatation));
			}	
		}else if ( interpretation instanceof InterComponentAnomalyInterpretation ){
			for ( AnomalyInterpretation componentInterpreatation : ((InterComponentAnomalyInterpretation)interpretation).getInterpretations() ) {
				ids.addAll(getComponentsIds(componentInterpreatation));
			}
		}
		
		Collections.sort(ids);
		
		return ids;
	}
	
	private void changed() {
		observable.notifyBCTObservers(null);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		
		tree.setBounds(0, 0, width-10, height-10);
		super.setBounds(x, y, width, height);
	}

	public void addBCTObserver(BCTObserver bctObserver) {
		observable.addBCTObserver(bctObserver);
	}

	public AvaAnalysisResult getAvaAnalysisResult() {
		return avaAnalysisResult;
	}

	public void getSelectionProvider() {
		
	}

	public void clear() {
		System.out.println("CLEARING AVA");
		
		
		avaAnalysisResult = null;
		
		
		clearGroupedTree();
		clearPropertiesTree();
		clearDetailedPropertiesTree();
	}

	public void clearGroupedTree(){
		if ( treeRoots == null ){
			return;
		}
		treeRoots.clear();
		treeData.clear();
		tree.setItemCount(0);
		tree.removeAll();
	}
	
	public void clearPropertiesTree(){
		if ( componentPropertiesTreeRoots == null ){
			return;
		}
		componentPropertiesTreeRoots.clear();
		componentPropertiesTreeData.clear();
		componentsPropertiesTree.setItemCount(0);
		componentsPropertiesTree.removeAll();
	}
	
	public void clearDetailedPropertiesTree(){
		if ( detailedComponentPropertiesTreeRoots == null ){
			return;
		}
		detailedComponentPropertiesTreeRoots.clear();
		detailedComponentPropertiesTreeData.clear();
		detailedComponentsPropertiesTree.setItemCount(0);
		detailedComponentsPropertiesTree.removeAll();
	}

	

}
