package xyz.tomd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Takes an array of locations of bean definition XML files,
 * loads each XML, reads the bean definitions located inside,
 * and then returns an object which shows which beans
 * are defined in each file. Suitable for analysis/debugging.
 */
public class SpringXmlBeanDefinitionReporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringXmlBeanDefinitionReporter.class);

    public static Map getXmlBeanDefinitions(ResourceLoader loader, String[] locations) {
        // A structure to store our bean registries
        Map contexts = new HashMap<String, BeanDefinitionRegistry>();

        try {
            for (String location : locations ) {
                Resource[] resources = ((ConfigurableApplicationContext) loader).getResources(location);

                // Loop over the Resources and parse 'em
                for (Resource resource : resources) {
                    // Create a new registry for each resource, so we keep our beans separate
                    SimpleBeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();

                    LOGGER.info("Processing resource: " + resource);
                    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
                    reader.loadBeanDefinitions(resource);

                    Map<String, Map> beans = new HashMap<String, Map>();
                    for (String bean : reader.getRegistry().getBeanDefinitionNames()) {
                        BeanDefinition beanDefinition = reader.getRegistry().getBeanDefinition(bean);

                        Map<String, Object> properties = new HashMap<String, Object>();
                        properties.put("className", beanDefinition.getBeanClassName());

                        beans.put(bean, properties);
                    }

                    contexts.put(resource.getURI().toString(), beans);
                    LOGGER.info("Added resources from: " + resource);
                }
            }
        } catch (IOException ex) {
            System.err.println("OH NOES! " + ex.getStackTrace());
        }

        return contexts;
    }
}
