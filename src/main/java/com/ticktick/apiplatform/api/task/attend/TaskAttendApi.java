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
@Project(name = "TickTick Task", baseUrl = "https://api.365dida.com")
@Group(name = "Task-Attend")
public interface TaskAttendApi {


    /**
     * 创建用户owner
     */
    @Api(name = "创建用户owner", url = "/api/v2/user/signup", method = RequestMethod.POST)
    @ReqBodies(body = {
            @Property(name = "username", value = "owner@gmail.com"),
            @Property(name = "password", value = "123456")
    })
    @RespStatus(is = HttpStatus.OK)
    @StorageCookie(storeKey = "owner")
    @TestCase(order = 1)
    @RespBodies({
            @Property(name = "userId", matcher = "notNullValue()", storeKey = "oid")
    })
    void createOwner();

    /**
     * 创建用户guest
     */
    @Api(name = "创建用户guest", url = "/api/v2/user/signup", method = RequestMethod.POST)
    @ReqBodies(body = {
            @Property(name = "username", value = "guest@gmail.com"),
            @Property(name = "password", value = "123456")
    })
    @RespStatus(is = HttpStatus.OK)
    @StorageCookie(storeKey = "guest")
    @TestCase(order = 2)
    void createGuest();

    /**
     * 创建owner任务
     */
    @Api(name = "创建owner任务", url = "/api/v2/task", method = RequestMethod.POST)
    @ReqBodies(body = {
            @Property(name = "title", value = "分享任务"),
            @Property(name = "content", value = "分享任务内容"),
            @Property(name = "startDate", value = "2018-09-10  12:00:00"),
            @Property(name = "creator", value = "$oid"),
            @Property(name = "projectId", value = "inbox$oid"),
    })
    @TestCase(order = 3)
    @RespStatus(is = HttpStatus.OK)
    @RespBodies({
            @Property(name = "id", storeKey = "owner_tid", matcher = "isA(java.lang.String.class)")
    })
    @WithCookie(withKey = "owner")
    void createOwnerTask();

    /**
     * 销毁用户owner
     */
    @Api(name = "销毁用户owner", url = "/api/v2/user/deleteAccount", method = RequestMethod.DELETE)
    @ReqBodies(body = {
            @Property(name = "username", value = "owner@gmail.com"),
            @Property(name = "password", value = "123456")
    })
    @RespStatus(is = HttpStatus.OK)
    @TestCase(order = 100)
    @WithCookie(withKey = "owner")
    void deleteOwner();


    /**
     * 销毁用户guest
     */
    @Api(name = "销毁用户guest", url = "/api/v2/user/deleteAccount", method = RequestMethod.DELETE)
    @ReqBodies(body = {
            @Property(name = "username", value = "guest@gmail.com"),
            @Property(name = "password", value = "123456")
    })
    @RespStatus(is = HttpStatus.OK)
    @TestCase(order = 100)
    @WithCookie(withKey = "guest")
    void deleteGuest();
}
