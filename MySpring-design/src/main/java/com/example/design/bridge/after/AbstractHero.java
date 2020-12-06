package com.example.design.bridge.after;

import com.example.design.bridge.after.equipment.Equipment;
import com.example.design.bridge.after.skill.Skill;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象角色
 *
 * @author SuccessZhang
 * @date 2020/12/05
 */
public abstract class AbstractHero {

    protected List<Equipment> equipments = new ArrayList<>();

    protected List<Skill> skills = new ArrayList<>();

    /**
     * 查看英雄信息
     */
    public abstract void show();

    /**
     * 获得了装备
     */
    public void addEquipment(Equipment equipment) {
        this.equipments.add(equipment);
    }

    /**
     * 学习了新技能
     */
    public void learnSkill(Skill skill) {
        this.skills.add(skill);
    }
}
