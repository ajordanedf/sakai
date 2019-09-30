package org.sakaiproject.tool.assessment.ui.listener.author;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.sakaiproject.tool.assessment.ui.bean.author.RestorePublishedAssessmentsBean;
import org.sakaiproject.tool.assessment.ui.listener.util.ContextUtil;

public class RestorePublishedAssessmentsListener implements ActionListener {
	public void processAction(ActionEvent event) throws AbortProcessingException {
		try
		{			
			RestorePublishedAssessmentsBean bean = (RestorePublishedAssessmentsBean) ContextUtil.lookupBean("restorePublishedAssessmentsBean");
			bean.init();
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("failed to get archived published assessments");
		}
	}
}
