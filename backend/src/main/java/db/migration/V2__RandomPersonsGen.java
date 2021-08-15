package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.flywaydb.core.internal.util.DateUtils;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Нагенерим кучу рандомных персон,
 * чтобы можно было проверить скорость работы приложения на относительно большом объёме данных
 * (прежне всего нужно проверить скорость работы lucene)
 */
public class V2__RandomPersonsGen extends BaseJavaMigration {
    private static final String TOP_NAMES_DATA = "db/top_names.json";
    private static final Integer PERSONS_NUMBER = 100000;
    private static final Integer BATCH_SIZE = 500;

    @Override
    public void migrate(Context context) throws Exception {
        DataSource dataSource = new SingleConnectionDataSource(context.getConnection(), false);
        JsonParser jsonParser = new JacksonJsonParser();

        String json = new String(getResource(TOP_NAMES_DATA).getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, Object> topNames = jsonParser.parseMap(json);

        @SuppressWarnings("unchecked")
        List<String> surnames = (List<String>) topNames.get("surnames");

        @SuppressWarnings("unchecked")
        List<String> maleNames = (List<String>) topNames.get("male_names");

        @SuppressWarnings("unchecked")
        List<String> femaleNames = (List<String>) topNames.get("female_names");

        RandomPersonGenerator personGenerator = new RandomPersonGenerator(surnames, maleNames, femaleNames);

        // generate random persons
        List<MapSqlParameterSource> batch = new ArrayList<>();
        for (int i = 1; i <= PERSONS_NUMBER; i++) {
            MapSqlParameterSource personRow = personGenerator.generate();
            batch.add(personRow);

            if (i % BATCH_SIZE == 0 || i == PERSONS_NUMBER) {
                new SimpleJdbcInsert(dataSource).withTableName("person")
                        .usingColumns(RandomPersonGenerator.COLUMNS)
                        .executeBatch(batch.toArray(new SqlParameterSource[]{}));
                batch.clear();
            }
        }
    }

    private Resource getResource(String name) {
        return new ClassPathResource(name);
    }

    static class RandomPersonGenerator {
        public static final String[] COLUMNS = new String[]{"id", "name", "surname", "patronymic",
                "birth_date", "passport_series", "passport_number"};

        private final List<String> surnames;
        private final List<String> maleNames;
        private final List<String> femaleNames;
        private final Date birthDateFrom = DateUtils.toDate(1900, 1, 1);
        private final Date birthDateTo = DateUtils.toDate(2005, 1, 1);

        public RandomPersonGenerator(List<String> surnames, List<String> maleNames, List<String> femaleNames) {
            this.surnames = surnames;
            this.maleNames = maleNames;
            this.femaleNames = femaleNames;
        }

        public MapSqlParameterSource generate() {
            boolean isMale = ThreadLocalRandom.current().nextBoolean();
            String name = getRandomElement(isMale ? maleNames : femaleNames);
            String surname = getRandomElement(surnames);

            MapSqlParameterSource row = new MapSqlParameterSource();
            row.addValue("id", UUID.randomUUID().toString());
            row.addValue("name", name);
            row.addValue("surname", isMale ? surname : getFemaleSurname(surname));
            row.addValue("patronymic", isMale ? "Александрович" : "Александровна");
            row.addValue("birth_date", getRandomDate());
            row.addValue("passport_series", String.format("%04d", ThreadLocalRandom.current().nextInt(10000)));
            row.addValue("passport_number", String.format("%06d", ThreadLocalRandom.current().nextInt(1000000)));

            return row;
        }

        private static <T> T getRandomElement(List<T> list) {
            return list.get(ThreadLocalRandom.current().nextInt(list.size()));
        }

        private static String getFemaleSurname(String maleSurname) {
            if (maleSurname.matches("^.*(ов|ев|ёв|ин)$")) {
                return maleSurname.replaceAll("(ов|ев|ёв|ин)$", "$1а");
            } else if (maleSurname.matches("^.*(ий|ый|ой)$")) {
                return maleSurname.replaceAll("(ий|ый|ой)$", "ая");
            }
            return maleSurname;
        }

        private Date getRandomDate() {
            return new Date(ThreadLocalRandom.current().nextLong(birthDateFrom.getTime(), birthDateTo.getTime()));
        }
    }
}
