package com.el.canno.random;

import java.util.*;

/**
 * User Canno
 * Date 2018/4/25
 * Time 16:47
 */
public class RandomUtils {
//    static Random random = new Random(19930902);
    static Random random = new Random(19940527);

    /**
     * 随机生产大乐透彩票
     *
     * @param totalCount
     * @return
     */
    public static Set<ArrayList<String>> getRandom(int totalCount) {
        Set<ArrayList<String>> sets = new HashSet<>();
        while (sets.size() < totalCount) {
            Set<Integer> randomSet = new TreeSet<>();
            ArrayList<String> sortList = new ArrayList<>();
            while (randomSet.size() < 5) {
                randomSet.add(random.nextInt(29) + 1);
            }
            for(Integer i : randomSet){
                if(i < 10){
                    sortList.add("0" + i);
                }else {
                    sortList.add(i.toString());
                }
            }
            randomSet.clear();

            while (randomSet.size() < 2) {
                randomSet.add(random.nextInt(11) + 1);
            }

            for(Integer i : randomSet){
                if(i < 10){
                    sortList.add("0" + i);
                }else {
                    sortList.add(i.toString());
                }
            }

            sets.add(sortList);
        }

        System.out.println();

        return sets;
    }

    public static void main(String[] args) {
        getRandom(5).forEach(
                (v) -> {
                    v.forEach(integer -> System.out.print(integer + "\t"));
                    System.out.println();
                }

        );

    }
}
