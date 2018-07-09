package com.nastrsoft.commitChecks.back_end;

import com.nastrsoft.commitChecks.entity.BranchesEntity;
import com.nastrsoft.commitChecks.entity.CommitsEntity;

import java.util.List;
import java.util.Set;

public interface SourceRepo {
    List<CommitsEntity> getCommitsEtity();
    List<BranchesEntity> getBranchesEntity();
    Set<String> getData(Set<String> repos);
    Set<String> getData();
}
