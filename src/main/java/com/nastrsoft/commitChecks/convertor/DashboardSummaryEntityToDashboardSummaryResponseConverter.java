package com.nastrsoft.commitChecks.convertor;

import com.nastrsoft.commitChecks.back_end.entity.DashboardSummaryEntity;
import com.nastrsoft.commitChecks.model.response.DashboardSummaryResponse;
import org.springframework.core.convert.converter.Converter;

import java.util.function.Function;

public class DashboardSummaryEntityToDashboardSummaryResponseConverter implements
        Function<DashboardSummaryEntity, DashboardSummaryResponse>,
        Converter<DashboardSummaryEntity, DashboardSummaryResponse> {
    @Override
    public DashboardSummaryResponse apply(DashboardSummaryEntity entity) {
        DashboardSummaryResponse response = new DashboardSummaryResponse();
        if (entity == null)
            return response;
        response.setIn_scope(entity.getIn_scope());
        response.setNot_in_scope(entity.getNot_in_scope());
        response.setJustified(entity.getJustified());
        return response;
    }

    @Override
    public DashboardSummaryResponse convert(DashboardSummaryEntity entity) {
        return apply(entity);
    }
}
