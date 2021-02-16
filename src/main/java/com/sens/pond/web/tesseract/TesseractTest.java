package com.sens.pond.web.tesseract;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Controller
public class TesseractTest {
    public static void main(String[] args) {

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Program Files (x86)\\Tesseract-OCR\\tessdata");
        tesseract.setLanguage("eng");
            
        File inputFile = new File(
                Paths.get(System.getProperty("user.dir"), "src/main/resources/static/img/logo3.png").toUri());
        String result = "";
        try {
            result = tesseract.doOCR(inputFile);
        } catch (TesseractException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(result);
    }
    
    // https://medium.com/@lampada_19456/spring-boot-tesseract-ocr-2a6e3f3053e1
	@PostMapping("/test")
    @ResponseBody
	public ResponseEntity<String> traduzir(@RequestParam(name="file") MultipartFile file) throws Exception{
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		if (!"png".equals(ext) && !"jpg".equals(ext)) {
			return ResponseEntity.badRequest().build();
		}
		String resultado = "";
		
		try {
			BufferedImage img = ImageIO.read(file.getInputStream());
			//Em versões anteriors do tesseract seria Tesseract.getInstance()
	        Tesseract tesseract = new Tesseract();
	        //Path da pasta pai onde fica a pasta "tessdata"
	        tesseract.setDatapath("C:\\Program Files (x86)\\Tesseract-OCR\\tessdata");
	        resultado = "";	     
	        //lingua: por, eng etc...
            tesseract.setLanguage("eng");
			resultado = tesseract.doOCR(img);	        
		} catch (IOException e) {
			throw new Exception("Erro ao ler arquivo");
		}
		return ResponseEntity.ok(resultado);						
	}
}
