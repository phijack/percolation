/**
 * Created by lenovo on 2015/6/25.
 */
public class Percolation {
    private int[] open;
    private int n;
    private WeightedQuickUnionUF uf;
    public Percolation(int N)
    {
        n=N;
        uf =new WeightedQuickUnionUF(N*N+1);
        open = new int[N*N+1];
        for(int j=0;j<(n*n+1);j++)
        {open[j]=0;}
        open[0]=1;
        for(int i=0;i<n+1;i++) uf.union(0,i);
    }
    public boolean isOpen(int i,int j)
    {
        return (open[(i-1)*n+j]==1);
    }
    public void open(int i,int j)
    {
        open[(i-1)*n+j]= 1;
        if(j==n&&i<n)
        {
            uf.union((i-1)*n+j,i*n+j);
            if(isOpen(i,j-1)) uf.union((i-1)*n+j,(i-1)*n+j-1);
        }
        else
        {
            if(j==1&&i>1)
            {
                if(isOpen(i,j+1)) uf.union((i-1)*n+j,(i-1)*n+j+1);
                if(i<n) uf.union((i-1)*n+j,i*n+j);
            }
            else
            {
                if(i==n&&j<n)
                {
                    if(isOpen(i,j+1)) uf.union((i-1)*n+j,(i-1)*n+j+1);
                    if(isOpen(i,j-1)) uf.union((i-1)*n+j,(i-1)*n+j-1);
                }
                else
                {
                    if(i<n&&j>1&&j<n){
                        if(isOpen(i,j+1)) uf.union((i-1)*n+j,(i-1)*n+j+1);
                        if(isOpen(i,j-1)) uf.union((i-1)*n+j,(i-1)*n+j-1);
                        uf.union((i-1)*n+j,i*n+j);
                    }
                }
            }
        }


    }
    public boolean isFull(int i,int j)
    {
        if (isOpen(i, j)) return uf.connected(0, (i - 1) * n + j);
        return false;
    }
    public boolean percolates()
    {
        boolean flag=false;
        for(int i=1;i<n+1;i++)
        {
            if(isFull(n,i))
            {
                flag=true;
                break;
            }
        }
        return flag;
    }
    public static void main(String[] arg)
    {
        int N = StdIn.readInt();
        Percolation pe = new Percolation(N);
        while(!StdIn.isEmpty())
        {
            int i = StdIn.readInt();
            int j = StdIn.readInt();
            if(pe.isOpen(i,j))continue;
            pe.open(i,j);
            StdOut.println(i+","+j);
        }
        StdOut.println(pe.percolates());
    }
}
