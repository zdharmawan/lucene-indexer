package com.zdharmawan.indexer;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuceneIndexer {

    private static String pathToIndex;
    private static final Logger logger = LoggerFactory.getLogger(LuceneIndexer.class);
    
    public IndexWriter createIndex(String path) {
        if (path != null && !path.equals("")) {
            pathToIndex = path;
        } else {
            pathToIndex = "data";
        }
        
        try (Directory dir = FSDirectory.open(new File(pathToIndex))) {
            Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_48);
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_48, analyzer);
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            try (IndexWriter indexWriter =  new IndexWriter(dir, iwc)) {
                return indexWriter;
            }
        } catch (IOException e) {
            logger.error("Cannot create index: " + e.getClass() + " :: " + e.getMessage());
            return null;
        }
    }

    public void commitIndex(IndexWriter indexWriter) {
        try {
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException ex) {
            logger.error("Cannot commit or close the index: " + ex.getClass() + " :: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        LuceneIndexer luceneIndexer = new LuceneIndexer();
        luceneIndexer.createIndex("");
    }
}
