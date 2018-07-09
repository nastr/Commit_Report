package com.nastrsoft.commitChecks.convertor;

import com.nastrsoft.commitChecks.entity.CommitsEntity;
import com.nastrsoft.commitChecks.model.response.CommitsResponse;
import org.springframework.core.convert.converter.Converter;

import java.util.function.Function;

public class CommitsEntiryToCommitsResponseConverter implements
        Function<CommitsEntity, CommitsResponse>,
        Converter<CommitsEntity, CommitsResponse> {
    @Override
    public CommitsResponse apply(CommitsEntity entity) {
        CommitsResponse response = new CommitsResponse();
        if (entity == null)
            return response;
        response.setRevision(entity.getRevision());
        response.setAuthor(entity.getAuthor());
        response.setDate(entity.getDate());
        response.setMessage(entity.getMessage());
        response.setArtifact(entity.getArtifact());
        response.setSvn_path(entity.getSvn_path());
        response.setArtf_calc(entity.getArtf_calc());
        response.setRelease(entity.getRevision());
        response.setType(entity.getType());
        return response;
    }

    @Override
    public CommitsResponse convert(CommitsEntity entity) {
        return apply(entity);
    }
}
