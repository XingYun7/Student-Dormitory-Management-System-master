package com.sdms.config;

import lombok.val;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    // 将密码的加密器交给spring管理
    @Bean("hashedCredentialsMatcherBean")
    public HashedCredentialsMatcher getHashedCredentialsMatcher() {
        val credentialsMatcher = new HashedCredentialsMatcher();
        // 指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        // 加密次数
        credentialsMatcher.setHashIterations(1);
        // storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    // 将自定义的ShiroRealm交给spring管理
    @Bean(name = "realmBean")
    public ShiroRealm getShiroRealm(@Qualifier("hashedCredentialsMatcherBean") HashedCredentialsMatcher credentialsMatcher) {
        val realm = new ShiroRealm();
        realm.setCachingEnabled(true);
        // 关联 credentialsMatcher
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }

    @Bean(name = "sessionManagerBean")
    public DefaultWebSessionManager getSessionManager() {
        val sessionManager = new DefaultWebSessionManager();
        // 去除因JSESSIONID导致第一次访问请求时报400错误
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    // 将SecurityManager交给spring管理
    @Bean(name = "securityManagerBean")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("realmBean") ShiroRealm realm,
                                                                  @Qualifier("sessionManagerBean") DefaultWebSessionManager sessionManager) {
        val securityManager = new DefaultWebSecurityManager();
        // 关联 realm 对象
        securityManager.setRealm(realm);
        // 关联 sessionManager 对象
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    // 将ShiroFilterFactory交给spring管理
    @Bean(name = "ShiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManagerBean") DefaultWebSecurityManager securityManager) {
        val bean = new ShiroFilterFactoryBean();
        // 关联 securityManager 对象
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("/login"); // 登录页面
        // 添加 shiro 内置的过滤器
        val filterMap = new LinkedHashMap<String, String>();
        // 登录页面
        filterMap.put("/login", "anon");//不拦截
        // 登录认证请求
        filterMap.put("/login-auth", "anon");//不拦截
        // 静态资源
        filterMap.put("/css/**", "anon");//不拦截
        filterMap.put("/lib/**", "anon");//不拦截
        filterMap.put("/favicon.ico", "anon");//不拦截
        filterMap.put("/sdms-images/*", "anon");//上传的图片资源，不拦截
        // swagger2 接口文档
        filterMap.put("/swagger*/**", "anon");//不拦截  http://localhost:8080/swagger-ui/index.html
        filterMap.put("/v2/**", "anon");//不拦截        http://localhost:8080/v2/api-docs
        filterMap.put("/webjars/**", "anon");//不拦截
        //其它
        filterMap.put("/**", "authc");// 拦截
        // filterMap.put("/**", "anon");//不拦截
        bean.setFilterChainDefinitionMap(filterMap);
        return bean;
    }
}
