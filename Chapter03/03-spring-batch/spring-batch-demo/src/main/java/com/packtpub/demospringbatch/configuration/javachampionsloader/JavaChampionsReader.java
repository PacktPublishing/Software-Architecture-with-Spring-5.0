package com.packtpub.demospringbatch.configuration.javachampionsloader;

import com.packtpub.demospringbatch.domain.JavaChampion;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class JavaChampionsReader extends FlatFileItemReader<JavaChampion> {

    private String fileName;

    public JavaChampionsReader(String fileName) {
        this.fileName = fileName;
        initialize();
    }

    private void initialize() {
        Resource resource = new ClassPathResource(fileName);
        setResource(resource);

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_COMMA);
        delimitedLineTokenizer.setNames(new String[]{"firstName", "lastName", "country", "year"});

        BeanWrapperFieldSetMapper<JavaChampion> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(JavaChampion.class);

        DefaultLineMapper<JavaChampion> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        setLineMapper(defaultLineMapper);
    }

}
