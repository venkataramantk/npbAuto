package ui.support;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Created by skonda on 5/18/2016.
 */
public class Config {
    public static final String EXEC_TYPE = "env.execType";
    public static final String BROWSER_TYPE = "driver.browser";
    public static final String HUB_URL = "hubUrl";
    public static final String MOB_HUB_URL = "mobileHubUrl";

    private static Configuration configuration;
    private static String configFile = "Config.properties";
    public static final String ENVIRONMENT_PROFILE = "environment.profile";
    private static String REPORTS_PROPERTY_FILE="reports.properties.file";

    public static String E2E_TESTING_DATAFILE = "e2eTesting.DataFile";
    public static String PHASE2_DETAILS = "phase2Details.Datafile";
    public static String DT2_DETAILS = "dt2Details.Datafile";
    public static String DT2_MOBILE ="dt2MobileData.Datafile";

    public static String DOM_USERNAME = "DomUserName";
    public static String DOM_PASSWORD = "DomPassword";




    public static String getHubUrl(){
        try {
            configuration = new PropertiesConfiguration((loadAndGetResourceLocation(configFile)));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return configuration.getString(HUB_URL);
    }

    public static String getMobHubUrl(){
        try {
            configuration = new PropertiesConfiguration((loadAndGetResourceLocation(configFile)));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return configuration.getString(MOB_HUB_URL);
    }


    public static String getDomUsername(){
        try {
            configuration = new PropertiesConfiguration((loadAndGetResourceLocation(configFile)));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return configuration.getString(DOM_USERNAME);
    }

    public static String getDomPassword(){
        try {
            configuration = new PropertiesConfiguration((loadAndGetResourceLocation(configFile)));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return configuration.getString(DOM_PASSWORD);
    }

    public static String getExecutionType(){
        try {
            configuration = new PropertiesConfiguration((loadAndGetResourceLocation(configFile)));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return configuration.getString(EXEC_TYPE);
    }

    public static String getBrowserType(){
        try {
            configuration = new PropertiesConfiguration((loadAndGetResourceLocation(configFile)));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return configuration.getString(BROWSER_TYPE);
    }

    public static String loadAndGetResourceLocation(String fileName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource(fileName).toString();
    }


    public static String getEnvironmentProfile() throws Exception {
        configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        return configuration.getString(ENVIRONMENT_PROFILE);
    }

    public static String getReportPropertiesFile()  {
        try {
            configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return configuration.getString(REPORTS_PROPERTY_FILE);
    }


    public static String getDataFile(String propertyName)  {
        try {
            configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return configuration.getString(propertyName);
    }

}
