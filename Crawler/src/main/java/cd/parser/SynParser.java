package cd.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cd.model.ParseData;
import cd.proto.RuleProto.Rule;
import cd.proto.RuleProto.PageRule;
import cd.rule.RuleManager;

/**
 * Created by chendong on 16/4/29.
 */
@Component
public class SynParser {

    @Autowired
    private RuleManager ruleManager;

    public SynParser() {
        ruleManager = new RuleManager();
        try {
            ruleManager.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ParseData parseHtml(String page, String url) {
        PageRule pageRule = ruleManager.getPageRule(url);
        Document doc = Jsoup.parse(page);
        return parseDocument(doc.getAllElements(), pageRule.getRuleList());
    }

    public ParseData parseDocument(Elements elements, List<Rule> rules) {
        ParseData parseData = new ParseData();
        for (Rule rule: rules) {
            parseData.addEntry(parseDocument(elements, rule));
        }
        return parseData;
    }
    public ParseData.Entry parseDocument(Elements document, Rule rule) {
        String name = rule.getDataName();
        Object value = null;
        ParseData.EntryType entryType = ParseData.EntryType.DATA;

        Elements elements = document.select(rule.getSelector());
        switch (rule.getExtractorType()) {
            case Text:
                value = extractText(elements);
                entryType = ParseData.EntryType.DATA;
                break;
            case Attribute:
                value = extractAttribute(elements, rule.getAttribute());
                entryType = ParseData.EntryType.DATA;
                break;
            case SubRules:
                value = extractSubDatas(elements, rule.getSubRuleList());
                entryType = ParseData.EntryType.SUBENTRY;
                break;
        }
        return new ParseData.Entry(name, value, entryType);
    }

    private Object extractText(Elements elements) {
        if (elements == null || elements.size() == 0) {
            return null;
        }
        return elements.get(0).text();
    }

    private Object extractAttribute(Elements elements, String attribute) {
        if (elements == null || elements.size() == 0) {
            return null;
        }
        return elements.get(0).attr(attribute);
    }

    private Object extractSubDatas(Elements elements, List<Rule> subRules) {
        if (elements == null || elements.size() == 0) {
            return null;
        }
        List<ParseData> subDatas = new ArrayList<>();
        for (int i = 0; i < elements.size() && i < 20; ++i) {
            Element element = elements.get(i);
            ParseData subData = parseDocument(element.getAllElements(), subRules);
            subDatas.add(subData);
        }
        return subDatas;
    }
}
