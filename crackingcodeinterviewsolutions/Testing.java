public class Permutations {
  public static void main(String args[]) {
    Testing t = new Testing();
    boolean result = t.permutations("god11", "dog22");

    if (result) {
      System.out.println("Are permutations");
    } else {
      System.out.println("NOT permutations");
    }
  }

  private boolean permutations(String s, String t) {
    if (s.length() != t.length()) {
      return false;
    }

    int[] letters = new int[256];

    char[] s_array = s.toCharArray();
    for (char c : s_array) {
      letters[c]++;
    }

    for (int i = 0; i < t.length(); i++) {
      int c = (int) t.charAt(i);
      if (--letters[c] < 0) {
        return false;
      }
    }

    return true;
  }
}