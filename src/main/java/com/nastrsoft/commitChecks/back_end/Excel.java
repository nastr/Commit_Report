package com.nastrsoft.commitChecks.back_end;

import com.nastrsoft.commitChecks.back_end.entity.DashboardTableEntity;
import com.nastrsoft.commitChecks.convertor.CorrectionsEntityToCorrectionsResponseConverter;
import com.nastrsoft.commitChecks.entity.*;
import com.nastrsoft.commitChecks.model.response.CorrectionsResponse;
import com.nastrsoft.commitChecks.repository.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@Service
public class Excel {
    private static final Logger logger = LoggerFactory.getLogger(Excel.class);
    private Map<String, List<String>> headers = new HashMap<String, List<String>>() {{
        put("commit", asList("Revision", "Author", "Date", "Message", "Timestamp", "Artifact", "SVN Path", "ARTF CALC", "Release", "MERGED", "IS_MERGE", "TYPE"));
        put("branches", asList("Repository Name", "Path", "Trusted Base Line", "Current Base Line"));
        put("JIRA", asList("Key", "Title", "Type", "Status", "Assignee", "Reporter", "Parent", "Sub-Task", "Affects Versions", "Fix Versions", "Sprint"));
        put("Corrections", asList("Artifact", "Title", "Justification", "Status", "Date", "Author", "commits_revision", "repository_name"));
        put("Dashboard", asList("Artifact", "Release", "Title", "Parent", "Scope", "Type", "Status", "Commits authors", "commitsRevision", "repositoryName", "commitsMessage"));
    }};

    @Autowired
    ReportsRepository reportsRepository;

    @Autowired
    CommitsRepository commitsRepository;

    @Autowired
    JirasRepository jirasRepository;

    @Autowired
    CorrectionsRepository correctionsRepository;

    @Autowired
    BranchesRepository branchesRepository;

    @Autowired
    DashboardsProcessor dashboardsProcessor;

    public Excel() {
    }

    public Excel(Map<String, List<String>> headers) {
        this.headers = new HashMap<>(headers);
    }

    public ByteArrayOutputStream prepareFile(String jiraVersion) {
        Long releaseId = reportsRepository.findIdByJiraVersion(jiraVersion);
        List<CommitsEntity> commitsEntity = commitsRepository.findByReleaseId(releaseId);
        List<JirasEntity> jirasEntities = jirasRepository.findByReleaseId(releaseId);
        List<CorrectionsEntity> correctionsEntities = correctionsRepository.findByReleaseId(releaseId);
        List<BranchesEntity> branchesEntities = branchesRepository.findByReleaseId(releaseId);
        List<DashboardTableEntity> dashboardTableEntities = dashboardsProcessor.prepareDashboardTable(jiraVersion, commitsEntity, jirasEntities, correctionsEntities);
        return prepareFile(commitsEntity, branchesEntities, jirasEntities, correctionsEntities, dashboardTableEntities);
    }


