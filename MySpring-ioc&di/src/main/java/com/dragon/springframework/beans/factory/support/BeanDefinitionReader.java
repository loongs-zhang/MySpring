package com.dragon.springframework.beans.factory.support;

/**
 * 规范Bean定义阅读器的简单接口，使用String位置参数指定加载方法
 * （这里作者为了便于实现，进行了简化），具体的Bean定义阅读器
 * 可以为Bean定义添加附加的加载和注册方法，特定于其Bean定义格式。
 * 请注意，bean定义读取器不必实现此接口，它仅对希望遵循标准命名约定的
 * bean定义读取器提供建议。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
public interface BeanDefinitionReader {

    /**
     * 返回Bean工厂以向其注册Bean定义。
     * 工厂通过BeanDefinitionRegistry接口公开，
     * 封装了与Bean定义处理相关的方法。
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 返回用于Bean类的类加载器。
     */
    ClassLoader getBeanClassLoader();

    /**
     * 从指定的资源位置加载bean定义。
     */
    int loadBeanDefinitions(String location) throws Exception;

    /**
     * 从指定的一些资源位置加载bean定义。
     */
    int loadBeanDefinitions(String... locations) throws Exception;

}
