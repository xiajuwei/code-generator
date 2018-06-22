package cn.victorplus.util;

import java.util.Map;

import org.springframework.context.ConfigurableApplicationContext;

public class SpringBeanUtil {
    public static ConfigurableApplicationContext context = null;

    public static Object getBeanByName(String beanName) {
        return context.getBean(beanName);
    }

    public static <T> Map<String, T> getBeanByType(Class<T> type) {
        return context.getBeansOfType(type);
    }

    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }

}
