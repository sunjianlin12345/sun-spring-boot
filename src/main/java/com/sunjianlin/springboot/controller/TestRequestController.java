package com.sunjianlin.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.sunjianlin.springboot.common.Result;
import com.sunjianlin.springboot.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * Created by sunjianlin
 * 2018年05月16日 21:00:05
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestRequestController extends AbstractController {

    //https://blog.csdn.net/u010399316/article/details/52913299

    // application/x-www-form-urlencoded
    // 可选（即非必须，因为这种情况的数据@RequestParam, @ModelAttribute也可以处理，当然@RequestBody也能处理）；

    //multipart/form-data
    // 不能处理（即使用@RequestBody不能处理这种格式的数据）；

    //其他格式
    // 必须（其他格式包括application/json, application/xml等。这些格式的数据，必须使用@RequestBody来处理）；

    /**
     * 普通请求
     *
     * http://localhost:8089/test/test1
     */
    @RequestMapping(value = "/test1")
    public String test1() {
        return "test1";
    }

    /**
     * 简单get请求
     * 组合注解 @GetMapping
     * 是@RequestMapping(method = RequestMethod.GET)的缩写
     *
     * http://localhost:8089/test/test2/sun
     */
    @GetMapping(value = "test2/{name}")
    public String test2(@PathVariable String name) {
        // \n不起作用了,那就直接用html中的标签吧
        return "oh you are " + name + "<br> nice to meet you";
    }

    /**
     * 简单get请求2
     * 注解 @PathVariable 映射URL绑定的占位符
     * 1）带占位符的URL是Spring3.0新增的功能，该功能在SpringMVC向REST目标挺进发展过程中具有里程碑的意义。
     * 2）通过@PathVariable可以将URL中占位符参数绑定到控制器处理方法的入参中：
     *    URL中的{xxx}占位符可以通过@PathVariable("xxx")绑定到操作方法的入参中
     *
     * http://localhost:8089/test/test3/admin&admin
     */
    @GetMapping(value = "/test3/{name}&{pwd}")
    public String test3(@PathVariable String name, @PathVariable String pwd) {
        if (name.equals("admin") && pwd.equals("admin")) {
            return "hello welcome admin";
        } else {
            return "oh sorry user name or password is wrong";
        }
    }

    /**
     * get请求
     * 注解 @RequestParam绑定请求参数值
     * 在处理方法入参使用@RequestParam可以把请求参数传递给请求方法，@RequestParam包含的属性值：
     * --- value ：参数名称
     * --- required ：是否必须，默认为true，表示请求参数中必须包含对应的参数，否则抛出异常。
     * --- defaultValue：当请求参数缺少或者有请求参数但值为空时，值采用该设置值
     *
     * http://localhost:8089/test/test4?name=111&pwd=111
     */
    @RequestMapping(value = "/test4", method = RequestMethod.GET)
    public String test4(@RequestParam(value = "name", required = true) String name,
                             @RequestParam(value = "pwd", required = true) String pwd) {
        if (name.equals("admin") && pwd.equals("admin")) {
            return JSON.toJSONString(Result.success());
        } else {
            return JSON.toJSONString(Result.error());
        }
    }

    /**
     * 简单post请求
     * 组合注解 @PostMapping
     * 是@RequestMapping(method = RequestMethod.POST)的缩写。
     *
     * http://localhost:8089/test/test5
     */
    @RequestMapping(value = "/test5", method = RequestMethod.POST)
    public String test5() {
        System.out.println("hello test5");
        return "test5";
    }


    /**
     * post请求
     *
     * http://localhost:8089/test/test6?name=111&pwd=111
     */
    @RequestMapping(value = "/test6", method = RequestMethod.POST)
    public String test6(@RequestParam(value = "name", required = true) String name,
                              @RequestParam(value = "pwd", required = true) String pwd) {
        if (name.equals("admin") && pwd.equals("admin")) {
            return JSON.toJSONString(Result.success());
        } else {
            return JSON.toJSONString(Result.error());
        }
    }

    /**
     * 参数为一个bean对象.spring会自动为我们关联映射
     *
     * get post
     * http://localhost:8089/test/test7?id=1
     *
     * post form-data和 x-www-form-urlencoded
     * http://localhost:8089/test/test7
     * id  1
     */
    @RequestMapping(value = "/test7", method = { RequestMethod.POST, RequestMethod.GET })
    public String test7(BaseEntity entity) {
        log.info(entity.toString());
        if (null != entity && entity.getId() == 1) {
            return JSON.toJSONString(Result.success());
        } else {
            return JSON.toJSONString(Result.error());
        }
    }

    /**
     * 请求内容是一个json串,spring会自动把他和我们的参数bean对应起来
     *
     *
     * Apache Http Client 和  OkHttpClient 都不支持  GET 请求发送 Body 数据，而 AsyncHttpClient 是可以的。
     * Dsl.asyncHttpClient()
     * .prepareGet("http://localhost:8080/?id=100")
     * .setBody("Get Body")
     * .execute()
     * .toCompletableFuture()
     * .thenAccept(System.out::println).join();
     *
     * 不过要加@RequestBody注解
     *
     * post row(application/json)
     * http://localhost:8089/test/test8
     * id   1
     */
    @RequestMapping(value = "/test8", method = { RequestMethod.POST, RequestMethod.GET })
    public String test8(
//       https://unmi.cc/why-http-get-cannot-sent-data-with-reuqest-body/
//       https://stackoverflow.com/questions/978061/http-get-with-request-body
//       以下格式可以传递body数据,传递json对象失败
//       使用curl命令 curl -v -X GET http://localhost:8089/test/test8?id=1 -d "666"
//            @RequestParam("id") String id, @RequestBody String body) {
//        return JSON.toJSONString(Result.success());DB2
            @RequestBody BaseEntity entity) {
        if (null != entity && entity.getId() == 1) {
            return JSON.toJSONString(Result.success());
        } else {
            return JSON.toJSONString(Result.error());
        }
    }

}
