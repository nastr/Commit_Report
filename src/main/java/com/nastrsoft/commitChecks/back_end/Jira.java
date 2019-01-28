package com.nastrsoft.commitChecks.back_end;

import com.nastrsoft.commitChecks.entity.JirasEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.nastrsoft.commitChecks.back_end.Fields.*;

public class Jira {
    private Map<Fields, String> credentials = null;

    public Jira() {
    }

    public Jira(Map<Fields, String> credentials) {
        this.credentials = credentials;
    }

    public List<JirasEntity> getData(Set<String> jiras) {
        return Stream.of(
                new JirasEntity(Long.valueOf(credentials.get(releaseId)), "key-6780", "title", "defect", "in progress", "user name",
                        "reporter Name", "", "key-789", "18-01", "fix", "sprint"),
                new JirasEntity(Long.valueOf(credentials.get(releaseId)), "key-7890", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-678", "", "", "fix", "sprint"),
                new JirasEntity(Long.valueOf(credentials.get(releaseId)), "key-8900", "title", "story", "in progress", "user name",
                        "reporter Name", "", "key-901", "affected", "18-01", "sprint"),
                new JirasEntity(Long.valueOf(credentials.get(releaseId)), "key-9010", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-890", "", "affected", "", "sprint"),

                new JirasEntity(Long.valueOf(credentials.get(releaseId)), "key-1200", "title", "defect", "in progress", "user name",
                        "reporter Name", "", "key-230", "17-12", "fix", "sprint"),
                new JirasEntity(Long.valueOf(credentials.get(releaseId)), "key-2300", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-120", "", "", "fix", "sprint"),
                new JirasEntity(Long.valueOf(credentials.get(releaseId)), "key-3400", "title", "story", "in progress", "user name",
                        "reporter Name", "", "key-560", "affected", "17-12", "sprint"),
                new JirasEntity(Long.valueOf(credentials.get(releaseId)), "key-5600", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-340", "", "affected", "", "sprint"),

                new JirasEntity(Long.valueOf(credentials.get(releaseId)), "key-1230", "title", "defect", "in progress", "user name",
                        "reporter Name", "", "key-234", "17-11", "fix", "sprint"),
                new JirasEntity(Long.valueOf(credentials.get(releaseId)), "key-2340", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-123", "", "", "fix", "sprint"),
                new JirasEntity(Long.valueOf(credentials.get(releaseId)), "key-3450", "title", "story", "in progress", "user name",
                        "reporter Name", "", "key-456", "affected", "17-11", "sprint"),
                new JirasEntity(Long.valueOf(credentials.get(releaseId)), "key-4560", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-345", "", "affected", "", "sprint")
        ).collect(Collectors.toList());
    }

    private class CollectLogs implements Runnable {

        @Override
        public void run() {

        }
    }
}
