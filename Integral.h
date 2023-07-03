//
// Created by asgrim on 10.04.23.
//

#ifndef COMP_MATH3_INTEGRAL_H
#define COMP_MATH3_INTEGRAL_H
#include <functional>
#include <string>
struct BreakPoint{
    double x;
    bool solveable;
};

class Integral {
public:
    Integral(std::function<double(double)> f, std::string fString);
    ~Integral() = default;
    double apply(double x);
    void addBreakPoint(const BreakPoint & breakPoint);
    std::string getFString();
    std::vector<BreakPoint> & getBreakPoints();
private:
    std::vector<BreakPoint> m_breakPoints;
    std::function<double(double)> m_f;
    std::string m_fString;
};


#endif //COMP_MATH3_INTEGRAL_H
