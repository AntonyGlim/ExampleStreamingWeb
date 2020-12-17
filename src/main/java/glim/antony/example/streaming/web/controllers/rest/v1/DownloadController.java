package glim.antony.example.streaming.web.controllers.rest.v1;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@RestController
@RequestMapping("/download")
public class DownloadController {

    @GetMapping("")
    public ResponseEntity<StreamingResponseBody> download(HttpServletRequest req, final HttpServletResponse resp) {

        log.info("<-<- from '{}'", req.getRequestURL());

        resp.setContentType("application/zip");
        resp.setHeader("Content-Disposition","attachment;filename=sample.zip");

        StreamingResponseBody stream = out -> {

            final String home = System.getProperty("user.dir");
            final File directory = new File(home + File.separator + "files" + File.separator + "examples");
            final ZipOutputStream zipOut = new ZipOutputStream(resp.getOutputStream());

            if (directory.exists() && directory.isDirectory()) {
                try {
                    for (final File file : directory.listFiles()) {
                        final InputStream inputStream = new FileInputStream(file);
                        final ZipEntry zipEntry = new ZipEntry(file.getName());
                        zipOut.putNextEntry(zipEntry);
                        byte[] bytes = new byte[64];
                        int length;
                        while ((length = inputStream.read(bytes)) >= 0) {
                            Thread.sleep(10L);
                            zipOut.write(bytes, 0, length);
                        }
                        inputStream.close();
                    }
                    zipOut.close();
                } catch (final IOException | InterruptedException e) {
                    log.error("Exception while reading and streaming data, message : '{}' ", e.getMessage());
                }
            }
        };
        log.info("steaming response {} ", stream);
        return new ResponseEntity(stream, HttpStatus.OK);
    }
}