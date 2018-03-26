public class UniqueChars {
  public static void main(String args[]) {
    UniqueChars uc = new UniqueChars();

    if (uc.isUniqueChars("abcdefga")) {
      System.out.println("UNIQUE");
    } else {
      System.out.println("NOT UNIQUE");
    }
  }

  public boolean isUniqueChars(String str) {
    int checker = 0;
    for (int i = 0; i < str.length(); i++) {
      int val = str.charAt(i) - 'a';

      System.out.println("val is "+val);
      int val2 = checker & (1 << val);
      System.out.println("val again is "+val2);

      if ((checker & (1 << val)) > 0) {
        return false;
      }
      checker |= (1 << val);
      System.out.println("checker is "+checker);
    }
    return true;
  }
}