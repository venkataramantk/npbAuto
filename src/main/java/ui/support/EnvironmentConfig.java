package ui.support;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;

import java.net.URISyntaxException;


/**
 * Created by skonda on 5/18/2016.
 */
public class EnvironmentConfig {
    public static String configFile;
    private static XMLConfiguration config;

    public static final String ENV_PATH = "Environment/";
    public static final String APP_URL = "AppUrl";


    private static void getConfig()  {

        configFile = getEnvironmentProfile();
        if (configFile != null) {
            configFile = configFile + ".xml";

            try {
                config = new XMLConfiguration(loadAndGetResourceLocation(configFile));
            } catch (ConfigurationException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            config.setExpressionEngine(new XPathExpressionEngine());
        } else
            throw new IllegalStateException("As a test, I didnt get any environment config. Please pass a environment config to me");
    }

    public static String loadAndGetResourceLocation(String fileName) throws URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource(fileName).toString();
    }
    public static String getApplicationUrl() throws Exception {
        getConfig();
        return config.getString(ENV_PATH + APP_URL);
    }

    public static String
    getEnvironmentProfile() {
        try {
            return Config.getEnvironmentProfile();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
