/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication;

/**
 *
 * @author hemra
 */
import com.sun.istack.internal.NotNull;

public class SPLAYN {
    //---------- Переменные
    private double[][] N;
    private double[][] M;
    private double[][] K;
    private double[][] f;
    double[][] r;
    double[][] z;
    private double a;
    private double b;
    private double c;
    private double d;
    private double h;
    private double t;
    private double R;
    private int n;
    private int m;
    private double[] X;
    private double[] Y;
    private double[] answer;
    private String MathExpression;
    private String result;

    //---------- Конструктор
    SPLAYN(double a, double b, double c, double d, int n, int m, String MathExpression) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.h = (b - a) / n;
        this.t = (d - c) / m;
        this.n = n;
        this.m = m;
        this.MathExpression = MathExpression;
    }

    //---------- запуск программы
    public void run(){
        SetSizeX();
        SetSizeY();
        SetSizeR();
        SetSizeZ();
        SetX();
        SetY();
        SetF();
        calculate_N();
        calculate_M();
        calculate_K();
        calculate_z();
        result = "";
        result += "\t\t\t\tz:\n";
        for (int i = 0; i < z.length; i++) {
            for (int j = 0; j < z[i].length; j++) {
                result += z[i][j] + "\t";
            }
            result += "\n\n";
        }
        result += "\t\t\t\tf:\n";
            calculate_r();
        result += "\t\t\t\tr:\n";
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                result += r[i][j] + "\t";
            }
            result += "\n\n";
        }
        calculate_R();
    }

    // --------- указываем размер массива, который хранит значение i-го узла
    private void SetSizeX(){
        X = new double[n + 1];
    }

    private void SetX(){
        for (int i = 0; i < X.length; i++) {
            X[i] = a + i * h;
        }
    }

    // --------- указываем размер массива, который хранит значение j-го узла
    private void SetSizeY(){
        Y = new double[m + 1];
    }

    private void SetY(){
        for (int j = 0; j < Y.length; j++) {
            Y[j] = c + j * t;
        }
    }

    //----------
    private void SetSizeR(){
        r = new double[n][m];
    }

    private void SetSizeZ(){
        z = new double[n][m];
    }

    //---------- Находим значения двумерного массива N
    private void calculate_N(){
        N = new double[n + 1][m + 1];
        for (int i = 0; i < N.length; i++) {
            N[i] = calculate_SLAU(f[i], t, m);
        }
    }

    //---------- Находим значения двумерного массива M
    private void calculate_M(){
        M = new double[n + 1][m + 1];

        for (int j = 0; j < M[0].length; j++) {
            double[] Temp;
            double[] func = new double[n + 1];
            for (int i = 0; i < func.length; i++) {
                func[i] = f[i][j];
            }
            Temp = calculate_SLAU(func, h, n);
            for (int i = 0; i < Temp.length; i++) {
                M[i][j] = Temp[i];
            }
        }
    }

    //---------- Находим значения двумерного массива K
    private void calculate_K(){
        K = new double[n + 1][m + 1];
        for (int i = 0; i < K.length; i++) {
            K[i] = calculate_SLAU(M[i], t, m);
        }
    }

    //---------- Решение СЛАУ
    private double[] calculate_SLAU(double[] f, double h, int n){
        double[][] C = new double[n - 1][n - 1];
        double[] D = new double[n - 1];
        double[] M = new double[n + 1];
        for (int i = 0; i < n - 1; i++) {
            if (i != 0) C[i][i - 1] = h / 6;
            if ((i + 2) != n) C[i][i + 1] = h / 6;
            C[i][i] = 2 * h / 3;
            D[i] = (f[i  + 2] - 2 * f[i + 1] + f[i]) / h;
        }
        Lu lu = new Lu(C, D);
        lu.run();
        double[] X =lu.getXX();
        for (int i = 0; i < M.length; i++) {
            if (i == 0 || i == M.length - 1)
                M[i] = 0;
            else M[i] = X[i - 1];
        }
        return M;
    }

    //---------- Заполняем массив, который хранит значение функций в узле (x_i ; y_j)
    private void SetF() {
        f = new double[n + 1][m + 1];
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < Y.length; j++) {
                f[i][j] = f(X[i], Y[j]);
            }
        }
    }

    //---------- Вычисляем значение функций в узле (x ; y)
    private double f(double x, double y){
        MathParser parser = new MathParser();
        try{
            String s = "";
            for (int i = 0; i < MathExpression.length(); i++) {
                s += (MathExpression.substring(i, i + 1).equals("x")) ? x : (MathExpression.substring(i, i + 1).equals("y")) ? y : MathExpression.substring(i, i + 1);
            }
            return parser.Parse(s);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    //---------- Вывод на экран одномерного массива
    private void show(@NotNull double[] T){
        for (int i = 0; i < T.length; i++) {
            if (i == T.length - 1)
                System.out.println(T[i]);
            else
                System.out.print(T[i] + "\t");
        }
        System.out.println("-------------------------------------------");
    }

    //---------- Вывод на экран двумерного массива
    private void show(@NotNull double[][] T){
        for (int i = 0; i < T.length; i++) {
            for (int j = 0; j < T[i].length; j++) {
                System.out.print(T[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------");
    }

    //----------

    private double g1(int i, int j, double y){
        return N[i][j - 1] * Math.pow(Y[j] - y, 3.0) / (6 * t) + N[i][j] * Math.pow(y - Y[j - 1],3.) / (6 * t)+
                (f[i][j - 1] - N[i][j - 1] * t * t / 6) * (Y[j] - y) / t + (f[i][j] - N[i][j] * t * t / 6) * (y - Y[j - 1]) / t;
    }

    private double g2(int i, int j, double y){
        return K[i][j - 1] * Math.pow(Y[j] - y, 3.) / (6 * t) + K[i][j] * Math.pow(y - Y[j - 1], 3.) / (6 * t) +
                (M[i][j - 1] - K[i][j - 1] * t * t / 6) * (Y[j] - y) / t + (M[i][j] - K[i][j] * t * t / 6) * (y - Y[j - 1]) / t;
    }

    private double g(int i, int j, double x, double y){
        return g2(i - 1, j, y) * Math.pow(X[i] - x, 3.) / (6 * h) + g2(i, j, y) * Math.pow(x - X[i - 1], 3.) / (6 * h) +
                (g1(i - 1, j, y) - g2(i - 1, j, y) * h * h / 6) * (X[i] - x) / h + (g1(i, j, y) - g2(i, j, y) * h * h / 6) * (x - X[i - 1]) / h;
    }

    private void calculate_z(){
        for (int i = 0; i < z.length; i++) {
            for (int j = 0; j < z[i].length; j++) {
                double x = (X[i + 1] + X[i]) / 2;
                double y = (Y[j + 1] + Y[j]) / 2;
                z[i][j] = g(i + 1, j + 1, x, y);
            }
        }
    }

    private void calculate_r(){
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                double x = (X[i + 1] + X[i]) / 2;
                double y = (Y[j + 1] + Y[j]) / 2;
                r[i][j] = Math.abs(f(x, y) - z[i][j]);
               result += f(x, y) + "\t";
            }
            result += "\n\n";
        }
    }

    private void res(){
        System.out.println("X\tY\tr");
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                System.out.println(X[i + 1] + "\t" + Y[j + 1] + "\t" + r[i][j]);
            }
        }
    }
    
    private void calculate_R(){
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                R += r[i][j] * r[i][j];
            }
        }
        R *= (b - a) * (d - c) / (n * m);
        R = Math.sqrt(R);
    }
    
    public String getResult(){
        return result;
    }
    
    public double getR(){
        return R;
    }
}