package com.younes.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.younes.helper.ZXingHelper;
import com.younes.service.ProductService;
import com.younes.util.MediaTypeUtils;


@Controller
@RequestMapping(value = {"/product","/"})
public class ProductController {

	@Value("${uploadDir}")
	private String uploadFolder;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
    private ServletContext servletContext;
	
	@GetMapping
	public String index(HttpServletRequest request,Model model) {
		 productService = new ProductService();
		 
		model.addAttribute("products", productService.findAll());
		return "index";
	}
	
	@GetMapping("/download-qrcode/{id}")
	public ResponseEntity<InputStreamResource> download(@PathVariable("id") String fileName,
			HttpServletRequest request,HttpServletResponse response,
			Model model) throws Exception {
		String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
		 try {
			   File dir = new File(uploadDirectory);
				  if (!dir.exists()) {
					dir.mkdirs();
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
        String filename=fileName+"qrcode.png";
		
		// Load file as Resource
		   
				String filePath = Paths.get(uploadDirectory, filename).toString();
				MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, filename);
				File thumb=new File(filePath);
				 InputStreamResource resource;
				resource = new InputStreamResource(new FileInputStream(thumb));
		
		//response.setContentLength();
		model.addAttribute("fileName", filename);
        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentLength(thumb.length())
                .body(resource);
	}

	@GetMapping("/download-barcode/{id}")
	public ResponseEntity<InputStreamResource> downloadbarcode(@PathVariable("id") String fileName,
			HttpServletRequest request,HttpServletResponse response,
			Model model) throws Exception {
		String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
		 try {
			   File dir = new File(uploadDirectory);
				  if (!dir.exists()) {
					dir.mkdirs();
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
        String filename=fileName+"barcode.png";
		
		// Load file as Resource
		   
				String filePath = Paths.get(uploadDirectory, filename).toString();
				MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, filename);
				File thumb=new File(filePath);
				 InputStreamResource resource;
				resource = new InputStreamResource(new FileInputStream(thumb));
		
		//response.setContentLength();
		model.addAttribute("fileName", filename);
        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentLength(thumb.length())
                .body(resource);
	}
	
	@GetMapping("/barcode/{id}")
	public void barcode(@PathVariable("id") String id,
			HttpServletResponse response,HttpServletRequest request) throws Exception {
		
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(ZXingHelper.getBarCodeImage(id, 250, 200));
		outputStream.flush();
		outputStream.close();
		
		response.setContentType("image/png");
		String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
		ZXingHelper.generateBarCodeImage(id, 300, 200, uploadDirectory+"/"+id+"barcode.png");
		
	}
	
	
	@GetMapping("qrcode/{id}")
	public void qrcode(@PathVariable("id") String id,
			HttpServletResponse response,HttpServletRequest request) throws Exception {
		response.setContentType("image/png");
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(ZXingHelper.getQRCodeImage(id, 200, 200));
		outputStream.flush();
		outputStream.close();
		String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
		ZXingHelper.generateQRCodeImage(id, 250, 200, uploadDirectory+"/"+id+"qrcode.png");
		
	}

}
