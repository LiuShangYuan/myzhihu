package cn.scut.zhihu.configuration;

import cn.scut.zhihu.interceptor.LoginRequireInterceptor;
import cn.scut.zhihu.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * User: yinkai
 * Date: 2019-11-09
 * Time: 11:31
 */
@Component
public class ZhihuWebConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    private LoginRequireInterceptor loginRequireInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(passportInterceptor);
        // 在访问用户相关的页面时需要登录
        registry.addInterceptor(loginRequireInterceptor).addPathPatterns("/user/*");

        super.addInterceptors(registry);
    }
}
