package com.zdharmawan.search;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneIndexer {

    private static final String pathToIndex = "";
    
    public boolean openIndex() {
        IndexWriter indexWriter = null;
        try {
            Directory dir = FSDirectory.open(new File(pathToIndex));
            Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_48);

            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_48, analyzer);
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            indexWriter = new IndexWriter(dir, iwc);
            
            return true;
        } catch (Exception e) {
            System.out.println("Threw an exception trying to open the index for writing: " + e.getClass() + " :: " + e.getMessage());
            return false;
        }
    }
    
    public static void main(String[] args) {
        LuceneIndexer luceneIndexer = new LuceneIndexer();
        luceneIndexer.openIndex();
    }
}
