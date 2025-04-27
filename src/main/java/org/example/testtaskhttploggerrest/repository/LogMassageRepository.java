package org.example.testtaskhttploggerrest.repository;

import jakarta.inject.Singleton;
import jakarta.persistence.EntityGraph;
import org.example.testtaskhttploggerrest.models.LogMessage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.LinkedList;
import java.util.List;

import static org.example.testtaskhttploggerrest.utils.HibernateUtils.buildSessionFactory;

@Singleton
public class LogMassageRepository {
    public Long addLog(LogMessage logMessage, Session session) {
        session.persist(logMessage);

        return logMessage.getId();
    }

    public List<LogMessage> getAllLogs(Session session) {
        return session.createSelectionQuery("from LogMessage", LogMessage.class).list();
    }
}
