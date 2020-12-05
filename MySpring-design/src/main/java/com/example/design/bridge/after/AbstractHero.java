package com.example.design.bridge.after;

import com.example.design.bridge.after.equipment.Equipment;
import com.example.design.bridge.after.skill.Skill;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/12/05/
 */
public abstract class AbstractHero {

    protected List<Equipment> equipments = new ArrayList<>();

    protected List<Skill> skills = new ArrayList<>();

    /**
     * 查看英雄信息
     */
    public abstract void show();

    public void addEquipment(Equipment equipment) {
        this.equipments.add(equipment);
    }

    public void learnSkill(Skill skill) {
        this.skills.add(skill);
    }
}
