package ${basePackage}.module.web;
import ${basePackage}.common.core.base.controller.BaseController;
import ${basePackage}.common.core.base.result.*;
import ${basePackage}.module.model.${modelNameUpperCamel};
import ${basePackage}.module.service.${modelNameUpperCamel}Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by ${author} on ${date}.
*/
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller  extends BaseController {
    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @PostMapping("/add")
    public ResponseMsg add(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.save(${modelNameLowerCamel});
        return  resultOk();
    }

    @PostMapping("/delete")
    public ResponseMsg delete(@RequestParam String id) {
        ${modelNameLowerCamel}Service.deleteById(id);
        return  resultOk();
    }

    @PostMapping("/update")
    public ResponseMsg update(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
        return  resultOk();
    }

    @PostMapping("/detail")
    public ResponseData<${modelNameUpperCamel}> detail(@RequestParam String id) {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.findById(id);
        return resultData(${modelNameLowerCamel});
    }

    @PostMapping("/list")
    public ResponseData<List<${modelNameUpperCamel}>> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return resultData(pageInfo);
    }
}
