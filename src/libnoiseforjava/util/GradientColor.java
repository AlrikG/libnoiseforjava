/*******************************************************************************
 *  Copyright (c) 2003, 2004 Jason Bevins (original libnoise code)
 *  Copyright (c) 2010 Thomas J. Hodge (java port of libnoise)
 *  Copyright (c) Nick Whitney ( changed noisegen to perlin basis. added javadoc)
 *  
 *  This file is part of libnoiseforjava.
 *  
 *  libnoiseforjava is a Java port of the C++ library libnoise, which may be
 *  found at http://libnoise.sourceforge.net/. libnoise was developed by Jason
 *  Bevins, who may be contacted at jlbezigvins@gmzigail.com (for great email,
 *  take off every 'zig'). Porting to Java was done by Thomas Hodge, who may be
 *  contacted at libnoisezagforjava@gzagmail.com (remove every 'zag').
 *  
 *  libnoiseforjava is free software: you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the Free
 *  Software Foundation, either version 3 of the License, or (at your option) any
 *  later version.
 *  
 *  libnoiseforjava is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 *  details.
 *  
 *  You should have received a copy of the GNU General Public License along with
 *  libnoiseforjava. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

package libnoiseforjava.util;

import libnoiseforjava.Misc;

/**
 * Defines a color gradient.
 * <p>
 * A color gradient is a list of gradually-changing colors. A color gradient is
 * defined by a list of <i>gradient points</i>. Each gradient point has a
 * position and a color. In a color gradient, the colors between two adjacent
 * gradient points are linearly interpolated.
 * <p>
 * To add a gradient point to the color gradient, pass its position and color to
 * the addGradientPoint() method.
 * <p>
 * To retrieve a color from a specific position in the color gradient, pass that
 * position to the getColor() method.
 * <p>
 * This class is a useful tool for coloring height maps based on elevation.
 * <p>
 * <b>Gradient example</b>
 * <p>
 * Suppose a gradient object contains the following gradient points: - -1.0 maps
 * to black. - 0.0 maps to white. - 1.0 maps to red.
 * <p>
 * If an application passes -0.5 to the getColor() method, this method will
 * return a gray color that is halfway between black and white.
 * <p>
 * If an application passes 0.25 to the getColor() method, this method will
 * return a very light pink color that is one quarter of the way between white
 * and red.
 */
public class GradientColor {

    GradientPoint[] gradientPoints;
    int gradientPointCount;
    ColorCafe workingColor;

    public GradientColor() {
        this.gradientPoints = new GradientPoint[1];
        this.gradientPoints[0] = new GradientPoint(0.0, new ColorCafe(0, 0, 0, 0));
    }

    /**
     * Adds a gradient point to this gradient object.
     *
     * @param gradientPos The position of this gradient point.
     * @param gradientColor The color of this gradient point.
     *
     * @pre No two gradient points have the same position.
     *
     * @throws IllegalArgumentException See the precondition.
     */
    public void addGradientPoint(double gradientPos, ColorCafe gradientColor) throws IllegalArgumentException {
        // Find the insertion point for the new gradient point and insert the
        // new
        // gradient point at that insertion point. The gradient point array will
        // remain sorted by gradient position.
        int insertionPos = findInsertionPos(gradientPos);
        insertAtPos(insertionPos, gradientPos, gradientColor);
    }

    /**
     * Deletes all the gradient points from this gradient object.
     *
     * @post All gradient points from this gradient object are deleted.
     */
    public void clear() {
        this.gradientPoints = null;
        this.gradientPointCount = 0;
    }

    /**
     * Determines the array index in which to insert the gradient point into the
     * internal gradient-point array.
     * <p>
     * By inserting the gradient point at the returned array index, this object
     * ensures that the gradient-point array is sorted by input value. The code
     * that maps a value to a color requires a sorted gradient-point array.
     *
     * @param gradientPos The position of this gradient point.
     *
     * @return The array index in which to insert the gradient point.
     *
     * @pre No two gradient points have the same input value.
     *
     * @throws IllegalArgumentException See the precondition.
     */
    public int findInsertionPos(double gradientPos) throws IllegalArgumentException {
        int insertionPos;
        for (insertionPos = 0; insertionPos < this.gradientPointCount; insertionPos++) {
            if (gradientPos < this.gradientPoints[insertionPos].position) {
                // We found the array index in which to insert the new gradient
                // point.
                // Exit now.
                break;
            } else if (gradientPos == this.gradientPoints[insertionPos].position) {
                // Each gradient point is required to contain a unique gradient
                // position, so throw an exception.
                throw new IllegalArgumentException("Invalid Parameter in Gradient Color");
            }
        }
        return insertionPos;
    }

