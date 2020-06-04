package cn.infomany.util;

import cn.hutool.core.bean.BeanUtil;
import lombok.Builder;
import lombok.Data;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

public class SpelUtil {


    @Data
    @Builder
    private static class VariableClass {

        /**
         * 方法名称
         */
        private String methodName;

        /**
         * 全类名
         */
        private String className;

        /**
         * 类名
         */
        private String simpleClassName;


    }

    /**
     * 获取缓存的key
     * key 定义在注解上，支持SPEL表达式
     *
     * @return
     */
    public static String parseKey(String key, Method method, Object[] args) {

        if (StringUtils.isEmpty(key)) return null;

        // 获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);
        assert paraNameArr != null;

        // 使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        // SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        String methodName = method.getName();
        Class<?> declaringClass = method.getDeclaringClass();


        // 设置预先定义好的值
        VariableClass.VariableClassBuilder variableClassBuilder = VariableClass.builder()
                .methodName(methodName)
                .simpleClassName(declaringClass.getSimpleName())
                .className(declaringClass.getName());

        context.setVariables(BeanUtil.beanToMap(variableClassBuilder));


        // 把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }
}
