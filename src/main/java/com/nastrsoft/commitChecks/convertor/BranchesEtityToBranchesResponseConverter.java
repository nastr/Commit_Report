package com.nastrsoft.commitChecks.convertor;

import com.nastrsoft.commitChecks.entity.BranchesEntity;
import com.nastrsoft.commitChecks.model.response.BranchesResponse;
import org.springframework.core.convert.converter.Converter;

import java.util.function.Function;

public class BranchesEtityToBranchesResponseConverter implements
        Function<BranchesEntity, BranchesResponse>,
        Converter<BranchesEntity, BranchesResponse> {
    @Override
    public BranchesResponse apply(BranchesEntity entity) {
        BranchesResponse response = new BranchesResponse();
        if (entity == null)
            return response;

        response.setRepository_name(entity.getRepository_name());
        response.setPath(entity.getPath());
        response.setCurrent_base_line(entity.getCurrent_base_line());
        response.setTrusted_base_line(entity.getTrusted_base_line());

        return response;
    }

    @Override
    public BranchesResponse convert(BranchesEntity entity) {
        return apply(entity);
    }
}
