#include <iostream>
#include "string"
#include "vector"
using namespace std;

template<typename T>
class math_array {
public:
    explicit math_array(size_t size);
    ~math_array();
    size_t size(){
        return m_size;
    }
    T & operator[](int ind);
private:
    size_t m_size;
    T* m_data = nullptr;

};

template<typename T>
math_array<T>::math_array(size_t size) {
    m_data = new T[size];
    m_size = size;
}

template<typename T>
math_array<T>::~math_array() {
    delete m_data;
}

template<typename T>
T &math_array<T>::operator[](int ind) {
    return m_data[ind - 1];
}

class Spline{
public:
    Spline(double a, double b, double c, double d, double  x_i, pair<double,double> left_bound, pair<double,double> right_bound);
    ~Spline() = default;
    double apply(double x);
    bool isContains(double x);
private:
    double m_a,
           m_b,
           m_c,
           m_d,
           m_x_i;
    pair<double,double> m_left_bound, m_right_bound;
};

Spline::Spline(double a, double b, double c, double d, double x_i, pair<double,double> left_bound, pair<double,double> right_bound) {
    m_a = a;
    m_b = b;
    m_c = c;
    m_d = d;
    m_x_i = x_i;
    m_left_bound = left_bound;
    m_right_bound = right_bound;
}

bool Spline::isContains(double x) {
    if (m_left_bound.first <= x && x <= m_right_bound.first)
    {
        return true;
    }
    return false;
}

double Spline::apply(double x) {
    if(x == m_left_bound.first){
        return m_left_bound.second;
    }
    if (x == m_right_bound.first){
        return m_right_bound.second;
    }
    double t = (x - m_x_i);
    return m_a + m_b*t + (m_c * t*t) + (m_d * t*t*t);
}

double* get_a_array(vector<double> & y_axis, int n) {
    double *a_arr = new double[n+1];
    for (int i = 0; i < n + 1; ++i) {
        a_arr[i] = y_axis[i];
    }
    return a_arr;
}

double* get_h_array(vector<double> & x_axis, int n){
    double *h_arr = new double [n];
    for (int i = 0; i < n; ++i) {
        h_arr[i] = x_axis[i+1] - x_axis[i];
    }
    return h_arr;
}

void fill_alpha_array(math_array<double> & alpha_arr, const double *a_arr, const double *h_arr, int n) {
    for (int i = 1; i <= n - 1; ++i) {
        alpha_arr[i] = (3 / h_arr[i]) * (a_arr[i+1] - a_arr[i]) - (3 / h_arr[i - 1]) * (a_arr[i] - a_arr[i - 1]);
    }
}


vector<Spline> getSplines(vector<double> & x_axis, vector<double> & y_axis){
    vector<Spline> out;
    int n = min(x_axis.size(), y_axis.size()) - 1;
    if (n < 2){
        return out;
    }
    double *a_arr = get_a_array(y_axis, n);
    double b_arr[n];
    double d_arr[n];
    double *h_arr = get_h_array(x_axis, n);
    math_array<double> alpha_arr(n);
    fill_alpha_array(alpha_arr,a_arr,h_arr,n);
    double l_arr[n+1];
    double m_arr[n+1];
    double z_arr[n+1];
    double c_arr[n+1];
    l_arr[0] = 1;
    m_arr[0] = 0;
    z_arr[0] = 0;
    for (int i = 1; i <= n - 1; ++i) {
        l_arr[i] = 2 *(x_axis[i+1] - x_axis[i-1]) - (h_arr[i-1] * m_arr[i-1]);
        m_arr[i] = h_arr[i] / l_arr[i];
        z_arr[i] = (alpha_arr[i] - h_arr[i-1] * z_arr[i-1]) / l_arr[i];
    }
    l_arr[n] = 1;
    z_arr[n] = 0;
    c_arr[n] = 0;
    for (int j = n-1; j >= 0 ; --j) {
        c_arr[j] = z_arr[j] - m_arr[j] * c_arr[j+1];
        b_arr[j] = ((a_arr[j+1] - a_arr[j]) / h_arr[j]) - (h_arr[j] * (c_arr[j+1] + 2 * c_arr[j])) / 3;
        d_arr[j] = (c_arr[j+1] - c_arr[j]) / (3 * h_arr[j]);
    }

    for (int i = 0; i <= n-1; ++i) {
        out.push_back(Spline(a_arr[i], b_arr[i], c_arr[i], d_arr[i],
                             x_axis[i],
                             {x_axis[i],y_axis[i]},{x_axis[i + 1],y_axis[i+1]}));
    }
    delete h_arr;
    delete a_arr;
    return out;
}

/*
 * Complete the 'interpolate_by_spline' function below.
 *
 * The function is expected to return a DOUBLE.
 * The function accepts following parameters:
 *  1. DOUBLE_ARRAY x_axis
 *  2. DOUBLE_ARRAY y_axis
 *  3. DOUBLE x
 */
double interpolate_by_spline(vector<double> x_axis, vector<double> y_axis, double x) {
    vector<Spline> splines = getSplines(x_axis, y_axis);
    for (auto spl : splines) {
        if(spl.isContains(x)){
            return spl.apply(x);
        }
    }
    return 0;
}
#include "matplot/matplot.h"
#include "cmath"
double cube_func(double x){
    return pow(x,3) + 2*x*x -1;
}

double func2(double x){
    return pow(x, cos(x));
}

void printOptions(){
    cout << "Choose function\n";
    cout << "\t1) x^3 + 2x^2 - 1\n";
    cout << "\t2) x^cos(x)\n";
}

int main(int argc, char* argv[]) {
    printOptions();
    ifstream file1;
    ios::sync_with_stdio(0);
//    cin.tie(0);
//    cout.tie(0);

    char * label;
    function<double(double)> my_f;
    int inp;
    cin >> inp;
    cout << inp;
//    if(argc >= 2){
//        inp = *argv[1] - '0';
//        cout << inp;
//    }
    switch (inp) {
        case 1:
            file1.open("../1");
            my_f = cube_func;
            label = "x^3 + 2x^2 - 1";
            break;
        case 2:
            file1.open("../2");
            my_f = func2;
            label = "x^{cos(x)}";
            break;
        default:
            cout << "no such\n";
            return 0;
    }

    double epsilon = 0.1;
    int n;
    double x;
    vector<double> x_axis;
    vector<double> y_axis;
    vector<double> _x;
    vector<double> _y;
    vector<double> _s_y;
    vector<Spline> splines;
    file1 >> n;
    for (int i = 0; i < n; ++i) {
        file1 >> x;
        x_axis.push_back(x);
    }
    for (int i = 0; i < n; ++i) {
        file1 >> x;
        y_axis.push_back(x);
    }
    splines = getSplines(x_axis,y_axis);
    double left = x_axis[0];
    double right = x_axis.back();
    for (double i = left; i <= right ; i += epsilon) {
        _x.push_back(i);
        _y.push_back(my_f(i));
        for (auto spl : splines) {
            if(spl.isContains(i)){
                _s_y.push_back(spl.apply(i));
            }
        }
    }

    matplot::hold(true);
    matplot::grid(true);
    matplot::plot(_x,_y)->line_width(1).display_name(label);
    matplot::plot(_x,_s_y)->display_name("interpolation");
    matplot::legend();

    matplot::scatter(x_axis,y_axis)->marker_face_color({0.f, 0.447f, 0.741f}).line_width(2);
    matplot::show();
}
