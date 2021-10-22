package com.binary_search;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 *  TODO 寻找重复数
 */
public class FindDuplicatedNumber {
    // TODO 方法一：使用HashMap保存每个数出现的次数
    public int findDuplicate1(int[] nums){
        HashMap<Integer, Integer> countMap = new HashMap<>();
        // 遍历所有元素，统计count值
        for (Integer num: nums){
            // 判断当前num是否在map中出现过
            if (countMap.containsKey(num))
                return num;    // 如果出现过，num就是重复数
            else
                countMap.put(num, 1);
        }
        return -1;
    }

    // TODO 方法二：使用HashSet保存数据，判断是否出现过
    public int findDuplicate2(int[] nums){
        HashSet<Integer> hashSet = new HashSet<>();
        // 遍历所有元素，添加到set中
        for (Integer num: nums){
            // 判断当前num是否在map中出现过
            if (hashSet.contains(num))
                return num;    // 如果出现过，num就是重复数
            else
                hashSet.add(num);
        }
        return -1;
    }

    // TODO 方法四：二分查找，查找1~N的自然数序列，寻找target
    public int findDuplicate4(int[] nums){
        // 定义左右指针
        int left = 1;
        int right = nums.length - 1;

        while (left <= right){
            // 计算中间值
            int mid = (left + right) / 2;

            // 对当前的mid计算count值
            int count = 0;
            for (int j = 0; j < nums.length; j++){
                if (nums[j] <= mid) count ++;
            }

            // 判断count和mid本身的大小关系
            if (count <= mid)
                left = mid + 1;    // count小于等于mid自身，说明mid比target小，左指针右移
            else
                right = mid;

            // 左右指针重合时，找到target
            if (left == right)
                return left;
        }
        return -1;
    }

    // TODO 方法五：快慢指针

    /**
     * 时间复杂度：O(n)，不管是寻找环上的相遇点，还是环的入口，访问次数都不会超过数组长度。
     * 空间复杂度：O(1)，我们只需要定义几个指针就可以了。
     * @param nums
     * @return
     */
    public int findDuplicate(int[] nums){
        // 定义快慢指针
        int fast = 0, slow = 0;
        // 1. 寻找环内的相遇点
        do {
            // 快指针一次走两步，慢指针一次走一步
            slow = nums[slow];
            fast = nums[nums[fast]];
        }while (fast != slow);

        // 循环结束，slow和fast相等，都是相遇点
        // 2. 寻找环的入口点
        // 另外定义两个指针，固定间距
        int before = 0, after = slow;
        while (before != after){
            before = nums[before];
            after = nums[after];
        }

        // 循环结束，相遇点就是环的入口点，也就是重复元素
        return before;
    }

    public static void main(String[] args) {
        int[] nums1 = {1,3,4,2,2,3,6,7};
        int[] nums2 = {1,1};

        FindDuplicatedNumber findDuplicatedNumber = new FindDuplicatedNumber();
        System.out.println(findDuplicatedNumber.findDuplicate1(nums1));
        System.out.println(findDuplicatedNumber.findDuplicate(nums1));
        System.out.println(findDuplicatedNumber.findDuplicate4(nums1));
    }
}
