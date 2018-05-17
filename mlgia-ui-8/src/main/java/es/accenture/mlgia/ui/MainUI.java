package es.accenture.mlgia.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.material.MaterialTheme;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import es.accenture.mlgia.dto.MessageDTO;
import es.accenture.mlgia.ui.audio.AudioRecorder;
import es.accenture.mlgia.ui.consumer.ConsumerAssistant;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringUI()
@Theme("mlgia")
@Viewport("width=device-width,initial-scale=1.0,user-scalable=no")
public class MainUI extends UI {
	
	//ResponsiveLayout layout;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	Binder<MessageDTO> binder;
	TextField tfQuery;
	Panel contentPanel;
	
	
	
	
	@Autowired
	ConsumerAssistant consumerAssistant;
	
	@Override
	protected void init(VaadinRequest request) {
		buildUI();
		log.debug("Compoentes UI Ok");
		
		initBinder();
		initConversation();

	}

	private void initBinder() {
		binder = new Binder<>(MessageDTO.class);
		binder.setBean(MessageDTO.builder().build());
		
		binder.forField(tfQuery)
			.bind(MessageDTO::getMessageIn, MessageDTO::setMessageIn);
		
	}

	private void initConversation() {
		
		MessageDTO messageDTO = consumerAssistant.initAssistant();
		binder.getBean().setConversationId(messageDTO.getConversationId());
	}
	
	private void clickSendText(ClickEvent event) {
		consumerAssistant.invokeAssistant(binder.getBean());
		
	}
	

	private void buildUI() {
		// TODO Auto-generated method stub
		
		// Top
		Label lbinfo = new Label("MLGIA Car Park");
		lbinfo.addStyleName(MaterialTheme.LABEL_BOLD);
		
		
		// Chat Area
		
		
		
		// Bottom
		// -----------------------------
		Button btMicro = new Button(VaadinIcons.MICROPHONE);
		btMicro.addStyleName(MaterialTheme.BUTTON_FLAT + " " + MaterialTheme.BUTTON_FLOATING_ACTION);
		btMicro.setHeight("50px");
		btMicro.setWidth("50px");
		btMicro.addClickListener(this::clickMicro);
		
		tfQuery = new TextField();
		tfQuery.setPlaceholder("Enter your query");
		tfQuery.setWidth("100%");
		
		
		Button btSendText = new Button(VaadinIcons.PAPERPLANE);
		btSendText.addStyleName(MaterialTheme.BUTTON_FLAT + " " + MaterialTheme.BUTTON_FLOATING_ACTION);
		btSendText.addClickListener(this::clickSendText);
		btSendText.setHeight("50px");
		btSendText.setWidth("50px");
		btSendText.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		

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
	
	




	private void clickMicro(ClickEvent event) {
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
