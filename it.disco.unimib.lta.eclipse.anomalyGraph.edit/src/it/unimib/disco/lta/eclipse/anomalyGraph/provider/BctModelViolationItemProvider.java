/**
 * <copyright>
 * </copyright>
 *
 * $Id: BctModelViolationItemProvider.java,v 1.1 2008-07-07 20:08:51 pastore Exp $
 */
package it.unimib.disco.lta.eclipse.anomalyGraph.provider;


import it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage;
import it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class BctModelViolationItemProvider
	extends ItemProviderAdapter
	implements	
		IEditingDomainItemProvider,	
		IStructuredItemContentProvider,	
		ITreeItemContentProvider,	
		IItemLabelProvider,	
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BctModelViolationItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addIdPropertyDescriptor(object);
			addViolatedModelPropertyDescriptor(object);
			addViolationPropertyDescriptor(object);
			addCreationTimePropertyDescriptor(object);
			addActiveActionsPropertyDescriptor(object);
			addActiveTestsPropertyDescriptor(object);
			addPidPropertyDescriptor(object);
			addThreadIdPropertyDescriptor(object);
			addStackTracePropertyDescriptor(object);
			addViolationTypePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIdPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_BctModelViolation_id_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_BctModelViolation_id_feature", "_UI_BctModelViolation_type"),
				 AnomalyGraphPackage.Literals.BCT_MODEL_VIOLATION__ID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Violated Model feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addViolatedModelPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_BctModelViolation_violatedModel_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_BctModelViolation_violatedModel_feature", "_UI_BctModelViolation_type"),
				 AnomalyGraphPackage.Literals.BCT_MODEL_VIOLATION__VIOLATED_MODEL,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Violation feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addViolationPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_BctModelViolation_violation_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_BctModelViolation_violation_feature", "_UI_BctModelViolation_type"),
				 AnomalyGraphPackage.Literals.BCT_MODEL_VIOLATION__VIOLATION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Creation Time feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCreationTimePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_BctModelViolation_creationTime_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_BctModelViolation_creationTime_feature", "_UI_BctModelViolation_type"),
				 AnomalyGraphPackage.Literals.BCT_MODEL_VIOLATION__CREATION_TIME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Active Actions feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addActiveActionsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_BctModelViolation_activeActions_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_BctModelViolation_activeActions_feature", "_UI_BctModelViolation_type"),
				 AnomalyGraphPackage.Literals.BCT_MODEL_VIOLATION__ACTIVE_ACTIONS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Active Tests feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addActiveTestsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_BctModelViolation_activeTests_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_BctModelViolation_activeTests_feature", "_UI_BctModelViolation_type"),
				 AnomalyGraphPackage.Literals.BCT_MODEL_VIOLATION__ACTIVE_TESTS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Pid feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPidPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_BctModelViolation_pid_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_BctModelViolation_pid_feature", "_UI_BctModelViolation_type"),
				 AnomalyGraphPackage.Literals.BCT_MODEL_VIOLATION__PID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Thread Id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addThreadIdPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_BctModelViolation_threadId_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_BctModelViolation_threadId_feature", "_UI_BctModelViolation_type"),
				 AnomalyGraphPackage.Literals.BCT_MODEL_VIOLATION__THREAD_ID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Stack Trace feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStackTracePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_BctModelViolation_stackTrace_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_BctModelViolation_stackTrace_feature", "_UI_BctModelViolation_type"),
				 AnomalyGraphPackage.Literals.BCT_MODEL_VIOLATION__STACK_TRACE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Violation Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addViolationTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_BctModelViolation_violationType_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_BctModelViolation_violationType_feature", "_UI_BctModelViolation_type"),
				 AnomalyGraphPackage.Literals.BCT_MODEL_VIOLATION__VIOLATION_TYPE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((BctModelViolation)object).getId();
		return label == null || label.length() == 0 ?
			getString("_UI_BctModelViolation_type") :
			getString("_UI_BctModelViolation_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(BctModelViolation.class)) {
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__ID:
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATED_MODEL:
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATION:
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__CREATION_TIME:
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__ACTIVE_ACTIONS:
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__ACTIVE_TESTS:
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__PID:
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__THREAD_ID:
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__STACK_TRACE:
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATION_TYPE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return AnomalyGraphEditPlugin.INSTANCE;
	}

}
