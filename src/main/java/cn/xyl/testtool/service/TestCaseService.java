package cn.xyl.testtool.service;

import java.util.List;
import java.util.Map;


public interface TestCaseService {
    public Map<Integer, List<String>> getTestCaseCombinations(String seed);

    public Map<String, List<String>> getCases(String seed);

    public List<String> getOneKindCases(String seed, String kind);

    public Boolean addOneCase(String seed, String kind, String name);

    public Boolean deleteOneCase(String seed, String kind, String name);

    public Boolean addCases(String seed, String kind, List<String> names);

    public Boolean deleteCases(String seed, String kind);

    public Boolean clearCases(String seed);
}