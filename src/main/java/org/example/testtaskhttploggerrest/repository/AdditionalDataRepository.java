package org.example.testtaskhttploggerrest.repository;

import jakarta.inject.Singleton;
import org.example.testtaskhttploggerrest.models.AdditionalLogData;
import org.example.testtaskhttploggerrest.models.AdditionalSetting;
import org.example.testtaskhttploggerrest.models.LevelMessage;
import org.example.testtaskhttploggerrest.models.LogMessage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

import static org.example.testtaskhttploggerrest.utils.HibernateUtils.buildSessionFactory;

@Singleton
public class AdditionalDataRepository {

    public LevelMessage getSetting(String loggerName, Session session) {
        Query<AdditionalSetting> query =
                session.createQuery(
                        "from AdditionalSetting " +
                                "where :argLoggerName like loggerName || '%' " + // startWith(argLoggerName, loggerName)
                                "order by length(loggerName) desc " +
                                "limit 1"

                        , AdditionalSetting.class);

        query.setParameter("argLoggerName", loggerName);

        AdditionalSetting setting = query.getSingleResultOrNull();

        return (setting != null)? setting.getLevel() : null;
    }

    public AdditionalSetting getRecordSetting(String loggerName, Session session) {
        return session.get(AdditionalSetting.class, loggerName);
    }

    public List<AdditionalSetting> getAllSetting(Session session) {
        return session.createSelectionQuery("from AdditionalSetting", AdditionalSetting.class).list();
    }

    public void setSetting(AdditionalSetting setting, Session session) {
        session.merge(setting);
    }

    public void delSetting(String loggerName, Session session) {
        session.remove(getRecordSetting(loggerName, session));
    }

    public void setDefaultLevel(LevelMessage level, Session session) {
        AdditionalSetting setting = AdditionalSetting.builder()
                .loggerName("")
                .level(level)
                .build();
        session.merge(setting);
    }

    public void addLogData(AdditionalLogData logData, Session session) {
        session.persist(logData);
    }
}
