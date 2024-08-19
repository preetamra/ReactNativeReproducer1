package com.blockerplus.blockerplus.Utils;

import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        try{
            TrieNode node = root;
            for (char ch : word.toCharArray()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    node = node.getChildren().computeIfAbsent(ch, c -> new TrieNode());
                }
            }
            node.setEndOfWord(true);
        }catch(Exception e) {

        }
    }

    public boolean search(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node = node.getChildren().get(ch);
            if (node == null) {
                return false;
            }
        }
        return node.isEndOfWord();
    }

    private void getAllWordsFromNode(TrieNode node, String currentWord, List<String> words) {
        if (node.isEndOfWord()) {
            words.add(currentWord);
        }

        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            char ch = entry.getKey();
            TrieNode childNode = entry.getValue();
            getAllWordsFromNode(childNode, currentWord + ch, words);
        }
    }


    private static class TrieNode {
        private final Map<Character, TrieNode> children = new HashMap<>();
        private boolean endOfWord;

        public Map<Character, TrieNode> getChildren() {
            return children;
        }

        public boolean isEndOfWord() {
            return endOfWord;
        }

        public void setEndOfWord(boolean endOfWord) {
            this.endOfWord = endOfWord;
        }
    }
}

