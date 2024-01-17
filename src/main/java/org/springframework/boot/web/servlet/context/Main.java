package org.springframework.boot.web.servlet.context;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1.处理命令行参数
        // --server.port=8080 --numbers=11,22,33 debug
        ApplicationArguments applicationArguments = handleCommandArgs(args);

        // 2. 创建applicationServletEnvironment
        ApplicationServletEnvironment environment = new ApplicationServletEnvironment();

        // 3. add 命令行参数 to Environment
        environment.getPropertySources().addFirst(new SimpleCommandLinePropertySource(args));

        // 4.configureProfiles(environment, args); 空方法，子类可以实现， 对environment进行拓展

        // 5. 处理参数名不规范的问题
        environment.getPropertySources().addLast(new ResourcePropertySource("step",new ClassPathResource("step.properties")));

        System.out.println(environment.getProperty("user.first-name"));
        System.out.println(environment.getProperty("user.middle-name"));
        System.out.println(environment.getProperty("user.last-name"));

        ConfigurationPropertySources.attach(environment);

        System.out.println(environment.getProperty("user.first-name"));
        System.out.println(environment.getProperty("user.middle-name"));
        System.out.println(environment.getProperty("user.last-name"));

        // 6.发布时间，environmentPrepared
    }

    private static ApplicationArguments handleCommandArgs(String[] args) {
        DefaultApplicationArguments defaultApplicationArguments = new DefaultApplicationArguments(args);
        List<String> nonOptionArgs = defaultApplicationArguments.getNonOptionArgs();
        Set<String> optionNames = defaultApplicationArguments.getOptionNames();
        String[] sourceArgs = defaultApplicationArguments.getSourceArgs();

        System.out.println(nonOptionArgs);
        System.out.println(optionNames);
        System.out.println(Arrays.toString(sourceArgs));
        for (String optionName : optionNames) {
            List<String> optionValues = defaultApplicationArguments.getOptionValues(optionName);
            System.out.println(optionName + ": " + optionValues);
        }

        return defaultApplicationArguments;
    }
}
