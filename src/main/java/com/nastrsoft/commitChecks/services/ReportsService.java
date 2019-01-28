package com.nastrsoft.commitChecks.services;

import com.nastrsoft.commitChecks.convertor.ReportEntityToReportResponseConverter;
import com.nastrsoft.commitChecks.entity.ReportEntity;
import com.nastrsoft.commitChecks.model.request.ReleasesRequest;
import com.nastrsoft.commitChecks.model.response.ReportResponse;
import com.nastrsoft.commitChecks.repository.ReportsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("reports")
@CrossOrigin
public class ReportsService {
    private static final Logger logger = LoggerFactory.getLogger(ReportsService.class);
    /*@Autowired
    PageableReleasesRepository pageableReleasesRepository;*/

    @Autowired
    private ReportsRepository reportsRepository;

    @Autowired
    private ConversionService conversionService;

    /*@RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<ReportResponse> getAllReleases(Pageable pageable) {

        Page<ReportEntity> pageableReleasesAll = pageableReleasesRepository.findAll(pageable);

        System.out.println(new Date() + "\tgetAllReleases Pageable\n" +
                pageableReleasesAll.getContent().stream().map(e -> e.toString()).collect(Collectors.joining("\n")));
        return pageableReleasesAll.map(new ReportEntityToReportResponseConverter());
    }*/

    @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<ReportResponse> getAllReleases() {

        List<ReportEntity> pageableReleasesAll = StreamSupport.stream(reportsRepository.findAll().spliterator(), false).collect(Collectors.toList());
        return pageableReleasesAll.stream().map(new ReportEntityToReportResponseConverter()).collect(Collectors.toList());
    }

    /*@RequestMapping(path = "/{jira_version}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReportEntity> getReleaseById(
            @PathVariable
                    String jira_version) {
        ReportEntity ReportEntity = reportsRepository.findByJiraVersion(jira_version);
        logger.debug("getReleaseById\t" + ReportEntity);
        return new ResponseEntity<>(ReportEntity, HttpStatus.OK);
    }*/

    @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReportResponse> createRelease(
            @RequestBody
                    ReleasesRequest releasesRequest) {
        LocalDate date = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        ReportEntity reportEntity = new ReportEntity(
                releasesRequest.getJira_version(), "DRAFT", releasesRequest.getRelease_name(), date);
        reportsRepository.save(reportEntity);
        ReportResponse ReportResponse = conversionService.convert(reportEntity, ReportResponse.class);
        logger.debug("createRelease\t" + reportEntity);
        return new ResponseEntity<>(ReportResponse, HttpStatus.CREATED);
    }

    @RequestMapping(path = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReportResponse> updateRelease(
            @RequestBody
                    ReleasesRequest releasesRequest) {
        ReportEntity ReportEntity = reportsRepository.findByJiraVersion(releasesRequest.getJira_version());
        reportsRepository.deleteById(ReportEntity.getId());

        LocalDate date = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        ReportEntity updatedReportEntity = new ReportEntity(
                releasesRequest.getJira_version(), ReportEntity.getStatus(), releasesRequest.getRelease_name(), date);
        reportsRepository.save(updatedReportEntity);
        ReportResponse ReportResponse = conversionService.convert(updatedReportEntity, ReportResponse.class);

        logger.debug("updateRelease\t" + updatedReportEntity);
        return new ResponseEntity<>(ReportResponse, HttpStatus.OK);
    }

    @RequestMapping(path = "/{jira_version}", method = RequestMethod.DELETE)
    public ResponseEntity<ReportResponse> deleteRelease(
            @PathVariable
                    String jira_version) {
        ReportEntity ReportEntity = reportsRepository.findByJiraVersion(jira_version);
        reportsRepository.deleteById(ReportEntity.getId());

        logger.debug("deleteRelease\t" + ReportEntity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
