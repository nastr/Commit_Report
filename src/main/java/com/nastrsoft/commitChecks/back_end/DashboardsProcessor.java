package com.nastrsoft.commitChecks.back_end;

import com.nastrsoft.commitChecks.back_end.entity.DashboardSummaryEntity;
import com.nastrsoft.commitChecks.back_end.entity.DashboardTableEntity;
import com.nastrsoft.commitChecks.entity.CommitsEntity;
import com.nastrsoft.commitChecks.entity.CorrectionsEntity;
import com.nastrsoft.commitChecks.entity.JirasEntity;
import com.nastrsoft.commitChecks.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Component
public class DashboardsProcessor {
    private final Logger logger = LoggerFactory.getLogger(DashboardsProcessor.class);
    @Autowired
    private CommitsRepository commitsRepository;

    @Autowired
    private JirasRepository jirasRepository;

    @Autowired
    private CorrectionsRepository correctionsRepository;

    @Autowired
    private ReportsRepository reportsRepository;

    public DashboardSummaryEntity getDashboardSummary(String jiraVersion) {
        Long releaseId = reportsRepository.findIdByJiraVersion(jiraVersion);
        List<CommitsEntity> commitsEntities = commitsRepository.findByReleaseId(releaseId);
        List<CorrectionsEntity> correctionsEntity = correctionsRepository.findByReleaseId(releaseId);
        List<JirasEntity> jirasEntities = jirasRepository.findByReleaseId(releaseId);
        int notInScoupeAmount = prepareDashboardTable(jiraVersion, commitsEntities, jirasEntities, correctionsEntity).size();
        return getDashboardSummary(commitsEntities.size(), correctionsEntity.size(), notInScoupeAmount);
    }

    public DashboardSummaryEntity getDashboardSummary(int total, int justified, int notInScope) {
        int inScope = total - (justified + notInScope);
        return new DashboardSummaryEntity(inScope, notInScope, justified);
    }

    public List<DashboardSummaryEntity> getListDashboardSummary(int total, int justified, int notInScope) {
        int inScope = total - (justified + notInScope);
        return asList(new DashboardSummaryEntity(inScope, notInScope, justified));
    }

    public List<DashboardTableEntity> prepareDashboardTable(String jiraVersion) {
        Long releaseId = reportsRepository.findIdByJiraVersion(jiraVersion);
        List<CommitsEntity> commitsEntities = commitsRepository.findByReleaseId(releaseId);
        List<JirasEntity> jirasEntities = jirasRepository.findByReleaseId(releaseId);
        List<CorrectionsEntity> correctionsEntities = correctionsRepository.findByReleaseId(releaseId);
        return prepareDashboardTable(jiraVersion, commitsEntities, jirasEntities, correctionsEntities);
    }

    public List<DashboardTableEntity> prepareDashboardTable(String jiraVersion, List<CommitsEntity> commitsEntities,
                                                                   List<JirasEntity> jirasEntities, List<CorrectionsEntity> correctionsEntity) {
        if (commitsEntities == null || commitsEntities.size() == 0) {
            logger.error("commitsEntities empty");
            return null;
        }
        if (jirasEntities == null || jirasEntities.size() == 0) {
            logger.error("jirasEntities empty");
            return null;
        }
        if (correctionsEntity == null) {
            logger.error("commitsEntities null");
            return null;
        }

        return commitsEntities.stream()
                .map(e -> {
                    CorrectionsEntity correction = correctionsEntity.stream().filter(c ->
                            StringUtils.containsIgnoreCase(c.getCommits_revision(), e.getRevision()) &&
                                    StringUtils.containsIgnoreCase(c.getRepository_name(), e.getSvn_path())).findFirst().orElse(null);
                    JirasEntity entity = jirasEntities.stream().filter(j -> StringUtils.equalsIgnoreCase(j.getKey(), e.getArtf_calc())).findFirst()
                            .orElse(null);
                    String parent = entity.getParent();
                    String title = entity.getTitle();
                    if (StringUtils.isNotEmpty(parent))
                        entity = jirasEntities.stream().filter(j -> j.getKey() == parent).findFirst().get();
                    if (StringUtils.containsIgnoreCase(entity.getType(), "defect")) {
                        if (!StringUtils.containsIgnoreCase(entity.getAffects_versions(), jiraVersion) && correction == null)
                            return new DashboardTableEntity(e.getRevision(), e.getSvn_path(), e.getMessage(), e.getArtf_calc(), entity.getAffects_versions(),
                                    title, entity.getKey(), "Not in scope", entity.getType(), entity.getStatus(), e.getAuthor());
                        else return null;
                    } else {
                        if (!StringUtils.containsIgnoreCase(entity.getFix_versions(), jiraVersion) && correction == null)
                            return new DashboardTableEntity(e.getRevision(), e.getSvn_path(), e.getMessage(), e.getArtf_calc(), entity.getAffects_versions(),
                                    title, entity.getKey(), "Not in scope", entity.getType(), entity.getStatus(), e.getAuthor());
                        else return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
