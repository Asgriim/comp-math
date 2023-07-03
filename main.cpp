#include <iostream>
#include <functional>
#include "funcs.h"
#include "Integral.h"
#include "methods.h"

void println(const std::string & s)
{
    std::cout << s << std::endl;
}

void println(const char* p)
{
    std::cout << p << std::endl;
}

void print_integrals(std::vector<Integral> & v)
{
    for (int i = 0; i < v.size(); ++i)
    {
        std::cout << i + 1;
        println( ") " + v[i].getFString());
    }
}

int main()
{
    const  std::string ex = "_q";
    std::string input;
    std::vector<Integral> v;
    Integral int_1(sinFunc,"3sin(x)/x -1");
    int_1.addBreakPoint(BreakPoint{0.0, true});
    Integral int_2(gipebol,"1/4x");
    int_2.addBreakPoint(BreakPoint{0.0, false});
    Integral int_3(cube,"x^3/10");
    v.push_back(int_1);
    v.push_back(int_2);
    v.push_back(int_3);
    double left, right, epsilon;
    int n, op = 0;
    while (input != ex)
    {

        println("Choose integral or enter _q to exit");
        print_integrals(v);
        std::cin >> input;
        if(input == ex)
        {
            println("Bye UwU");
            return 0;
        }
        try
        {
            op = std::stoi(input);
            if(op < 1 || op > 3)
            {
                continue;
            }
            println("Enter a, b and epsilon like this\n 1 2 0.001");
            std::cin >> left;
            std::cin >> right;
            std::cin >> epsilon;
            if (epsilon > 0.1 || epsilon <= 0)
            {
                println("Wrong epsilon");
                continue;
            }
            double result, last_result;
            last_result = 123123123;
            result = 0;
            n = 2;
            while(std::abs(result - last_result) > epsilon)
            {
                last_result = result;
                result = simpsonMethod(left,right,n,v[op - 1],epsilon);
                n *= 2;
            }
            std::cout << "Result : " << result << std::endl;



        }
        catch (std::invalid_argument & e)
        {
            continue;
        }
        catch (std::string & e)
        {
            println(e);
        }

    }
    return 0;
}
