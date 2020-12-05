package com.example.design.bridge.after.skill.impl;

import com.example.design.bridge.after.skill.Skill;

/**
 * @author SuccessZhang
 * @date 2020/12/05
 */
public class InjurySkill implements Skill {
    @Override
    public void show() {
        System.out.println("我会释放伤害技能");
    }
}
