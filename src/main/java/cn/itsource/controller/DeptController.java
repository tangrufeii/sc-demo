package cn.itsource.controller;

import cn.itsource.result.JSONResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tangrufei
 * @date 2022-09-07 18:05
 */
@RestController
@RequestMapping("/dept")
public class DeptController {
    @RequestMapping("/list")
    public JSONResult Test() {
        return JSONResult.success("部门查询功能调用成功");
    }
}
