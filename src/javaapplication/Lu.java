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
public class Lu {
    private double[][] A_;
    private double[][] A;
    private double[][] L;
    private double[][] U;
    private double[] B;
    private double[] XX;
    private int n;

    Lu(double[][] A, double[] B) {
        this.A = A;
        this.B = B;
        this.n = B.length;
        A_ = new double[n][n];
        L = new double[n][n];
        U = new double[n][n];
    }

    void run(){
//        System.out.println("СЛАУ : ");
//        show(A, B);
//        System.out.println();
        lu();
//        System.out.println("Матрица L : ");
//        show(L);
//        System.out.println("Матрица U : ");
//        show(U);
//        System.out.println();
        obrat();
//        System.out.println("Обратная матрица к матрице А : ");
//        show(A_);
//        System.out.println("Решением СЛАУ будет : ");
        answer();
//        show(answer());
    }

    private void show(double[][] A, double[] B) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j <= A.length; j++) {
                if (j == A.length)
                    System.out.println(" | " + B[i]);
                else System.out.print(A[i][j] + " ");
            }
        }
    }

    private void lu() {
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                for (int j = 0; j < n; j++) {
                    U[i][j] = A[i][j];
                    L[j][i] = A[j][i] / U[i][i];
                    L[j][j] = 1;
                }
            } else {
                for (int j = i; j < n; j++) {
                    double sum = 0;
                    for (int k = 0; k < i; k++)
                        sum += L[i][k] * U[k][j];
                    U[i][j] = A[i][j] - sum;
                }
                for (int j = i + 1; j < n; j++) {
                    double sum = 0;
                    for (int k = 0; k < i; k++)
                        sum += L[j][k] * U[k][i];
                    L[j][i] = (A[j][i] - sum) / U[i][i];
                }
            }
        }
    }

    private void show(double[][] X) {
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < X.length; j++) {
                System.out.print(X[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void show(double[] X) {
        for (int i = 0; i < X.length; i++) {
            System.out.print(X[i] + " ");
        }
        System.out.println();
    }

    private void obrat() {
        double[][] B = new double[n][n];
        double[][] E = new double[n][n];
        for (int i = 0; i < n; i++) {
            E[i][i] = 1;
        }
        for (int i = 0; i < n; i++) {
            if (i == 0)
                for (int j = 0; j < n; j++)
                    B[i][j] = E[i][j];
            else
                for (int j = 0; j < n; j++) {
                    double sum = 0;
                    for (int k = 0; k < i; k++)
                        sum += L[i][k] * B[k][j];
                    B[i][j] = E[i][j] - sum;
                }
        }
        // Теперь находим обратную матрицу
        for (int i = n - 1; i >= 0; i--) {
            if (i == n - 1)
                for (int j = 0; j < n; j++)
                    A_[i][j] = B[i][j] / U[i][i];
            else
                for (int j = 0; j < n; j++) {
                    double sum = 0;
                    for (int k = n - 1; k > i; k--)
                        sum += U[i][k] * A_[k][j];
                    A_[i][j] = (B[i][j] - sum) / U[i][i];
                }
        }
    }

    private double[] answer(){
        double[] X = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                X[i] += A_[i][j] * B[j];
            }
        }
        XX = X;
        return X;
    }

    public double[] getXX() {
        return XX;
    }
}