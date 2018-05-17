package es.accenture.mlgia.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.accenture.mlgia.watson.SpeachToText;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@MultipartConfig(fileSizeThreshold = 20971520)
@RestController
public class SpeechToTextController    {

	@Value("${mensaje.informacion}")
	@Getter private String sMensajeInfo;

	@Autowired
	SpeachToText speach;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info() {
    	log.debug("Inicio info()");
    	return sMensajeInfo;
    }


    @RequestMapping(value = "/speech-to-text", method = RequestMethod.POST)
    public @ResponseBody void upload(@RequestParam(value = "audiofile")MultipartFile file){

        String nombreFichero = "";
        try{
            log.debug("Upload Image "+file.getOriginalFilename());
            nombreFichero = file.getOriginalFilename();
            File audio = convert(file);

            speach.getText(audio);

          log.debug("File:" + nombreFichero);
        }catch (IOException e){
            log.error("Upload File "+nombreFichero+" failed !");
        }
    }


    public File convert(MultipartFile file) throws IOException
    {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
