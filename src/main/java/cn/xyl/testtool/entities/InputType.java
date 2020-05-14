package cn.xyl.testtool.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
public class InputType {
    /**
     * 水平因子数
     */
    private int choiceSize;
    /**
     *  因素数
     */
    private int columnSize;
}
