package org.example.testtaskhttploggerrest.service;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.example.testtaskhttploggerrest.models.AdditionalLogData;
import org.example.testtaskhttploggerrest.models.AdditionalSetting;
import org.example.testtaskhttploggerrest.models.LevelMessage;
import org.example.testtaskhttploggerrest.models.LogMessage;
import org.example.testtaskhttploggerrest.repository.AdditionalDataRepository;
import org.hibernate.Session;

import java.io.IOException;
import java.util.*;

import static org.example.testtaskhttploggerrest.utils.CookiesToJsonUtils.cookiesToJsonString;
import static org.example.testtaskhttploggerrest.utils.HibernateUtils.buildSessionFactory;
import static org.example.testtaskhttploggerrest.utils.IPUtils.getClientIpAddress;

@Singleton
public class AdditionalLogDataService {
    @Inject
    private HttpServletRequest request;

    @Inject
    private AdditionalDataRepository settingsRepository;

    private static final LevelMessage defaultLevel = LevelMessage.INFO;

    public void addAdditionalData(LogMessage logMessage, Session session) {
        LevelMessage levelInSetting = settingsRepository.getSetting(logMessage.getLoggerName(), session);

        if (levelInSetting == null) {
            levelInSetting = defaultLevel;
        }

        System.out.println("levelInSetting " + levelInSetting);

        if (logMessage.getLevel().getLevelCritical() >= levelInSetting.getLevelCritical()) {
            String cookiesStr = null;
            try {
                Cookie[] cookies = request.getCookies();

                cookiesStr = (cookies != null)? cookiesToJsonString(cookies).toString() : null;

            } catch (IOException ioException) {
                System.out.printf("При форматировании Cookies в json прозошла ошибка: %s%n", ioException.getMessage());
            }

            Enumeration<String> headerNames = request.getHeaderNames();
            Map<String, String> headers = new HashMap<>();

            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headers.put(headerName, request.getHeader(headerName));
            }

            AdditionalLogData logData = AdditionalLogData.builder()
                    .id(logMessage.getId())
                    .ip(getClientIpAddress(request))
                    .headers(headers.toString())
                    .cookies(cookiesStr)
                    .build();

            settingsRepository.addLogData(logData, session);
        }
    }

    public String getLevelByLoggerName(String loggerName) {
        try (Session session = buildSessionFactory().openSession()) {
            LevelMessage levelMessage = settingsRepository.getSetting(loggerName, session);

            if (levelMessage == null) {
                levelMessage = defaultLevel;
            }

            return String.format("level=%s, criticalLevel=%s", levelMessage.name(), levelMessage.getLevelCritical());
        }
    }

    public String getSettingByLoggerName(String loggerName) {
        try (Session session = buildSessionFactory().openSession()) {
            AdditionalSetting setting = settingsRepository.getRecordSetting(loggerName, session);
            return (setting != null)? setting.toString() : String.format("loggerName=%s, not found", loggerName);
        }
    }

    public String getAllSetting() {
        try (Session session = buildSessionFactory().openSession()) {
            List<AdditionalSetting> setting = settingsRepository.getAllSetting(session);
            return setting.toString();
        }
    }

    public String setSetting(AdditionalSetting setting) {
        try (Session session = buildSessionFactory().openSession()) {
            session.beginTransaction();

            settingsRepository.setSetting(setting, session);

            session.getTransaction().commit();

            return String.format("Success, set setting %s", session);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println(Arrays.toString(e.getStackTrace()));
            return String.format("Failed %s", e.getMessage());
        }
    }

    public String delSetting(String loggerName) {
        try (Session session = buildSessionFactory().openSession()) {
            session.beginTransaction();

            settingsRepository.delSetting(loggerName, session);

            session.getTransaction().commit();

            return String.format("Success, remove setting %s", loggerName);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println(Arrays.toString(e.getStackTrace()));
            return String.format("Failed %s", e.getMessage());
        }
    }

    public String setDefaultLevel(LevelMessage level) {
        try (Session session = buildSessionFactory().openSession()) {
            session.beginTransaction();

            settingsRepository.setDefaultLevel(level, session);

            session.getTransaction().commit();

            return String.format("Success, default level set=%s", level.name());
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println(Arrays.toString(e.getStackTrace()));
            return String.format("Failed %s", e.getMessage());
        }
    }
}
