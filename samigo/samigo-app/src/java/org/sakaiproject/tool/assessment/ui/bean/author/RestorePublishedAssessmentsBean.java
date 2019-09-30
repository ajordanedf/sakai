package org.sakaiproject.tool.assessment.ui.bean.author;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.event.ActionEvent;

import org.sakaiproject.tool.assessment.services.assessment.PublishedAssessmentService;
import org.sakaiproject.tool.assessment.facade.PublishedAssessmentFacadeQueries;
import org.sakaiproject.tool.assessment.facade.AgentFacade;
import org.sakaiproject.tool.assessment.data.dao.assessment.PublishedAssessmentData;
import org.sakaiproject.tool.assessment.services.PersistenceService;

import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.tool.assessment.ui.listener.util.ContextUtil;

import org.sakaiproject.tool.assessment.ui.listener.author.AuthorActionListener;

public class RestorePublishedAssessmentsBean implements Serializable{
	
	private static Log log = LogFactory.getLog(RestorePublishedAssessmentsBean.class);	
	    
	private DataArchivedPublishedAssessments dataArchivedPublishedAssessments = null;
	
	private DataModel archivedPublishedAssessmentsList = null;
	
	// Paging.
	private int firstRestoreRow;
	private int maxDisplayedRestoreRows=50;
	private int restoreDataRows;
	
	// Searching
	private String searchString;
	private String defaultSearchString;
	
		
	public class DataArchivedPublishedAssessments{
		
		boolean checkAssessment;
		Long id;
		String title;
		Date startDate;
		Integer submissions;			
				
		/* Getters and Setters */
		
		public boolean isCheckAssessment() {
			return checkAssessment;
		}
		public void setCheckAssessment(boolean checkAssessment) {
			this.checkAssessment = checkAssessment;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Integer getSubmissions() {
			return submissions;
		}
		public void setSubmissions(Integer submissions) {
			this.submissions = submissions;
		}
	}
		
	public void init()
	{			
		getArchivedPublishedAssessmentsList();
	}
	
	public String restorePublishedAssessments()
	{
		ArrayList<Long> publishedAssessmentsIds = new ArrayList<Long>(); 
					
		for (DataArchivedPublishedAssessments dataArchivedPublishedAssessments : (List<DataArchivedPublishedAssessments>)archivedPublishedAssessmentsList.getWrappedData())
		{
			if(dataArchivedPublishedAssessments.isCheckAssessment())
			{				
				publishedAssessmentsIds.add(dataArchivedPublishedAssessments.getId());								
			}			
		}
		
		if(publishedAssessmentsIds.size() > 0)
		{
			PublishedAssessmentService publishedAssessmentService = new PublishedAssessmentService();
			publishedAssessmentService.restoreArchivedPublishedAssessments(publishedAssessmentsIds);
		}
		
		archivedPublishedAssessmentsList = null;	

		AuthorActionListener listener = new AuthorActionListener();
		listener.processAction(null);
		
		// return to assessments page
		return "author";
	}
	
