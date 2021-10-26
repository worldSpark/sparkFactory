package com.sliding_windows;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个字符串s和一个非空字符串p，找到s中所有是p的字母异位词的子串，返回这些子串的起始索引。
 * 字符串只包含小写英文字母，并且字符串s和 p的长度都不超过 20100。
 *
 * 说明：
 * 字母异位词指字母相同，但排列不同的字符串。
 * 不考虑答案输出的顺序。
 * 输入:
 * s: "cbaebabacd" p: "abc"
 * 输出:
 * [0, 6]
 * 解释:
 * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的字母异位词。
 * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的字母异位词。
 * 示例 2:
 * 输入:
 * s: "abab" p: "ab"
 * 输出:
 * [0, 1, 2]
 * 解释:
 * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的字母异位词。
 * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的字母异位词。
 * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的字母异位词
 */
public class FindAllAnagrams {
    // 方法一：暴力法，枚举所有的长度为p.length()的子串
    public List<Integer> findAnagrams1(String s, String p){
        // 定义一个结果列表
        ArrayList<Integer> result = new ArrayList<>();

        // 1. 统计p中所有字符频次
        int[] pCharCounts = new int[26];

        for (int i = 0; i < p.length(); i++){
            pCharCounts[p.charAt(i) - 'a'] ++;
        }

        // 2. 遍历s，以每一个字符作为起始，考察长度为p.length()的子串
        for (int i = 0; i <= s.length() - p.length(); i++){
            // 3. 判断当前子串是否为p的字母异位词
            boolean isMatched = true;

            // 定义一个数组，统计子串中所有字符频次
            int[] subStrCharCounts = new int[26];

            for (int j = i; j < i + p.length(); j++){
                subStrCharCounts[s.charAt(j) - 'a'] ++;

                // 判断当前字符频次，如果超过了p中的频次，就一定不符合要求
                if (subStrCharCounts[s.charAt(j) - 'a'] > pCharCounts[s.charAt(j) - 'a']){
                    isMatched = false;
                    break;
                }
            }
            if (isMatched)
                result.add(i);
        }
        return result;
    }

    // 方法二：滑动窗口法，分别移动起始和结束位置
    public List<Integer> findAnagrams(String s, String p){
        // 定义一个结果列表
        ArrayList<Integer> result = new ArrayList<>();

        // 1. 统计p中所有字符频次
        int[] pCharCounts = new int[26];

        for (int i = 0; i < p.length(); i++){
            pCharCounts[p.charAt(i) - 'a'] ++;
        }

        // 统计子串中所有字符出现频次的数组
        int[] subStrCharCounts = new int[26];
        // 定义双指针，指向窗口的起始和结束位置
        int start = 0, end = 1;

        // 2. 移动指针，总是截取字符出现频次全部小于等于p中字符频次的子串
        while (end <= s.length()){
            // 当前新增字符
            char newChar = s.charAt(end - 1);
            subStrCharCounts[newChar - 'a'] ++;

            // 3. 判断当前子串是否符合要求
            // 如果新增字符导致子串中频次超出了p中频次，那么移动start，消除新增字符的影响
            while ( subStrCharCounts[newChar - 'a'] > pCharCounts[newChar - 'a'] && start < end){
                char removedChar = s.charAt(start);
                subStrCharCounts[removedChar - 'a'] --;
                start ++;
            }

            // 如果当前子串长度等于p的长度，那么就是一个字母异位词
            if (end - start == p.length())
                result.add(start);

            end ++;
        }
        return result;
    }

    public static void main(String[] args) {
        String s = "cbaebabacd";
        String p = "abc";

        FindAllAnagrams findAllAnagrams = new FindAllAnagrams();

        List<Integer> result = findAllAnagrams.findAnagrams(s, p);

        for (int index: result){
            System.out.print(index + "\t");
        }
        System.out.println();
    }
}
