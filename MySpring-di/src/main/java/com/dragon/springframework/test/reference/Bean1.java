package com.dragon.springframework.test.reference;

import com.dragon.springframework.beans.factory.annotation.Autowired;
import com.dragon.springframework.context.stereotype.Component;

/**
 * @author SuccessZhang
 * @date 2020/06/24
 */
@Component
public class Bean1 {
    @Autowired
    private Bean2 bean2;
}
