/**
 * <copyright>
 * </copyright>
 *
 * $Id: BctModelViolationImpl.java,v 1.1 2008-07-07 20:07:50 pastore Exp $
 */
package anomalyGraph.impl;

import anomalyGraph.AnomalyGraphPackage;
import anomalyGraph.BctModelViolation;
import anomalyGraph.ViolationTypes;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bct Model Violation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link anomalyGraph.impl.BctModelViolationImpl#getId <em>Id</em>}</li>
 *   <li>{@link anomalyGraph.impl.BctModelViolationImpl#getViolatedModel <em>Violated Model</em>}</li>
 *   <li>{@link anomalyGraph.impl.BctModelViolationImpl#getViolation <em>Violation</em>}</li>
 *   <li>{@link anomalyGraph.impl.BctModelViolationImpl#getCreationTime <em>Creation Time</em>}</li>
 *   <li>{@link anomalyGraph.impl.BctModelViolationImpl#getActiveActions <em>Active Actions</em>}</li>
 *   <li>{@link anomalyGraph.impl.BctModelViolationImpl#getActiveTests <em>Active Tests</em>}</li>
 *   <li>{@link anomalyGraph.impl.BctModelViolationImpl#getPid <em>Pid</em>}</li>
 *   <li>{@link anomalyGraph.impl.BctModelViolationImpl#getThreadId <em>Thread Id</em>}</li>
 *   <li>{@link anomalyGraph.impl.BctModelViolationImpl#getStackTrace <em>Stack Trace</em>}</li>
 *   <li>{@link anomalyGraph.impl.BctModelViolationImpl#getViolationType <em>Violation Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class BctModelViolationImpl extends EObjectImpl implements BctModelViolation {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getViolatedModel() <em>Violated Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViolatedModel()
	 * @generated
	 * @ordered
	 */
	protected static final String VIOLATED_MODEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getViolatedModel() <em>Violated Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViolatedModel()
	 * @generated
	 * @ordered
	 */
	protected String violatedModel = VIOLATED_MODEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getViolation() <em>Violation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViolation()
	 * @generated
	 * @ordered
	 */
	protected static final String VIOLATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getViolation() <em>Violation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViolation()
	 * @generated
	 * @ordered
	 */
	protected String violation = VIOLATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreationTime() <em>Creation Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationTime()
	 * @generated
	 * @ordered
	 */
	protected static final Date CREATION_TIME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCreationTime() <em>Creation Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationTime()
	 * @generated
	 * @ordered
	 */
	protected Date creationTime = CREATION_TIME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getActiveActions() <em>Active Actions</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActiveActions()
	 * @generated
	 * @ordered
	 */
	protected EList<String> activeActions;

	/**
	 * The cached value of the '{@link #getActiveTests() <em>Active Tests</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActiveTests()
	 * @generated
	 * @ordered
	 */
	protected EList<String> activeTests;

	/**
	 * The default value of the '{@link #getPid() <em>Pid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPid()
	 * @generated
	 * @ordered
	 */
	protected static final String PID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPid() <em>Pid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPid()
	 * @generated
	 * @ordered
	 */
	protected String pid = PID_EDEFAULT;

	/**
	 * The default value of the '{@link #getThreadId() <em>Thread Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThreadId()
	 * @generated
	 * @ordered
	 */
	protected static final String THREAD_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getThreadId() <em>Thread Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThreadId()
	 * @generated
	 * @ordered
	 */
	protected String threadId = THREAD_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStackTrace() <em>Stack Trace</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStackTrace()
	 * @generated
	 * @ordered
	 */
	protected EList<String> stackTrace;

	/**
	 * The default value of the '{@link #getViolationType() <em>Violation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViolationType()
	 * @generated
	 * @ordered
	 */
	protected static final ViolationTypes VIOLATION_TYPE_EDEFAULT = ViolationTypes.UNEXPECTED_EVENT;

	/**
	 * The cached value of the '{@link #getViolationType() <em>Violation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViolationType()
	 * @generated
	 * @ordered
	 */
	protected ViolationTypes violationType = VIOLATION_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BctModelViolationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AnomalyGraphPackage.Literals.BCT_MODEL_VIOLATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnomalyGraphPackage.BCT_MODEL_VIOLATION__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getViolatedModel() {
		return violatedModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setViolatedModel(String newViolatedModel) {
		String oldViolatedModel = violatedModel;
		violatedModel = newViolatedModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATED_MODEL, oldViolatedModel, violatedModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getViolation() {
		return violation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setViolation(String newViolation) {
		String oldViolation = violation;
		violation = newViolation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATION, oldViolation, violation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreationTime(Date newCreationTime) {
		Date oldCreationTime = creationTime;
		creationTime = newCreationTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnomalyGraphPackage.BCT_MODEL_VIOLATION__CREATION_TIME, oldCreationTime, creationTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getActiveActions() {
		if (activeActions == null) {
			activeActions = new EDataTypeUniqueEList<String>(String.class, this, AnomalyGraphPackage.BCT_MODEL_VIOLATION__ACTIVE_ACTIONS);
		}
		return activeActions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getActiveTests() {
		if (activeTests == null) {
			activeTests = new EDataTypeUniqueEList<String>(String.class, this, AnomalyGraphPackage.BCT_MODEL_VIOLATION__ACTIVE_TESTS);
		}
		return activeTests;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPid(String newPid) {
		String oldPid = pid;
		pid = newPid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnomalyGraphPackage.BCT_MODEL_VIOLATION__PID, oldPid, pid));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getThreadId() {
		return threadId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThreadId(String newThreadId) {
		String oldThreadId = threadId;
		threadId = newThreadId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnomalyGraphPackage.BCT_MODEL_VIOLATION__THREAD_ID, oldThreadId, threadId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getStackTrace() {
		if (stackTrace == null) {
			stackTrace = new EDataTypeUniqueEList<String>(String.class, this, AnomalyGraphPackage.BCT_MODEL_VIOLATION__STACK_TRACE);
		}
		return stackTrace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ViolationTypes getViolationType() {
		return violationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setViolationType(ViolationTypes newViolationType) {
		ViolationTypes oldViolationType = violationType;
		violationType = newViolationType == null ? VIOLATION_TYPE_EDEFAULT : newViolationType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATION_TYPE, oldViolationType, violationType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__ID:
				return getId();
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATED_MODEL:
				return getViolatedModel();
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATION:
				return getViolation();
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__CREATION_TIME:
				return getCreationTime();
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__ACTIVE_ACTIONS:
				return getActiveActions();
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__ACTIVE_TESTS:
				return getActiveTests();
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__PID:
				return getPid();
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__THREAD_ID:
				return getThreadId();
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__STACK_TRACE:
				return getStackTrace();
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATION_TYPE:
				return getViolationType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__ID:
				setId((String)newValue);
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATED_MODEL:
				setViolatedModel((String)newValue);
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATION:
				setViolation((String)newValue);
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__CREATION_TIME:
				setCreationTime((Date)newValue);
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__ACTIVE_ACTIONS:
				getActiveActions().clear();
				getActiveActions().addAll((Collection<? extends String>)newValue);
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__ACTIVE_TESTS:
				getActiveTests().clear();
				getActiveTests().addAll((Collection<? extends String>)newValue);
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__PID:
				setPid((String)newValue);
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__THREAD_ID:
				setThreadId((String)newValue);
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__STACK_TRACE:
				getStackTrace().clear();
				getStackTrace().addAll((Collection<? extends String>)newValue);
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATION_TYPE:
				setViolationType((ViolationTypes)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__ID:
				setId(ID_EDEFAULT);
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATED_MODEL:
				setViolatedModel(VIOLATED_MODEL_EDEFAULT);
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATION:
				setViolation(VIOLATION_EDEFAULT);
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__CREATION_TIME:
				setCreationTime(CREATION_TIME_EDEFAULT);
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__ACTIVE_ACTIONS:
				getActiveActions().clear();
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__ACTIVE_TESTS:
				getActiveTests().clear();
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__PID:
				setPid(PID_EDEFAULT);
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__THREAD_ID:
				setThreadId(THREAD_ID_EDEFAULT);
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__STACK_TRACE:
				getStackTrace().clear();
				return;
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATION_TYPE:
				setViolationType(VIOLATION_TYPE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATED_MODEL:
				return VIOLATED_MODEL_EDEFAULT == null ? violatedModel != null : !VIOLATED_MODEL_EDEFAULT.equals(violatedModel);
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATION:
				return VIOLATION_EDEFAULT == null ? violation != null : !VIOLATION_EDEFAULT.equals(violation);
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__CREATION_TIME:
				return CREATION_TIME_EDEFAULT == null ? creationTime != null : !CREATION_TIME_EDEFAULT.equals(creationTime);
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__ACTIVE_ACTIONS:
				return activeActions != null && !activeActions.isEmpty();
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__ACTIVE_TESTS:
				return activeTests != null && !activeTests.isEmpty();
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__PID:
				return PID_EDEFAULT == null ? pid != null : !PID_EDEFAULT.equals(pid);
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__THREAD_ID:
				return THREAD_ID_EDEFAULT == null ? threadId != null : !THREAD_ID_EDEFAULT.equals(threadId);
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__STACK_TRACE:
				return stackTrace != null && !stackTrace.isEmpty();
			case AnomalyGraphPackage.BCT_MODEL_VIOLATION__VIOLATION_TYPE:
				return violationType != VIOLATION_TYPE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(", violatedModel: ");
		result.append(violatedModel);
		result.append(", violation: ");
		result.append(violation);
		result.append(", creationTime: ");
		result.append(creationTime);
		result.append(", activeActions: ");
		result.append(activeActions);
		result.append(", activeTests: ");
		result.append(activeTests);
		result.append(", pid: ");
		result.append(pid);
		result.append(", threadId: ");
		result.append(threadId);
		result.append(", stackTrace: ");
		result.append(stackTrace);
		result.append(", violationType: ");
		result.append(violationType);
		result.append(')');
		return result.toString();
	}

} //BctModelViolationImpl
