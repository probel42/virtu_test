package ru.ibelan.test.backend.services.impl;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.ibelan.test.backend.entities.Person;
import ru.ibelan.test.backend.services.PersonCache;
import ru.ibelan.test.backend.repos.PersonRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class PersonCacheImpl implements PersonCache {
    private static final int LOAD_PAGE_SIZE = 500;
    private static final String[] SEARCH_FIELDS = new String[]{
            "name",
            "surname",
            "patronymic",
            "passportSeries",
            "passportNumber"
    };

    private final PersonRepository personRepository;

    public Directory index;

    public static final Logger LOG = LoggerFactory.getLogger(PersonCacheImpl.class);

    public PersonCacheImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void load() {
        try {
            index = new MMapDirectory(Files.createTempDirectory("persons-cache").toAbsolutePath());
            IndexWriter indexWriter = new IndexWriter(index, new IndexWriterConfig(new WhitespaceAnalyzer()));

            int page = 0;
            List<Person> persons = personRepository.findAll(PageRequest.of(page, LOAD_PAGE_SIZE)).getContent();
            while (!persons.isEmpty()) {
                for (Person person : persons) {
                    addDoc(indexWriter, person);
                }
                persons = personRepository.findAll(PageRequest.of(++page, LOAD_PAGE_SIZE)).getContent();
            }
            indexWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't create search index", e);
        }
    }

    @Override
    public void addPerson(Person person) {
        try {
            IndexWriter indexWriter = new IndexWriter(index, new IndexWriterConfig(new WhitespaceAnalyzer()));
            addDoc(indexWriter, person);
            indexWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't create search index", e);
        }
    }

    @Override
    public List<String> search(String search) {
        List<String> ids = new ArrayList<>();
        if (search.isEmpty()) {
            return ids;
        }
        try {
            MultiFieldQueryParser queryParser = new MultiFieldQueryParser(SEARCH_FIELDS, new WhitespaceAnalyzer());
            Query query = queryParser.parse(search);
            IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(index));
            TopDocs docs = searcher.search(query, 10);

            for (ScoreDoc scoreDoc : docs.scoreDocs) {
                ids.add(searcher.doc(scoreDoc.doc).get("id"));
            }
            return ids;
        } catch (Exception e) {
            LOG.error("Ошибка в строке \"" + search + "\"", e);
        }
        return ids;
    }

    private void addDoc(IndexWriter w, Person person) throws IOException {
        Document doc = new Document();
        doc.add(new StringField("id", Objects.requireNonNull(person.getId()).toString(), Field.Store.YES));
        doc.add(new StringField("name", person.getName(), Field.Store.YES));
        doc.add(new StringField("surname", person.getSurname(), Field.Store.YES));
        doc.add(new StringField("patronymic", Optional.ofNullable(person.getPatronymic()).orElse(""),
                Field.Store.YES));
        doc.add(new StringField("passportSeries", person.getPassportSeries(), Field.Store.YES));
        doc.add(new StringField("passportNumber", person.getPassportNumber(), Field.Store.YES));
        w.addDocument(doc);

    }
}
