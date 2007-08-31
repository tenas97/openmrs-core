package org.openmrs.api.impl;

import java.util.List;

import org.openmrs.Cohort;
import org.openmrs.Patient;
import org.openmrs.api.CohortService;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.CohortDAO;

public class CohortServiceImpl implements CohortService {

	private CohortDAO dao;
	
	public CohortServiceImpl() { }
	
	private CohortDAO getCohortDAO() {
		return dao;
	}
	
	public void setCohortDAO(CohortDAO dao) {
		this.dao = dao;
	}
	
	public void createCohort(Cohort cohort) {
		if (cohort.getCreator() == null)
			cohort.setCreator(Context.getAuthenticatedUser());
		if (cohort.getDateCreated() == null)
			cohort.setDateCreated(new java.util.Date());
		if (cohort.getName() == null)
			throw new IllegalArgumentException("Missing Name");
		getCohortDAO().createCohort(cohort);
	}

	public Cohort getCohort(Integer id) {
		return getCohortDAO().getCohort(id); 
	}
	
	public List<Cohort> getCohorts() {
		return getCohortDAO().getCohorts();
	}
	
	public void voidCohort(Cohort cohort, String reason) {
		cohort.setVoided(true);
		cohort.setVoidedBy(Context.getAuthenticatedUser());
		cohort.setVoidReason(reason);
		updateCohort(cohort);
	}

	/**
     * @see org.openmrs.api.CohortService#getCohortByGuid(java.lang.String)
     */
    public Cohort getCohortByGuid(String guid) {
		return getCohortDAO().getCohortByGuid(guid); 
    }

	/**
     * @see org.openmrs.api.CohortService#addPatientToCohort(org.openmrs.Cohort, org.openmrs.Patient)
     */
    public void addPatientToCohort(Cohort cohort, Patient patient) {
	    cohort.getMemberIds().add(patient.getPatientId());
	    updateCohort(cohort);
    }

	/**
     * @see org.openmrs.api.CohortService#removePatientFromCohort(org.openmrs.Cohort, org.openmrs.Patient)
     */
    public void removePatientFromCohort(Cohort cohort, Patient patient) {
	    cohort.getMemberIds().remove(patient.getPatientId());
	    updateCohort(cohort);
    }

	/**
     * @see org.openmrs.api.CohortService#updateCohort(org.openmrs.Cohort)
     */
    public void updateCohort(Cohort cohort) {
		if (cohort.getCreator() == null)
			cohort.setCreator(Context.getAuthenticatedUser());
		if (cohort.getDateCreated() == null)
			cohort.setDateCreated(new java.util.Date());
		if (cohort.getName() == null)
			throw new IllegalArgumentException("Missing Name");
		// TODO: Add modifiedBy and dateModified to Cohort
		//cohort.setDateModified(new java.util.Date());
		//cohort.setModifiedBy(Context.getAuthenticatedUser());
		getCohortDAO().updateCohort(cohort);
	}

	/**
     * @see org.openmrs.api.CohortService#getCohortsContainingPatient(org.openmrs.Patient)
     */
    public List<Cohort> getCohortsContainingPatient(Patient patient) {
	    return getCohortDAO().getCohortsContainingPatientId(patient.getPatientId());
    }
    
    public List<Cohort> getCohortsContainingPatientId(Integer patientId) {
    	return getCohortDAO().getCohortsContainingPatientId(patientId);
    }

}
