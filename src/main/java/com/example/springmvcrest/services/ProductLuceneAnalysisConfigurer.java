package com.example.springmvcrest.services;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;


public class ProductLuceneAnalysisConfigurer implements LuceneAnalysisConfigurer {
    @Override
    @Bean
    public void configure(LuceneAnalysisConfigurationContext luceneAnalysisConfigurationContext) {
        luceneAnalysisConfigurationContext.analyzer( "product_name_analyzer" ).custom()
                .tokenizer( StandardTokenizerFactory.class )
                .tokenFilter( LowerCaseFilterFactory.class )
                .tokenFilter( ASCIIFoldingFilterFactory.class );


        luceneAnalysisConfigurationContext.analyzer( "product_description_analyzer" ).custom()
                .tokenizer( StandardTokenizerFactory.class )
                .tokenFilter( LowerCaseFilterFactory.class );

        luceneAnalysisConfigurationContext.analyzer( "tags_analyzer" ).custom()
                .tokenizer( StandardTokenizerFactory.class )
                .tokenFilter( LowerCaseFilterFactory.class )
                .tokenFilter( SnowballPorterFilterFactory.class )
                .param( "language", "English" )
                .tokenFilter( ASCIIFoldingFilterFactory.class );

    }
}
