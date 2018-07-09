package com.nastrsoft.commitChecks.services;

import com.nastrsoft.commitChecks.back_end.DashboardsProcessor;
import com.nastrsoft.commitChecks.back_end.entity.DashboardSummaryEntity;
import com.nastrsoft.commitChecks.back_end.entity.DashboardTableEntity;
import com.nastrsoft.commitChecks.convertor.*;
import com.nastrsoft.commitChecks.entity.*;
import com.nastrsoft.commitChecks.model.request.CorrectionRequest;
import com.nastrsoft.commitChecks.model.response.*;
import com.nastrsoft.commitChecks.repository.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("release")
@CrossOrigin
public class ReleaseService {
    private static final Logger logger = LoggerFactory.getLogger(ReleaseService.class);
    @Autowired
    private ReportsRepository reportsRepository;

    @Autowired
    private BranchesRepository branchesRepository;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    CommitsRepository commitsRepository;

    @Autowired
    JirasRepository jirasRepository;

    @Autowired
    CorrectionsRepository correctionsRepository;

    @Autowired
    DashboardsProcessor dashboardsProcessor;

    @RequestMapping(path = "/{jiraVersion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<JSONObject> getReleaseData(
            @PathVariable
                    String jiraVersion) {
        Long releaseId = reportsRepository.findIdByJiraVersion(jiraVersion);

        List<CommitsEntity> commitsEntity = commitsRepository.findByReleaseId(releaseId);
        List<JirasEntity> jirasEntities = jirasRepository.findByReleaseId(releaseId);
        List<CorrectionsEntity> correctionsEntities = correctionsRepository.findByReleaseId(releaseId);
        List<BranchesEntity> branchesEntities = branchesRepository.findByReleaseId(releaseId);
        List<DashboardTableEntity> dashboardTableEntities = dashboardsProcessor.prepareDashboardTable(jiraVersion, commitsEntity, jirasEntities, correctionsEntities);
        DashboardSummaryEntity dashboardSummaryEntities = dashboardsProcessor.getDashboardSummary(commitsEntity.size(), correctionsEntities.size(), dashboardTableEntities.size());

        if (dashboardSummaryEntities == null)
            return new ResponseEntity<>(new JSONObject(), HttpStatus.INTERNAL_SERVER_ERROR);

        JSONObject object = new JSONObject(new HashMap<String, JSONArray>());
        object.put("commits", commitsEntity.stream().map(new CommitsEntiryToCommitsResponseConverter()).collect(toList()));
        object.put("jiras", jirasEntities.stream().map(new JirasEntityToJirasResponseConverter()).collect(toList()));
        object.put("corrections", correctionsEntities.stream().map(new CorrectionsEntityToCorrectionsResponseConverter()).collect(toList()));
        object.put("branches", branchesEntities.stream().map(new BranchesEtityToBranchesResponseConverter()).collect(toList()));
        object.put("dashboardTable", dashboardTableEntities.stream().map(new DashboardTableEntityToDashboardTableResponseConverter()).collect(toList()));
        object.put("dashboardSummary", conversionService.convert(dashboardSummaryEntities, DashboardSummaryResponse.class));
        logger.info("getReleaseData\n" + object.toJSONString());
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> pushJustification(
            @RequestBody
                    CorrectionRequest correctionRequest) {
        Long releaseId = reportsRepository.findIdByJiraVersion(correctionRequest.getReportJiraVersion());
        LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        CorrectionsEntity correctionsEntity = new CorrectionsEntity(
                releaseId,
                correctionRequest.getRepositoryName(),
                correctionRequest.getCommitsRevision(),
                correctionRequest.getJustification(),
                correctionRequest.getJustificationAuthor(),
                localDate
        );
        correctionsRepository.save(correctionsEntity);
        logger.info("pushJustification" + correctionRequest.toString());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*@RequestMapping(path = "/{releaseId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<JSONObject>> getBranchesByreleaseId(
            @PathVariable
                    Long releaseId) {

        return null;
    }*/
}
