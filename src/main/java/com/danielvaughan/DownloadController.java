package com.danielvaughan;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class DownloadController {

    private static final String INTERNAL_FILE = "text.csv";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File file = new File(classloader.getResource(INTERNAL_FILE).getFile());

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
        response.setContentLength((int) file.length());

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
}
