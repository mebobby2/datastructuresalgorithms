#include <iostream>

class Reverser {
  public:
    void reverse(char *str) {
      char *end = str;
      char tmp;
      if (str) {
        while(*end) {
          ++end;
        }
        --end;

        while (str < end) {
          tmp = *str;
          *str++ = *end;
          *end-- = tmp;
        }
      }
    }
};

int main() {
  char myword[] = "hello";
  std::cout << myword;

  Reverser r;
  r.reverse(myword);

  
  printf("Reversed string is %s\n", myword); 

  return 0;
}

