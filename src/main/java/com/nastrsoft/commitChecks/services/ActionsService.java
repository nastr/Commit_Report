package com.nastrsoft.commitChecks.services;

import com.nastrsoft.commitChecks.back_end.Excel;
import com.nastrsoft.commitChecks.back_end.Processor;
import com.nastrsoft.commitChecks.model.request.ActionRequest;
import com.nastrsoft.commitChecks.model.response.ActionResponse;
import com.nastrsoft.commitChecks.repository.ReportsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("actions")
@CrossOrigin
public class ActionsService {
    private static final Logger logger = LoggerFactory.getLogger(ActionsService.class);

    @Autowired
    ReportsRepository reportsRepository;

    @Autowired
    Excel excel;

    @Autowired
    Processor processor;

    @RequestMapping(value = "/{jiraVersion}", method = RequestMethod.GET)
    public void getFile(@PathVariable
                                String jiraVersion, HttpServletResponse response) {

//        Excel excel = new Excel();
        ByteArrayOutputStream is = excel.prepareFile(jiraVersion);

        try {
            is.writeTo(response.getOutputStream());
            response.setContentType("application/x-msdownload");
            logger.debug("getFile\t" + is.size() + "\t" + response.getBufferSize());
//            response.setHeader("Content-disposition", "attachment; filename=fileName.xls");
            response.flushBuffer();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ActionResponse> generateRelease(
            @RequestBody
                    ActionRequest actionRequest) {
        logger.debug("generateRelease\t" + actionRequest);
        Long releaseId = reportsRepository.findIdByJiraVersion(actionRequest.getJira_version());

//        Processor processor = Processor.getInstance(releaseId);
        ResponseEntity<ActionResponse> response = processor.getData(releaseId);
        logger.debug("generateRelease\t" + response);
        return response;
    }
}
