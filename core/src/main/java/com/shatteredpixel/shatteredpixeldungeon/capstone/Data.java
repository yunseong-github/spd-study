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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Data {
    public int depth;
    static Data Data;
    public ArrayList<boolean[]> statusAbnormals;
    //public ArrayList<Mob> mobs;
    public List<Trap> traps;

    // spawn mobs information
    public ArrayList<Integer> spawnMobsHT;
    public ArrayList<Integer> spawnMobsATT;
    public ArrayList<Integer> spawnMobsDEF;
    public ArrayList<Integer> spawnMobsEXP;

    public double averageMobsHT;
    public double averageMobsATT;
    public double averageMobsDEF;
    public double averageMobsEXP;
    // player information
    public int hp;
    public int ht;
    public int damaged;
    public int attackDamage;

    static int preHp=20;
    static int postHp=0;
    static int healPoint=0;

    public int killMonster;
    public int moving;
    public int earnEXP;


    public Weapon hero_weapon = (Weapon) Dungeon.hero.belongings.weapon;

    public Armor hero_armor=Dungeon.hero.belongings.armor;

    // 장착 아이템의 정보보
    public int weapon_damage = (hero_weapon.max(hero_weapon.level())+hero_weapon.min(hero_weapon.level()))/2;
    public int weapon_strreq = hero_weapon.STRReq(hero_weapon.level());
    public int weapon_static = hero_weapon.WEAPON_STATIC;

    public int armor_armor = (hero_armor.DRMax(hero_armor.level())+hero_armor.DRMin(hero_armor.level()))/2;
    public int armor_strreq = hero_armor.STRReq(hero_armor.level());
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
    public int get_spells_cnt(){
        return Dungeon.spells_cnt;
    }

    public int artifacts=get_artifacts_cnt();
    public int bombs=get_bombs_cnt();
    public int food=get_food_cnt();
    public int potions=get_potions_cnt();
    public int scrolls=get_scrolls_cnt();
    public int spells=get_spells_cnt();



    // 변수 저장을 쉽게 하기 위해 임시 저장
    static private int totalDamaged=0;
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
    /*public void storeMobs(HashSet<Mob> mobs_clone){
        mobs = new ArrayList<>(mobs_clone);

        storeSpawnMobsHT();
        storeSpawnMobsATT();
        storeSpawnMobsDEF();
        storeSpawnMobsEXP();
    }
    */

    public void storeTraps(List<Trap> traps){
        this.traps = traps;
    }
    /*
    // 스폰된 mob들의 정보 추출
    public void storeSpawnMobsHT(){
        if(spawnMobsHT == null) spawnMobsHT = new ArrayList<>();
        else spawnMobsHT.clear();
        for(int i = 0; i < mobs.size(); i++)
            {spawnMobsHT.add(mobs.get(i).HT);}
        averageMobsHT = arrayListAverage(spawnMobsHT);
    }

    public void storeSpawnMobsATT(){
        if(spawnMobsATT == null) spawnMobsATT = new ArrayList<>();
        else spawnMobsATT.clear();
        for(int i = 0; i < mobs.size(); i++)
        {spawnMobsATT.add((mobs.get(i).MAX_ATT + mobs.get(i).MIN_ATT) / 2);}
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
*/
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

    public void storeHealing(int postHp){
        if(preHp<postHp){
            healPoint += (postHp-preHp);
        }
        preHp = postHp;
    }

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
    static public Data getInstance(){
        if(Data!=null){
            return Data;
        }else
            return new Data();
    }
    public void print(){
        //System.out.println("mobs : " + mobs.size());
        System.out.println("traps : " + traps.size());
        System.out.println("hp : " + hp);
        System.out.println("ht : " + ht);
        System.out.println("damaged : " + damaged);
        System.out.println("attackDamage : " + attackDamage);
        System.out.println("totalAttackDamage : " + totalAttackDamage);
        System.out.println("killMonster : " + killMonster);
        System.out.println("moving : " + moving);
        System.out.println("earnEXP : " + earnEXP);

        System.out.println("spawnMobsATT");
        /*for(int i = 0; i < spawnMobsATT.size(); i++){
            System.out.println(spawnMobsATT.get(i));
        }*/
    }

    public static void makeCSV(ArrayList<Data> data){
        String filePath = "C:\\spd\\temp_data.csv";

        File file = null;
        BufferedWriter bw = null;

        try{
            file = new File(filePath);
            bw = new BufferedWriter((new FileWriter(file, true)));

//            bw.write("HP,HT,damaged,attackDamage,killMonster,moving,earnEXP,averageMobsHT," +
//                    "averageMobsATT,averageMobsDEF,averageMobsEXP,depth");
//            bw.write("\r\n");

            for(int i = 0; i < data.size(); i++){
                Data temp = data.get(i);

                bw.write(temp.hp + "," +
                        temp.ht + "," +
                        temp.damaged + "," +
                        temp.attackDamage + "," +
                        temp.killMonster + "," +
                        temp.moving + "," +
                        temp.earnEXP + "," +
                        temp.artifacts + "," +
                        temp.bombs + "," +
                        temp.food + "," +
                        temp.potions + "," +
                        temp.scrolls + "," +
                        temp.spells + "," +
                        healPoint + "," +
                        /*temp.averageMobsHT + "," +
                        temp.averageMobsATT + "," +
                        temp.averageMobsDEF + "," +
                        temp.averageMobsEXP + "," +*/
                        temp.weapon_damage + "," +
                        temp.weapon_static + "," +
                        temp.weapon_strreq + "," +
                        temp.armor_armor + "," +
                        temp.armor_static + "," +
                        temp.armor_strreq + "," +
                        temp.depth);
                bw.write("\r\n");
            }

            bw.flush();
            bw.close();
        } catch(Exception e){
        }

    }
    public ArrayList<Object> returnlist() {
        ArrayList<Object> list = new ArrayList<>();
        //Date today = new Date();

        list.add(this.hp);
        list.add(this.ht);
        list.add(this.damaged);
        list.add(this.attackDamage);
        list.add(this.killMonster);
        list.add(this.moving);
        list.add(this.earnEXP);
        /*list.add(this.averageMobsHT);
        list.add(this.averageMobsATT);
        list.add(this.averageMobsDEF);
        list.add(this.averageMobsEXP);*/
        list.add(this.depth);

        return list;
    }
    public int CreateCSV(ArrayList<Object> list, String title, String filepath) {

        int cnt = 0;

        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(filepath+"/"+title+".csv", true));

            for(Object dom : list){
                fw.write(dom+",");
                cnt++;
            }
            fw.newLine();
            fw.flush();
            fw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return cnt;
    }
}