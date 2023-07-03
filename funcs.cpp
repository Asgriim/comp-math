//
// Created by asgrim on 10.04.23.
//

#include "funcs.h"
#include <cmath>

double sinFunc(double x)
{
    return (sin(x) / x) * 3 - 1;
}

double gipebol(double x)
{
    return 1 / (4 * x);
}

double cube(double x)
{
    return (x * x * x) / 10;
}
