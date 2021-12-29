package dev.felnull.fnjl.math;

import java.util.Objects;

/**
 * 複素数
 *
 * @author MORIMORI0317
 * @since 1.19
 */
public class FNComplex {
    private double real;
    private double imag;

    public FNComplex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public double getImag() {
        return imag;
    }

    public double getReal() {
        return real;
    }

    public void setImag(double imag) {
        this.imag = imag;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public FNComplex add(FNComplex complex) {
        this.real += complex.real;
        this.imag += complex.imag;
        return this;
    }

    public FNComplex sub(FNComplex complex) {
        this.real -= complex.real;
        this.imag -= complex.imag;
        return this;
    }

    public FNComplex mul(FNComplex complex) {
        double r = this.real * complex.real - this.imag * complex.imag;
        double i = this.real * complex.imag + this.imag * complex.real;
        this.real = r;
        this.imag = i;
        return this;
    }

    public FNComplex div(FNComplex complex) {
        double den = complex.real * complex.real + complex.imag * complex.imag;
        double r = (this.real * complex.real + this.imag * complex.imag) / den;
        double i = (this.imag * complex.real - this.real * complex.imag) / den;
        this.real = r;
        this.imag = i;
        return this;
    }

    public FNComplex con() {
        this.imag *= -1;
        return this;
    }

    public double abs() {
        return Math.sqrt(this.real * this.real + this.imag * this.imag);
    }

    public double arg() {
        return Math.atan(this.imag / this.real);
    }

    public int mandelbrot(int max) {
        FNComplex z = new FNComplex(0, 0);
        for (int i = 0; i < max; i++) {
            if (z.abs() > 2.0) return i;
            z.mul(z).add(this);
        }
        return max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FNComplex fnComplex = (FNComplex) o;
        return Double.compare(fnComplex.real, real) == 0 && Double.compare(fnComplex.imag, imag) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, imag);
    }

    @Override
    public String toString() {
        return "FNComplex{" +
                "real=" + real +
                ", imag=" + imag +
                '}';
    }

    public FNVec2d convertVec2d() {
        return new FNVec2d(real, imag);
    }
}
