//
// Created by asgrim on 10.04.23.
//

#include "methods.h"
#include <string>
#include "iostream"
//#include ""
double simpsonMethod(double left, double right, int n, Integral & integral, double epsilon)
{
    int sign = 1;
    if (left > right){
        sign = -1;
        double t = right;
        right = left;
        left = t;
    }
    std::vector<BreakPoint> badPoints;
    for (BreakPoint point : integral.getBreakPoints())
    {
        if(point.x >= left && point.x <= right)
        {
            if(!point.solveable)
            {
                throw std::string("Can not solve integral. Gap of second kind");
            }
            badPoints.push_back(point);
        }

    }
    const double w = (right - left) / n;
    double result = 0;
    for (int i = 0; i < n; ++i)
    {
        double x1 = left + i * w;
        double x2 = left + (i + 1) * w;
        double t1, t2, t3;
        t1 = integral.apply(x1);
        t2 = integral.apply(0.5 * (x1 + x2));
        t3 = integral.apply(x2);
        for (auto p : badPoints)
        {
            if(p.x == x1)
            {
                t1 = integral.apply(x1 - epsilon) + integral.apply(x1 + epsilon);
            }
            if (p.x == x2)
            {
                t2 = integral.apply(x2 - epsilon) + integral.apply(x2 + epsilon);
            }
            if(p.x == (0.5 * (x1 + x2)))
            {
                t3 = integral.apply(0.5 * (x1 + x2) - epsilon) + integral.apply(0.5 * (x1 + x2) + epsilon);
            }
        }
        result += (x2 - x1) /6.0 * (t1 + 4.0 * t2 + t3);
    }
    return result*sign;
}

