package com.nastrsoft.commitChecks.convertor;

import com.nastrsoft.commitChecks.entity.JirasEntity;
import com.nastrsoft.commitChecks.model.response.JirasResponse;
import org.springframework.core.convert.converter.Converter;

import java.util.function.Function;

public class JirasEntityToJirasResponseConverter implements
        Function<JirasEntity, JirasResponse>,
        Converter<JirasEntity, JirasResponse> {

    @Override
    public JirasResponse apply(JirasEntity entity) {
        JirasResponse response = new JirasResponse();
        if (entity == null)
            return response;
        response.setKey(entity.getKey());
        response.setTitle(entity.getTitle());
        response.setType(entity.getType());
        response.setStatus(entity.getStatus());
        response.setAssignee(entity.getAssignee());
        response.setReporter(entity.getReporter());
        response.setParent(entity.getParent());
        response.setSub_task(entity.getSub_task());
        response.setAffects_versions(entity.getAffects_versions());
        response.setFix_versions(entity.getFix_versions());
        response.setSprint(entity.getSprint());

        return response;
    }

    @Override
    public JirasResponse convert(JirasEntity entity) {
        return apply(entity);
    }
}
