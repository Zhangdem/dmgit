package com.dm.DGCat.AppTest.DemoController;

import com.dm.DGCat.service.SiteUserService;
import com.dm.DGCat.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/Swagger2Test")
@Api(value = "Swagger2实例")
public class DemoController {
	@Autowired SiteUserService siteUserService;

    @PostMapping(value="/test0")
    @ApiOperation(value = "根据用户昵称获取用户信息",notes = "显示用户信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName",value = "用户的昵称",required = true),
            @ApiImplicitParam(name = "userCode",value = "用户的编号",required = true)
    })
    public Object getDiarySome(String userName, String userCode) {
        String sss=userName;
        System.out.println(userCode);
        return Result.fail(userName);
    }
}