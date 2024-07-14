import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

import static java.lang.Math.abs;

class node{
    int x,y;
    node next;
}

interface IStack {
    /**
     * Removes the element at the top of stack and returns that element.
     * @return top of stack element, or through exception if empty
     */
    public Object pop();
    /**
     * Get the element at the top of stack without removing it from stack.
     * @return top of stack element, or through exception if empty
     */
    public Object peek();
    /**
     * Pushes an item onto the top of this stack.
     * @param element to insert
     */
    public void push(Object element);
    /**
     * Tests if this stack is empty
     * @return true if stack empty
     */
    public boolean isEmpty();
    public int size();
}
interface IMazeSolver {
    /**
     * Read the maze file, and solve it using Breadth First Search
     * @param maze maze file
     * @return the coordinates of the found path from point ’S’
     *
    to point ’E’ inclusive, or null if no path is found.
     */
    public int[][] solveBFS(java.io.File maze);
    /**
     * Read the maze file, and solve it using Depth First Search
     * @param maze maze file
     * @return the coordinates of the found path from point ’S’
     *
    to point ’E’ inclusive, or null if no path is found.
     */
    public int[][] solveDFS(java.io.File maze);
}
class Main implements IMazeSolver {
    static int ss1;
    static int ss2;
    static int d1=-1;
    static int d2=-1;
    static int s1;
    static int s2;
    public static void main(String[] args) throws FileNotFoundException {
        //taking the maze from the file
        File file=new File("D:\\java codes\\bfs\\src\\input.txt");
        Scanner scan=new Scanner(file);
        String in[]=scan.nextLine().split(" ");
        int r= Integer.parseInt(in[0]),c= Integer.parseInt(in[1]);
        char a[][]=new char[r][c];
        char b[][]=new char[r][c];
        for(int i=0;i<r;i++){
            String z= scan.nextLine();
            a[i]=z.toCharArray();
            b[i]=z.toCharArray();
        }
        // main menu
        System.out.println("Enter option number");
        System.out.println("1.DFS");
        System.out.println("2.BFS");
        System.out.println("3.Both");
        Scanner sc=new Scanner(System.in);
        int opt=sc.nextInt();

        int co[]=findS(a);
        int sx=co[0],sy=co[1];
        ss1 =sx;
        ss2 =sy;
        s1=ss1;
        s2=ss2;
        findE(a);
        if(d1==-1){
            System.out.println("cant find destination");
            System.exit(0);
        }
            BFS(a, sx, sy);
            if (d1 == -1) {
                System.out.println("Can't find Destination");
                System.exit(0);
            }
            getPoints();
            fltr();

            stack finalDfs = new stack();
            int i = 0;
            while (!(s.isEmpty())) {
                node n = (node) s.pop();
                if (n.x == -1) {
                    continue;
                }
                finalDfs.push(n);
            }
            node temp12 = finalDfs.top;
            if (finalDfs.isEmpty() || ss1 == -1) {
                System.out.println("no path");
                System.exit(0);
            }
            if(opt==1) {
                System.out.print("DFS: ");
                while (temp12.next != null) {
                    System.out.print("{" + temp12.x + "," + temp12.y + "}");
                    temp12 = temp12.next;
                    System.out.print(",");
                }
                System.out.print("{" + temp12.x + "," + temp12.y + "}");
                System.exit(0);
            }


            System.out.println();

            node parent[][] = new node[r][c];
            boolean visit[][] = new boolean[r][c];
            setFalse(visit);
            bfs(b, s1, s2, visit, parent);


            if(opt==2){
            System.out.print("BFS: ");
            printBfs(parent);
            System.exit(0);
        }
            else {
                System.out.print("DFS: ");
                while (temp12.next != null) {
                    System.out.print("{" + temp12.x + "," + temp12.y + "}");
                    temp12 = temp12.next;
                    System.out.print(",");
                }
                System.out.print("{" + temp12.x + "," + temp12.y + "}");
                System.out.println();
                System.out.print("BFS: ");
                printBfs(parent);
                System.exit(0);
            }
    }
    ///
    static void setFalse(boolean visit[][]){
        for(int i=0; i<visit.length; i++) {
            for(int j=0; j<visit[0].length; j++) {
                visit[i][j] = false;
            }
        }
    }
    public static void bfs(char a[][],int i,int j,boolean visit[][],node parent[][]){
        node first=new node();
        queue q=new queue();
        first.x=i;
        first.y=j;
        q.enqueue(first);
        visit[i][j]=true;
        while(!(q.isEmpty())){
            node work=(node)q.dequeue();
            if( work.x== d1 && work.y== d2){
                return;
            }
            if( isValid(a,work.x+1,work.y,visit)){

                node push=new node();
                push.x= work.x+1;
                push.y=work.y;
                q.enqueue(push);
                parent[work.x+1][work.y]= work;

                visit[work.x+1][work.y]=true;
            }
            if(  isValid(a,work.x-1,work.y,visit)){
                node push=new node();
                push.x= work.x-1;
                push.y=work.y;
                q.enqueue(push);
                parent[work.x-1][work.y]= work;
                visit[work.x-1][work.y]=true;
            }
            if( isValid(a,work.x,work.y+1,visit)){
                node push=new node();
                push.x= work.x;
                push.y=work.y+1;
                q.enqueue(push);
                parent[work.x][work.y+1]= work;
                visit[work.x][work.y+1]=true;
            }
            if( isValid(a,work.x,work.y-1,visit)){
                node push=new node();
                push.x= work.x;
                push.y=work.y-1;
                q.enqueue(push);
                parent[work.x][work.y-1]= work;
                visit[work.x][work.y-1]=true;
            }
        }

    }
    static boolean isValid(char a[][], int i, int j,boolean visit[][]){
        return (i>=0 && j>=0 && i<a.length && j<a[0].length && a[i][j]!='#' && !(visit[i][j]));
    }
    static void findE(char a[][]){
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[0].length;j++){
                if(a[i][j]=='E'){
                    d1 =i;
                    d2 =j;
                    break;
                }
            }
        }
    }
    static void printBfs(node parent[][]){
        node print[]=new node[10000];
        int i= d1,j= d2;
        stack show=new stack();
        node first=new node();
        first.x=d1;
        first.y=d2;
        show.push(first);
        node count=new node();
        count.x=d1;
        count.y=d2;
        while(true){
            try {
                show.push(parent[count.x][count.y]);
                count=parent[count.x][count.y];
            }
            catch (Exception e){
                break;
            }
        }
        node temp= show.top;
        if(temp.x!=s1 && temp.y!=s2){
            node ins=new node();
            ins.x=s1;
            ins.y=s2;
            show.push(ins);
            temp= show.top;
        }
        while(temp.next!=null){
            System.out.print("{"+temp.x+","+temp.y+"},");
            temp=temp.next;
        }
        System.out.print("{"+temp.x+","+temp.y+"}");
    }


    ///


    static stack s=new stack();
    static stack filter=new stack();
    public static void fltr(){
        node temp=s.top;
        node temp2=s.top;
        for(int i=0;temp!=null;i++){
            for(temp2=temp.next;temp2!=null;temp2=temp2.next){
                if((abs(temp.x-temp2.x)==1 && abs(temp.y-temp2.y)==0) || (abs(temp.y-temp2.y)==1 && abs(temp.x-temp2.x)==0)){
                    temp=temp2;
                    break;
                }
                else {
                    temp2.x=-1;
                    temp2.y=-1;
                }
            }
            if(temp2==null){
                break;
            }
        }
    }
    public static void BFS(char a[][], int i, int j){
        if(j<0 || j>=a[0].length || i<0 || i>=a.length || a[i][j]=='#'){

            return;
        }
        if(a[i][j]=='E'){
            node temp=new node();
            temp.x=i;
            temp.y=j;
            d1=i;
            d2=j;
            s.push(temp);
            a[i][j]='#';
            return;
        }
            node temp=new node();
            temp.x=i;
            temp.y=j;
            s.push(temp);
            a[i][j]='#';

            BFS(a,i-1,j);//up
            BFS(a,i+1,j);//down
            BFS(a,i,j-1);//left
            BFS(a,i,j+1);//right

    }
    public static void getPoints(){
        int c,r;
        node search=s.top;
        while(search!=null){
            if(search.x==d1 && search.y==d2){
                break;
            }
            search=search.next;
        }
        node delete=s.top;
        while(delete!=search){
            delete=delete.next;
            node dmg=(node)s.pop();
        }
    }
    public static boolean CanPass(char a[][], int i, int j){
        if(i<0 || j<0 || i>=a.length || j>=a[0].length){
            return false;
        }
        if(a[i][j]=='#'){
            return false;
        }
        return true;
    }
    public static int [] findS(char a[][]){
        int b[]=new int[2];
        int flag=0;
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[0].length;j++){
                if(a[i][j]=='S'){
                    b[0]=i;
                    b[1]=j;
                    flag=1;
                    break;
                }
            }
            if(flag==1){
                break;
            }
        }
        if(flag==0){
            System.out.println("can't find Source");
            System.exit(0);
        }
        return b;
    }


    @Override
    public int[][] solveBFS(File maze) {
        return new int[0][];
    }

    @Override
    public int[][] solveDFS(File maze) {
        return new int[0][];
    }
}
class stack implements IStack{
    node top=null;
    int size=0;
    @Override
    public Object pop() {
        if(top==null){
            return null;
        }
        node temp=top;
        top = top.next;
        temp.next = null;
        size--;

        return temp;
    }

