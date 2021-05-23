package com.shatteredpixel.shatteredpixeldungeon.capstone;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Belongings;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;

import com.shatteredpixel.shatteredpixeldungeon.levels.traps.Trap;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Iterator;

public class Data {

    public ArrayList<boolean[]> statusAbnormals;
    public HashSet<Mob> mobs;
    public List<Trap> traps;

    // spawn mobs information
    public ArrayList<Integer> spawnMobsHT;
    public ArrayList<Integer> spawnMobsATT;
    public ArrayList<Integer> spawnMobsDEF;
    public ArrayList<Integer> spawnMobsEXP;

    // player information
    public int hp;
    public int ht;
    public int damaged;
    public int attackDamage;
    public int healPoint;
    public int killMonster;
    public int moving;
    public int earnEXP;


    public Weapon hero_weapon = (Weapon) Dungeon.hero.belongings.weapon;

    public Armor hero_armor=Dungeon.hero.belongings.armor;

    // 장착 아이템의 정보보
    public int weapon_damage = (hero_weapon.max(level())+hero_weapon.min(level()))/2;
    public int weapon_strreq = hero_weapon.STRReq(level());
    public int weapon_static = hero_weapon.getWEAPON_STATIC();

    public int armor_armor = (hero_armor.DRMax(level())+hero_armor.DRMin(level()))/2;
    public int armor_strreq = hero_armor.STRReq(level());
    public int armor_static = hero_armor.armor_static;
    //아이템 사용횟수 리턴 함수
    public int get_artifacts_cnt(){
        return Dungeon.artifacts_cnt;
    }
    public int get_bombs_cnt(){
        return Dungeon.bombs_cnt;
    }
    public int get_food_cnt(){
        return Dungeon.food_cnt;
    }
    public int get_potions_cnt(){
        return Dungeon.potions_cnt;
    }
    public int get_scrolls_cnt(){
        return Dungeon.scrolls_cnt;
    }
    public int get_stones_cnt(){
        return Dungeon.stones_cnt;
    }



/*
        무기.max(int 강화수치)
        무기.min(int 강화수치)
        해당 무기의 최소/최대 대미지

        무기.STRReq(int 강화수치)
        해당 무기의 요구 힘 수치량

        무기.getWEAPON_STATIC()
        해당 무기의 강화효율
        
        방어구.DRMin(int 강화수치)
        방어구.DRMax(int 강화수치)
        해당 방어구의 최소/최대 방어력
        
        방어구.STRReq(int 강화수치)
        해당 방어구의 요구 힘 수치량 
     */



    // 변수 저장을 쉽게 하기 위해 임시 저장
    static private int totalDamaged;
    static private int totalAttackDamage;
    static private int totalKillMonster;
    static private int totalMoving;
    static private int totalEXP;
    
    // level.java 편집
    // flamable, solid, avoid
    public void addStatusAbnormal(boolean[] statusAbnormal){
        if(statusAbnormals == null) statusAbnormals = new ArrayList<boolean[]>();

        statusAbnormals.add(statusAbnormal);
    }

    // 스폰된 mob들의 정보 추출
    public void storeSpawnMobsHT(){
        if(spawnMobsHT == null) spawnMobsHT = new ArrayList<>();
        else spawnMobsHT.clear();
        for(int i = 0; i < mobs.size(); i++)
            spawnMobsHT.add(mobs.get(i).HT);
        averageMobsHT = arrayListAverage(spawnMobsHT);
    }

    public void storeSpawnMobsATT(){
        if(spawnMobsATT == null) spawnMobsATT = new ArrayList<>();
        else spawnMobsATT.clear();
        for(int i = 0; i < mobs.size(); i++)
            spawnMobsATT.add((mobs.get(i).MAX_ATT + mobs.get(i).MIN_ATT) / 2);
        averageMobsATT = arrayListAverage(spawnMobsATT);
    }

    public void storeSpawnMobsDEF(){
        if(spawnMobsDEF == null) spawnMobsDEF = new ArrayList<>();
        else spawnMobsDEF.clear();
        for(int i = 0; i < mobs.size(); i++)
            spawnMobsDEF.add((mobs.get(i).MAX_DEF + mobs.get(i).MIN_DEF) / 2);
        averageMobsDEF = arrayListAverage(spawnMobsDEF);
    }

    public void storeSpawnMobsEXP(){
        if(spawnMobsEXP == null) spawnMobsEXP = new ArrayList<>();
        else spawnMobsEXP.clear();
        for(int i = 0; i < mobs.size(); i++)
            spawnMobsEXP.add(mobs.get(i).EXP);
        averageMobsEXP = arrayListAverage(spawnMobsEXP);
    }

    private double arrayListAverage(ArrayList<Integer> list) {
        double temp = 0;
        for(int i = 0; i < list.size(); i++)
            temp += list.get(i);
        temp = temp / list.size();
        return temp;
    }

    // Hero.java 편집
    public void storeHP(int HP){
        this.hp = HP;
    }

    public void storeHT(int HT){
        this.ht = HT;
    }

    public void storeDamaged(int damaged){
        this.damaged = damaged - totalDamaged;
        totalDamaged = damaged;
    }

    public void storeAttackDamage(int attackDamage){
        this.attackDamage = attackDamage - totalAttackDamage;
        totalAttackDamage = attackDamage;
    }

    //    public void storeHealPoint(int healPoint){
//        this.healPoint = healPoint;
//    }
//
    public void storeKillMonster(int killMonster){
        this.killMonster = killMonster - totalKillMonster;
        totalKillMonster = killMonster;
    }

    public void storeMoving(int moving){
        this.moving = moving - totalMoving;
        totalMoving = moving;
    }

    public void storeEarnEXP(int EXP){
        this.earnEXP = EXP - totalEXP;
        totalEXP = EXP;
    }
}