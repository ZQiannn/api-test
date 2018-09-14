package com.ticktick.apiplatform.api.task.attend;

import com.ticktick.apiplatform.annotation.base.Api;
import com.ticktick.apiplatform.annotation.base.Group;
import com.ticktick.apiplatform.annotation.base.Project;
import com.ticktick.apiplatform.annotation.base.Property;
import com.ticktick.apiplatform.annotation.base.TestCase;
import com.ticktick.apiplatform.annotation.request.ReqBodies;
import com.ticktick.apiplatform.annotation.response.RespBodies;
import com.ticktick.apiplatform.annotation.response.RespStatus;
import com.ticktick.apiplatform.annotation.state.StorageCookie;
import com.ticktick.apiplatform.annotation.state.WithCookie;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Title TaskAttendApi
 * @Description
 * @Author ZQian
 * @date: 2018/9/11 11:24
 */
@Project(name = "TickTick Task", baseUrl = "https://api.365dida.com") //声明Project以及baseUrl
@Group(name = "Task-Attend")  //声明组
public interface TaskAttendApi {


    /**
     * 创建用户owner
     */
    @Api(name = "创建用户owner", url = "/api/v2/user/signup", method = RequestMethod.POST)  //声明API的名称以及URL，方法
    @ReqBodies(body = {    //设置请求体为RequestBody，并输入username和password参数
            @Property(name = "username", value = "owner@gmail.com"),
            @Property(name = "password", value = "123456")
    })
    @RespStatus(is = HttpStatus.OK) //期望响应的状态为OK
    @StorageCookie(storeKey = "owner") //保存返回的所有cookie值到owner
    @TestCase(order = 1)  //声明测试用例并且执行顺序为1
    @RespBodies({  //对返回体进行验证
            @Property(name = "userId", matcher = "notNullValue()", storeKey = "oid")
            //对返回体的userId进行验证，验证公式为org.hamcrest.Matcher.notNullValue(),并且缓存该值到oid
    })
    void createOwner();

    /**
     * 创建用户guest
     */
    @Api(name = "创建用户guest", url = "/api/v2/user/signup", method = RequestMethod.POST) //声明API的名称以及URL，方法
    @ReqBodies(body = {//设置请求体为RequestBody，并输入username和password参数
            @Property(name = "username", value = "guest@gmail.com"),
            @Property(name = "password", value = "123456")
    })
    @RespStatus(is = HttpStatus.OK)//期望响应的状态为OK
    @StorageCookie(storeKey = "guest")//保存返回的所有cookie值到guest
    @TestCase(order = 2) //声明测试用例并且执行顺序为2
    void createGuest();

    /**
     * 创建owner任务
     */
    @Api(name = "创建owner任务", url = "/api/v2/task", method = RequestMethod.POST) //声明API的名称以及URL，方法
    @ReqBodies(body = { //设置请求体为RequestBody，并输入JSON参数
            @Property(name = "title", value = "分享任务"),
            @Property(name = "content", value = "分享任务内容"),
            @Property(name = "startDate", value = "2018-09-10 12:00:00"),
            @Property(name = "creator", value = "$oid"), //此处用el表达式声明去缓存中取值
            @Property(name = "projectId", value = "inbox$oid"),//此处用el表达式声明去缓存中取值
    })
    @TestCase(order = 3)//声明测试用例并且执行顺序为3
    @RespStatus(is = HttpStatus.OK)//期望响应的状态为OK
    @RespBodies({ //对返回体的id进行验证，验证公式为org.hamcrest.Matcher.isA(java.lang.String.class),并且缓存该值到owner_tid
            @Property(name = "id", storeKey = "owner_tid", matcher = "isA(java.lang.String.class)")
    })
    @WithCookie(withKey = "owner")//声明带上缓存的owner cookie进行请求
    void createOwnerTask();

    /**
     * 销毁用户owner
     */
    @Api(name = "销毁用户owner", url = "/api/v2/user/deleteAccount", method = RequestMethod.DELETE)//声明API的名称以及URL，方法
    @ReqBodies(body = {//设置请求体为RequestBody，并输入JSON参数
            @Property(name = "username", value = "owner@gmail.com"),
            @Property(name = "password", value = "123456")
    })
    @RespStatus(is = HttpStatus.OK)//期望响应的状态为OK
    @TestCase(order = 100)//声明测试用例并且执行顺序为100
    @WithCookie(withKey = "owner")//声明带上缓存的owner cookie进行请求
    void deleteOwner();


    /**
     * 销毁用户guest
     */
    @Api(name = "销毁用户guest", url = "/api/v2/user/deleteAccount", method = RequestMethod.DELETE)//声明API的名称以及URL，方法
    @ReqBodies(body = {//设置请求体为RequestBody，并输入JSON参数
            @Property(name = "username", value = "guest@gmail.com"),
            @Property(name = "password", value = "123456")
    })
    @RespStatus(is = HttpStatus.OK)//期望响应的状态为OK
    @TestCase(order = 100)//声明测试用例并且执行顺序为100
    @WithCookie(withKey = "guest")//声明带上缓存的guest cookie进行请求
    void deleteGuest();
}
