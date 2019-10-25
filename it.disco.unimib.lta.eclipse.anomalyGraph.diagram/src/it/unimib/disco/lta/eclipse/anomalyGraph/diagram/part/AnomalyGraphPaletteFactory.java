package it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;

/**
 * @generated
 */
public class AnomalyGraphPaletteFactory {

	/**
	 * @generated
	 */
	public void fillPalette(PaletteRoot paletteRoot) {
		paletteRoot.add(createIt1Group());
	}

	/**
	 * Creates "it" palette tool group
	 * @generated
	 */
	private PaletteContainer createIt1Group() {
		PaletteGroup paletteContainer = new PaletteGroup(
				it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.Messages.It1Group_title);
		paletteContainer.add(createBctIOModelViolation1CreationTool());
		paletteContainer.add(createBctFSAModelViolation2CreationTool());
		paletteContainer.add(createRelationship3CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createBctIOModelViolation1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types
				.add(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.BctIOModelViolation_1001);
		NodeToolEntry entry = new NodeToolEntry(
				it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.Messages.BctIOModelViolation1CreationTool_title,
				it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.Messages.BctIOModelViolation1CreationTool_desc,
				types);
		entry
				.setSmallIcon(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes
						.getImageDescriptor(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.BctIOModelViolation_1001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createBctFSAModelViolation2CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types
				.add(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.BctFSAModelViolation_1002);
		NodeToolEntry entry = new NodeToolEntry(
				it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.Messages.BctFSAModelViolation2CreationTool_title,
				it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.Messages.BctFSAModelViolation2CreationTool_desc,
				types);
		entry
				.setSmallIcon(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes
						.getImageDescriptor(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.BctFSAModelViolation_1002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createRelationship3CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types
				.add(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.Relationship_3001);
		LinkToolEntry entry = new LinkToolEntry(
				it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.Messages.Relationship3CreationTool_title,
				it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.Messages.Relationship3CreationTool_desc,
				types);
		entry
				.setSmallIcon(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes
						.getImageDescriptor(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.Relationship_3001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private static class NodeToolEntry extends ToolEntry {

		/**
		 * @generated
		 */
		private final List elementTypes;

		/**
		 * @generated
		 */
		private NodeToolEntry(String title, String description,
				List elementTypes) {
			super(title, description, null, null);
			this.elementTypes = elementTypes;
		}

		/**
		 * @generated
		 */
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeCreationTool(elementTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}

	/**
	 * @generated
	 */
	private static class LinkToolEntry extends ToolEntry {

		/**
		 * @generated
		 */
		private final List relationshipTypes;

		/**
		 * @generated
		 */
		private LinkToolEntry(String title, String description,
				List relationshipTypes) {
			super(title, description, null, null);
			this.relationshipTypes = relationshipTypes;
		}

		/**
		 * @generated
		 */
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeConnectionTool(relationshipTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}
}
