package pers.dhx_.usercenter.leetcode;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Dhx_
 * @className leetcode3
 * @description TODO
 * @date 2022/9/22 20:13
 */
public class leetcode3 {
    public boolean wordPattern(String pattern, String s) {

        String[]strings=s.split(" ");
        String []patterns=pattern.split("");
        if(strings.length!=patterns.length) return false;
        HashMap<String,String> map=new HashMap<>();
        for (int i = 0; i < strings.length; i++) {
            if(!map.containsKey(strings[i])){
                if(map.containsValue(patterns[i])){
                    return false;
                }
                map.put(strings[i],patterns[i]);
            }else if(!map.get(strings[i]).equals(patterns[i])){
                return false;
            }
        }
        return true;
    }
    int left=0;
    int right=0;
    int res=0;
    public int longestPalindrome(String s) {
        char[]chars=s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            count(chars,i,i+1);// 偶数规模回文串
            count(chars,i,i);// 奇数规模回文串
        }
        return res;
    }
    void count(char[]chars,int left,int right){
        while(left>=0&&right<=chars.length&&chars[left]==chars[right]){
            left--;
            right++;
        }
        res=Math.max(right-left,right);
    }
    public String addStrings(String num1, String num2) {
        StringBuilder s1=new StringBuilder(num1);
        StringBuilder s2=new StringBuilder(num2);
        s1.reverse();
        s2.reverse();
        char[]chars1=s1.toString().toCharArray();
        char[]chars2=s2.toString().toCharArray();
        StringBuilder res=new StringBuilder();
        int pre=0; //存储进位
        for (int i = 0; i < Math.max(s1.length(),s2.length()); i++) {
            int cur=0;
            if(chars1.length>i){
                cur+=chars1[i]-48;
            }
            if(chars2.length>i){
                cur+=chars2[i]-48;
            }
            pre=cur/10;
            res.append(cur%10+pre);
        }
        return res.reverse().toString();
    }
    class MyLinkedList {

        class Node{
            int val;
            Node next;
            public Node(int val){
                this.val=val;
            }
        }
        LinkedList<Node> list=new LinkedList<>();
        Node head;
        public MyLinkedList() {

        }

        public int get(int index) {
            return 0;
        }

        public void addAtHead(int val) {
            list.addFirst(new Node(val));
        }

        public void addAtTail(int val) {
            list.addLast(new Node(val));
        }

        public void addAtIndex(int index, int val) {
            list.add(index,new Node(val));
        }

        public void deleteAtIndex(int index) {
            list.remove(index);
        }
    }

    @Test
    void temperatureTrendest(){
        temperatureTrend(new int[]{1,1,1,2},new int[]{1,1,1,1});
    }
        public int temperatureTrend(int[] temperatureA, int[] temperatureB) {
    //分析并返回两地气温变化趋势相同的最大连续天数
            int res=0;
            int up,down,used;
            int[]A=new int[temperatureA.length-1];
            int[]B=new int[temperatureB.length-1];
            for (int i = 0; i < A.length; i++) {
                A[i]=temperatureA[i+1]-temperatureA[i]; // <0 0 >0
            }
            for (int i = 0; i < B.length; i++) {
                B[i]=temperatureB[i+1]-temperatureB[i];
            }
            int pre=0;
            for (int i = 0; i < A.length; i++) {
                int tmp=A[i]*B[i];
                if(tmp>0){ //同号
                    pre++;
                }else if(A[i]==0&&B[i]==0){ //异号
                    pre++;
                }else{
                    pre=0;
                }
                res=Math.max(pre,res);
            }
            return  res+1;
        }
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    /*8
开关 1：切换当前节点的灯的状态；                              改变当前灯的状态 (关一个)
开关 2：切换 以当前节点为根 的子树中，所有节点上的灯的状态，；    改变当前灯为根节点 , 所有灯的状态(一次关完)
开关 3：切换 当前节点及其左右子节点（若存在的话） 上的灯的状态；  同时改变一个树的左右孩子的灯的状态(关左右孩子)
*/
    class Solution {
        int res = 0;

        public int closeLampInTree(TreeNode root) {
            dfs(root);
            return res;
        }

        // 先遍历左右孩子, 吧下面的节点的灯全部转移到  父节点,最后统一关闭?
//        void dfs(TreeNode root){
//            if(root==null){  // 如果当前的灯是开着的, 直接返回
//                return ;
//            }
//            if(root.val==1){
//                dfs(root.left);
//                dfs(root.right);
//                return ;
//            }
//            int sum=root.val+(root.right==null?0:root.right.val)+(root.left==null?0:root.left.val);
//            if(sum==0){
//                res++; // 同时开启当前节点以及左右孩子的情况
//                return ;
//            }
//            dfs(root.left);
//            dfs(root.right);
//            root.val=1;
//            res++; // 当前节点开灯
//        }
        // 先遍历当前的节点, 根据当前的节点判断  开灯
        void dfs(TreeNode root) {
            if (root == null) {
                return;
            }
            if (root.val == 1) {// 如果当前开灯 ,下一位
                dfs(root.left);
                dfs(root.right);
                return;
            }
            //root.val=0, 那么如何开灯?
            root.val=1; // 当前的位置开灯
            // 怎么开灯 ?  左右孩子+当前 or 当前为树的所有?
            dfs(root.left);
            dfs(root.right);
        }
    }
    void open(TreeNode root){
        if(root==null||root.val==1) return ;
        root.val=1;
        open(root.left);
        open(root.right);
    }
}
