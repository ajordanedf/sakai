package org.sakaiproject.site.tool.pages;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.CssContentHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import org.sakaiproject.site.tool.panel.MyAssignmentsPanel;

public class BasePage extends WebPage implements IHeaderContributor {

    FeedbackPanel feedbackPanel;

    public BasePage() {
        MyAssignmentsPanel panel = new MyAssignmentsPanel("my-assignments");
        add(panel);
    }

    /**
     * Helper to clear the feedbackpanel display.
     * @param f FeedBackPanel
     */
    public void clearFeedback(FeedbackPanel f) {
        if(!f.hasFeedbackMessage()) {
            f.add(AttributeModifier.replace("class", ""));
        }
    }

    /**
     * This block adds the required wrapper markup to style it like a Sakai tool.
     * Add to this any additional CSS or JS references that you need.
     *
     */
    public void renderHead(IHeaderResponse response) {
        //get the Sakai skin header fragment from the request attribute
        HttpServletRequest request = (HttpServletRequest)getRequest().getContainerRequest();

        response.render(StringHeaderItem.forString((String)request.getAttribute("sakai.html.head")));
        response.render(OnLoadHeaderItem.forScript("setMainFrameHeight( window.name )"));

        //Tool additions (at end so we can override if required)
        response.render(StringHeaderItem.forString("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"));
        response.render(JavaScriptHeaderItem.forUrl("/library/webjars/bootstrap/3.3.7/js/bootstrap.min.js"));

        response.render(CssContentHeaderItem.forUrl("/library/js/jquery/qtip/jquery.qtip-latest.min.css"));
        response.render(JavaScriptHeaderItem.forUrl("/library/js/jquery/qtip/jquery.qtip-latest.min.js"));
        
        response.render(JavaScriptHeaderItem.forUrl("/library/webjars/momentjs/2.11.1/moment.js"));
    }

}
