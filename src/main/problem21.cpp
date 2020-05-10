// Example program
#include <iostream>
#include <string>
#include <list>
#include <sstream>
#include <math.h>

std::list<double> parse(std::string s){
  std::istringstream iss (s);
  std::list<double> accounts;
  for(std::string s; iss >> s; )
    accounts.push_back(std::stof(s));
  return accounts;
}

double percent(std::list<double> accounts, double percent) {
    double perc = (100 - percent);
    accounts.sort();
    while(accounts.size() > 1) {
        double front = accounts.front();
        accounts.pop_front();
        double sum = round((front + accounts.front()) * perc)/100;
        accounts.pop_front();
        bool inserted = false;
        for (std::list<double>::iterator it = accounts.begin(); it != accounts.end(); it++) {
            if (*it > sum) {
                accounts.insert(it, sum); // adds element before the front
                inserted = true;
                break;
            }
        }
        if (!inserted) accounts.insert(accounts.end(), sum);
    }
    return accounts.front();
}

int main()
{
  std::string first, second;
  getline (std::cin, first);
  getline (std::cin, second);
  std::list<double> numbers = parse(first);
  std::list<double> accounts = parse(second);

  // let's do the easiest read. Maybe getline is the fastest
//   for (std::list<int>::iterator it = accounts.begin(); it != accounts.end(); it++)
//     std::cout << *it << ' ';

  printf("%.2f\n", percent(accounts, numbers.back()));
}