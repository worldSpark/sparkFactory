package com.arrays;

import java.util.*;

/**
 * 示例
 * TODO 给定数组 nums = [-1, 0, 1, 2, -1, -4]，  三个值加起来为0
 * 满足要求的三元组集合为：
 * [
 *   [-1, 0, 1],
 *   [-1, -1, 2],
 *   [-1, 2, -1]
 * ]
 */
public class ThreeSum {

    //方法二:用哈希表保存结果
    public List<List<Integer>> threeSum2(int[] nums){
        List<List<Integer>> result = new ArrayList<>();
        Map<Integer,List<Integer>> map = new HashMap<>();
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            int thatNum = 0 - nums[i];
            if(map.containsKey(thatNum)){
                //存在就找到了一组解
                ArrayList<Integer> tempList = new ArrayList<>(map.get(thatNum));
                tempList.add(nums[i]);
                result.add(tempList);
            }
            //把当前数对应两组数保存map中
            for (int j = 0; j < i; j++) {
                //两数之和作为key
                int newKey = nums[i] +nums[j];
                //不包含则添加
                if(!map.containsKey(newKey)){
                    ArrayList<Integer> tempList = new ArrayList<>();
                    tempList.add(nums[i]);
                    tempList.add(nums[j]);
                    map.put(newKey,tempList);
                }
            }
        }
        return result;
    }

    //TODO 方法三:双指针法
    public List<List<Integer>> threeSum3(int[] nums){
        int n = nums.length;
        List<List<Integer>> result = new ArrayList<>();
        //1.先排序
        Arrays.sort(nums);
        //2.遍历每一个元素,作为当前三元组中最小的那个
        for (int i = 0; i < n; i++) {
            if(nums[i]>0){
                break;
            }
            //当前数已经出现过,直接跳过
            if(i>0 && nums[i] == nums[i-1]){
                continue;
            }
            //以当前数为最小数,定义左右指针
            int lp = i + 1;
            int rp = n - 1;
            //只要左右指针不重叠,继续移动指针
            while (lp<rp){
                int sum = nums[i] + nums[lp] + nums[rp];
                //判断sum,与0做大小对比
                if(sum == 0){
                    //为0,就找到了一组
                    result.add(Arrays.asList(nums[i],nums[lp],nums[rp]));
                    lp ++;
                    rp --;
                    //如果移动之后元素相同直接跳过
                    while (lp<rp && nums[lp] == nums[lp -1]){
                        lp ++;
                    }
                    while (lp<rp &&nums[rp] == nums[rp +1]){
                        rp --;
                    }
                }else if(sum<0){
                    //小于0,较小的数增大,左指针右移
                    lp ++;
                }else{
                    //大于0,较大的数减小,右指针左移
                    rp --;
                }
            }
        }
        return  result;
    }

    public static void main(String[] args) {
        int[] input = {-1, 0, 1, 2, -1, -4};

        ThreeSum threeSum = new ThreeSum();

        System.out.println(threeSum.threeSum3(input));
    }
}
