package org.example.testtaskhttploggerrest.service;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jfr.Name;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.example.testtaskhttploggerrest.models.LogMessage;
import org.example.testtaskhttploggerrest.repository.LogMassageRepository;
import org.hibernate.Session;

import java.io.IOException;
import java.util.Arrays;

import static org.example.testtaskhttploggerrest.utils.HibernateUtils.buildSessionFactory;

@Name("logService")
@Singleton
@NoArgsConstructor
public class LogService {
    @Inject
    private LogMassageRepository logMassageRepository;

    @Inject
    private AdditionalLogDataService additionalLogDataService;

    public String addLog(LogMessage log) {
        try (Session session = buildSessionFactory().openSession()) {
            session.beginTransaction();

            System.out.println("log : " + log);
            Long id = logMassageRepository.addLog(log, session);

            additionalLogDataService.addAdditionalData(log, session);

            session.getTransaction().commit();

            return String.format("Success, save log by id = %s", id);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println(Arrays.toString(e.getStackTrace()));
            return String.format("Failed %s", e.getMessage());
        }
    }

    public String getAllLogs() {
        try (Session session = buildSessionFactory().openSession()) {
            return logMassageRepository.getAllLogs(session).toString();
        } catch (Exception e) {
            return String.format("Failed %s", e.getMessage());
        }
    }
}
