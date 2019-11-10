package cn.scut.zhihu.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import sun.text.normalizer.Trie;

import javax.security.auth.callback.CallbackHandler;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * User: yinkai
 * Date: 2019-11-10
 * Time: 10:45
 */
@Service
public class SensitiveService implements InitializingBean {
    public static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);


    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String linetext;

            while ((linetext = bufferedReader.readLine()) != null) {
                addWord(linetext);
            }

            inputStreamReader.close();
        } catch (Exception e) {
            logger.error("读取敏感词文件失败！" + e.getMessage());
        }
    }

    /**
     * 在字典树中增加关键词
     *
     * @param lineText
     */
    private void addWord(String lineText) {
        TrieNode trieNode = root;
        for (int i = 0; i < lineText.length(); i++) {
            Character c = lineText.charAt(i);
            TrieNode node = root.getSubNode(c);

            if (node == null) {
                node = new TrieNode();
                trieNode.addSubNode(c, node);
            }
            trieNode = node;
        }

        trieNode.setKeyWordEnd(true);
    }


    private class TrieNode {
        private boolean end = false; // 是否可以结束

        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public void addSubNode(Character key, TrieNode trieNode) {
            subNodes.put(key, trieNode);
        }

        public TrieNode getSubNode(Character key) {
            return subNodes.get(key);
        }


        public boolean isKeyWordEnd() {
            return end;
        }


        public void setKeyWordEnd(boolean end) {
            this.end = end;
        }
    }


    private TrieNode root = new TrieNode();


    private boolean isSymbol(char c) {
        int ic = (int) c;

        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }


    public String filter(String text) {

        if (StringUtils.isBlank(text)) {
            return text;
        }


        String replacement = "***";
        TrieNode trieNode = root;
        int begin = 0;
        int position = 0;

        StringBuilder res = new StringBuilder();

        while (position < text.length()) {
            char c = text.charAt(position);

            // 开始的特殊字符忽略
            if (isSymbol(c)) {
                if (trieNode == root) {
                    res.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }
            trieNode = trieNode.getSubNode(c);

            // 没匹配到, 从下一个位置继续
            if (trieNode == null) {
                res.append(text.charAt(position));
                position = begin + 1;
                begin = position;
                trieNode = root;
            } else if (trieNode.isKeyWordEnd()) { // 匹配到了
                res.append(replacement);
                position++;
                begin = position;
                trieNode = root;
            } else {
                position++;
            }

        }

        res.append(text.substring(begin));
        return res.toString();

    }


    public static void main(String[] args) {
        SensitiveService sensitiveService = new SensitiveService();
        sensitiveService.addWord("赌博");
        sensitiveService.addWord("色情");
        System.out.println(sensitiveService.filter("你好色情"));
    }

}
