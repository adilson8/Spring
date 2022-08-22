package org.zerock.myapp.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.myapp.exception.ControllerException;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/fileupload/")
@Controller
public class FileUploadController {
	
	// Mapping URI : /fileupload/page
	@GetMapping("/page")
	public void fileUploadPage() {
		log.trace("fileUploadPage() invoked.");
				
	} // fileUploadPage
	
	@PostMapping("/doit")
	public void DoFileUpload( 
			String myName, String myAge, 
//			MultipartFile[] files) {            // OK : 배열로 달라! 
//			List<MultipartFile> files) {        // OK : List 객체로 달라!
//			Set<MultipartFile> files) {         // XX : Set 객체로 달라!
//			@RequestParam("files") Set<MultipartFile> files) {     	   // OK : @RequestParam 붙이면 해결됨
			@RequestParam("files") List<MultipartFile> files) throws ControllerException  {        // OK : ?
			
		log.trace("DoFileUpload(myName, myAge, files) invoked.");
		
		log.info("\t+ myName : {}, myAge : {}", myName, myAge);
//		log.info("\t+ files : {}", Arrays.toString(files));
		log.info("\t+ files : {}", files);
		
		try {
			for( MultipartFile file : files ) {
					
				String targetDir = "C:/Temp/upload/";			
				
				log.info("\t =========================");
				log.info("\t+ 1. name : {}", file.getName());
				log.info("\t+ 2. originalFilename : {}", file.getOriginalFilename());
				log.info("\t+ 3. contentType : {}", file.getContentType());
				log.info("\t+ 4. empty : {}", file.isEmpty());
				log.info("\t+ 5. size : {}", file.getSize());
				log.info("\t+ 6. resource : {}", file.getResource());
				
				String targetFile = targetDir + file.getOriginalFilename();
				log.info("\t+ 7. targetFile : {}", targetFile);
				
				file.transferTo( new File(targetFile) );
				
			} // enhanced for
			
			throw new IllegalStateException("TEST"); // 에러를 임의대로 만들어서 예외처리 배워보자
			
		} catch (IllegalStateException | IOException e) {
			throw new ControllerException(e);
		} // try-catch
		
	} // DoFileUpload

} // end class
