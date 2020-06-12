package com.dragon.springframework.beans.factory.support;

import com.dragon.springframework.beans.config.BeanDefinition;
import com.dragon.springframework.core.StringUtils;

/**
 * {@link BeanNameGenerator}接口的默认实现。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
public class DefaultBeanNameGenerator implements BeanNameGenerator {
    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return StringUtils.lowerFirstCase(definition.getBeanClass().getSimpleName());
    }
}
