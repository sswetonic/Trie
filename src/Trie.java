import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Trie {
    private Tree trie = new Tree();

    public void printTrie() {
        trie.print();
    }

    public void addWords(List<String> words) {
        for (String word: words) {
            insert(word);
        }
    }

    public void insert(String word) {
        EntryNode current = trie.getRoot();
        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);

            EntryNode child = current.getChild(character);
            if (child == null) {
                child = new EntryNode(character, i == word.length() - 1);
                current.addChild(child);
            }

            current = child;
        }
        current.setTerminal(true);
    }

    public void delete(String word) {
        delete(trie.getRoot(), word, 0);
    }

    private boolean delete(EntryNode current, String word, int index) {
        if (index == word.length()) {
            if (!current.isTerminal()) {
                return false;
            }
            current.setTerminal(false);
            return current.childrenSize() == 0;
        }
        char ch = word.charAt(index);
        EntryNode node = current.getChild(ch);
        if (node == null) {
            return false;
        }
        boolean shouldDeleteCurrentNode = delete(node, word, index + 1);
        if (shouldDeleteCurrentNode) {
            current.getChild(ch).remove(ch);
            return current.childrenSize() == 0;
        }
        return false;
    }

    public boolean contains(String potentialWord) {
        EntryNode current = trie.getRoot();
        for (int i = 0; i < potentialWord.length(); i++) {
            char character = potentialWord.charAt(i);

            EntryNode child = current.getChild(character);
            if (child == null) {
                return false;
            }
            current = child;
        }

        return current.isTerminal();
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("src/dict.txt"));
        List<String> words = new ArrayList<>();

        while (scan.hasNext()) {
            words.addAll(Arrays.asList(scan.nextLine().split(" ")));
        }

        Trie trie = new Trie();
        trie.addWords(words);
        trie.printTrie();

        //Shore is correctly added after editing insert() and the delete function works
        trie.delete("shore");

        System.out.println();
        System.out.println("This test should report false:");
        System.out.println("Contains 's': " + trie.contains("s"));
        System.out.println("Contains 'bye': " + trie.contains("bye"));
        System.out.println("Contains 'bird': " + trie.contains("bird"));

        System.out.println();
        System.out.println("These tests should report true:");
        System.out.println("Contains 'she': " + trie.contains("she"));
        System.out.println("Contains 'sells': " + trie.contains("sells"));
        System.out.println("Contains 'sea': " + trie.contains("sea"));
        System.out.println("Contains 'shells': " + trie.contains("shells"));
        System.out.println("Contains 'by': " + trie.contains("by"));
        System.out.println("Contains 'the': " + trie.contains("the"));
        System.out.println("Contains 'shore': " + trie.contains("shore"));
        System.out.println("Contains 'shorebird': " + trie.contains("shorebird"));
    }
}