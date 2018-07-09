package com.nastrsoft.commitChecks.convertor;

import com.nastrsoft.commitChecks.entity.CorrectionsEntity;
import com.nastrsoft.commitChecks.model.response.CorrectionsResponse;
import org.springframework.core.convert.converter.Converter;

import java.util.function.Function;

public class CorrectionsEntityToCorrectionsResponseConverter implements
        Function<CorrectionsEntity, CorrectionsResponse>,
        Converter<CorrectionsEntity, CorrectionsResponse> {

    @Override
    public CorrectionsResponse apply(CorrectionsEntity entity) {

        CorrectionsResponse response = new CorrectionsResponse();
        if (entity == null)
            return response;
        response.setCommitsRevision(entity.getCommits_revision());
        response.setRepositoryName(entity.getRepository_name());
        response.setJustification(entity.getJustification());
        response.setDate(entity.getUpdated());
        response.setJustificationAuthor(entity.getAuthor());
        return response;
    }

    @Override
    public CorrectionsResponse convert(CorrectionsEntity entity) {
        return apply(entity);
    }
}
