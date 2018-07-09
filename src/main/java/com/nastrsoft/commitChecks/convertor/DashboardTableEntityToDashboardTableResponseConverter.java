package com.nastrsoft.commitChecks.convertor;

import com.nastrsoft.commitChecks.back_end.entity.DashboardTableEntity;
import com.nastrsoft.commitChecks.model.response.DashboardTableResponse;
import org.springframework.core.convert.converter.Converter;

import java.util.function.Function;

public class DashboardTableEntityToDashboardTableResponseConverter implements
        Function<DashboardTableEntity, DashboardTableResponse>,
        Converter<DashboardTableEntity, DashboardTableResponse> {
    @Override
    public DashboardTableResponse apply(DashboardTableEntity entity) {
        DashboardTableResponse response = new DashboardTableResponse();
        if (entity == null)
            return response;
        response.setCommits_revision(entity.getCommitsRevision());
        response.setRepository_name(entity.getRepositoryName());
        response.setCommits_message(entity.getCommitsMessage());
        response.setCommits_authors(entity.getCommitsAuthor());
        response.setJira_artifact(entity.getJiraArtifact());
        response.setJira_title(entity.getJiraTitle());
        response.setJira_parent_artifact(entity.getJiraParentArtifact());
        response.setJira_type(entity.getJiraType());
        response.setScope(entity.getScope());
        response.setJira_version(entity.getJiraVersion());
        return response;
    }

    @Override
    public DashboardTableResponse convert(DashboardTableEntity entity) {
        return apply(entity);
    }
}