    /**
     * Returns the color at the specified position in the color gradient.
     *
     * @param gradientPos The specified position.
     *
     * @return The color at that position.
     */
    public ColorCafe getColor(double gradientPos) {
        assert (this.gradientPointCount >= 2);

        /*
         * Find the first element in the gradient point array that has a
         * gradient position larger than the gradient position passed to this
         * method.
         */
        int indexPos;
        for (indexPos = 0; indexPos < this.gradientPointCount; indexPos++) {
            if (gradientPos < this.gradientPoints[indexPos].position) {
                break;
            }
        }

        // Find the two nearest gradient points so that we can perform linear
        // interpolation on the color.
        int index0 = Misc.ClampValue(indexPos - 1, 0, this.gradientPointCount - 1);
        int index1 = Misc.ClampValue(indexPos, 0, this.gradientPointCount - 1);

        /*
         * If some gradient points are missing (which occurs if the gradient
         * position passed to this method is greater than the largest gradient
         * position or less than the smallest gradient position in the array),
         * get the corresponding gradient color of the nearest gradient point
         * and exit now.
         */
        if (index0 == index1) {
            this.workingColor = this.gradientPoints[index1].color;
            return this.workingColor;
        }

        // Compute the alpha value used for linear interpolation.
        double input0 = this.gradientPoints[index0].position;
        double input1 = this.gradientPoints[index1].position;
        double alpha = (gradientPos - input0) / (input1 - input0);

        // Now perform the linear interpolation given the alpha value.
        ColorCafe color0 = this.gradientPoints[index0].color;
        ColorCafe color1 = this.gradientPoints[index1].color;
        this.workingColor = MiscUtilities.linearInterpColor(color0, color1, (float) alpha);
        return this.workingColor;
    }

    /**
     * Inserts the gradient point at the specified position in the internal
     * gradient-point array.
     * <p>
     * To make room for this new gradient point, this method reallocates the
     * gradient-point array and shifts all gradient points occurring after the
     * insertion position up by one.
     * <p>
     * Because this object requires that all gradient points in the array must
     * be sorted by the position, the new gradient point should be inserted at
     * the position in which the order is still preserved.
     *
     * @param insertionPos The zero-based array position in which to insert the
     *            gradient point.
     * @param gradientPos The position of this gradient point.
     * @param gradientColor The color of this gradient point.
     *
     */
    public void insertAtPos(int insertionPos, double gradientPos, ColorCafe gradientColor) {
        // Make room for the new gradient point at the specified insertion
        // position
        // within the gradient point array. The insertion position is determined
        // by
        // the gradient point's position; the gradient points must be sorted by
        // gradient position within that array.
        GradientPoint[] newGradientPoints;
        newGradientPoints = new GradientPoint[this.gradientPointCount + 1];

        for (int t = 0; t < (this.gradientPointCount + 1); t++) {
            newGradientPoints[t] = new GradientPoint();
        }

        for (int i = 0; i < this.gradientPointCount; i++) {
            if (i < insertionPos) {
                newGradientPoints[i] = this.gradientPoints[i];
            } else {
                newGradientPoints[i + 1] = this.gradientPoints[i];
            }
        }

        this.gradientPoints = newGradientPoints;
        ++this.gradientPointCount;

        // Now that we've made room for the new gradient point within the array,
        // add
        // the new gradient point.
        this.gradientPoints[insertionPos].position = gradientPos;
        this.gradientPoints[insertionPos].color = gradientColor;
    }

    /**
     * Returns a pointer to the array of gradient points in this object.
     * <p>
     * Before calling this method, call getGradientPointCount() to determine the
     * number of gradient points in this array.
     * <p>
     * It is recommended that an application does not store this pointer for
     * later use since the pointer to the array may change if the application
     * calls another method of this object.
     * 
     * @return A pointer to the array of gradient points.
     */
    public GradientPoint[] getGradientPointArray() {
        return this.gradientPoints;
    }

    /**
     * Returns the number of gradient points stored in this object.
     *
     * @return The number of gradient points stored in this object.
     */
    public int getGradientPointCount() {
        return this.gradientPointCount;
    }

}
