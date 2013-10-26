package com.ae.assignment.cdrproject.cdrservice.utilities;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class DefaultScopedBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	 
	String defaultSpringScope;
	public DefaultScopedBeanFactoryPostProcessor (String defaultScope)
	{
		this.defaultSpringScope = defaultScope;
	}
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
 
        for (String beanName : factory.getBeanDefinitionNames()) {
 
            BeanDefinition beanDef = factory.getBeanDefinition(beanName);
 
            String explicitScope = beanDef.getScope();
 
            if ("".equals(explicitScope)) {
 
                beanDef.setScope(defaultSpringScope);
            }
        }
    }
}
