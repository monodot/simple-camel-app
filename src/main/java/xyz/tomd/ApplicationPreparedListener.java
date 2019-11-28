package xyz.tomd;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.util.Map;

/**
 * Reports all beans in XML files BEFORE they are loaded.
 *
 * ApplicationPreparedEvent happens before any beans are loaded,
 * including before Spring has a chance to detect any duplicates.
 */
public class ApplicationPreparedListener implements ApplicationListener<ApplicationPreparedEvent> {

    public void onApplicationEvent(ApplicationPreparedEvent event) {
        // Get the application context so we can use it to load resources
        ResourceLoader loader = event.getApplicationContext();

        // TODO find some way of fetching this value from @ImportResource annotation, because I can't figure out how to do it.
        String[] locations = {"classpath*:/META-INF/spring/beans.xml"};
        Map definitions = SpringXmlBeanDefinitionReporter.getXmlBeanDefinitions(loader, locations);

        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(new File("beans.json"), definitions);
        } catch (Exception ex) {
            System.err.println("Exception while printing beans JSON: " + ex.getMessage());
        }


//        ConfigurableApplicationContext context = event.getApplicationContext();
//        ConfigurableListableBeanFactory beans = context.getBeanFactory();
//
//        Iterator<String> it = beans.getBeanNamesIterator();
//        System.out.println("**** DUMPING MY BEANS");
//        String beanName = "";
//        while (it.hasNext()) {
//            try {
//                beanName = it.next();
//                BeanDefinition bean = beans.getBeanDefinition(beanName);
//                System.out.println("**** " + beanName + " = " + bean.getBeanClassName());
//            } catch (Exception e) {
//                System.out.println("**** Skipping " + beanName + " : " + e.getMessage());
//            }
//        }
//        System.out.println("**** END DUMP");
    }
}
