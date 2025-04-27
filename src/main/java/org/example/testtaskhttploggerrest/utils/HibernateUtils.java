package org.example.testtaskhttploggerrest.utils;

import org.example.testtaskhttploggerrest.models.AdditionalLogData;
import org.example.testtaskhttploggerrest.models.AdditionalSetting;
import org.example.testtaskhttploggerrest.models.LogMessage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    public static SessionFactory buildSessionFactory(){
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(LogMessage.class);
        configuration.addAnnotatedClass(AdditionalSetting.class);
        configuration.addAnnotatedClass(AdditionalLogData.class);
        return configuration.buildSessionFactory();
    }

}
