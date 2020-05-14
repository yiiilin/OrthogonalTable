package cn.xyl.testtool.dao;

import cn.xyl.testtool.entities.TestCase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TestCaseDao {
    //    public List<TestCase> getCases(String seed);
    public List<TestCase> getCases(TestCase testCase);

    //    public List<String> getOneKindCases(String seed, String kind);
    public List<TestCase> getOneKindCases(TestCase testCase);

    //    public Boolean addOneCase(String seed, String kind, String name);
    public Boolean addOneCase(TestCase testCase);

    //    public Boolean deleteOneCase(String seed, String kind, String name);
    public Boolean deleteOneCase(TestCase testCase);

    //    public Boolean addCases(String seed, String kind, List<String> names);
    public Boolean addCases(List<TestCase> testCases);

    //    public Boolean deleteCases(String seed, String kind);
    public Boolean deleteCases(TestCase testCase);

    //    public Boolean clearCases(String seed);
    public Boolean clearCases(TestCase testCase);


}
