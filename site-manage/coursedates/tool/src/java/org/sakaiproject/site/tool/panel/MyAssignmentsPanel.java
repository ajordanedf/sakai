/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sakaiproject.site.tool.panel;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.ExternalImage;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
/**
 *
 */
@Slf4j
public class MyAssignmentsPanel extends Panel {

    DataView<Object> dataview;
    
    public MyAssignmentsPanel(String id) {
        super(id);
        
        Label noResultsMsg = new Label("Hola mundoooooooooooooo", Model.of("There are no upcoming assignments on this group."));
        add(noResultsMsg);
    }

}