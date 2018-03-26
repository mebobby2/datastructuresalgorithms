public class ReplaceSpaces {
  public static void main(String args[]) {
    String input = "my name is bobby lei";
    System.out.println(input);

    char[] charArray = new char[200];
    char[] inputArray = input.toCharArray();
    for (int i = 0; i < inputArray.length; i++) {
      charArray[i] = inputArray[i];
    }
    System.out.println(replaceSpaces(charArray, input.length()));
  }

  public static String replaceSpaces(char[] str, int length) {
    System.out.println("length is "+length);
    int spaceCount = 0, newLength, i;

    for (i = 0; i < str.length; i++ ) {
      if (str[i] == ' ')
        spaceCount++;
    }

    System.out.println("spaceCount is "+spaceCount);

    newLength = length + spaceCount * 2;
    System.out.println("newLength is "+newLength);
    str[newLength] = '\0';
    for (i = length - 1; i >= 0; i-- ) {
      if (str[i] == ' ') {
        str[newLength-1] = '0';
        str[newLength-2] = '2';
        str[newLength-3] = '%';
        newLength = newLength - 3;
      } else {
        str[newLength-1] = str[i];
        newLength = newLength - 1;
      }
    }
    return String.valueOf(str);
  }
}