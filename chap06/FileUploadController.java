package org.zerock.myapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.NoArgsConstructor;
import lombok.NonNull;
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
	public void DoFileUpload( @NonNull MultipartFile[] parts) {
		log.trace("DoFileUpload() invoked.");
		
		log.info("\t+ parts : {}", parts);
		
	} // DoFileUpload

} // end class
