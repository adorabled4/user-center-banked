package pers.dhx_.usercenter.leetcode;

import lombok.val;

/**
 * @author Dhx_
 * @className MainLCP
 * @description TODO
 * @date 2022/9/25 20:28
 */
public class MainLCP {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    /*
    *
- 开关 `1`：切换当前节点的灯的状态；
- 开关 `2`：切换 **以当前节点为根** 的子树中，所有节点上的灯的状态，；
- 开关 `3`：切换 **当前节点及其左右子节点**（若存在的话） 上的灯的状态；*/
    public int closeLampInTree(TreeNode root) {

        return 0;
    }
    int min(int...args){
        int res=args[0];
        for (int i = 1; i < args.length; i++) {
            res=Math.min(args[i],res);
        }
        return res;
    }
    /*
做法树形dp，大概就是每个结点有4种状态
1.根结点+子树全部亮
2.根不亮+子树全部亮
3.根亮+子树全部不亮
4.根结点+子树全部不亮
*/
    class Solution {
        int[][] sz = new int[100010][2];
        int[][] dp = new int[100010][4];
        int idx = 0;

        public int closeLampInTree(TreeNode root) {
            dfs(root);
            return dp[1][3];
        }

        public int dfs(TreeNode p) {
            if (p == null) {
                return 0;
            }
            int rt = ++idx;
            sz[rt][p.val] = 1;
            int l = dfs(p.left), r = dfs(p.right);
            sz[rt][0] += sz[l][0] + sz[r][0];
            sz[rt][1] += sz[l][1] + sz[r][1];
            dp[rt][0] = sz[rt][0];
            dp[rt][3] = sz[rt][1];
            dp[rt][1] = sz[l][0] + sz[r][0] + p.val;
            dp[rt][2] = sz[l][1] + sz[r][1] + 1 - p.val;
            if (l == 0 && r == 0) {
                return rt;
            }
            int x = p.val;
            int y = 1 - p.val;
            dp[rt][1] = Math.min(dp[rt][1], dp[l][0] + dp[r][0] + x);
            dp[rt][1] = Math.min(dp[rt][1], dp[l][3] + dp[r][3] + 1 + y);
            dp[rt][1] = Math.min(dp[rt][1], dp[l][1] + dp[r][1] + 1 + y);

            dp[rt][2] = Math.min(dp[rt][2], dp[rt][1] + 1);
            dp[rt][2] = Math.min(dp[rt][2], dp[l][3] + dp[r][3] + y);
            dp[rt][2] = Math.min(dp[rt][2], dp[l][0] + dp[r][0] + 1 + x);
            dp[rt][2] = Math.min(dp[rt][2], dp[l][2] + dp[r][2] + 1 + x);

            dp[rt][0] = Math.min(dp[rt][0], dp[rt][1] + 1);
            dp[rt][0] = Math.min(dp[rt][0], dp[l][0] + dp[r][0] + y);
            dp[rt][0] = Math.min(dp[rt][0], dp[l][3] + dp[r][3] + 1 + x);
            dp[rt][0] = Math.min(dp[rt][0], dp[l][1] + dp[r][1] + 1 + x);

            dp[rt][3] = Math.min(dp[rt][3], dp[rt][0] + 1);
            dp[rt][3] = Math.min(dp[rt][3], dp[l][3] + dp[r][3] + x);
            dp[rt][3] = Math.min(dp[rt][3], dp[l][0] + dp[r][0] + 1 + y);
            dp[rt][3] = Math.min(dp[rt][3], dp[l][2] + dp[r][2] + 1 + y);

            dp[rt][0] = Math.min(dp[rt][0], dp[rt][3] + 1);
            dp[rt][1] = Math.min(dp[rt][1], dp[rt][2] + 1);
            return rt;
        }
    }
    class MyLinkedList {
        class Node{
            public int val;
            public Node next;
            Node(int val){
                this.val=val;
            }
            Node(){}
        }
        Node head;
        int len;
        public MyLinkedList() {
            len=0;
            head=null;
        }

        public int get(int index) {
            if(index>=len){
                return -1;
            }
            int cnt=0;
            Node cur=head;
            while(cnt!=index){
                cur=cur.next;
                cnt++;
            }
            return cur.val;
        }

        public void addAtHead(int val) {
            System.out.println("add head: "+val);
            Node pre=new Node(val);
            pre.next=head;
            head=pre;
            len++;
        }

        public void addAtTail(int val) {
            Node cur=head;
            while(cur.next!=null){
                cur=cur.next;
            }
            System.out.println("add last: "+val);

            cur.next=new Node(val);
            len++;
        }

        public void addAtIndex(int index, int val) {
            if(index>len){
                return ;
            }
            if(index<0){
                addAtHead(val);
                return ;
            }
            if(index==len){// 1  2
                addAtTail(val);
                return ;
            }
            int cnt=1; // 需要走到 添加节点 的位置的前一个节点进行操作, 因此初始化为1
            Node cur=head;
            while(cnt!=index){
                cnt++;
                cur=cur.next;
            }
            Node tmp=cur.next;
            cur.next=new Node(val);
            System.out.println("delete idx: " +cnt+" val : "+val);
            cur.next.next=tmp;
            len++;
        }

        public void deleteAtIndex(int index) {
            if(index>=len||index<0){
                return ;
            }
            int cnt=1;
            Node cur=head;
            while(cnt!=index){
                cnt++;
                cur=cur.next;
            }
            Node tmp=cur.next;
            System.out.println("delete idx: " +cnt+" head: "+tmp.val);
            cur.next=tmp.next==null?null:tmp.next;
            len--;
        }
    }
}
