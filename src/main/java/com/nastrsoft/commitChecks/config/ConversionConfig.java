package com.nastrsoft.commitChecks.config;

import com.nastrsoft.commitChecks.convertor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConversionConfig {
    private Set<Converter> getConverters() {
        Set<Converter> converters = new HashSet<>();
        converters.add(new BranchesEtityToBranchesResponseConverter());
        converters.add(new CommitsEntiryToCommitsResponseConverter());
        converters.add(new CorrectionsEntityToCorrectionsResponseConverter());
        converters.add(new DashboardSummaryEntityToDashboardSummaryResponseConverter());
        converters.add(new DashboardTableEntityToDashboardTableResponseConverter());
        converters.add(new JirasEntityToJirasResponseConverter());
        converters.add(new ReportEntityToReportResponseConverter());
        converters.add(new ReposEntityToRepositoriesResponseConverter());
        return converters;
    }

    /*private Set<Function> getConverters() {
        Set<Function> converters = new HashSet<>();
        converters.add(new ReportEntityToReportResponseConverter());
        converters.add(new BranchesEtityToBranchesResponseConverter());

        return converters;
    }*/

    @Bean
    public ConversionService conversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(getConverters());
        bean.afterPropertiesSet();

        return bean.getObject();
    }
}
