/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    ApproximateSigmoid.java
 *    Copyright (C) 2015 University of Waikato, Hamilton, New Zealand
 */

package weka.classifiers.functions.activation;

/**
 * <!-- globalinfo-start -->
 * Computes approximate (fast) version of sigmoid activation function f(x) = 1 / (1 + e^(-x))
 * <br><br>
 * <!-- globalinfo-end -->
 *
 * @author Eibe Frank (eibe@cs.waikato.ac.nz)
 * @version $Revision: 10949 $
 */
public class ApproximateSigmoid implements ActivationFunction {

  /**
   * Returns info for this class.
   */
  public String globalInfo() {

    return "Computes approximate (fast) version of sigmoid activation function f(x) = 1 / (1 + e^(-x))";
  }

  /**
   * Computes approximate sigmoid function. Derivative is stored in d at position index if argument d != null.
   */
  public double activation(double x, double[] d, int index) {

    // Compute approximate sigmoid
    double y = 1.0 + (-x) / 4096.0;
    x = y * y;
    x *= x;
    x *= x;
    x *= x;
    x *= x;
    x *= x;
    x *= x;
    x *= x;
    x *= x;
    x *= x;
    x *= x;
    x *= x;
    double output = 1.0 / (1.0 + x);

    // Compute derivative if desired
    if (d != null) {
      d[index] = output * (1.0 - output) / y;
    }

    return output;
  }
}

