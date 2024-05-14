package com.gpersist.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;

import com.gpersist.utils.FileUtils;
import com.gpersist.utils.OfficeUtils;
import com.gpersist.utils.PropertiesUtils;

public class OfficeManagerListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            OfficeUtils.om.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            String officehome = PropertiesUtils.GetString(
                    FileUtils.IsLinux() ? OfficeUtils.LINUX_OFFICE_CONVERT_EXE : OfficeUtils.OFFICE_CONVERT_EXE);

            event.getServletContext().log(officehome);

            OfficeUtils.om = new DefaultOfficeManagerConfiguration().setOfficeHome(officehome).buildOfficeManager();

            OfficeUtils.om.start();

            event.getServletContext().log("officemanager started.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
