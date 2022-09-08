package cn.itsource.controller;

import cn.itsource.result.JSONResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tangrufei
 * @date 2022-09-07 18:06
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @RequestMapping("/list")
    @PreAuthorize("hasAnyAuthority('employee:list')")
    public JSONResult Test() {
        return JSONResult.success("员工查询接口调用成功");
    }
}
