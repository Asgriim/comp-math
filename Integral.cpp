//
// Created by asgrim on 10.04.23.
//

#include "Integral.h"


Integral::Integral(std::function<double(double)> f, std::string fString)
{
    m_f = f;
    m_fString = fString;
}


double Integral::apply(double x)
{
    return m_f(x);
}


void Integral::addBreakPoint(const BreakPoint &breakPoint)
{
    m_breakPoints.push_back(breakPoint);
}

std::vector<BreakPoint> & Integral::getBreakPoints()
{
    return m_breakPoints;
}

std::string Integral::getFString()
{
    return m_fString;
}
