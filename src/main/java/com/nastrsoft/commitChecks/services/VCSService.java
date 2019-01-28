package com.nastrsoft.commitChecks.services;

import com.nastrsoft.commitChecks.entity.VCSEntity;
import com.nastrsoft.commitChecks.model.response.VCSResponse;
import com.nastrsoft.commitChecks.repository.VCSRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("repositories")
@CrossOrigin
public class VCSService {

    private static final Logger logger = LoggerFactory.getLogger(VCSService.class);

    @Autowired
    private VCSRepository VCSRepository;

    @Autowired
    private ConversionService conversionService;

    @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<VCSResponse> listRepositories() {
        List<VCSEntity> reposEntities = StreamSupport.stream(VCSRepository.findAll().spliterator(), false).collect(Collectors.toList());
        VCSResponse response = conversionService.convert(reposEntities, VCSResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /*@RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RepositoryResponse> createRepository(
            @RequestBody
                    RepositoryRequest releasesRequest) {
        LocalDate date = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        RepositoryEntity repositoryEntity = new RepositoryEntity(
                releasesRequest.getJira_version(), "DRAFT", releasesRequest.getRelease_name(), date);
        reposRepository.save(repositoryEntity);
        ReportResponse ReportResponse = conversionService.convert(repositoryEntity, ReportResponse.class);
        logger.debug("createRepository\t" + repositoryEntity);
        return new ResponseEntity<>(ReportResponse, HttpStatus.CREATED);
    }*/


}
