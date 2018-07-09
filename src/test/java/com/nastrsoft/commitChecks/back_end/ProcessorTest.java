package com.nastrsoft.commitChecks.back_end;

import com.nastrsoft.commitChecks.back_end.entity.DashboardSummaryEntity;
import com.nastrsoft.commitChecks.back_end.entity.DashboardTableEntity;
import com.nastrsoft.commitChecks.entity.CommitsEntity;
import com.nastrsoft.commitChecks.entity.CorrectionsEntity;
import com.nastrsoft.commitChecks.entity.JirasEntity;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class ProcessorTest {

    /*@Before
    public void setUp() {
        Application.main(new String[]{""});
    }*/

 /*   @Test
    public void getData() {
    }*/

    @Test
    public void prepareDashboardTable() {
        String jiraVersion = "18-02";
        Long releaseId = 123L;
        LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        List<CommitsEntity> commitsEntities = Stream.of(
                new CommitsEntity(releaseId, "rev1", "author", localDate, "message", "", "repo1", "key-789", jiraVersion, "release/branch"),
                new CommitsEntity(releaseId, "rev2", "author", localDate, "message", "", "repo1", "key-901", jiraVersion, "release/branch"),
                new CommitsEntity(releaseId, "rev3", "author", localDate, "message", "", "repo1", "key-230", jiraVersion, "release/branch"),
                new CommitsEntity(releaseId, "rev4", "author", localDate, "message", "", "repo1", "key-560", jiraVersion, "release/branch"),
                new CommitsEntity(releaseId, "rev5", "author", localDate, "message", "", "repo1", "key-234", jiraVersion, "release/branch"),
                new CommitsEntity(releaseId, "rev6", "author", localDate, "message", "", "repo1", "key-456", jiraVersion, "release/branch"),
                new CommitsEntity(releaseId, "rev7", "author", localDate, "message", "", "repo1", "Error", jiraVersion, "release/branch"),
                new CommitsEntity(releaseId, "rev9", "author", localDate, "message", "", "repo1", "Error", jiraVersion, "release/branch"),
                new CommitsEntity(releaseId, "rev8", "author", localDate, "message", "", "repo1", "key-4814", jiraVersion, "release/branch")
        ).collect(Collectors.toList());

        List<JirasEntity> jirasEntities = Stream.of(
                new JirasEntity(releaseId, "key-678", "title", "defect", "in progress", "user name",
                        "reporter Name", "", "key-789", "18-02", "fix", "sprint"),
                new JirasEntity(releaseId, "key-789", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-678", "", "", "fix", "sprint"),
                new JirasEntity(releaseId, "key-890", "title", "story", "in progress", "user name",
                        "reporter Name", "", "key-901", "affected", "18-02", "sprint"),
                new JirasEntity(releaseId, "key-901", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-890", "", "affected", "", "sprint"),

                new JirasEntity(releaseId, "key-120", "title", "defect", "in progress", "user name",
                        "reporter Name", "", "key-230", "17-12", "fix", "sprint"),
                new JirasEntity(releaseId, "key-230", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-120", "", "", "fix", "sprint"),
                new JirasEntity(releaseId, "key-340", "title", "story", "in progress", "user name",
                        "reporter Name", "", "key-560", "affected", "17-12", "sprint"),
                new JirasEntity(releaseId, "key-560", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-340", "", "affected", "", "sprint"),

                new JirasEntity(releaseId, "key-123", "title", "defect", "in progress", "user name",
                        "reporter Name", "", "key-234", "17-11", "fix", "sprint"),
                new JirasEntity(releaseId, "key-234", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-123", "", "", "fix", "sprint"),
                new JirasEntity(releaseId, "key-345", "title", "story", "in progress", "user name",
                        "reporter Name", "", "key-456", "affected", "17-11", "sprint"),
                new JirasEntity(releaseId, "key-456", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-345", "", "affected", "", "sprint"),

                new JirasEntity(releaseId, "Error", "Undefined JIRA", "", "", "", "", "", "", "", "", ""),
                new JirasEntity(releaseId, "key-4814", "Undefined JIRA", "", "", "", "", "", "", "", "", "")
        ).collect(Collectors.toList());

        List<CorrectionsEntity> correctionsEntities = Stream.of(
                new CorrectionsEntity(releaseId, "repo1", "rev5", "justification1", "Autor", localDate),
                new CorrectionsEntity(releaseId, "repo1", "rev6", "justification2", "Autor", localDate)
        ).collect(Collectors.toList());

        Processor processor = Processor.getInstance(1L);
        DashboardsProcessor dashboardsProcessor = new DashboardsProcessor();
        List<DashboardTableEntity> dashboardTable = dashboardsProcessor.prepareDashboardTable(jiraVersion, commitsEntities, jirasEntities, correctionsEntities);
        assertNotNull(dashboardTable);
        assertEquals(5, dashboardTable.size());

        DashboardSummaryEntity dashboardSummary = dashboardsProcessor.getDashboardSummary(commitsEntities.size(), correctionsEntities.size(), dashboardTable.size());
        assertEquals(2, dashboardSummary.getIn_scope());
        assertEquals(5, dashboardSummary.getNot_in_scope());
        assertEquals(2, dashboardSummary.getJustified());
    }
}