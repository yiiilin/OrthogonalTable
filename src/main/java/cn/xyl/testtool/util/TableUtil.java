package cn.xyl.testtool.util;

import cn.xyl.testtool.entities.InputType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class TableUtil {
    //正交表索引
    private static final List<String> orthogonalTableList = new ArrayList<>();

    static {
        BufferedReader bufferedReader;
        try {
            ClassPathResource classPathResource = new ClassPathResource("OrthogonalTable.txt");
            bufferedReader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()));
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                orthogonalTableList.add(temp);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("无法找到正交表文件或读取文件地址出错！");
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 寻找正交表path
     *
     * @return 正交表位置
     * @throws UnsupportedEncodingException
     */
//    private static String lookupTablePath() throws FileNotFoundException {
//        File file = ResourceUtils.getFile("classpath:OrthogonalTable.txt");
//        String tablePath = file.getPath();
//        return tablePath;
//    }

    /**
     * 获取正交表数据
     *
     * @param inputTypes 输入类型
     * @return
     */
    public static List<String> getTableContent(List<InputType> inputTypes) {
        if(inputTypes.size()==0||inputTypes.get(0).getColumnSize()==0){
            return null;
        }
        String type = formatType(inputTypes);
        //正交表索引
        int tableIndex = 0;
        List<String> list = new ArrayList<>();
        int lineSize = 0;
        //先找能够完美匹配的
        while (tableIndex < orthogonalTableList.size()) {
            //找到了完美匹配的
            if (orthogonalTableList.get(tableIndex).split("n=")[0].trim().equals(type.trim())) {
                lineSize = Integer.parseInt(orthogonalTableList.get(tableIndex).split("n=")[1]);
                tableIndex++;
                break;
            }
            tableIndex++;
        }
        for (int i = 0; i < lineSize; i++) {
            list.add(orthogonalTableList.get(tableIndex++));
        }
        //没有找到存在的正交表的情况
        if (lineSize == 0) {
            //重置缓冲区
            tableIndex = 0;
            //找出查找实例最少有几种组合
            Integer minType = 0;
            for (InputType inputType : inputTypes) {
                minType += inputType.getColumnSize() * (inputType.getChoiceSize() - 1);
            }
            minType += 1;
            //找出水平数和因子数都大于或等于查询实例的正交表
            Boolean hasFound = false;
            //读取的正交表的水平数与因子数
            List<InputType> tempInputTypes = null;
            while (tableIndex < orthogonalTableList.size()) {
                if (orthogonalTableList.get(tableIndex).contains("n=")) {
                    //行数比最少组合数还小
                    if (Integer.parseInt(orthogonalTableList.get(tableIndex).split("n=")[1].trim()) < minType) {
                        tableIndex++;
                        continue;
                    }
                    //获取水平数与因子数
                    List<String> tempTypes = Arrays.asList(orthogonalTableList.get(tableIndex).split("n=")[0].trim().split(" "));
                    tempInputTypes = new ArrayList<>();
                    for (String s : tempTypes) {
                        String[] tempType = s.split("\\^");
                        tempInputTypes.add(
                                new InputType(
                                        Integer.parseInt(tempType[0]),
                                        Integer.parseInt(tempType[1])
                                )
                        );
                    }
                    //筛选符合规定的正交表，即查询的正交表的水平数和因子数都应大于或等于查询实例
                    //先排列出水平数与因子数
                    List<Integer> iti = new ArrayList<>();
                    for (InputType inputType : inputTypes) {
                        for (int i = 0; i < inputType.getColumnSize(); i++) {
                            iti.add(inputType.getChoiceSize());
                        }
                    }
                    List<Integer> itj = new ArrayList<>();
                    for (InputType inputType : tempInputTypes) {
                        for (int i = 0; i < inputType.getColumnSize(); i++) {
                            itj.add(inputType.getChoiceSize());
                        }
                    }
                    int counter = 0;
                    for (int i = 0; i < iti.size(); i++) {
                        for (int j = 0; j < itj.size(); j++) {
                            if (i >= iti.size()) {
                                break;
                            }
                            if (itj.get(j) >= iti.get(i)) {
                                counter++;
                                i++;
//                                j++;
                            }
                        }
                    }
                    //该正交表没有办法囊括实例
                    if (counter < iti.size()) {
//                            filterResult.clear();
                        tempInputTypes.clear();
                        tableIndex++;
                        continue;
                    }
                    //该正交表能包含实例，又因为表的行数是从小到大排列的，所以这个正交表符合要求
                    hasFound = true;
                    lineSize = Integer.parseInt(orthogonalTableList.get(tableIndex).split("n=")[1]);
                    tableIndex++;
                    break;
                }
                tableIndex++;
            }
            //如果找到了合适的正交表
            if (hasFound) {
                //读取该符合条件的正交表
                List<String> tempList = new ArrayList<>();
                for (int i = 0; i < lineSize; i++) {
                    tempList.add(orthogonalTableList.get(tableIndex++));
                }
                int max = 0;
                for (InputType inputType : inputTypes) {
                    max += inputType.getColumnSize();
                }
                //计算应该被截去的列   并将处理后的结果添加到list返回结果中
                for (String combination : tempList) {
                    char[] chars = combination.toCharArray();
                    StringBuilder stringBuilder = new StringBuilder();
                    //inputType的第几个
                    int whichInputType = 0;
                    //inputType中的第几个
                    int inputTypeIndex = 0;
                    //tempInputType的第几个
                    int whichTempInputType = 0;
                    //tempInputType中的第几个
                    int tempInputTypeIndex = 0;
                    //该添加第几个字符
                    int i = 0;
                    while (i < max) {
                        if (inputTypeIndex >= inputTypes.get(whichInputType).getColumnSize()) {
                            inputTypeIndex = 0;
                            whichInputType++;
                            continue;
                        }
                        if (tempInputTypeIndex >= tempInputTypes.get(whichTempInputType).getColumnSize()) {
                            tempInputTypeIndex = 0;
                            whichTempInputType++;
                            continue;
                        }
                        if (tempInputTypes.get(whichTempInputType).getChoiceSize() < inputTypes.get(whichInputType).getChoiceSize()) {
                            tempInputTypeIndex = 0;
                            whichTempInputType++;
                            continue;
                        }
                        stringBuilder.append(chars[getIndexOfInputTypes(tempInputTypes, whichTempInputType, tempInputTypeIndex)]);
                        inputTypeIndex++;
                        tempInputTypeIndex++;
                        i++;
                    }
                    list.add(stringBuilder.toString());
                }
                //截去结果中不符合条件，超出水平因子大小的行
                int index = 0;
                Boolean isLegal = true;
                ListIterator<String> iterator = list.listIterator();
                while (iterator.hasNext()) {
                    String s = iterator.next();
                    index = 0;
                    isLegal = true;
                    char[] chars = s.toCharArray();
                    for (InputType inputType : inputTypes) {
                        for (int i = 0; i < inputType.getColumnSize(); i++) {
                            //不合法
                            if (Integer.parseInt(String.valueOf(chars[index++])) >= inputType.getChoiceSize()) {
                                isLegal = false;
                                break;
                            }
                        }
                        if (!isLegal) {
                            break;
                        }
                    }
                    if (!isLegal) {
                        iterator.remove();
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取inputTypes数组中的索引
     *
     * @param inputTypes     inputTypes数组
     * @param whichInputType 第几个inputType
     * @param inputTypeIndex inputType中的第几个
     * @return 总的索引
     */
    private static int getIndexOfInputTypes(List<InputType> inputTypes, int whichInputType, int inputTypeIndex) {
        int index = 0;
        for (int i = 0; i < whichInputType; i++) {
            index += inputTypes.get(whichInputType).getColumnSize();
        }
        index += inputTypeIndex;
        return index;
    }

    /**
     * 将输入类型格式化
     *
     * @param types 输入类型
     * @return
     */
    private static String formatType(List<InputType> types) {
        StringBuilder stringBuilder = new StringBuilder();
        for (InputType t : types) {
            stringBuilder.append(t.getChoiceSize() + "^" + t.getColumnSize() + " ");
        }
        return stringBuilder.toString();
    }
}
