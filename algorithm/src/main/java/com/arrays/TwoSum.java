package com.arrays;


import java.util.*;

/**
 * 用于集合的随机两个数之和等于多少
 * list = [1,3,2,4,5]   list[1]+list[3]=5   做一个这样的算法
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *
 */
public class TwoSum {

    //方法二,哈希表保存所有数信息

    /**
     * 时间复杂度：O(N)，我们把包含有O(N)个元素的列表遍历两次。由于哈希表将查找时间缩短到O(1)，所以时间复杂度为O(N)。
     * 空间复杂度：O(N)，所需的额外空间取决于哈希表中存储的元素数量，该表中存储了 N 个元素。
     * @param nums
     * @param target
     * @return
     */
    public int[] towSum2(int[] nums,int target){
        //定义一个哈希
        Map<Integer,Integer> map = new HashMap<>();
        //遍历数组,全部保存如hash表
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i],i);
        }
        //再次遍历数组,寻找每个数对应的个数是否存在
        for (int i = 0; i < nums.length; i++) {
            int thatNum = target - nums[i];
            //存在,且不是当前数自身,返回结果
            if(map.containsKey(thatNum) && map.get(thatNum) != i){
                return new int[]{i,map.get(thatNum)};
            }
        }
        throw new IllegalArgumentException("未找到");
    }

    /**
     * 方法三,改进哈希表,遍历一次  一遍哈希表
     * TODO 以后尽量用这个方法
     * 时间复杂度：O(N)，我们只遍历了包含有N个元素的列表一次。在表中进行的每次查找只花费O(1)的时间。其实这个过程中，我们也借鉴了动态规划的思想、把子问题解保存起来，后面用到就直接查询。
     * 空间复杂度：O(N)，所需的额外空间取决于哈希表中存储的元素数量，该表最多需要存储N个元素。
     */
    public int[] twoSum(int[] nums,int target){

        Map<Integer,Integer> map = new HashMap<>();
        //遍历,寻找每个对应的那个数是否存在,只向前寻找
        for (int i = 0; i < nums.length; i++) {
            int thatNum = target - nums[i];
            if(map.containsKey(thatNum)){
                return new int[]{map.get(thatNum),i};
            }
            map.put(nums[i],i);
        }
        throw new IllegalArgumentException("未找到");
    }

    public static void main(String[] args) {
        int[] input = {2,7,11,15,1,8,3,6};
        int[] input2 = {3, 1, 3};
        int target = 9;
        int target2 = 6;
        TwoSum twoSum = new TwoSum();
        ArrayList<int[]> num = twoSum.getNum(input, target);
        System.out.println(num);
        // 定义一个大数组，进行性能测试
        int[] input3 = new int[1000000];
        for (int i = 0; i < input3.length; i++)
            input3[i] = input3.length - i;

        int target3 = 567890;

        // 为了计算程序运行时间，开始计算和计算完成分别计时
        long startTime = System.currentTimeMillis();

        int[] output = twoSum.twoSum(input3, target3); //时间:91ms
//        int[] output = twoSum.towSum2(input3, target3);//105ms

        long endTime = System.currentTimeMillis();

        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");

        for (int index: output){
            System.out.print(index + "\t");
        }
    }

    public ArrayList<int[]> getNum(int[] input3,int target){
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        ArrayList<int[]> objects = new ArrayList<>();
        //获取循环
        for (int i = 0; i < input3.length; i++) {
            hashMap.put(input3[i],i);
        }
        for (int i = 0; i < input3.length; i++) {
            int thisNum = target - input3[i];
            if(hashMap.containsKey(thisNum) && hashMap.get(thisNum) != i){
                int[] ints = {input3[i], thisNum};
                objects.add(ints);
            }
        }
        if(objects.size()>0){
            return objects;
        }
        throw new IllegalArgumentException("未找到");
    }

}