	public DataModel getArchivedPublishedAssessmentsList() 
	{			
		//if(archivedPublishedAssessmentsList == null)
		//{
			defaultSearchString = ContextUtil.getLocalizedString("org.sakaiproject.tool.assessment.bundle.EvaluationMessages", "search_default_string_restore_assessments");
			
			if (searchString == null) {
				searchString = defaultSearchString;
			}
					
			PublishedAssessmentService publishedAssessmentService = new PublishedAssessmentService();
			ArrayList<PublishedAssessmentData> archivedPublishedAssessments = publishedAssessmentService.getBasicInfoOfAllArchivedPublishedAssessments2(
				  PublishedAssessmentFacadeQueries.TITLE, true, AgentFacade.getCurrentSiteId());
									
			HashMap submittedCounts = PersistenceService.getInstance().getAssessmentGradingFacadeQueries().getSubmittedCounts(AgentFacade.getCurrentSiteId());
						
			archivedPublishedAssessmentsList = new ListDataModel(new ArrayList<DataArchivedPublishedAssessments>());			
						
			for(PublishedAssessmentData p : archivedPublishedAssessments)
			{								
				dataArchivedPublishedAssessments = new DataArchivedPublishedAssessments();
				dataArchivedPublishedAssessments.setCheckAssessment(false);
				dataArchivedPublishedAssessments.setId(p.getPublishedAssessmentId());
				dataArchivedPublishedAssessments.setTitle(p.getTitle());
				dataArchivedPublishedAssessments.setStartDate(p.getStartDate());
				
				if(submittedCounts.get(p.getPublishedAssessmentId()) == null)
					dataArchivedPublishedAssessments.setSubmissions(0);
				else
					dataArchivedPublishedAssessments.setSubmissions((Integer)submittedCounts.get(p.getPublishedAssessmentId()));
					
				((List)archivedPublishedAssessmentsList.getWrappedData()).add(dataArchivedPublishedAssessments) ;
			}
			
			ArrayList archivedPublishedAssessmentsArrayList;
			if (isFilteredSearch()) {
				archivedPublishedAssessmentsArrayList = findMatchingAgents(searchString);
			}
			else {
				archivedPublishedAssessmentsArrayList = (ArrayList)archivedPublishedAssessmentsList.getWrappedData();
			}
			
			restoreDataRows = archivedPublishedAssessmentsArrayList.size();
			ArrayList archivedPublishedAssessmentsSubList;
			if (maxDisplayedRestoreRows == 0) {
				archivedPublishedAssessmentsSubList = archivedPublishedAssessmentsArrayList;
			} else {				
				int nextPageRow = Math.min(firstRestoreRow + maxDisplayedRestoreRows, restoreDataRows);				
				archivedPublishedAssessmentsSubList = new ArrayList(archivedPublishedAssessmentsArrayList.subList(firstRestoreRow, nextPageRow));				
			}
			
			archivedPublishedAssessmentsList.setWrappedData(archivedPublishedAssessmentsSubList);	
		//}
		return archivedPublishedAssessmentsList;
	}

	public void setArchivedPublishedAssessmentsList(DataModel archivedPublishedAssessmentsList) {
		this.archivedPublishedAssessmentsList = archivedPublishedAssessmentsList;
	}
	
	public DataArchivedPublishedAssessments getDataArchivedPublishedAssessments() {
		return dataArchivedPublishedAssessments;
	}

	public void setDataArchivedPublishedAssessments(DataArchivedPublishedAssessments dataArchivedPublishedAssessments) {
		this.dataArchivedPublishedAssessments = dataArchivedPublishedAssessments;
	}
	
	public int getFirstRow() {
		return firstRestoreRow;
	}
	public void setFirstRow(int firstRow) {
		firstRestoreRow = firstRow;
	}
	public int getMaxDisplayedRows() {
		return maxDisplayedRestoreRows;
	}
	public void setMaxDisplayedRows(int maxDisplayedRows) {
		maxDisplayedRestoreRows = maxDisplayedRows;
	}
	public int getDataRows() {
		return restoreDataRows;
	}
	
	public String getSearchString() {
	  return searchString;
	}
	public void setSearchString(String searchString) {
	  if (StringUtils.trimToNull(searchString) == null) {
		  searchString = defaultSearchString;
	  }
	  if (!StringUtils.equals(searchString, this.searchString)) {			
			this.searchString = searchString;
			setFirstRow(0); // clear the paging when we update the search
	  }
	}
	
	private boolean isFilteredSearch() {
        return !StringUtils.equals(searchString, defaultSearchString);
	}
	
	public ArrayList findMatchingAgents(final String pattern) {
		ArrayList filteredList = new ArrayList();
		
		for(Iterator iter = ((ArrayList)archivedPublishedAssessmentsList.getWrappedData()).iterator(); iter.hasNext();) {
			DataArchivedPublishedAssessments result = (DataArchivedPublishedAssessments)iter.next();
			
			if(result.getStartDate() != null)
			{
				SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
															
				if (result.getTitle().toLowerCase().contains(pattern.toLowerCase()) ||
					formatter.format(result.getStartDate()).equals(pattern.toLowerCase())) {
					filteredList.add(result);
				}
			}
			else
			{							
				if (result.getTitle().toLowerCase().contains(pattern.toLowerCase())) {
					filteredList.add(result);
				}
			}
		}
		return filteredList;
	}
	
	public void search(ActionEvent event) {
      // We don't need to do anything special here, since init will handle the search
      log.debug("search");
	}
	
	public void clear(ActionEvent event) {      
      setSearchString(null);
	  init();
  }
}
