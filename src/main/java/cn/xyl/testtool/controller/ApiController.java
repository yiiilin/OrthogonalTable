package cn.xyl.testtool.controller;

import cn.xyl.testtool.entities.InputType;
import cn.xyl.testtool.service.TestCaseService;
import cn.xyl.testtool.util.TableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class ApiController {

    @Autowired
    private TestCaseService service;

    /**
     * 获取种子库下正交组合后的结果
     * @param seed 种子编号
     * @return 组合结果
     */
    @PostMapping("getTestCaseCombinations")
    public Map<Integer, List<String>> getTestCaseCombinations(@RequestParam String seed) {
        return service.getTestCaseCombinations(seed);
    }

    /**
     * 获取种子库下的所有实例
     * @param seed 种子编号
     * @return 实例
     */
    @PostMapping("getCases")
    public Map<String, List<String>> getCases(@RequestParam String seed) {
        return service.getCases(seed);
    }

    /**
     * 获取一类实例的集合
     * @param seed 种子编号
     * @param kind 种类名称
     * @return 实例的集合
     */
    @PostMapping("getOneKindCases")
    public List<String> getOneKindCases(@RequestParam String seed,
                                        @RequestParam String kind) {
        return service.getOneKindCases(seed, kind);
    }

    /**
     * 添加一个实例
     * @param seed 种子编号
     * @param kind 种类名称
     * @param name 实例
     * @return 是否成功添加
     */
    @PostMapping("addOneCase")
    public Boolean addOneCase(String seed, String kind, String name) {
        try {
            return service.addOneCase(seed, kind, name);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除一个实例
     * @param seed 种子编号
     * @param kind 种类名称
     * @param name 实例名称集合
     * @return 是否删除成功
     */
    @PostMapping("deleteOneCase")
    public Boolean deleteOneCase(String seed, String kind, String name) {
        return service.deleteOneCase(seed, kind, name);
    }

    /**
     * 添加一类实例
     * @param seed 种子编号
     * @param kind 种类名称
     * @param names 实例名称集合，使用`,`隔开
     * @return 是否添加成功
     */
    @PostMapping("addCases")
    public Boolean addCases(String seed, String kind, String names) {
        List<String> nameList = new ArrayList<>();
        nameList.addAll(Arrays.asList(names.split(",")));
        return service.addCases(seed, kind, nameList);
    }

    /**
     * 删除一类实例
     * @param seed 种子编号
     * @param kind 种类名称
     * @return 是否删除成功
     */
    @PostMapping("deleteCases")
    public Boolean deleteCases(String seed, String kind) {
        return service.deleteCases(seed, kind);
    }

    /**
     * 删除该库下的所有实例
     * @param seed 种子编号
     * @return 是否删除成功
     */
    @PostMapping("clearCases")
    public Boolean clearCases(String seed) {
        return service.clearCases(seed);
    }
}
