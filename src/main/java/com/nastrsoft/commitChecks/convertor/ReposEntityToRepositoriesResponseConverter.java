package com.nastrsoft.commitChecks.convertor;

import com.nastrsoft.commitChecks.entity.VCSEntity;
import com.nastrsoft.commitChecks.model.response.VCSResponse;
import org.springframework.core.convert.converter.Converter;

import java.util.function.Function;

public class ReposEntityToRepositoriesResponseConverter implements
        Function<VCSEntity, VCSResponse>,
        Converter<VCSEntity, VCSResponse> {
    @Override
    public VCSResponse apply(VCSEntity reposEntity) {
        VCSResponse response = new VCSResponse();
        response.setName(reposEntity.getName());
        response.setType(reposEntity.getType());
        return response;
    }

    @Override
    public VCSResponse convert(VCSEntity reposEntity) {
        return apply(reposEntity);
    }
}
