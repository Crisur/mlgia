package es.accenture.mlgia.watson;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SpeachToText {

	@Value("${watson.seech.to.text.username}")
	@Getter private String usuario;

	@Value("${watson.seech.to.text.password}")
	@Getter private String password;


	SpeechToText speechService;


	public String getText(File audioFile) {
		String salida = "";

		 speechService = new SpeechToText();
	     speechService.setUsernameAndPassword(usuario, password);

	     ServiceCall<SpeechResults> result = speechService.recognize(audioFile, getRecognizeOptions());
	     log.debug("Texto:" + result.toString());
		return salida;
	}


	private RecognizeOptions getRecognizeOptions() {
		    return new RecognizeOptions.Builder()
		    		.continuous(true)
		    		.model("en-US_BroadbandModel")
		    		.interimResults(true)
		    		.inactivityTimeout(2000)
		    		.build();
	}
}