    public ByteArrayOutputStream prepareFile(List<CommitsEntity> commitsEntities, List<BranchesEntity> branchesEntities,
                                             List<JirasEntity> jirasEntities, List<CorrectionsEntity> correctionsEntities, List<DashboardTableEntity> dashboardTableEntities) {
        Workbook workbook = new XSSFWorkbook();

        workbook.createSheet("commit");
        workbook.getSheet("commit").createRow(0);
        IntStream.range(0, headers.get("commit").size()).forEach(cellNum ->
                workbook.getSheet("commit").getRow(0).createCell(cellNum).setCellValue(headers.get("commit").get(cellNum)));
        IntStream.range(0, commitsEntities.size()).forEach(rowNum -> {
            int cellNum = 0;
            Row row = workbook.getSheet("commit").createRow(rowNum + 1);
            row.createCell(cellNum++).setCellValue(commitsEntities.get(rowNum).getRevision());  //Revision
            row.createCell(cellNum++).setCellValue(commitsEntities.get(rowNum).getAuthor());    //Author
            row.createCell(cellNum++).setCellValue(Date.from(commitsEntities.get(rowNum).getDate().atStartOfDay(ZoneId.systemDefault()).toInstant())); //Date
            row.createCell(cellNum++).setCellValue(commitsEntities.get(rowNum).getMessage());   //Message
            row.createCell(cellNum++).setCellValue(Date.from(commitsEntities.get(rowNum).getDate().atStartOfDay(ZoneId.systemDefault()).toInstant())); //Timestamp
            row.createCell(cellNum++).setCellValue(commitsEntities.get(rowNum).getArtifact());  //Artifact
            row.createCell(cellNum++).setCellValue(commitsEntities.get(rowNum).getSvn_path());  //SVN Path
            row.createCell(cellNum++).setCellValue(commitsEntities.get(rowNum).getArtf_calc()); //ARTF CALC
            row.createCell(cellNum++).setCellValue(commitsEntities.get(rowNum).getRelease());   //Release
            row.createCell(cellNum++).setCellValue(false);                                      //MERGED
            row.createCell(cellNum++).setCellValue(false);                                      //IS_MERGE
            row.createCell(cellNum++).setCellValue(commitsEntities.get(rowNum).getType());      //TYPE
        });

        workbook.createSheet("branches");
        workbook.getSheet("branches").createRow(0);
        IntStream.range(0, headers.get("branches").size()).forEach(cellNum ->
                workbook.getSheet("branches").getRow(0).createCell(cellNum).setCellValue(headers.get("branches").get(cellNum)));
        IntStream.range(0, branchesEntities.size()).forEach(rowNum -> {
            int cellNum = 0;
            Row row = workbook.getSheet("branches").createRow(rowNum + 1);
            row.createCell(cellNum++).setCellValue(branchesEntities.get(rowNum).getRepository_name());    //Repository Name
            row.createCell(cellNum++).setCellValue(branchesEntities.get(rowNum).getPath());               //Path
            row.createCell(cellNum++).setCellValue(branchesEntities.get(rowNum).getTrusted_base_line());  //Trusted Base Line
            row.createCell(cellNum++).setCellValue(branchesEntities.get(rowNum).getCurrent_base_line());  //Current Base Line
        });

        workbook.createSheet("JIRA");
        workbook.getSheet("JIRA").createRow(0);
        IntStream.range(0, headers.get("JIRA").size()).forEach(cellNum ->
                workbook.getSheet("JIRA").getRow(0).createCell(cellNum).setCellValue(headers.get("JIRA").get(cellNum)));
        IntStream.range(0, jirasEntities.size()).forEach(rowNum -> {
            int cellNum = 0;
            Row row = workbook.getSheet("JIRA").createRow(rowNum + 1);
            row.createCell(cellNum++).setCellValue(jirasEntities.get(rowNum).getKey());    //Key
            row.createCell(cellNum++).setCellValue(jirasEntities.get(rowNum).getTitle());    //Title
            row.createCell(cellNum++).setCellValue(jirasEntities.get(rowNum).getType());    //Type
            row.createCell(cellNum++).setCellValue(jirasEntities.get(rowNum).getStatus());    //Status
            row.createCell(cellNum++).setCellValue(jirasEntities.get(rowNum).getAssignee());    //Assignee
            row.createCell(cellNum++).setCellValue(jirasEntities.get(rowNum).getReporter());    //Reporter
            row.createCell(cellNum++).setCellValue(jirasEntities.get(rowNum).getParent());    //Parent
            row.createCell(cellNum++).setCellValue(jirasEntities.get(rowNum).getSub_task());    //Sub-Task
            row.createCell(cellNum++).setCellValue(jirasEntities.get(rowNum).getAffects_versions());    //Affects Versions
            row.createCell(cellNum++).setCellValue(jirasEntities.get(rowNum).getFix_versions());    //Fix Versions
            row.createCell(cellNum++).setCellValue(jirasEntities.get(rowNum).getSprint());    //Sprint
        });

        workbook.createSheet("Corrections");
        workbook.getSheet("Corrections").createRow(0);
        IntStream.range(0, headers.get("Corrections").size()).forEach(cellNum ->
                workbook.getSheet("Corrections").getRow(0).createCell(cellNum).setCellValue(headers.get("Corrections").get(cellNum)));
//        List<CorrectionsResponse> responses = correctionsEntities.stream().map(new CorrectionsEntityToCorrectionsResponseConverter(dashboardTableEntities)).collect(toList());
        IntStream.range(0, correctionsEntities.size()).forEach(rowNum -> {
            int cellNum = 0;
            Row row = workbook.getSheet("Corrections").createRow(rowNum + 1);
            row.createCell(cellNum++).setCellValue(correctionsEntities.get(rowNum).getRepository_name());    //Artifact
            row.createCell(cellNum++).setCellValue(correctionsEntities.get(rowNum).getCommits_revision());    //Title
            row.createCell(cellNum++).setCellValue(correctionsEntities.get(rowNum).getJustification());    //Justification
            row.createCell(cellNum++).setCellValue(correctionsEntities.get(rowNum).getAuthor());    //Status
            row.createCell(cellNum++).setCellValue(Date.from(correctionsEntities.get(rowNum).getUpdated().atStartOfDay(ZoneId.systemDefault()).toInstant()));    //Date
        });

        workbook.createSheet("Dashboard");
        workbook.getSheet("Dashboard").createRow(0);
        IntStream.range(0, headers.get("Dashboard").size()).forEach(cellNum ->
                workbook.getSheet("Dashboard").getRow(0).createCell(cellNum).setCellValue(headers.get("Dashboard").get(cellNum)));
        IntStream.range(0, dashboardTableEntities.size()).forEach(rowNum -> {
            int cellNum = 0;
            Row row = workbook.getSheet("Dashboard").createRow(rowNum + 1);
            row.createCell(cellNum++).setCellValue(dashboardTableEntities.get(rowNum).getJiraArtifact());    //Artifact
            row.createCell(cellNum++).setCellValue(dashboardTableEntities.get(rowNum).getJiraVersion());    //Release
            row.createCell(cellNum++).setCellValue(dashboardTableEntities.get(rowNum).getJiraTitle());    //Title
            row.createCell(cellNum++).setCellValue(dashboardTableEntities.get(rowNum).getJiraParentArtifact());    //Parent
            row.createCell(cellNum++).setCellValue(dashboardTableEntities.get(rowNum).getScope());    //Scope
            row.createCell(cellNum++).setCellValue(dashboardTableEntities.get(rowNum).getJiraType());    //Type
            row.createCell(cellNum++).setCellValue(dashboardTableEntities.get(rowNum).getStatus());    //Status
            row.createCell(cellNum++).setCellValue(dashboardTableEntities.get(rowNum).getCommitsAuthor());    //Commits authors
            row.createCell(cellNum++).setCellValue(dashboardTableEntities.get(rowNum).getCommitsRevision());    //commitsRevision
            row.createCell(cellNum++).setCellValue(dashboardTableEntities.get(rowNum).getRepositoryName());    //repositoryName
            row.createCell(cellNum++).setCellValue(dashboardTableEntities.get(rowNum).getCommitsMessage());    //commitsMessage
        });

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        try {
            workbook.write(byteArray);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return byteArray;

    }

}
