package com.example.design.bridge.after.skill.impl;

import com.example.design.bridge.after.skill.Skill;

/**
 * 具体实现角色
 *
 * @author SuccessZhang
 * @date 2020/12/05
 */
public class InjurySkill implements Skill {
    @Override
    public void show() {
        System.out.println("伤害技能");
    }
}
