package com.binary_search;

/**
 * 编写一个高效的算法来判断m x n矩阵中，是否存在一个目标值。该矩阵具有如下特性：
 * 每行中的整数从左到右按升序排列。
 * 每行的第一个整数大于前一行的最后一个整数。
 * 示例 1：
 * 输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,50]], target = 3
 * 输出：true
 * 示例 2：
 * 输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,50]], target = 13
 * 输出：false
 * 示例 3：
 * 输入：matrix = [], target = 0
 * 输出：false
 */
public class SearchMatrix {
    public boolean searchMatrix( int[][] matrix, int target ){
        // 先定义m和n
        int m = matrix.length;
        if (m == 0) return false;
        int n = matrix[0].length;

        // 二分查找，定义左右指针
        int left = 0;
        int right = m * n - 1;

        while ( left <= right ){
            // 计算中间位置
            int mid = (left + right) / 2;
            // 计算二维矩阵中对应的行列号，取出对应元素
            int midElement = matrix[mid/n][mid%n];

            // 判断中间元素与target的大小关系
            if (midElement < target)
                left = mid + 1;
            else if (midElement > target)
                right = mid - 1;
            else
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = {{1,3,5,7}, {10,11,16,20}, {23,30,34,50}};
        int target = 13;
        SearchMatrix searchMatrix = new SearchMatrix();

        System.out.println(searchMatrix.searchMatrix(matrix, target));
    }
}