    @Override
    public Object peek() {
        return top;
    }

    @Override
    public void push(Object element) {
        node x=(node)element;
        if(top==null){
            top=x;
            top.next=null;
            size++;
        }
        else {
            x.next = top;
            top = x;
            size++;
        }
    }

    @Override
    public boolean isEmpty() {
        if(top==null){
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

}
interface IQueue {
    /*** Inserts an item at the queue front.*/
    public void enqueue(Object item);
    /*** Removes the object at the queue rear and returnsit.*/
    public Object dequeue();
    /*** Tests if this queue is empty.*/
    public boolean isEmpty();
    /*** Returns the number of elements in the queue*/
    public int size();
}
class queue implements IQueue{
    node head=null,tail=null;
    int size=0;
    @Override
    public void enqueue(Object item) {
        node element1 = (node) item;
        if(head==null){
            head=element1;
            tail=element1;
            element1.next=null;
            size++;
        }
        else {
            tail.next=element1;
            tail=element1;
            element1.next=null;
            size++;
        }
    }

    @Override
    public Object dequeue() {
        if(head==null){
            System.out.println("Error");
            System.exit(0);
        }
        node re=head;
        head=head.next;
        size--;
        return re;
    }

    @Override
    public boolean isEmpty() {
        if(size==0){
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }
}