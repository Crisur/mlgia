package es.accenture.mlgia.controller;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import com.ibm.watson.developer_cloud.assistant.v1.model.Context;
import com.ibm.watson.developer_cloud.assistant.v1.model.InputData;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;

import es.accenture.mlgia.dto.MessageDTO;

@RestController
public class AssistantController {
	
	private Assistant service;
	
	private HashMap<String, Context> contexts;
	
	@Value("${service.assistant.workspace}")
	private String workspaceId;
	
	@Value("${service.assistant.username}")
	private String username;
	
	@Value("${service.assistant.password}")
	private String password;
	
	@PostConstruct
	public void setup() {
		service = new Assistant("2018-04-29");
		service.setUsernameAndPassword(username, password);
		contexts = new HashMap<String, Context>();
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody MessageDTO sendMessage(@RequestBody MessageDTO message) {
		
		Context context = null;
		MessageResponse response = null;
		
		if (message.getConversationId() != null && !"".equals(message.getConversationId())) {
			context = contexts.get(message.getConversationId());
		}
		
		MessageOptions newMessageOptions = new MessageOptions.Builder()
				  .workspaceId(workspaceId)
				  .input(new InputData.Builder(message.getMessageIn()).build())
				  .context(context)
				  .build();
		
		response = service.message(newMessageOptions).execute();
		
		if (!contexts.containsKey(response.getContext().getConversationId())) {
			contexts.put(response.getContext().getConversationId(), response.getContext());	
		}
		
		message.setMessageOut(response.getOutput().getText().get(0));
		message.setConversationId(response.getContext().getConversationId());
		return message;
	}
		
}
