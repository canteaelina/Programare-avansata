package Lab1;

public class Matrix2D
{
    public static String MatToString(int mat[][])
    {
        String res = "";
        int i, j;
        for(i = 0; i < mat.length; i++)
        {
            for (j = 0; j < mat.length; j++)
            {
                if (mat[i][j] == 255) res = res + '\u2588';
                else
                    res = res + '\u2591';
            }
            res = res + '\n';
        }
        return res;
    }

    public static int[] BoundingBox(int mat[][], int color)
    {
        int n = mat.length;
        int minl = n, minc = n;
        int maxl = -1, maxc = -1;
        int i, j;

        for(i = 0; i < n; i++)
            for(j = 0; j < n; j++)
            {
                if(mat[i][j] == color)
                {
                    if(i < minl)    minl = i;
                    if(i > maxl)    maxl = i;
                    if(j < minc)    minc = j;
                    if(j > maxc)    maxc = j;
                }

            }
        if(minl == n) return new int[]{-1, -1, -1, -1};

        return new int[]{minl, minc, maxl, maxc};

    }

    public static int[][] Boundary(int mat[][], int shcol, int bgcol)
    {
        int n = mat.length;
        int boundary[][] = new int[n][n];
        int i, j, k;

        for(i = 0; i < n; i++)
            for(j = 0; j < n; j++)
                boundary[i][j] = bgcol;

        int dl[] = {-1, 1, 0, 0};
        int dc[] = {0, 0, -1, 1};

        for(i = 0; i < n; i++)
            for(j = 0; j < n; j++)
            {
                if(mat[i][j] == shcol)
                {
                    boolean edge = false;
                    if(i == 0 || i == n - 1 ||  j == 0 || j == n -1)
                        edge = true;
                    else
                    {
                        for(k = 0; k < 4; k++)
                        {
                            int vecinl = i + dl[k];
                            int vecinc = j + dc[k];

                            if(mat[vecinl][vecinc] == bgcol)
                            {
                                edge = true;
                                break;
                            }
                        }
                    }

                    if(edge)
                        boundary[i][j] = shcol;
                }
            }

        return boundary;
    }

    public static void main (String args[]) {
        if (args.length != 2) {
            System.out.println("Invalid arguments!");
            System.exit(-1);
        }
        long st = System.currentTimeMillis();

        int bb[] = new int[4]; //PT BOUNDING BOX
        String shape = args[1];
        int n = Integer.parseInt(args[0]);

        int boundary[][] = new int[n][n]; // PT BOUNDARY

        int matrix[][] = new int[n][n];

        if (shape.equals("rectangle"))
        {
            int i, j;
            for (i = 0; i < n; i++)
                for (j = 0; j < n; j++)
                    matrix[i][j] = 255; //fundal alb
            for (i = n / 4; i < n - n / 4; i++)
                for (j = n / 4; j < n - n / 4; j++)
                    matrix[i][j] = 0; //dreptunghi negru

            bb = BoundingBox(matrix, 0);
            boundary = Boundary(matrix, 0, 255);

        }
        else
        if(shape.equals("circle"))
        {

            int i, j;
            for (i = 0; i < n; i++)
                for (j = 0; j < n; j++)
                    matrix[i][j] = 0; //fundal negru (era implicit)
            int cx = n / 2;
            int cy = n / 2;
            int r = n / 4;
            for (i = 0; i < n; i++)
                for (j = 0; j < n; j++)
                {
                    int dx = i - cx;
                    int dy = j - cy;
                    if(dx * dx + dy * dy < r * r)
                        matrix[i][j] = 255; //CERC ALB
                }

            bb = BoundingBox(matrix, 255);

            boundary = Boundary(matrix, 255, 0);
        }
        else
        {
            System.out.println("Invalid shape!");
            System.exit(-2);
        }

        for(int i= 0; i < 4; i++)
            System.out.println(bb[i]);

        String boundary2 = MatToString(boundary);
        System.out.println(boundary2);

        /*for(int i= 0; i < boundary.length; i++)
        {
            for(int j= 0; j < boundary.length; j++)
                System.out.print(boundary[i][j]);
            System.out.print('\n');
        }*/

        String res = MatToString(matrix);
        long end = System.currentTimeMillis();
        if (end - st > 7000)
            System.out.println(end - st + "miliseconds");
        else
            System.out.println(res);

    }
}
