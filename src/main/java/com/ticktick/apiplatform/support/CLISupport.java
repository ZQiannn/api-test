package com.ticktick.apiplatform.support;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.ticktick.apiplatform.entity.ApiEntity;
import com.ticktick.apiplatform.service.ApiService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @Title CLISupport
 * @Description
 * @Author ZQian
 * @date: 2018/9/10 20:58
 */
public class CLISupport {

    private final ApiService apiService;

    @Parameter(names = "-p", description = "Run test with project name", order = 1)
    private String projectName;

    @Parameter(names = "-g", description = "Run test with group name", order = 2)
    private String groupName;

    @Parameter(names = "-a", description = "Run test with api name", order = 3)
    private String apiName;

    @Parameter(names = "-l", description = "Run test with level", order = 4)
    private String level;

    @Parameter(names = {"--help", "-h"}, help = true, description = "Display help information ")
    private boolean help;

    public CLISupport(ApiService apiService) {
        this.apiService = apiService;
    }

    public List<ApiEntity> run(JCommander jCommander) {
        if (help) {
            jCommander.setProgramName("java -jar application.jar");
            jCommander.usage();
            return null;
        }

        if (StringUtils.isEmpty(projectName) && StringUtils.isEmpty(groupName) && StringUtils
                .isEmpty(apiName)) {
            JCommander.getConsole().println("Please provide a specific parameter...");
            return null;
        }
        //SwaggerDiff diff = null;
        List<ApiEntity> list = apiService.getByParams(projectName, groupName, apiName);

        if (!StringUtils.isEmpty(apiName) && list.size() > 1) {
            JCommander.getConsole()
                    .println(
                            "More than 1 api were found, Please provide a more specific group name or project name...");
            return null;
        }

        if (CollectionUtils.isEmpty(list)) {
            JCommander.getConsole()
                    .println("None of api was found, Please provide other parameters...");
            return null;
        }

        if (!StringUtils.isEmpty(level)) {
            list = list.parallelStream().filter(
                    a -> level.toLowerCase().equals(a.getPriorityLevel().name().toLowerCase()))
                    .collect(
                            Collectors.toList());
            if (CollectionUtils.isEmpty(list)) {
                JCommander.getConsole()
                        .println(
                                "None of this level api was found, Please provide other level parameter...");
                return null;
            }
        }

        return list;

    }
}
