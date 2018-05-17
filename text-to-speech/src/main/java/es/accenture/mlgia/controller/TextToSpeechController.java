package es.accenture.mlgia.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.accenture.com.mlgia.audio.Audio;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TextToSpeechController    {

	@Value("${mensaje.informacion}")
	@Getter private String sMensajeInfo;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info() {
    	//log.debug("Inicio info()");
    	return sMensajeInfo;
    }
    
    @RequestMapping(value = "/hola", method = RequestMethod.GET)
    public String hola() {
    	//log.debug("Inicio info()");
    	return "hola";
    }
    
    @RequestMapping(value = "/audio", method = RequestMethod.GET)
	public void printURL(HttpServletResponse response,
			@RequestParam(value="text",required=true)String text) throws Exception
	{
		response.setHeader("Content-Disposition","attachment;filename=audio.wav");
		response.setContentType("audio/wave");
		
		Audio.getStream(response.getOutputStream(),text);		
	}

}
