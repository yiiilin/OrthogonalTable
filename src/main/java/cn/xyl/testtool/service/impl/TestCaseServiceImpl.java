package cn.xyl.testtool.service.impl;

import cn.xyl.testtool.dao.TestCaseDao;
import cn.xyl.testtool.entities.InputType;
import cn.xyl.testtool.entities.TestCase;
import cn.xyl.testtool.service.TestCaseService;
import cn.xyl.testtool.util.TableUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

@Service
public class TestCaseServiceImpl implements TestCaseService {
    @Autowired
    private TestCaseDao dao;

    @Override
    public Map<Integer, List<String>> getTestCaseCombinations(String seed) {
        //获取种子对应的数据
        Map<String, List<String>> map = getMap(seed);

        //获取每种测试用例的数量并且排序
        Map<String, Integer> sizeMap = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            sizeMap.put(entry.getKey(), entry.getValue().size());
        }
        sizeMap = sizeMap
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        //解析正交表规则
        List<InputType> inputTypeList = new ArrayList<>();
        int num = 0;
        int size = 0;
        for (Map.Entry<String, Integer> entry : sizeMap.entrySet()) {
            if (entry.getValue() != size) {
                if (size == 0) {
                    size = entry.getValue();
                    num++;
                } else {
                    inputTypeList.add(new InputType(size, num));
                    num = 1;
                    size = entry.getValue();
                }
            } else {
                num++;
            }
        }
        inputTypeList.add(new InputType(size, num));

        //根据正交规则获取正交表内容
        List<String> orthogonalTable = TableUtil.getTableContent(inputTypeList);
        //inputTypeList正交表匹配条件  orthogonalTable正交表  sizeMap排序后的key:value对  key种类名 value个数 map具体用例
        //要输出的map，key为第几种组合，value为
        Map<Integer, List<String>> outMap = new LinkedHashMap<>();
        if (orthogonalTable == null || orthogonalTable.size() == 0) {
            return null;
        } else {
            List<String> names = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : sizeMap.entrySet()) {
                names.add(entry.getKey());
            }
            outMap.put(0, names);
            for (int i = 0; i < orthogonalTable.size(); i++) {
                char[] chars = orthogonalTable.get(i).toCharArray();
                outMap.put(i + 1, getOutStringList(chars, inputTypeList, sizeMap, map));
            }
            return outMap;
        }
    }

    /**
     * 根据种子读取的数据
     *
     * @param seed 种子
     * @return 数据
     */
    private Map<String, List<String>> getMap(String seed) {
        List<TestCase> cases = dao.getCases(new TestCase().setSeed(seed));
        Map<String, List<String>> map = new LinkedHashMap<>();
        for (TestCase testCase : cases) {
            if (map.get(testCase.getKind()) == null) {
                List<String> oneKindCases = new ArrayList<>();
                oneKindCases.add(testCase.getName());
                map.put(testCase.getKind(), oneKindCases);
            } else {
                map.get(testCase.getKind()).add(testCase.getName());
            }
        }
        return map;
    }

    /**
     * 根据正交变规则将一行数据变为测试用例集合
     *
     * @return 测试用例集合
     */
    private List<String> getOutStringList(char[] chars, List<InputType> inputTypeList, Map<String, Integer> sizeMap, Map<String, List<String>> map) {
        //要输出的数组
        List<String> outStringList = new ArrayList<>();
        //遍历到第几个字符
        int index = 0;
        //操作字符
        StringBuilder temp = new StringBuilder();
        for (Map.Entry<String, Integer> entry : sizeMap.entrySet()) {
            int digit = getDigit(entry.getValue());
            while ((digit--) > 0) {
                temp.append(chars[index]);
                index++;
            }
            outStringList.add(
                    map.get(
                            entry.getKey()
                    ).get(
                            Integer.parseInt
                                    (temp.toString().trim()
                                    )
                    )
            );
            temp.delete(0, temp.length());
        }
        return outStringList;
    }

    private Integer getDigit(int num) {
        double i = 1;
        while (num / Math.pow(10, i) > 1) {
            i++;
        }
        return (int) i;
    }

    @Override
    public Map<String, List<String>> getCases(String seed) {
        return getMap(seed);
    }

    @Override
    public List<String> getOneKindCases(String seed, String kind) {
        List<TestCase> cases = dao.getOneKindCases(new TestCase().setSeed(seed).setKind(kind));
        List<String> oneKindCases = new ArrayList<>();
        for (TestCase testCase : cases) {
            oneKindCases.add(testCase.getName());
        }
        return oneKindCases;
    }

    @Override
    public Boolean addOneCase(String seed, String kind, String name) {
        TestCase testCase = new TestCase().setSeed(seed).setKind(kind).setName(name);
        return dao.addOneCase(testCase);
    }

    @Override
    public Boolean deleteOneCase(String seed, String kind, String name) {
        TestCase testCase = new TestCase().setSeed(seed).setKind(kind).setName(name);
        return dao.deleteOneCase(testCase);
    }

    @Override
    public Boolean addCases(String seed, String kind, List<String> names) {
        List<TestCase> testCases = new ArrayList<>();
        for (String name : names) {
            testCases.add(new TestCase().setSeed(seed).setKind(kind).setName(name));
        }
        return dao.addCases(testCases);
    }

    @Override
    public Boolean deleteCases(String seed, String kind) {
        TestCase testCase = new TestCase().setSeed(seed).setKind(kind);
        return dao.deleteCases(testCase);
    }

    @Override
    public Boolean clearCases(String seed) {
        TestCase testCase = new TestCase().setSeed(seed);
        return dao.clearCases(testCase);
    }
}
