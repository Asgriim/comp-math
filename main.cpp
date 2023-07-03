#include <iostream>
#include <cmath>
#include "matplot/matplot.h"
using namespace std;

string ltrim(const string &);
string rtrim(const string &);

using fn_t = double (double, double);

#include <vector>
#include <fstream>

double first_function (double x, double y)
{
    return sin(x);
}

double second_function (double x, double y)
{
    return (x * y)/2;
}

double third_function (double x, double y)
{
    return y - (2 * x)/y;
}


double fourth_function (double x, double y)
{
    return x + y;
}

double default_function(double x, double y)
{
    return 0.0;
}


/*
* How to use this function:
*    fn_t& f = get_function(4);
*    f(0.0001);
*/
fn_t& get_function (int n)
{
    switch (n)
    {
        case 1:  return first_function;
        case 2:  return second_function;
        case 3:  return third_function;
        case 4:  return fourth_function;
        default: return default_function;
    }
}

inline double get_d_y(double k1, double k2, double k3, double k4){
    return  (k1 + 2*k2 + 2*k3 + k4) / 6;
}


vector<pair<double,double>> get_additional_points(fn_t & fn, double h, double x_a,double y_a, int count){
    vector<pair<double,double>> out;
    double k1,k2,k3,k4;
    out.push_back({x_a,y_a});
    double y_i = y_a;
    double x_i = x_a;

    for (int i = 0; i < count; ++i) {
        k1 = h * fn(x_i,y_i);
        k2 = h * fn(x_i + h/2, y_i + k1/2);
        k3 = h * fn(x_i + h/2, y_i + k2/2);
        k4 = h * fn(x_i + h/2, y_i + k3);
        double d_y = get_d_y(k1,k2,k3,k4);
        y_i = y_i + d_y;
        x_i = x_i + h;
        out.push_back({x_i,y_i});
    }

    return out;

}

/*
 * Complete the 'solveByAdams' function below.
 *
 * The function is expected to return a DOUBLE.
 * The function accepts following parameters:
 *  1. INTEGER f
 *  2. DOUBLE epsilon
 *  3. DOUBLE a
 *  4. DOUBLE y_a
 *  5. DOUBLE b приме
 */
vector<pair<double,double>> solveByAdams(int f, double epsilon, double a, double y_a, double b) {
    fn_t &fn = get_function(f);
    int steps = 1000;
    double h = (b - a) / steps;
    vector<pair<double, double>> points = get_additional_points(fn, h, a, y_a, 3);
    double y_temp, x_temp;
    for (int i = 3; i < steps; ++i) {
        y_temp = points[i].second + (h / 24) * (55 * fn(points[i].first, points[i].second)
                                                - 59 * fn(points[i - 1].first, points[i - 1].second)
                                                + 37 * fn(points[i - 2].first, points[i - 2].second)
                                                 - 9 * fn(points[i - 3].first, points[i - 3].second));
        x_temp = points[i].first + h;
        points.push_back({x_temp,y_temp});
    }
    return points;
}

inline double first_func(double x){
    return -cos(x) + 2;
}

inline double second_func(double x){
    return exp((x * x) / 4);
}

void printOptions(){
    cout << "Choose equation\n";
    cout << "\t1) y' = sin(x)\n";
    cout << "\t2) y' = (x * y) / 2\n";
}

int main() {
    printOptions();
    ios::sync_with_stdio(0);
    ifstream f1;
    function<double(double)> orig;
    int f;
    double eps, a, y_a, b;
    int inp;
    cin >> inp;
    switch (inp) {
        case 1:
            f1.open("../1");
            orig = first_func;
            break;
        case 2:
            f1.open("../2");
            orig = second_func;
            break;
    }
    f1 >> f;
    f1 >> eps >> a >> y_a >> b;
    vector<pair<double,double>> ans = solveByAdams(f,eps,a,y_a,b);

    vector<double> x_ax, y_ax, adams_x, adams_y;

    for (double i = a; i <= b;  i+=eps) {
        x_ax.push_back(i);
        y_ax.push_back(orig(i));
    }

    for (auto p : ans) {
        adams_x.push_back(p.first);
        adams_y.push_back(p.second);
    }

    matplot::hold(true);
    matplot::grid(true);
    matplot::plot(x_ax, y_ax)->line_width(0.8).display_name("analytical solution");
    matplot::plot(adams_x,adams_y)->line_width(1).display_name("adams");
    matplot::legend();
    matplot::show();
    return 0;
}

