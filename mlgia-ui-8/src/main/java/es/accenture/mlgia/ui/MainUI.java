package es.accenture.mlgia.ui;

import com.github.appreciated.material.MaterialTheme;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import es.accenture.mlgia.ui.audio.AudioRecorder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringUI()
@Theme("mlgia")
@Viewport("width=device-width,initial-scale=1.0,user-scalable=no")
public class MainUI extends UI {
	
	//ResponsiveLayout layout;
	
	@Override
	protected void init(VaadinRequest request) {
		// TODO Auto-generated method stub

		buildUI();

	}

	private void buildUI() {
		// TODO Auto-generated method stub
		
		// Top
		Label lbinfo = new Label("MLGIA Car Park");
		lbinfo.addStyleName(MaterialTheme.LABEL_BOLD);
		
		
		// Bottom
		// -----------------------------
		Button btMicro = new Button(VaadinIcons.MICROPHONE);
		btMicro.addStyleName(MaterialTheme.BUTTON_FLAT + " " + MaterialTheme.BUTTON_FLOATING_ACTION);
		btMicro.setHeight("50px");
		btMicro.setWidth("50px");
		btMicro.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                log.debug("Clic");
            }
		});
		
		TextField tfQuery = new TextField();
		tfQuery.setPlaceholder("Enter your query");
		tfQuery.setWidth("100%");
		
		
		Button btSendText = new Button(VaadinIcons.PAPERPLANE);
		btSendText.addStyleName(MaterialTheme.BUTTON_FLAT + " " + MaterialTheme.BUTTON_FLOATING_ACTION);
		btSendText.addClickListener(e -> log.debug("click"));
		btSendText.setHeight("50px");
		btSendText.setWidth("50px");
		

		HorizontalLayout hlBotom = new HorizontalLayout();
		hlBotom.addComponents(btMicro, tfQuery, btSendText);
		hlBotom.setComponentAlignment(btSendText, Alignment.MIDDLE_RIGHT);
		hlBotom.setComponentAlignment(btMicro, Alignment.MIDDLE_LEFT);
		hlBotom.setComponentAlignment(tfQuery, Alignment.MIDDLE_CENTER);
		hlBotom.setExpandRatio(tfQuery, 1);
		hlBotom.setWidth("100%");
//		hlBotom.setMargin(true);
		//hlBotom.setWidth("100%");
		//hlBotom.setHeight("50px");
		
		
		
		// root layout
		VerticalLayout rootLayout = new VerticalLayout();
		rootLayout.addComponents(lbinfo, hlBotom);
		rootLayout.setComponentAlignment(lbinfo, Alignment.TOP_CENTER);
		rootLayout.setComponentAlignment(hlBotom, Alignment.BOTTOM_CENTER);
		rootLayout.setSizeFull();
		
		setContent(rootLayout);

	}

	private void clickMicro() {
		// TODO Auto-generated method stub
		final AudioRecorder recorder = new AudioRecorder();
		 
        // creates a new thread that waits for a specified
        // of time before stopping
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(120000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                recorder.finish();
            }
        });
 
        stopper.start();
 
        // start recording
        recorder.start();
		
	}

}
