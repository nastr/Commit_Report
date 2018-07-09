package com.nastrsoft.commitChecks.convertor;

import com.nastrsoft.commitChecks.entity.ReportEntity;
import com.nastrsoft.commitChecks.model.response.ReportResponse;
import org.springframework.core.convert.converter.Converter;

import java.util.function.Function;

public class ReportEntityToReportResponseConverter implements
        Function<ReportEntity, ReportResponse>,
        Converter<ReportEntity, ReportResponse> {

    @Override
    public ReportResponse apply(ReportEntity entity) {
        ReportResponse response = new ReportResponse();
        if (entity == null)
            return response;
        response.setJira_version(entity.getJira_version());
        response.setStatus(entity.getStatus());
        response.setRelease_name(entity.getRelease_name());
        response.setUpdated(entity.getUpdated());

        return response;
    }

    @Override
    public ReportResponse convert(ReportEntity entity) {
        return apply(entity);
    }
}
