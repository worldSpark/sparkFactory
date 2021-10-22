package com.strings;

/**
 * 这里不允许直接将输入字符串转为整数，那自然想到应该把字符串按每个字符char一一拆开，
 * 相当于遍历整数上的每一个数位，然后通过“乘10叠加”的方式，就可以整合起来了。这相当于算术中的“竖式加法”。
 * TODO 另外题目要求不能使用BigInteger的内建库，这其实就是让我们自己实现一个大整数相加的功能。
 */
public class AddStrings {
    public String addStrings(String num1, String num2){
        // 定义一个StringBuffer，保存最终的结果
        StringBuffer result = new StringBuffer();

        // 定义遍历两个字符串的初始位置
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int carry = 0;    // 用一个变量保存当前的进位

        // 从个位开始依次遍历所有数位，只要还有数没有计算，就继续；其他数位补0
        while ( i >= 0 || j >= 0 || carry != 0 ){
            // 取两数当前的对应数位
            int n1 = i >= 0 ? num1.charAt(i) - '0' : 0;     // 字符要将ascii码转换为数字
            int n2 = j >= 0 ? num2.charAt(j) - '0' : 0;

            // 对当前数位求和
            int sum = n1 + n2 + carry;

            // 把sum的个位保存到结果中，十位作为进位保存下来
            result.append(sum % 10);
            carry = sum / 10;

            // 移动指针，继续遍历下一位
            i --;
            j --;
        }

        return result.reverse().toString();
    }

    public static void main(String[] args) {
        String num1 = "745346";
        String num2 = "8564";

        AddStrings addStrings = new AddStrings();
        System.out.println(addStrings.addStrings(num1, num2));
        System.out.println(Integer.parseInt(num1)+Integer.parseInt(num2));

//        char c = '0';
//        int i = c;
//        System.out.println(c);
//        System.out.println(i);
    }
}
